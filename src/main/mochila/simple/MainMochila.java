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
package main.mochila.simple;

import main.generico.funciones.Ackley;
import main.generico.funciones.Esfera;
import metaheuristicas.Funcion;
import main.generico.funciones.Piso;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.Ejecutor;
import main.Utilidades;
import main.mochila.multidimensional.UtilMultid;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Individuo;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EvolucionDiferencial;
import metaheuristicas.poblacion.EvolucionDiferencialBinaria;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaper;
import metaheuristicas.simple.Hill_Climbing;

/**
 *
 * @author debian
 */
public class MainMochila {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int tamPoblacion;
        int iteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
//        graficaDispercion2D = true;
        // rango maximo de cambio en el tweak
        paso = 1;
        // limite de las funciones
        limite = 0;
        // dimension de los puntos
        tamPoblacion = 0;
        // iteraciones realizadas por los algoritmos
        iteraciones = 50;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;

        boolean maximizar = true;

        List listaPuntos = UtilMultid.obtenerDatosMochila("mochila/Knapsack4.txt");
        // dimension de los puntos;
        limite = (double) listaPuntos.remove(0);
        tamPoblacion = listaPuntos.size();
        System.out.println("dimension: " + tamPoblacion);

        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
        listaAlgoritmos.add(new EvolucionDiferencialBinaria(tamPoblacion));

        List<Funcion> listaFunciones = new ArrayList();
//        listaFunciones.add(new Esfera(limite, dimension));
//        listaFunciones.add(new Mochila(63, new double[][]{{12,23}, {15,24}, {13,43}, {23,20}, {24,35}, {13,65}, {21,41}, {13,37}, {12,22}, {6,3}}));
        MochilaSimple funcionMochila = new MochilaSimple(limite, listaPuntos, maximizar);
        listaFunciones.add(funcionMochila);

        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones);
        Individuo mejor = funcionMochila.getMejor();
        System.out.println("Peso: " + funcionMochila.obtenerPeso(mejor));
        System.out.println("Precio: " + funcionMochila.obtenerPrecio(mejor));

    }
}
