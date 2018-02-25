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
    protected final FuncionMochilaHyperplaneExploration funcionIHEA;
    protected Funcion funcion;

    public IteratedHyperplaneExplorationAlgoritm(FuncionMochilaHyperplaneExploration funcionIHEA) {
        super();
        nombre = "IHEA";
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
        this.funcion.setNombre(funcionIHEA.getNombre());
        return iterateHiperplaneExploration(L, rcl, maxIteraciones);
    }

    protected List<Individuo> iterateHiperplaneExploration(int L, int rcl, int maxIter) {

        List<Individuo> recorrido = new ArrayList();
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
        Individuo x_prima = x_inicial.clone();
        // linea 6: iter = 0
        iteraciones = 0;
        /**
         * linea 7: xb= x'. Xb records the best solution found in current round
         * of hyperplane exploration.
         */
        Individuo x_mejorRondaHyper = x_prima.clone();
        /**
         * linea 8: x* = xb. x* records the global best solution.
         */
        Individuo x_mejorGlobal = x_mejorRondaHyper.clone();
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
                VarFijas = determinarVariablesFijas(k, x_prima, lb);
                // linea 16: construct reduce constrain problem
                construirProblemaRestringidoReducido(VarFijas, cqkp_k, x_prima);
                // linea 17: run tabu serach engine (L,x',xb)
                Individuo x_bestSolution = tabuSearchEngine(L, x_prima, x_mejorRondaHyper);
                x_bestSolution.evaluar();
                // linea 18:
                if (x_bestSolution.compareTo(x_mejorRondaHyper) > 0) {
                    // linea 19:
                    x_mejorRondaHyper = x_bestSolution.clone();
                    // linea 21
                    x_prima = x_mejorRondaHyper.clone();
                    int pos = add(x_prima);
                    // linea 20:
                    k = dimensionHiperplano(x_prima);
                    // linea 22: construct constrain problem CQKP[k]
                    cqkp_k = new CQKP();
                } else {
                    // linea 24:
                    solucionEncontrada = false;
                }
            }
            funcionIHEA.reiniciarVijarVariables();
            //linea 27:
            if (x_mejorRondaHyper.compareTo(x_mejorGlobal) > 0) {
                // linea 28:
                x_mejorGlobal = x_mejorRondaHyper.clone();
            }
            // linea 30: fase de perturbacion
            // linea 31:
            x_prima = perturbacion(x_mejorRondaHyper, iteraciones);
            // linea 32:
            x_prima = descent(x_prima);
            // linea 33:
            x_mejorRondaHyper = x_prima.clone();
            recorrido.add(x_mejorGlobal);
        }
        return recorrido;
    }

    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @param dimensionHyp
     * @param individuo
     * @param lowerb
     * @return
     */
    protected int[] determinarVariablesFijas(int dimensionHyp, Individuo individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimensionHyp;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
        // vector de indices de variables fijas
        int[] varFijas = new int[nf];
        // items seleccionados
        List<Integer> itemsSeleccionados = obtener_I1(individuo);

        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(itemsSeleccionados, individuo, false);
        nf = Math.min(nf, listaIndices.size());
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
     * @param ascendente
     * @return
     */
    protected List<Integer> listaIndicesOrdenadosPorDensidad(Individuo individuo, boolean ascendente) {
        // almacen de indices
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < individuo.getDimension(); i++) {
            // indices de cada elemento
            listaIndices.add(i);
        }
        return listaIndicesOrdenadosPorDensidad(listaIndices, individuo, ascendente);
    }

    /**
     * obtiene la lista de indices de cada item en listaIndices, de acuerdo al
     * parametro ascendente, por densidad (p/w).
     *
     * @param listaIndices
     * @param individuo
     * @param ascendente
     * @return
     */
    protected List<Integer> listaIndicesOrdenadosPorDensidad(List<Integer> listaIndices, Individuo individuo, boolean ascendente) {
        // almacen de todas las densidades
        double[] densidades = new double[individuo.getDimension()];
        // calcular densidades
        for (int indice : listaIndices) {
            // calcular densidad solo de los elementos en listaIndices
            densidades[indice] = funcionIHEA.densidad(indice, individuo);
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

    protected void construirProblemaRestringidoReducido(int[] varFijas, CQKP cqkp_k, Individuo x_actual) {
        funcionIHEA.fijarVariables(x_actual, varFijas);
    }

    protected Individuo tabuSearchEngine(int L, Individuo x_inicial, Individuo x_referencia) {

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
        int iterTabu = 1;
        while (vmin != Double.POSITIVE_INFINITY && erl < L) {
            //linea9:
            vmin = Double.POSITIVE_INFINITY;
            fmax = Double.NEGATIVE_INFINITY;

            List<Integer> I0;
            List<Integer> I1;
            I0 = obtener_I0(x);
            I1 = obtener_I1(x);
            // linea 10:
            for (Integer i : I0) {
                // linea 11:
                for (Integer j : I1) {
                    // linea 12:
                    if (tabu[i][j] != iterTabu) {
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
                // linea 23:
                if (vmin >= 0) { // ############################# ==
                    // linea 24:
                    erl = 0;
                    frx = rawFuncion(x);
                    fmin = frx;
                    x_aster = x.clone();
                    // linea 25:
                } else {
                    //linea 26: actualizar estado tabu
                    iterTabu += 1;
                    list_RL.add(i_aster);
                    list_RL.add(j_aster);
                    erl += 2;
                    // linea 27:
                    i = erl - 1;
                    // linea 28:
                    while (i >= 0) {
                        // linea 29:
                        j = list_RL.get(i);

                        if (list_RCS.contains(j)) {
                            boolean ret = list_RCS.remove((Integer) j);
                        } else {
                            list_RCS.add(j);
                        }
                        if (list_RCS.size() == 2) {
                            tabu[list_RCS.get(0)][list_RCS.get(1)] = iterTabu;
                            tabu[list_RCS.get(1)][list_RCS.get(0)] = iterTabu;
                        }
                        i = i - 1;
                    }
                }
            }
        }
        return x_aster;
    }

    protected double rawFuncion(Individuo individuo) {
        return individuo.evaluar();
    }

    /**
     *
     * @param individuo
     * @param iteraciones
     * @return
     */
    protected Individuo perturbacion(Individuo individuo, int iteraciones) {
        List<Integer> I1 = obtener_I1(individuo);

        // dimension de individuo
        int dimX = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lb + Math.max(0, (dimX - lb) * (1 - 1 / (0.008 * n))));

        t = Math.min(10, I1.size() - nf);
        s = Math.min(3, t);
        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(I1, individuo, true);
        for (int i = 0; i < s; i++) {
            individuo.set(listaIndices.get(i), 0);
        }
        individuo = GreedyRandomizedConstruction(individuo, rcl);
        return individuo;
    }

    protected class CQKP {

    }

    protected CQKP construirCQKP(int numVarFijas, CQKP cqkp, Individuo x_individuo) {

        return cqkp;

    }

    protected Individuo GreedyRandomizedConstruction(int rcl) {
        Individuo individuo = funcionIHEA.generarIndividuo();
        individuo = GreedyRandomizedConstruction(individuo, rcl);
        return individuo;
    }

    protected Individuo GreedyRandomizedConstruction(Individuo individuo, int rcl) {
        List<Integer> listaNoSeleccionados = obtener_I0(individuo);
        List<Integer> listaOrdenada = listaIndicesOrdenadosPorBeneficio(listaNoSeleccionados,individuo, false);
        individuo = funcionIHEA.limitarInferiormente(individuo, listaOrdenada);
        individuo.evaluar();
        return individuo;
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    protected List<Integer> obtener_I1(Individuo individuo) {
        return funcionIHEA.obtener_I1(individuo);
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I1)
     * en individuo.
     *
     * @param individuo
     * @return List de indices de elementos no seleccionados
     */
    protected List<Integer> obtener_I0(Individuo individuo) {
        return funcionIHEA.obtener_I0(individuo);
    }

    /**
     * realiza un mejoramiento al individuo utilizando add y swap.
     *
     * @param original
     * @return
     */
    protected Individuo descent(Individuo original) { ////////////OPCION DE MEJORAR EN TIEMPO (for)
        Individuo mejor = original.clone();

        while (true) {
            Individuo individuo = mejor.clone();
            //ADD
            add_factible(individuo);

            //SWAP
            swap(individuo);

            //comparacion
            individuo.evaluar();
            if (individuo.compareTo(mejor) > 0) {
                mejor = individuo;
            } else {
                break;
            }
        }
        return mejor;
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo ya sea que el
     * individuo resultante sea factible o no factible.
     *
     * @param individuo
     * @return indice del elemento adicionado
     */
    protected int add(Individuo individuo) {
        List<Integer> listaI0 = obtener_I0(individuo);
        return cambiarValorAleatoriamente(individuo, listaI0, 1);
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo de manera tal que
     * el individuo resultante sea factible
     *
     * @param individuo
     * @return indice del elemento adicionado
     */
    protected int add_factible(Individuo individuo) {
        List<Integer> listaI0 = obtener_I0(individuo);
        return adicionarItemAleatoriamente(individuo, listaI0);
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo de manera tal que
     * el individuo resultante sea factible
     *
     * @param individuo
     * @param listaI0
     * @return indice del elemento adicionado
     *
     */
    private int adicionarItemAleatoriamente(Individuo individuo, List<Integer> listaI0) {
        List<Integer> listaFactibles = funcionIHEA.filtrarPorFactibles(listaI0, individuo);
        return cambiarValorAleatoriamente(individuo, listaFactibles, 1);
    }

    /**
     * asigna valor al elemento con el indice, escogido aleatoriamente de
     * listaIndices, dentro de individuo.
     *
     * @param individuo
     * @param listaIndices
     * @param valor
     * @return indice del elemento escogido aleatoriamente de listaIndices
     */
    private int cambiarValorAleatoriamente(Individuo individuo, List<Integer> listaIndices, int valor) {
        if (listaIndices.isEmpty()) {
            return -1;
        }
        int indice = Aleatorio.nextInt(listaIndices.size());
        individuo.set(listaIndices.get(indice), valor);
        return indice;
    }

    /**
     * saca un elemento y coloca otro de forma aleatoria.
     *
     * @param individuo
     * @return int[]{indice_salio,indice_entro}
     */
    public int[] swap(Individuo individuo) {
        List<Integer> listaNS = obtener_I0(individuo);
        List<Integer> listaSS = obtener_I1(individuo);
        int indice_salio = cambiarValorAleatoriamente(individuo, listaSS, 0);
        int indice_entro = adicionarItemAleatoriamente(individuo, listaNS);

        if (indice_entro == -1) {
            individuo.set(indice_salio, 1);
            return null;
        } else {
            individuo.evaluar();
        }
        return new int[]{indice_salio, indice_entro};
    }

    /**
     * optinene la dimension o número de elementos(unos) que hay en el
     * individuo.
     *
     * @param individuo
     * @return
     */
    protected int dimensionHiperplano(Individuo individuo) {
        int suma = 0;
        for (Double item : individuo) {
            suma += item;
        }
        return suma;
    }

    /**
     * obtiene la lista de indices de cada item de individuo ordenados, de
     * acuerdo al parametro ascendente, por densidad (p/w).
     *
     * @param individuo
     * @return
     */
    private List<Integer> listaIndicesOrdenadosPorBeneficio(Individuo individuo, boolean ascendente) {
        // almacen de indices
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < individuo.getDimension(); i++) {
            // indices de cada elemento
            listaIndices.add(i);
        }
        return listaIndicesOrdenadosPorBeneficio(listaIndices, individuo, ascendente);
    }

    /**
     * obtiene la lista de indices de cada item de individuo ordenados, de
     * acuerdo al parametro ascendente, por densidad (p/w).
     *
     * @param individuo
     * @return
     */
    private List<Integer> listaIndicesOrdenadosPorBeneficio(List<Integer> listaIndices, Individuo individuo, boolean ascendente) {
        // almacen de todas las densidades
        double[] densidades = new double[individuo.getDimension()];
        // calcular densidades
        for (Integer indice : listaIndices) {
            // calcular densidad solo de los elementos seleccionados
            densidades[indice] = funcionIHEA.densidad(indice, individuo);
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
