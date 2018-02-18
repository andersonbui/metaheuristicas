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
import main.mochila.cuadratica.anson.EstrategiaEvolucionDiferencialConGreedy;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy_MM;
import main.mochila.cuadratica.graspBasadoMemoria.FuncionGraspTabuR;
import main.mochila.cuadratica.graspBasadoMemoria.GraspFundamental;
import main.mochila.cuadratica.graspBasadoMemoria.GraspReinicio;
import main.mochila.cuadratica.graspBasadoMemoria.GraspTabuReinicio;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaHyperplaneExploration;
import main.mochila.cuadratica.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EvolucionDiferencial;
import metaheuristicas.poblacion.EvolucionDiferencialBinaria;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaper;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaperMejorado;
import metaheuristicas.simple.Hill_Climbing;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        int tamPoblacion;
        int maxIteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        // numero de individuos porpoblacion
        tamPoblacion = 20;// 20 ó 50 resultan buenos
        // iteraciones realizadas por los algoritmos
        maxIteraciones = 50;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;
        List listaParametros;
        //lim,rango,prob_ceros,poblacion, iteraciones
        //lim,20,0.90,20
//        listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/5000_25_1.txt");
//        listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/1000_25_1.dat");

//         listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_200_75_6.txt");
//        lim,15,0.99,20
//         listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_300_25_10.txt");
        //si-no,15,0.99,20
         listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_300_50_2.txt");
        //lim,15,0.99,10,14
//         listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_100_25_2.txt");
        //no,15,0.99,20,32
        //si,15,0.90-93,20,31
//         listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/jeu_100_25_1.txt");
        //lim,15,0.99,5->,1
//         listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/r_10_100_13.txt");
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
        System.out.println("dimension: " + vectorPesos.length);
        System.out.println("capacidad: " + capacidad);
        System.out.println("max iteraciones: " + maxIteraciones);
        System.out.println("numero de pruebas: " + numMuestras);

        Funcion funcionGreedy = new FuncionGraspTabuR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        FuncionMochilaHyperplaneExploration funcionHyperplanos = new FuncionMochilaHyperplaneExploration(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        FuncionMochilaCuadraticaGreedy funcionEDG = new FuncionMochilaCuadraticaGreedy(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        FuncionMochilaCuadraticaGreedy funcionEDG_2 = new FuncionMochilaCuadraticaGreedy_MM(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
//        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialBinariaPaper(tamPoblacion));
//        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialBinariaPaperMejorado(tamPoblacion));
//        listaAlgoritmos.add(new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos));
//        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialConGreedy(tamPoblacion, funcionEDG_2));
        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialConGreedy(tamPoblacion, funcionEDG));
//        listaAlgoritmos.add(new GraspReinicio((FuncionGreedy) funcionGreedy, maxIteraciones, 50, 5, 4));
//        listaAlgoritmos.add(new GraspFundamental((FuncionGreedy) funcionGreedy, maxIteraciones, 50, 5, 4));
        listaAlgoritmos.add(new GraspTabuReinicio((FuncionGraspTabuR) funcionGreedy, 50, 5, 4, 10, 20));

        List<Funcion> listaFunciones = new ArrayList();
        listaFunciones.add(new FuncionMochilaCuadratica(matrizBeneficios, capacidad, vectorPesos, maxGlobal));
        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, maxIteraciones);
    }

}
