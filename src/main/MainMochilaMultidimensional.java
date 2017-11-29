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
package main;

import funciones.Ackley;
import funciones.Esfera;
import funciones.Funcion;
import funciones.Mochila;
import funciones.MochilaMultidimensional;
import funciones.MochilaMultidimensionalMejorada;
import funciones.Piso;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Punto;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencial;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencialBinaria;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencialBinariaPaper;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencialBinariaPaperMejorado;
import metaheuristicas.simple.Hill_Climbing;
import static main.Utilidades.ejecutarAlgoritmosMasFunciones;
import static main.Utilidades.ejecutarAlgoritmosMasFuncionesED;

/**
 *
 * @author debian
 */
public class MainMochilaMultidimensional {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int tamPoblacion;
        int iteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        // rango maximo de cambio en el tweak
        paso = 1;
        // limite de las funciones
        limite = 10;
        // numero de individuos porpoblacion
        tamPoblacion = 10;
        // iteraciones realizadas por los algoritmos
        iteraciones = 100;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;

        boolean maximizar = true;

        List listaPuntos = Utilidades.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f2-1000.txt");
//        List listaPuntos = Utilidades.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f2-11.txt");
//        List listaPuntos = Utilidades.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f2-20000.txt");
//        List listaPuntos = Utilidades.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f3-100.txt");
//        List listaPuntos = Utilidades.obtenerDatosMochilaMultidimensional("mochilaMultidimencional/f4-22.txt");
        // dimension de los puntos;
        double[] capacidades = (double[]) listaPuntos.remove(listaPuntos.size() - 1);
        // tamPoblacion = listaPuntos.size();
        System.out.println("dimension: " + tamPoblacion);
        System.out.println("capacidades: " + Arrays.toString(capacidades));
        
        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaEvolucionDiferencialBinariaPaper(tamPoblacion)));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaEvolucionDiferencialBinariaPaperMejorado(tamPoblacion)));

        List<Funcion> listaFunciones = new ArrayList();
        MochilaMultidimensional funcionMochila = new MochilaMultidimensional(capacidades, listaPuntos, maximizar);
        MochilaMultidimensionalMejorada funcionMochilaM = new MochilaMultidimensionalMejorada(capacidades, listaPuntos, maximizar);
        listaFunciones.add(funcionMochila);
        listaFunciones.add(funcionMochilaM);

        // EJECUTAR ANALISIS
        ejecutarAlgoritmosMasFuncionesED(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones, paso);
//        Punto mejor = funcionMochila.getMejor();
//        for (int i = 0; i < capacidades.length; i++) {
//            System.out.println("Peso[" + i + "]: " + funcionMochila.obtenerPeso(mejor, i));
//        }
//        System.out.println("Precio: " + funcionMochila.obtenerPrecio(mejor));

    }
}
