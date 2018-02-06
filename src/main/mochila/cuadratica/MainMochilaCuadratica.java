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

import metaheuristicas.Funcion;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.Ejecutor;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaHyperplaneExploration;
import main.mochila.cuadratica.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EvolucionDiferencial;
import metaheuristicas.poblacion.EvolucionDiferencialBinaria;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaper;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaperMejorado;
import metaheuristicas.poblacion.Grasp;
import metaheuristicas.simple.Hill_Climbing;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double limite;
        int tamPoblacion;
        int iteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        // limite de las funciones
        limite = 20;
        // numero de individuos porpoblacion
        tamPoblacion = 20;// 20 ó 50 resultan buenos
        // iteraciones realizadas por los algoritmos
        iteraciones = 400;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;

//        List listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_200_100_1.txt");
//        List listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_300_25_10.txt");
//        List listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_300_50_1.txt");
//        List listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_100_25_2.txt");
//        List listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_100_25_8.txt");
        List listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/r_10_100_13.txt");
        // dimension de los puntos;
        String nombreInstancia = (String) listaParametros.remove(0);
        double[][] matrizBeneficios = (double[][]) listaParametros.remove(0);
        double capacidad = (double) listaParametros.remove(0);
        double[] vectorPesos = (double[]) listaParametros.remove(0);
        Double maxGlobal = Double.NaN;
        try {
            maxGlobal = (double) listaParametros.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {
            System.out.println("-");
        }
        // tamPoblacion = listaParametros.size();
        System.out.println("nombre instancia: " + nombreInstancia);
        System.out.println("dimension: " + tamPoblacion);
        System.out.println("capacidad: " + capacidad);
        Funcion FuncionMochilaCuadraticaGreedy = new FuncionMochilaCuadraticaGreedy(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        FuncionMochilaHyperplaneExploration funcionHyperplanos = new FuncionMochilaHyperplaneExploration(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
//        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialBinariaPaper(tamPoblacion));
//        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialBinariaPaperMejorado(tamPoblacion));
        listaAlgoritmos.add(new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos));
//        listaAlgoritmos.add(new Grasp(10, (FuncionMochilaGreedy) funcionGreedy));

        List<Funcion> listaFunciones = new ArrayList();
        listaFunciones.add(new FuncionMochilaCuadratica(matrizBeneficios, capacidad, vectorPesos, maxGlobal));
        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones);
    }

}
