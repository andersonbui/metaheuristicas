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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class IteratedHyperplaneExplorationAlgoritm extends AlgoritmoMetaheuristico {

    int rcl;
    int lb;
    int t;
    int s;
    int L;
    private final FuncionMochilaHyperplaneExploration funcionIHEA;
    private Funcion funcion;

    public IteratedHyperplaneExplorationAlgoritm(FuncionMochilaHyperplaneExploration funcionIHEA) {
        super("IHEA");
        this.funcionIHEA = funcionIHEA;
        lb = funcionIHEA.obtener_lb();
        t = 1;
        s = 1;
        rcl = 20;
        L = 300;
        maxIteraciones = (int) Math.sqrt(funcionIHEA.getDimension()) + 65;
    }

    @Override
    public List<Individuo> ejecutar(Funcion funcion) {
        this.funcion = funcion;
        iterateHiperplaneExploration(L, rcl, maxIteraciones);
        return null;
    }

    private List<Individuo> iterateHiperplaneExploration(int L, int rcl, int maxIter) {
        /**
         * maximo tamanio de la lista de ejecución(lista que guarda todos los
         * atributos de los movimientos implementados), que sirve como condicion
         * determinacion.
         */
        // indice de las variables fijas para el problema restringido
        int[] VarFijas;
        //variable bandera.
        boolean solucionEncontrada;
        //dimension k.
        int k;
        /**
         * linea 3: x0 = GreedyRandomizedConstruction(rcl). solucion inicial
         */
        Individuo x_inicial = GreedyRandomizedConstruction(rcl);
        /**
         * linea 4: x0 = descent(x0). mejoramiento de la solución inicial
         */
        x_inicial = descent(x_inicial);
        /**
         * linea 5: x' = x0. x' represents the current solution.
         */
        Individuo x_prima = x_inicial;
        // linea 6: iter = 0
        iteraciones = 0;
        /**
         * linea 7: xb= x'. Xb records the best solution found in current round
         * of hyperplane exploration.
         */
        Individuo x_mejorRondaHyper = x_prima;
        /**
         * linea 8: x* = xb. x* records the global best solution.
         */
        Individuo x_mejorGlobal = x_mejorRondaHyper;
        //linea 9:
        for (; iteraciones < maxIter; iteraciones++) {
            // linea 10:
            solucionEncontrada = true;
            // linea 11:
            k = dimensionHiperplano(x_prima);
            // linea 12: construct constrain problem CQKP[k]
            CQKP cqkp_k = new CQKP();
            // linea 13: fase de exploracion
            // linea 14:
            while (solucionEncontrada) {
                // linea 14:
                VarFijas = determinarVariablesFijas(k, x_prima);
                // linea 16: construct reduce constrain problem
                construirProblemaRestringidoReducido(VarFijas, cqkp_k, x_prima);
                // linea 17: run tabu serach engine (L,x',xb)
                Individuo x_bestSolution = tabuSearchEngine(L, x_prima, x_mejorRondaHyper, iteraciones);
                x_bestSolution.evaluar();
                // linea 18:
                if (x_bestSolution.compareTo(x_mejorRondaHyper) > 0) {
                    // linea 19:
                    x_mejorRondaHyper = x_bestSolution;
                    // linea 20:
                    k++;
                    // linea 21
                    x_prima = x_mejorRondaHyper;
                    int pos = adicionarItemAleatoriamente(x_prima);
                    // linea 22: construct constrain problem CQKP[k]
                    cqkp_k = new CQKP();
                } else {
                    // linea 24:
                    solucionEncontrada = false;
                }
            }
            //linea 27:
            if (x_mejorRondaHyper.compareTo(x_mejorGlobal) > 0) {
                // linea 28:
                x_mejorGlobal = x_mejorRondaHyper;
            }
            // linea 30: fase de perturbacion
            // linea 31:
            x_prima = perturbacion(x_mejorRondaHyper, iteraciones);
            // linea 32:
            x_prima = descent(x_prima);
            // linea 33:
            x_mejorRondaHyper = x_prima;
        }
        return null;
    }

    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @param k
     * @param individuo
     * @return
     */
    private int[] determinarVariablesFijas(int k, Individuo individuo) {
        // dimension de individuo
        int dimX = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lb + Math.max(0, (dimX - lb) * (1 - 1 / (0.008 * n))));
        // vector de indices de variables fijas
        int[] varFijas = new int[nf];

        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(individuo, false);

        // obtener los primeros nf indices de los elementos más densos
        for (int i = 0; i < nf; i++) {
            varFijas[i] = listaIndices.get(i);
        }
        return varFijas;
    }

    /**
     * obtiene la lista de indices de cada item de individuo ordenados, de
     * acuerdo al parametro ascendente, por densidad (p/w).
     *
     * @param individuo
     * @return
     */
    private List<Integer> listaIndicesOrdenadosPorDensidad(Individuo individuo, boolean ascendente) {
        // almacen de todas las densidades
        double[] densidades = new double[individuo.getDimension()];
        // almacen de indices
        List<Integer> listaIndices = new ArrayList();
        // calcular densidades
        for (int i = 0; i < densidades.length; i++) {
            // calcular densidad solo de los elementos seleccionados
            if (individuo.get(i) == 1) {
                densidades[i] = funcionIHEA.densidad(i, individuo);
            } else {
                densidades[i] = 0;
            }
            // indices de cada elemento
            listaIndices.add(i);
        }
        /**
         * ordenar la lista de indices de forma decreciente con respecto a la
         * densidad
         */
        listaIndices.sort((Integer ind1, Integer ind2) -> {
            Double densidad1 = densidades[ind1];
            Double densidad2 = densidades[ind2];
            return (ascendente ? 1 : -1) * densidad1.compareTo(densidad2);
        });
        return listaIndices;
    }

    private void construirProblemaRestringidoReducido(int[] varFijas, CQKP cqkp_k, Individuo x_actual) {

    }

    private Individuo tabuSearchEngine(int L, Individuo x_inicial, Individuo x_referencia, int iter) {

        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = 0;
        double fmax;
        tabu = new int[x_inicial.getDimension()][x_inicial.getDimension()];
        double frx = 0;

        // residual cancellation sequence list
        List<Integer> list_RCS = new ArrayList(L);
        // inicializa el tamaño de la lista de ejecucion
        List<Integer> list_RL = new ArrayList(L);
        // almacena el valor de la funcion objetivo de la actual mejor solución factible
        double fmin = x_referencia.evaluar();
        // x*: recuerda la mejor solucion encontrada hasta el momento
        Individuo x_aster = x_referencia;
        // linea 6;
        int erl = 0;
        // linea 7:
        Individuo x = x_inicial.clone();
        int i_aster = 0;
        int j_aster = 0;
        // linea 8:
        while (vmin != Double.POSITIVE_INFINITY || erl < L) {
            //linea9:
            vmin = Double.POSITIVE_INFINITY;
            fmax = Double.NEGATIVE_INFINITY;

            List[] listas = obtener_I1_I0(x);
            List<Integer> I0 = listas[0];
            List<Integer> I1 = listas[1];
            // linea 10:
            for (Integer i : I0) {
                // linea 11:
                for (Integer j : I1) {
                    // linea 12:
                    if (tabu[i][j] != iter) {
                        // linea 13:
                        x.set(i, 1);
                        x.set(j, 0);
                        // linea 14:
                        frx = rawFuncion(x);
                        // violación de capacidad
                        double vcx = funcionIHEA.violacionDeCapacidad(x);
                        if ((frx > fmin) && ((vcx < vmin) || ((vcx == vmin) && (frx >= fmax)))) {
                            i_aster = i;
                            j_aster = j;
                            vmin = vcx;
                            fmax = frx;
                        }
                        x.set(i, 0);
                        x.set(j, 1);
                        x.evaluar();
                    }
                }
            }
            int i;
            int j;
            // linea 21:
            if (vmin != Double.POSITIVE_INFINITY) {
                // linea 22:
                x.set(i_aster, 1);
                x.set(j_aster, 0);
                x.evaluar();
                // linea 23:
                if (vmin == 0) {
                    // linea 24:
                    erl = 0;
                    frx = rawFuncion(x);
                    fmin = frx;
                    x_aster = x;
                    // linea 25:
                } else {
                    //linea 26: actualizar estado tabu
                    iter += 1;
                    list_RL.add(i_aster);
                    list_RL.add(j_aster);
                    erl += 2;
                    // linea 27:
                    i = erl - 1;
                    // linea 28:
                    while (i > 0) {
                        // linea 29:
                        j = list_RL.get(i);

                        if (list_RCS.contains(j)) {
                            boolean ret = list_RCS.remove((Integer) j);
                        } else {
                            list_RCS.add(j);
                        }
                        if (list_RCS.size() == 2) {
                            tabu[list_RCS.get(0)][list_RCS.get(1)] = iter;
                            tabu[list_RCS.get(1)][list_RCS.get(0)] = iter;
                        }
                        i = i - 1;
                    }
                }
            }
        }
        return x_aster;
    }

    private double rawFuncion(Individuo individuo) {
        return individuo.evaluar();
    }

    /**
     * Agrega un item a individuo de manera tal que individuo sea factble
     *
     * @param individuo
     * @return
     */
    private int adicionarItemAleatoriamente(Individuo individuo) {
        List<Integer> listaI0 = obtener_I0(individuo);
        return adicionarItemAleatoriamente(individuo, listaI0);
    }

    private int adicionarItemAleatoriamente(Individuo individuo, List<Integer> listaI0) {
        List<Integer> listaFactibles = funcionIHEA.filtrarPorFactibles(listaI0, individuo);
        if (listaFactibles.isEmpty()) {
            return -1;
        }
        //ADD
        int pos_add = Aleatorio.nextInt(listaFactibles.size());
        add(individuo, listaFactibles.get(pos_add));
        return pos_add;
    }

    /**
     *
     * @param individuo
     * @param iteraciones
     * @return
     */
    private Individuo perturbacion(Individuo individuo, int iteraciones) {
        List[] listas = obtener_I1_I0(individuo);
        List<Integer> I1 = listas[0];
        List<Integer> I0 = listas[1];

        // dimension de individuo
        int dimX = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lb + Math.max(0, (dimX - lb) * (1 - 1 / (0.008 * n))));

        t = Math.min(10, I1.size() - nf);
        s = Math.min(3, t);
        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(individuo, false);
        for (int i = 0; i < s; i++) {
            individuo.set(listaIndices.get(i), 0);
        }
        individuo = GreedyRandomizedConstruction(individuo, rcl);
        return individuo;
    }

    private class CQKP {

    }

    private CQKP construirCQKP(int numVarFijas, CQKP cqkp, Individuo x_individuo) {

        return cqkp;

    }

    private Individuo GreedyRandomizedConstruction(int rcl) {
        Individuo individuo = funcionIHEA.generarIndividuo();
        individuo = GreedyRandomizedConstruction(individuo, rcl);
        return individuo;
    }

    private Individuo GreedyRandomizedConstruction(Individuo individuo, int rcl) {
        List<Integer> listaOrdenada = listaIndicesOrdenadosPorBeneficio(individuo, false);
        individuo = funcionIHEA.limitarInferiormente(individuo, listaOrdenada);
        individuo.evaluar();
        return individuo;
    }

    /**
     * procedimeinto que obtiene la lista de elementos no seleccionados (I0) y
     * los elementos seleccionados (I1).
     *
     * @param original
     * @return List[k], k=0 -> I0, K=1 -> I1
     */
    private List<Integer>[] obtener_I1_I0(Individuo individuo) {
        List<Integer> listaNS = new ArrayList(individuo.getDimension());
        List<Integer> listaSS = new ArrayList(individuo.getDimension());
        // Sacar listas de elementos seleccionados y no seleccionados
        for (int i = 0; i < individuo.getDimension(); i++) {
            if (individuo.get(i) == 0) {
                listaNS.add(i);
            } else {
                listaSS.add(i);
            }
        }
        return new List[]{listaNS, listaSS};
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param original
     * @return List de indices de elementos seleccionados
     */
    private List<Integer> obtener_I1(Individuo individuo) {
        List<Integer>[] lista = obtener_I1_I0(individuo);
        return lista[1];
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I1)
     * en individuo.
     *
     * @param original
     * @return List de indices de elementos no seleccionados
     */
    private List<Integer> obtener_I0(Individuo individuo) {
        List<Integer>[] lista = obtener_I1_I0(individuo);
        return lista[0];
    }

    /**
     * realiza un mejoramiento al individuo utilizando add y swap.
     *
     * @param original
     * @return
     */
    private Individuo descent(Individuo original) { ////////////OPCION DE MEJORAR EN TIEMPO (for)
        Individuo mejor = original.clone();
        int pos_add;
        int pos_sacar;

        while (true) {
            Individuo individuo = mejor.clone();

            adicionarItemAleatoriamente(individuo);

            List[] listas = obtener_I1_I0(individuo);
            List<Integer> listaNS = listas[0];
            List<Integer> listaSS = listas[1];
            //SWAP
            if (listaNS.size() < 1) {
                individuo.evaluar();
                iteraciones = iteraciones;
            }
            pos_sacar = Aleatorio.nextInt(listaSS.size());
            individuo.set(pos_sacar, 0);
            int result = adicionarItemAleatoriamente(individuo, listaNS);
            if (result == -1) {
                individuo.set(pos_sacar, 1);
            } else {
                individuo.evaluar();
            }

            //comparacion
            if (individuo.compareTo(mejor) > 0) {
                mejor = individuo;
            } else {
                break;
            }
        }
        return mejor;
    }

    /**
     * optinene la dimension o número de elementos(unos) que hay en el
     * individuo.
     *
     * @param individuo
     * @return
     */
    private int dimensionHiperplano(Individuo individuo) {
        int suma = 0;
        for (Double item : individuo) {
            suma += item;
        }
        return suma;
    }

    /**
     * Agregar el elemento de la posición i en el individuo.
     *
     * @param individuo
     * @param i posición del elemento no seleccionado a introducir en el
     * individuo.
     */
    private void add(Individuo individuo, int i) {
        individuo.set(i, 1);
    }

    /**
     * obtiene la lista de indices de cada item de individuo ordenados, de
     * acuerdo al parametro ascendente, por densidad (p/w).
     *
     * @param individuo
     * @return
     */
    private List<Integer> listaIndicesOrdenadosPorBeneficio(Individuo individuo, boolean ascendente) {
        // almacen de todas las densidades
        double[] densidades = new double[individuo.getDimension()];
        // almacen de indices
        List<Integer> listaIndices = new ArrayList();
        // calcular densidades
        for (int i = 0; i < densidades.length; i++) {
            // calcular densidad solo de los elementos seleccionados
            densidades[i] = funcionIHEA.densidad(i, individuo);
//                densidades[i] = funcionIHEA.beneficio(i);
            // indices de cada elemento
            listaIndices.add(i);
        }
        /**
         * ordenar la lista de indices de forma decreciente con respecto a la
         * densidad
         */
        listaIndices.sort((Integer ind1, Integer ind2) -> {
            Double densidad1 = densidades[ind1];
            Double densidad2 = densidades[ind2];
            return (ascendente ? 1 : -1) * densidad1.compareTo(densidad2);
        });
        return listaIndices;
    }
}
