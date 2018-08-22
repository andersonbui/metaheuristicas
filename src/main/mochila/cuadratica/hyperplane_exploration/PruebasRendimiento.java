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
package main.mochila.cuadratica.hyperplane_exploration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.GrupoAlgoritmosMochilaCuadratica;
import main.mochila.cuadratica.GrupoInstancias;
import main.mochila.cuadratica.LecturaParametrosCuadratica;
import main.mochila.cuadratica.ParametrosCuadratica;

/**
 *
 * @author debian
 */
public class PruebasRendimiento {

    public static void main(String[] args) throws FileNotFoundException, Exception {

        int maxIteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        // numero de individuos porpoblacion
        // iteraciones realizadas por los algoritmos
        maxIteraciones = 50;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 1;
        String nombreArchivo;
        List<GrupoInstancias> instancias = new ArrayList();
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_100_%d.txt", 8, 8));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_300_25_%d.txt", 1, 20));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_300_50_%d.txt", 1, 20));

        for (GrupoInstancias instancia : instancias) {
            System.out.println("########################################################################");
            for (int indice_instancia = instancia.inicio; indice_instancia <= instancia.cantidad; indice_instancia++) {
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
                double[][] matrizBeneficios = parametros.getMatrizBeneficios();
                double capacidad = parametros.getCapacidad();
                double[] vectorPesos = parametros.getVectorPesos();
                Double maxGlobal = parametros.getMaxGlobal();

                FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                IteratedHyperplaneExplorationAlgoritm aIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);

                //generar individuo
                IndividuoIHEA mochila = aIHEA.descent(funcionHyperplanos.generarIndividuo());
                // lista indices
                List<Integer> listaIndices = new ArrayList();
                for (int i = 0; i < funcionHyperplanos.getDimension(); i++) {
                    listaIndices.add(i);
                }
                //calculo tiempo
                int intentos = 10000;
                long tiempo_inicial = System.currentTimeMillis();
                for (int i = 0; i < intentos; i++) {
                    aIHEA.nPrimerosOrdenadosPorDensidad(listaIndices, mochila, 10, true);
                }
                long tiempo_final = System.currentTimeMillis();
                System.out.println("promedio: " + ((tiempo_final - tiempo_inicial) / (double)1));

            }
        }
    }
}
