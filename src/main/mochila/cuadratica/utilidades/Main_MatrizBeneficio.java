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
package main.mochila.cuadratica.utilidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.utilidades.EscribirArchivo;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;

/**
 * Imprimir matriz de beneficios ordenada para ver grupos de unos
 *
 * @author debian
 */
public class Main_MatrizBeneficio {

    public static void main(String[] args) {
        String nombreArchivo = "";
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
        nombreArchivo = "mochilaCuadratica/grupo1/jeu_200_50_2.txt";
        //lim,15,0.99,5->,1
//        nombreArchivo = "mochilaCuadratica/r_10_100_13.txt";

        // dimension de los puntos;
        LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
        ParametrosCuadratica parametros = pc.obtenerParametros(nombreArchivo);
        EscribirArchivo archivoEsc = new EscribirArchivo();
        archivoEsc.abrir("matriz_beneficio.txt");

        double[][] matrizBeneficios = parametros.getMatrizBeneficios();
        double capacidad = parametros.getCapacidad();
        double[] vectorPesos = parametros.getVectorPesos();
        Double maxGlobal = parametros.getMaxGlobal();

        FuncionMochilaIHEA funcion = new FuncionMochilaIHEA(matrizBeneficios,capacidad, vectorPesos, maxGlobal);

        IndividuoIHEA indi = funcion.generarIndividuo();

        double[][] matrizb = parametros.getMatrizBeneficios();
        double[] mPesos = parametros.getVectorPesos();
        double maximo = 0;

        int cols = matrizb.length;
        // volver simetrica
        for (int i = 0; i < cols; i++) {
            for (int j = i; j < matrizb[i].length; j++) {
                matrizb[j][i] = matrizb[i][j];
                if (maximo < matrizb[i][j]) {
                    maximo = matrizb[i][j];
                }
            }
        }
        System.out.println("maximo: " + maximo);
        // lista de indices para ordenamiento
        List<Integer> posiciones = new ArrayList();
        // aumentar la matriz y relizar sumatorias por fila y columna
        double[][] matrizAux = new double[cols + 1][cols + 1];
        for (int i = 0; i < matrizb.length; i++) {
            for (int j = 0; j < matrizb[i].length; j++) {
                matrizAux[i][j] = matrizb[i][j];
                matrizAux[i][cols] += matrizb[i][j];
                matrizAux[cols][j] += matrizb[i][j];
            }
            posiciones.add(i);
        }
        // encontrar orden

        Collections.sort(posiciones, (Integer o1, Integer o2) -> {
            double peso1 = funcion.peso(o1);
            double peso2 = funcion.peso(o2);
            double numceros1 = funcion.relaciones(o1);
            double numceros2 = funcion.relaciones(o2);
            double ben1 = funcion.beneficio(o1);
            double ben2 = funcion.beneficio(o2);

            numceros1 = ben1 / numceros1;
            numceros2 = ben2 / numceros2;
            ben1 = ben1 / peso1;
            ben2 = ben2 / peso2;

            double resultado1 = numceros1 - numceros2;
            double resultado2 = ben1 - ben2;

            if ((resultado1 > 0 && resultado2 > 0)) {
                return 1;
            }
            if (resultado1 == 0 && resultado2 == 0) {
                return 0;
            }
            return -1;
//            return -Double.compare(matrizAux[cols][o1], matrizAux[cols][o2]);
        });
        posiciones.add(cols);

        // ordenamiento de matriz
        for (int i = 0; i < matrizb.length; i++) {
            for (int j = 0; j < matrizb[i].length; j++) {
                matrizAux[i][j] = matrizb[posiciones.get(i)][posiciones.get(j)];
            }
        }
        matrizb = matrizAux;

        // impresion
        String cadena;
        for (int i = 0; i < matrizb.length; i++) {
            cadena = posiciones.get(i) + " ";
            for (int j = 0; j < matrizb[i].length; j++) {
                cadena += matrizb[i][j];
                cadena += (j < matrizb[i].length - 1 ? " " : " ");
            }
            if (i < matrizb.length - 1) {
                cadena += mPesos[i];
            }
            archivoEsc.escribir(cadena);
        }
        archivoEsc.terminar();

    }
}
