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
package main.mochila;

import main.mochila.funciones.MochilaMultidimensionalMejorada;
import main.mochila.funciones.MochilaMultidimensional;
import metaheuristicas.Funcion;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.Ejecutor;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencial;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencialBinaria;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencialBinariaPaper;
import main.mochila.estrategias.EstrategiaEvolucionDiferencialBinariaPaperMejorado;
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
        tamPoblacion = 4;
        // iteraciones realizadas por los algoritmos
        iteraciones = 100;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;

        boolean maximizar = true;

//        List listaPuntos = Util.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f2-1000.txt");
//        List listaPuntos = Util.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f2-11.txt");
//        List listaPuntos = Util.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f2-20000.txt");
//        List listaPuntos = Util.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f3-100.txt");
        List listaPuntos = Util.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f4-22.txt");
        // dimension de los puntos;
        double[] capacidades = (double[]) listaPuntos.remove(listaPuntos.size() - 1);
        // tamPoblacion = listaPuntos.size();
        System.out.println("dimension: " + tamPoblacion);
        System.out.println("capacidades: " + Arrays.toString(capacidades));
        
        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaEvolucionDiferencialBinariaPaper(tamPoblacion)));
        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaEvolucionDiferencialBinariaPaperMejorado(tamPoblacion)));

        List<Funcion> listaFunciones = new ArrayList();
        MochilaMultidimensional funcionMochila = new MochilaMultidimensional(capacidades, listaPuntos, maximizar);
        listaFunciones.add(funcionMochila);

        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones);
//        Punto mejor = funcionMochila.getMejor();
//        for (int i = 0; i < capacidades.length; i++) {
//            System.out.println("Peso[" + i + "]: " + funcionMochila.obtenerPeso(mejor, i));
//        }
//        System.out.println("Precio: " + funcionMochila.obtenerPrecio(mejor));

    }
}
