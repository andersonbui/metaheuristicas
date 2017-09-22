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
package pruebas;

import funciones.ErrorCuadratico;
import funciones.Funcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.AproximacionPG2;
import metaheuristicas.Punto;
import static pruebas.Utilidades.ejecutarAlgoritmosMasFunciones;
import tweaks.Tweak_1;
import tweaks.Tweak_Explotacion;

/**
 *
 * @author debian
 */
public class Proyecto1_regresionPG2 {

    public static void main(String[] args) {
        double paso;
        double limite;
        int iteraciones;
        int dimension;
        int numMuestras;
        int numDatos;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para 2 dimensiones + calidad 
        boolean graficaDispercion2D = true; // true para graficas de dispersion con gnuplot

//        Scanner scan = new Scanner(new File("parametros.txt"));
        // lectura de parametros desde cadena
//        Scanner scan = new Scanner("0,02\n10\n1000\n50000\n1\n");
        // rango maximo de cambio en el tweak
        paso = 0.1;
        // limite de las funciones
        limite = 10;
        // limite de las funciones
        numDatos = 1000;
        // iteraciones realizadas por los algoritmos
        iteraciones = 50000;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 2;

        List<Punto> listaPuntos = Utilidades.obtenerDatosRegresion(numDatos, "datos-regresion.txt");
        // dimension de los puntos
        dimension = listaPuntos.get(0).getValores().length;
        dimension = (dimension * (dimension + 1)) / 2;
        System.out.println("dimension: " + dimension);
        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();

        listaAlgoritmos.add(
                new AproximacionPG2(new Tweak_Explotacion()));
//        listaAlgoritmos.add(new Hill_Climbing(paso));
//        listaAlgoritmos.add(new Hill_Climbing_Random_Restarts(10, paso));
//        listaAlgoritmos.add(new Random_Search());
//        listaAlgoritmos.add(new Hill_Climbing(paso, 10,false));
//        listaAlgoritmos.add(new Hill_Climbing(paso, 10,true));
//        listaAlgoritmos.add(new Hill_Climbing_direccional(paso));
//        listaAlgoritmos.add(new Simulated_Annealing(paso));
//        listaAlgoritmos.add(new B_Hill_Climbing(paso));

        List<Funcion> listaFunciones = new ArrayList();

        listaFunciones.add(
                new ErrorCuadratico(limite, dimension, listaPuntos));

        // EJECUTAR ANALISIS
        ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones, paso);
    }

}
