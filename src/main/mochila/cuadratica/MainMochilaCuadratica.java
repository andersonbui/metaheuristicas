/*
 * Copyright (C) 2017 debian
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

import java.io.FileNotFoundException;
import java.util.List;
import main.Ejecutor;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        int maxIteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = true; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        // numero de individuos porpoblacion
        // iteraciones realizadas por los algoritmos
        maxIteraciones = 20;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 20;
        String nombreArchivo = "";
        //lim,rango,prob_ceros,poblacion, iteraciones
        //lim,20,0.90,20
//        listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/5000_25_1.txt");
//        listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/1000_25_1.dat");

//        nombreArchivo = "mochilaCuadratica/jeu_200_75_6.txt";
//        //lim,15,0.99,20
//        nombreArchivo = "mochilaCuadratica/jeu_300_25_10.txt";
//        //si-no,15,0.99,20
//        nombreArchivo = "mochilaCuadratica/jeu_300_50_2.txt";
//        //lim,15,0.99,10,14
//        nombreArchivo = "mochilaCuadratica/jeu_100_25_2.txt";
//        //no,15,0.99,20,32
//        //si,15,0.90-93,20,31
//        nombreArchivo = "mochilaCuadratica/jeu_100_25_1.txt";
        //lim,15,0.99,5->,1
        nombreArchivo = "mochilaCuadratica/r_10_100_13.txt";

        // dimension de los puntos;
        LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
        ParametrosCuadratica paramC = pc.obtenerParametros(nombreArchivo);
        GrupoAlgoritmosMochilaCuadratica grupo = new GrupoAlgoritmosMochilaCuadratica(paramC, maxIteraciones);
        grupo.inicializar();

        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(grupo, graficaRecorrido3D, graficaDispercion2D, numMuestras);
    }

}
