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
import java.util.ArrayList;
import java.util.List;
import main.Ejecutor;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {
//lim,rango,prob_ceros,poblacion, iteraciones
    //lim,20,0.90,20
//        nombreArchivo = "mochilaCuadratica/1000_25_1.dat";
//        nombreArchivo = "mochilaCuadratica/5000_25_1.txt";
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
//        nombreArchivo = "mochilaCuadratica/jeu_100_25_7.txt";
//        nombreArchivo = "mochilaCuadratica/jeu_100_100_1.txt";
    //lim,15,0.99,5->,1
//        nombreArchivo = "mochilaCuadratica/r_10_100_13.txt";
    public static void main(String[] args) throws FileNotFoundException, Exception {

        int maxIteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
//        graficaDispercion2D = true;
        // numero de individuos porpoblacion
        // iteraciones realizadas por los algoritmos
        maxIteraciones = 50;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 5;
        String nombreArchivo;
        List<GrupoInstancias> instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_75_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_50_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_25_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_100_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_100_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_25_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_50_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_75_%d.txt", 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_300_25_%d.txt", 20));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_300_50_%d.txt", 20));

        for (GrupoInstancias instancia : instancias) {
            System.out.println("########################################################################");
            for (int indice_instancia = 1; indice_instancia <= instancia.cantidad; indice_instancia++) {
                nombreArchivo = String.format(instancia.base, indice_instancia);

                System.out.println("---------------------------------------------------------------");
                System.out.println("Nombre archivo: " + nombreArchivo);
                // dimension de los puntos;
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
                ParametrosCuadratica parametros = lpc.obtenerParametros(nombreArchivo);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }
                GrupoAlgoritmosMochilaCuadratica grupo = new GrupoAlgoritmosMochilaCuadratica(parametros, maxIteraciones);
                grupo.inicializar();
                Ejecutor ejecutor = new Ejecutor();
                // EJECUTAR ANALISIS
                IndividuoGen individuo = ejecutor.ejecutarAlgoritmosMasFunciones(grupo, graficaRecorrido3D, graficaDispercion2D, numMuestras).getMejorIndividuo();
                // comprobar calidad de la actua instancia
                if (parametros.maxGlobal == null || parametros.maxGlobal.compareTo(individuo.getCalidad()) < 0) {
                    parametros.setMaxGlobal(individuo.getCalidad());
                    parametros.setVectorIdeal(trans(individuo.getValores()));
                    lpc.actualizar(nombreArchivo, parametros);
                }
            }
        }
    }

    public static int[] trans(double[] vector) {
        int[] vectorInt = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            vectorInt[i] = (int) vector[i];
        }
        return vectorInt;
    }
}
