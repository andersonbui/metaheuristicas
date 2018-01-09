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
package main.mochila;

import java.util.ArrayList;
import java.util.List;
import main.LeerArchivo;
import static main.Utilidades.eliminarEspaciosRepetidos;

/**
 *
 * @author debian
 */
public class Util {
    
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
        int numVar = -1;
        List listaObj = new ArrayList();
        int j;
        LeerArchivo.terminar();
        for (int i = 0; i < listaCadenas.size(); i++) {

            String cadena = listaCadenas.get(i).replace(',', '.');

            cadena = eliminarEspaciosRepetidos(cadena);

            String[] vectSubdivisiones = cadena.split("" + '\u0020');
            if (numVar < 0) {
                numVar = vectSubdivisiones.length;
            }
            double[] valoresPuntoActual = new double[vectSubdivisiones.length];
            for (j = 0; j < valoresPuntoActual.length; j++) {
//                System.out.print("[" + valoresPuntoActual.length + "]<" + vectSubdivisiones[posicionSubdivisiones] + ">");
                valoresPuntoActual[j] = Double.parseDouble(vectSubdivisiones[j].trim());
            }
            listaObj.add(valoresPuntoActual);
            if (vectSubdivisiones.length != numVar) {
                break;
            }
//            System.out.println("<" + puntoActual.toString() + ">");
        }
        return listaObj;
    }
}
