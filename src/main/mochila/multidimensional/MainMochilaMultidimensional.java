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
package main.mochila.multidimensional;

import main.mochila.multidimensional.funciones.MochilaMultidimensional_LimitRelleno;
import main.mochila.multidimensional.funciones.MochilasMultidimensionales;
import metaheuristicas.Funcion;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.Ejecutor;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EvolucionDiferencial;
import metaheuristicas.poblacion.EvolucionDiferencialBinaria;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaper;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaperMejorado;
import main.mochila.multidimensional.funciones.FuncionMochilaGreedy;
import metaheuristicas.poblacion.Grasp;
import metaheuristicas.simple.Hill_Climbing;

/**
 *
 * @author debian
 */
public class MainMochilaMultidimensional {

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
        limite = 10;
        // numero de individuos porpoblacion
        tamPoblacion = 5;
        // iteraciones realizadas por los algoritmos
        iteraciones = 20;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;

        boolean maximizar = true;

        String nombreArchivo;

//        nombreArchivo = "mochilaMultidimencional/f2-1000-2.txt";
        nombreArchivo = "mochilaMultidimencional/f2-1000.txt";
//        nombreArchivo = "mochilaMultidimencional/f2-11.txt";
//        nombreArchivo = "mochilaMultidimencional/f2-20000.txt";
//        nombreArchivo = "mochilaMultidimencional/f3-100.txt";
//        nombreArchivo = "mochilaMultidimencional/f4-22.txt";

        List listaParametros = UtilMultid.obtenerDatosMochilaMultidimensional(nombreArchivo);
        // dimension de los puntos;
        String nombreInstancia = (String) listaParametros.remove(0);
        double[][] VectorPesosMochilasElementos = (double[][]) listaParametros.remove(0);
        double[] beneficios = (double[]) listaParametros.remove(0);
        double[] capacidades = (double[]) listaParametros.remove(0);
        // tamPoblacion = listaParametros.size();
        System.out.println("dimension: " + tamPoblacion);
        System.out.println("capacidades: " + Arrays.toString(capacidades));
        System.out.println("nombre: " + nombreInstancia);
        Funcion funcionGreedy = new FuncionMochilaGreedy(VectorPesosMochilasElementos, beneficios, capacidades);
        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialBinariaPaper(tamPoblacion));
        listaAlgoritmos.add(new EstrategiaEvolucionDiferencialBinariaPaperMejorado(tamPoblacion));
//        listaAlgoritmos.add(new Grasp(10, (FuncionMochilaGreedy) funcionGreedy));

        List<Funcion> listaFunciones = new ArrayList();
        MochilasMultidimensionales funcionMochila = new MochilasMultidimensionales(VectorPesosMochilasElementos, beneficios, capacidades);
        listaFunciones.add(funcionMochila);
//        listaFunciones.add(funcionGreedy);
        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones);

    }

}
