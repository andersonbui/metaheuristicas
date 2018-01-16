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
package main.mochila.cuadratica;

import java.util.ArrayList;
import java.util.List;
import main.LeerArchivo;
import static main.Utilidades.eliminarEspaciosRepetidos;

/**
 *
 * @author debian
 */
public class UtilCuadratica {

    static public List obtenerDatosMochilaCuadratica(String nombreArchivo) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer();
        LeerArchivo.terminar();
        List listaObj = new ArrayList();

        // nombre de la instancia
        String nombreInstancia = listaCadenas.remove(0);
        listaObj.add(nombreInstancia);
        // numVar: numero de elementos para la mochila
        int numVar = Integer.parseInt(listaCadenas.remove(0).trim());
        double[][] matrizBeneficios = new double[numVar][numVar];
        double[] vectorPesos = new double[numVar];
        double capacidad;
        String cadena;
        String[] vectSubdivisiones;

        for (int i = 0; i < numVar; i++) {
            cadena = listaCadenas.remove(0).replace(',', '.');
            cadena = eliminarEspaciosRepetidos(cadena);
            vectSubdivisiones = cadena.split("" + '\u0020');
            for (int k = i, j = 0; k < numVar; j++, k++) {
                if (i == 0) {
                    matrizBeneficios[k][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                } else {
                    matrizBeneficios[i - 1][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                }
            }
        }
        listaObj.add(matrizBeneficios);
        String optimo_cad = listaCadenas.remove(0); // espacio vacio o maximo ideal

        listaCadenas.remove(0); // 0 ó 1
        capacidad = Double.parseDouble(listaCadenas.remove(0).trim()); // capacidad de la mochila
        listaObj.add(capacidad);

        cadena = listaCadenas.remove(0).replace(',', '.');
        cadena = eliminarEspaciosRepetidos(cadena);
        vectSubdivisiones = cadena.split("" + '\u0020');
        for (int k = 0; k < numVar; k++) {
            vectorPesos[k] = Double.parseDouble(vectSubdivisiones[k].trim());
        }
        listaObj.add(vectorPesos);

        try {
            double optimo = Double.parseDouble(optimo_cad); // espacio vacio o maximo ideal
            listaObj.add(optimo);
        } catch (NumberFormatException e) {

        }
        return listaObj;
    }
}
