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

import main.mochila.cuadratica.ConjuntoInstancias.Instancia;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.utilidades.EscribirArchivo;
import main.utilidades.LeerArchivo;

/**
 *
 * @author debian
 */
public class LecturaParametrosCuadratica {

    public LecturaParametrosCuadratica() {

    }

    public void actualizar(String nombreArchivo, ParametrosInstancia parametros) {

        ArchivoInstancia archivoInstancia = leerParametros(nombreArchivo);
        archivoInstancia.setMaximoGlobal(parametros.getMaxGlobal());
        archivoInstancia.setInstanciaMaxG(parametros.getVectorIdeal());

        EscribirArchivo archivo = new EscribirArchivo();
        archivo.abrir(nombreArchivo);
        archivo.escribir(archivoInstancia.getLineas());
        archivo.terminar();
    }

    public String vectorAString(int[] vector) {
        StringBuilder cadena = new StringBuilder();
        for (int i = 0; i < vector.length; i++) {
            cadena.append(vector[i]).append(" ");
        }
        return cadena.toString();
    }

    public ArchivoInstancia leerParametros(String nombreArchivo) {
        List<String> listaCadenas = null;
        if ("--estandar".equals(nombreArchivo)) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                listaCadenas = new ArrayList<>();
                String cadena;
                while (!"".equals(cadena = br.readLine())) {
                    listaCadenas.add(cadena);
                }
            } catch (IOException ex) {
                Logger.getLogger(LecturaParametrosCuadratica.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            LeerArchivo leer = new LeerArchivo();
            if (!leer.abrir(nombreArchivo)) {
                return null;
            }
            listaCadenas = leer.leer();
            leer.terminar();

        }
        ArchivoInstancia aInstancia = new ArchivoInstancia(listaCadenas);
        return aInstancia;
    }

    public ParametrosInstancia obtenerParametros(Instancia instancia) {
        String nombreArchivo = instancia.getNombreCompleto();
        ArchivoInstancia aInstancia = leerParametros(nombreArchivo);
        if (aInstancia == null) {
            return null;
        }
        ParametrosInstancia pc = new ParametrosInstancia(
                nombreArchivo,
                aInstancia.getNombre(),
                aInstancia.getMatriz(),
                aInstancia.getCapacidad(),
                aInstancia.getPesos(),
                aInstancia.getInstanciaMaxG(),
                aInstancia.getMaximoGlobal()
        );
        return pc;
    }

//    public ParametrosInstancia obtenerParametros2(String nombreArchivo) {
////        String nombreInstancia = (String) listaParametros.remove(0);
//        double[][] matrizBeneficios;
//        int[] vectorideal = null;
//        Double maxGlobal = null;
//        double capacidad;
//        String cadena;
//        String[] vectSubdivisiones;
//        double[] vectorPesos;
//        int numElementos;
//        ParametrosInstancia pc = new ParametrosInstancia();
//        pc.setNombreArchivo(nombreArchivo);
//        List<String> listaCadenas = leerParametros(nombreArchivo).getLineas();
//        if (listaCadenas == null) {
//            return null;
//        }
////        List listaResultado = new ArrayList();
//
//        ListIterator<String> iterador = listaCadenas.listIterator();
//        // nombre de la instancia
//        String nombreInstancia = iterador.next();
//        pc.setNombreInstancia(nombreInstancia);
//
//        // obtener numero de elementos para la mochila
//        numElementos = Integer.parseInt(iterador.next().trim());
//        matrizBeneficios = new double[numElementos][numElementos];
//
//        // obtener matriz de beneficios
//        for (int i = 0; i < numElementos; i++) {
//            cadena = iterador.next().replace(',', '.');
//            cadena = eliminarEspaciosRepetidos(cadena);
//            vectSubdivisiones = cadena.split("" + '\u0020');
//            for (int k = i, j = 0; k < numElementos; j++, k++) {
//                if (i == 0) {
//                    matrizBeneficios[k][k] = Double.parseDouble(vectSubdivisiones[j].trim());
//                } else {
//                    matrizBeneficios[i - 1][k] = Double.parseDouble(vectSubdivisiones[j].trim());
//                }
//            }
//        }
//        pc.setMatrizBeneficios(matrizBeneficios);
//
//        // obtener maximo global - optimo
//        pc.setPosicionMaxGlobal(iterador.nextIndex());
//        String optimo_cad = iterador.next(); // espacio vacio o maximo ideal
//        try {
//            maxGlobal = Double.parseDouble(optimo_cad); // espacio vacio o maximo ideal
//        } catch (NumberFormatException e) {
//        }
//        pc.setMaxGlobal(maxGlobal);
//
//        // 0 ó 1
//        iterador.next();
//        capacidad = Double.parseDouble(iterador.next().trim()); // capacidad de la mochila
//        pc.setCapacidad(capacidad);
//
//        vectorPesos = new double[numElementos];
//        // lectura de vector pesos
//        cadena = iterador.next().replace(',', '.');
//        cadena = eliminarEspaciosRepetidos(cadena);
//        vectSubdivisiones = cadena.split("" + '\u0020');
//        for (int k = 0; k < numElementos; k++) {
//            vectorPesos[k] = Double.parseDouble(vectSubdivisiones[k].trim());
//        }
//        pc.setVectorPesos(vectorPesos);
//
//        // lectura de vector individuo maximo ideal conocido
//        pc.setPosicionIdeal(iterador.nextIndex());
//        cadena = iterador.next().trim();
//        cadena = eliminarEspaciosRepetidos(cadena);
//        vectSubdivisiones = cadena.split("" + '\u0020');
//        if (vectSubdivisiones.length == numElementos) {
//            vectorideal = new int[numElementos];
//            for (int k = 0; k < numElementos; k++) {
//                vectorideal[k] = Integer.parseInt(vectSubdivisiones[k].trim());
//            }
//        }
//        pc.setVectorIdeal(vectorideal);
//
//        return pc;
//    }

}
