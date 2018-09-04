/*
 * Copyright (C) 2018 debian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.mochila.cuadratica.utilidades;

import main.mochila.cuadratica.utilidades.ParametrosCuadratica;
import java.util.List;
import java.util.ListIterator;
import main.utilidades.EscribirArchivo;
import main.utilidades.LeerArchivo;
import static main.utilidades.Utilidades.eliminarEspaciosRepetidos;

/**
 *
 * @author debian
 */
public class LecturaParametrosCuadratica {

    public LecturaParametrosCuadratica() {

    }

    public void actualizar(String nombreArchivo, ParametrosCuadratica parametros) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer();
        LeerArchivo.terminar();
        EscribirArchivo archivo = new EscribirArchivo();
        listaCadenas.set(parametros.getPosicionMaxGlobal(), "" + parametros.getMaxGlobal());
        listaCadenas.set(parametros.getPosicionIdeal(), vectorAString(parametros.getVectorIdeal()));
        archivo.abrir(nombreArchivo);
        archivo.escribir(listaCadenas);
        archivo.terminar();
    }

    public String vectorAString(int[] vector) {
        StringBuilder cadena = new StringBuilder();
        for (int i = 0; i < vector.length; i++) {
            cadena.append(vector[i]).append(" ");
        }
        return cadena.toString();
    }

    public ParametrosCuadratica obtenerParametros(String nombreArchivo) {
//        String nombreInstancia = (String) listaParametros.remove(0);
        double[][] matrizBeneficios;
        int[] vectorideal = null;
        Double maxGlobal = null;
        double capacidad;
        String cadena;
        String[] vectSubdivisiones;
        double[] vectorPesos;
        int numElementos;
        ParametrosCuadratica pc = new ParametrosCuadratica();

        if (!LeerArchivo.abrir(nombreArchivo)) {
            return null;
        }
        List<String> listaCadenas = LeerArchivo.leer();
        LeerArchivo.terminar();
//        List listaResultado = new ArrayList();

        ListIterator<String> iterador = listaCadenas.listIterator();
        // nombre de la instancia
        String nombreInstancia = iterador.next();
        pc.setNombreInstancia(nombreInstancia);

        // obtener numero de elementos para la mochila
        numElementos = Integer.parseInt(iterador.next().trim());
        matrizBeneficios = new double[numElementos][numElementos];
        vectorPesos = new double[numElementos];

        // obtener matriz de beneficios
        for (int i = 0; i < numElementos; i++) {
            cadena = iterador.next().replace(',', '.');
            cadena = eliminarEspaciosRepetidos(cadena);
            vectSubdivisiones = cadena.split("" + '\u0020');
            for (int k = i, j = 0; k < numElementos; j++, k++) {
                if (i == 0) {
                    matrizBeneficios[k][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                } else {
                    matrizBeneficios[i - 1][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                }
            }
        }
        pc.setMatrizBeneficios(matrizBeneficios);

        // obtener maximo global - optimo
        pc.setPosicionMaxGlobal(iterador.nextIndex());
        String optimo_cad = iterador.next(); // espacio vacio o maximo ideal
        try {
            maxGlobal = Double.parseDouble(optimo_cad); // espacio vacio o maximo ideal
        } catch (NumberFormatException e) {
        }
        pc.setMaxGlobal(maxGlobal);

        // 0 ó 1
        iterador.next();
        capacidad = Double.parseDouble(iterador.next().trim()); // capacidad de la mochila
        pc.setCapacidad(capacidad);

        // lectura de vector pesos
        cadena = iterador.next().replace(',', '.');
        cadena = eliminarEspaciosRepetidos(cadena);
        vectSubdivisiones = cadena.split("" + '\u0020');
        for (int k = 0; k < numElementos; k++) {
            vectorPesos[k] = Double.parseDouble(vectSubdivisiones[k].trim());
        }
        pc.setVectorPesos(vectorPesos);

        // lectura de vector individuo maximo ideal conocido
        pc.setPosicionIdeal(iterador.nextIndex());
        cadena = iterador.next().trim();
        cadena = eliminarEspaciosRepetidos(cadena);
        vectSubdivisiones = cadena.split("" + '\u0020');
        if (vectSubdivisiones.length == numElementos) {
            vectorideal = new int[numElementos];
            for (int k = 0; k < numElementos; k++) {
                vectorideal[k] = Integer.parseInt(vectSubdivisiones[k].trim());
            }
        }
        pc.setVectorIdeal(vectorideal);

        return pc;
    }

}
