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
package main.mochila.multidimensional;

import java.util.ArrayList;
import java.util.List;
import main.LeerArchivo;
import static main.Utilidades.eliminarEspaciosRepetidos;

/**
 *
 * @author debian
 */
public class UtilMultid {

    static public List obtenerDatosMochila(String nombreArchivo) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer();
        List listaObj = new ArrayList();
        int j;
        LeerArchivo.terminar();
        for (int i = 0; i < listaCadenas.size(); i++) {
            int posicionSubdivisiones = 0;
            String cadena = listaCadenas.get(i).replace(',', '.');

            cadena = eliminarEspaciosRepetidos(cadena);

            String[] vectSubdivisiones = cadena.split("" + '\u0020');
            double[] valoresPuntoActual = new double[vectSubdivisiones.length];
            for (j = 0; j < valoresPuntoActual.length; j++) {
//                System.out.print("[" + valoresPuntoActual.length + "]<" + vectSubdivisiones[posicionSubdivisiones] + ">");
                valoresPuntoActual[j] = Double.parseDouble(vectSubdivisiones[j].trim());
            }
            if (vectSubdivisiones.length == 1) {
                listaObj.add(0, Double.parseDouble(vectSubdivisiones[0]));
                break;
            }
            listaObj.add(valoresPuntoActual);
//            System.out.println("<" + puntoActual.toString() + ">");
        }
        return listaObj;
    }

    static public List obtenerDatosMochilaMultidimensional(String nombreArchivo) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer();
        LeerArchivo.terminar();
        List listaObj = new ArrayList();
        String separador = "" + '\u0020';

        // nombre de la instancia
        String nombreInstancia = listaCadenas.remove(0);
        listaObj.add(nombreInstancia);
        // numVar: numero de mochilas
        int numMochilas = Integer.parseInt(listaCadenas.remove(0).trim());
        // numVar: numero de elementos para las mochila
        int numElementos = Integer.parseInt(listaCadenas.remove(0).trim());

        double[][] VectorPesosMochilasElementos = new double[numMochilas][numElementos];
        double[] vectorBeneficio = new double[numElementos];
        double[] capacidades;
        double[] vectSubdivisiones;
        int j;
        for (int i = 0; i < numElementos; i++) {
            vectSubdivisiones = separarCadena_en_vectDouble(listaCadenas.remove(0), separador);
            for (j = 0; j < numMochilas; j++) {
                VectorPesosMochilasElementos[j][i] = vectSubdivisiones[j];
            }
            vectorBeneficio[i] = vectSubdivisiones[j];
        }
        capacidades = separarCadena_en_vectDouble(listaCadenas.remove(0), "" + separador);

        listaObj.add(VectorPesosMochilasElementos);
        listaObj.add(vectorBeneficio);
        listaObj.add(capacidades);
        return listaObj;
    }

    /**
     *
     * @param cadena
     * @param separador diferente de "."
     * @return
     */
    static public double[] separarCadena_en_vectDouble(String cadena, String separador) {
        cadena = cadena.replace(',', '.');
        cadena = eliminarEspaciosRepetidos(cadena);
        String[] vectSubdivisiones = cadena.split(separador);
        double[] vecDouble = new double[vectSubdivisiones.length];
        int i = 0;
        for (String cad : vectSubdivisiones) {
            vecDouble[i++] = Double.parseDouble(cad.trim());
        }
        return vecDouble;
    }
}
