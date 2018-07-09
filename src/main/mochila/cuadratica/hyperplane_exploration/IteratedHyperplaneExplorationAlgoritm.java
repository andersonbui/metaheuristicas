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
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class IteratedHyperplaneExplorationAlgoritm extends AlgoritmoMetaheuristico<FuncionMochilaIHEA, IndividuoIHEA> {

    int rcl;
    int lb;
    int t;
    int s;
    int L;

    public IteratedHyperplaneExplorationAlgoritm(FuncionMochilaIHEA funcion) {
        super();
        setFuncion(funcion);
        nombre = "IHEA";
        lb = funcion.obtenerLowerBound();
        rcl = 20;
        L = 10;
        maxIteraciones = (int) Math.sqrt(funcion.getDimension()) + 65;
    }

    @Override
    public List<IndividuoIHEA> ejecutar() {
        return iterateHiperplaneExploration(L, rcl, maxIteraciones);
    }

    protected List<IndividuoIHEA> iterateHiperplaneExploration(int L, int rcl, int maxIter) {

        List<IndividuoIHEA> recorrido = new ArrayList();
        /**
         * maximo tamanio de la lista de ejecución(lista que guarda todos los
         * atributos de los movimientos implementados), que sirve como condicion
         * determinacion.
         */
        // indice de las variables fijas para el problema restringido
        int[] variablesFijas;
        //variable bandera.
        boolean solucionEncontrada;
        //dimension k.
        int k;
        /**
         * linea 3: x0 = GreedyRandomizedConstruction(rcl). solucion inicial
         */
        IndividuoIHEA indi = funcion.generarIndividuo();
        IndividuoIHEA x_inicial = GreedyRandomizedConstruction(indi, rcl);
        /**
         * linea 4: x0 = descent(x0). mejoramiento de la solución inicial
         */
        x_inicial = descent(x_inicial);
        /**
         * linea 5: x' = x0. x' represents the current solution.
         */
        IndividuoIHEA x_prima = x_inicial.clone();
        // linea 6: iter = 0
        iteraciones = 0;
        /**
         * linea 7: xb= x'. Xb records the best solution found in current round
         * of hyperplane exploration.
         */
        IndividuoIHEA x_mejorRondaHyper = x_prima.clone();
        /**
         * linea 8: x* = xb. x* records the global best solution.
         */
        IndividuoIHEA x_mejorGlobal = x_mejorRondaHyper.clone();
        //linea 9:
        for (; iteraciones < maxIter; iteraciones++) {
            // linea 10:
            solucionEncontrada = true;
            // linea 11:
            k = dimensionHiperplano(x_prima);
            // linea 12: construct constrain problem CQKP[k]
            // linea 13: fase de exploracion
            // linea 14:
            while (solucionEncontrada) {
                // linea 14:
                variablesFijas = determinarVariablesFijas(k, x_prima, lb);
                // linea 16: construct reduce constrain problem
                construirProblemaRestringidoReducido(variablesFijas, x_prima);
                // linea 17: run tabu serach engine (L,x',xb)
                x_prima = tabuSearchEngine(L, x_prima, x_mejorRondaHyper);
                // linea 18:
                if (x_prima.compareTo(x_mejorRondaHyper) > 0) {
                    // linea 19:
                    if (x_prima.pesar() <= funcion.getCapacidad()) {

                        x_mejorRondaHyper = x_prima.clone();
                        // linea 21
                        x_prima = add(x_mejorRondaHyper);
                        // linea 20:
                        k = dimensionHiperplano(x_prima);
                        // linea 22: construct constrain problem CQKP[k]
                    }
                } else {
                    // linea 24:
                    solucionEncontrada = false;
                }
//                recorrido.add(x_mejorGlobal);
                funcion.reiniciarVijarVariables();
            }
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
        System.out.println("contador exaustiva-hiperplano: " + contador);
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
    protected int[] determinarVariablesFijas(int dimensionHyp, IndividuoIHEA individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimensionHyp;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);

        nf = Math.min(nf, itemsSeleccionados.size());
//        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(itemsSeleccionados, individuo, false);
        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        int[] varFijas = new int[nf];
        // obtener los primeros nf indices de los elementos más densos
        for (int i = 0; i < nf; i++) {
            varFijas[i] = listaIndices.get(i);
        }
        return varFijas;
    }

    /**
     * obtiene la lista de indices de cada item en listaIndices, de acuerdo al
     * parametro ascendente, por densidad (p/w).
     *
     * @param listaIndicesOriginal
     * @param individuo
     * @param nf
     * @param minimo
     * @return
     */
//    protected List<Integer> listaIndicesOrdenadosPorDensidad(List<Integer> listaIndices, IndividuoIHEA individuo, boolean ascendente) {
//        // almacen de todas las densidades
//        double[] densidades = new double[individuo.getDimension()];
//        // calcular densidades
//        listaIndices.forEach((indice) -> {
//            // calcular densidad solo de los elementos en listaIndices
//            densidades[indice] = funcion.densidad(indice, individuo);
//        });
//        /**
//         * ordenar la lista de indices de forma decreciente con respecto a la
//         * densidad
//         */
//        listaIndices.sort((Integer ind1, Integer ind2) -> {
//            Double densidad1 = densidades[ind1];
//            Double densidad2 = densidades[ind2];
//            return (ascendente ? 1 : -1) * densidad1.compareTo(densidad2);
//        });
//        return listaIndices;
//    }
    protected List<Integer> listaIndicesOrdenadosPorDensidad(List<Integer> listaIndicesOriginal, IndividuoIHEA individuo, int nf, boolean minimo) {
        List<Integer> listaIndices = new ArrayList(listaIndicesOriginal);
        List<Integer> resultado = new ArrayList();
        int posicion = 0;
//        ascendente = !ascendente;
        double valor;

        // almacen de todas las densidades
        double[] densidades = new double[individuo.getDimension()];

        // calcular densidades
        listaIndicesOriginal.forEach((indice) -> {
            // calcular densidad solo de los elementos en listaIndices
            densidades[indice] = funcion.densidad(indice, individuo);
        });

        for (int i = 0; i < nf; i++) {
            valor = minimo ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            for (int indice : listaIndices) {
                if ((minimo && valor > densidades[indice]) || (!minimo && valor < densidades[indice])) {
                    valor = densidades[indice];
                    posicion = indice;
                }
            }
            resultado.add(posicion);
            listaIndices.remove((Integer) posicion);
        }
        return resultado;

//        /**
//         * ordenar la lista de indices de forma decreciente con respecto a la
//         * densidad
//         */
//        listaIndices.sort((Integer ind1, Integer ind2) -> {
//            Double densidad1 = densidades[ind1];
//            Double densidad2 = densidades[ind2];
//            return (ascendente ? 1 : -1) * densidad1.compareTo(densidad2);
//        });
//        return listaIndices;
    }

    protected void construirProblemaRestringidoReducido(int[] varFijas, IndividuoIHEA x_actual) {
        funcion.fijarVariables(x_actual, varFijas);
    }

    int contador = 0;

    protected IndividuoIHEA tabuSearchEngine(int L, IndividuoIHEA x_inicial, IndividuoIHEA x_referencia) {

        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = 0;
        double fmax;
        tabu = new int[x_inicial.getDimension()][x_inicial.getDimension()];
        double frx = 0;
        double vcx;

        // residual cancellation sequence list
        List<Integer> list_RCS = new ArrayList();
        // inicializa el tamaño de la lista de ejecucion
        List<Integer> list_RL = new ArrayList();
        // almacena el valor de la funcion objetivo de la actual mejor solución factible
        double fmin = x_referencia.evaluar();
        // x*: recuerda la mejor solucion encontrada hasta el momento
        IndividuoIHEA x_aster = x_referencia;
        // linea 6;
        int erl = 0;
        // linea 7:
        IndividuoIHEA x = x_inicial.clone();
        int i_aster = 0;
        int j_aster = 0;

        int imax = -1;
        int jmax = -1;
        // linea 8:
        int iterTabu = 1;
        int penalizacion = 0;

        double pesoi;
        double calidadi;

        while ((vmin != Double.POSITIVE_INFINITY && list_RL.size() < L)) {

            vmin = Double.POSITIVE_INFINITY;
            fmax = Double.NEGATIVE_INFINITY;
            imax = -1;
            jmax = -1;
            List<Integer> I0;
            List<Integer> I1;
            I0 = elementosFuera(x);
            I1 = elementosDentro(x);
            // linea 10:
            for (int i : I0) {

                pesoi = funcion.getCapacidad() - x.pesar() - funcion.peso(i);
                calidadi = x.getCalidad() + funcion.contribucion(i, x);
                // linea 11:
                for (int j : I1) {
                    // linea 12:
                    if (tabu[i][j] < iterTabu) {

                        //contribucion
                        frx = calidadi - funcion.contribucion(j, x, i);
                        // peso del articulo
                        vcx = pesoi + funcion.peso(j);

                        ////
////                         linea 13:
//                        x.set(i, 1);
//                        x.set(j, 0);
////                         linea 14:
//                        frx = rawFuncion(x);
//                        // violación de capacidad
//                        double vcx = funcion.violacionDeCapacidad(x);
//                        if ((frx > fmin) && ((vcx >= 0 && (vcx < vmin)) || ((vcx == vmin) && (frx >= fmax)))) {
                        if ((frx > fmin) && (((vcx < vmin)) || ((vcx == vmin) && (frx >= fmax)))) {
                            i_aster = i;
                            j_aster = j;
                            vmin = vcx;
                            fmax = frx;
                            if (vcx >= 0) {
                                jmax = j;
                                imax = i;
                            }
                        }
//                        x.set(i, 0);
//                        x.set(j, 1);
                        contador++;
                    }
                }
            }
            int i;
            Integer j;
            // linea 21:
            if (vmin != Double.POSITIVE_INFINITY) {

                if (imax >= 0 && true) {
                    x.set(imax, 1);
                    x.set(jmax, 0);
                    x_aster = x.clone();
                    fmin = x_aster.getCalidad();
                    list_RL.clear();
                    // linea 25:
                } else {
// linea 22:
                    x.set(i_aster, 1);
                    x.set(j_aster, 0);
                    if (vmin == 0) { // ############################# ==
                        // linea 24:
                        list_RL.clear();
                        fmin = rawFuncion(x);
                        x_aster = x.clone();
                        // linea 25:
                    } else {
                        //linea 26: actualizar estado tabu
                        iterTabu += 1;
                        list_RL.add(i_aster);
                        list_RL.add(j_aster);
                        // linea 27:
                        i = list_RL.size() - 1;
                        // linea 28:
                        list_RCS.clear();
                        while (i >= 0) {
                            // linea 29:
                            j = list_RL.get(i);
//                        if (i % 2 == 1) {
//                            int pos2 = list_RL.get(i - 1);
//                            tabu[j][pos2] = iterTabu + penalizacion;
//                            tabu[pos2][j] = iterTabu + penalizacion;
//                        }
                            if (list_RCS.contains(j)) {
                                boolean ret = list_RCS.remove(j);
                            } else {
                                list_RCS.add(j);
                            }
                            if (list_RCS.size() == 2) {
                                tabu[list_RCS.get(0)][list_RCS.get(1)] = iterTabu + penalizacion;
                                tabu[list_RCS.get(1)][list_RCS.get(0)] = iterTabu + penalizacion;
                            }
                            i--;
                        }
                    }
                }
            }
        }
        return x_aster;

    }

    protected double rawFuncion(IndividuoIHEA individuo) {
        return individuo.evaluar();
    }

    /**
     *
     * @param individuo
     * @param iteraciones
     * @return
     */
    protected IndividuoIHEA perturbacion(IndividuoIHEA individuo, int iteraciones) {
        List<Integer> I1 = elementosDentro(individuo);

        // dimension de individuo
        int dimX = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lb + Math.max(0, (dimX - lb) * (1 - 1 / (0.008 * n))));

        t = Math.min(10, I1.size() - nf);
        s = Math.min(3, t);
        List<Integer> listaIndices = listaIndicesOrdenadosPorDensidad(I1, individuo, t, true);
        int posaleatoria;
        for (int i = 0; i < s; i++) {
            posaleatoria = Aleatorio.nextInt(t);
            individuo.set(listaIndices.get(posaleatoria), 0);
        }
        individuo = GreedyRandomizedConstruction(individuo, rcl);
        return individuo;
    }

    protected class CQKP {

    }

    protected CQKP construirCQKP(int numVarFijas, CQKP cqkp, IndividuoIHEA x_individuo) {

        return cqkp;

    }

    /**
     * Ejecuta un algoritmo gredy aleatorizado a partir de un individuo y el
     * tamaño de la lista restringida de candidatos (rcl)
     *
     * @param individuo
     * @param rcl tamaño de la lista restringida de candidatos
     * @return el individuo mejorado por elalgoritmo gredy
     */
    protected IndividuoIHEA GreedyRandomizedConstruction(IndividuoIHEA individuo, int rcl) {

        return (new Greedy()).ejecutar(individuo, rcl);
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    protected List<Integer> elementosDentro(IndividuoIHEA individuo) {
        return funcion.obtener_I1(individuo);
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I1)
     * en individuo.
     *
     * @param individuo
     * @return List de indices de elementos no seleccionados
     */
    protected List<Integer> elementosFuera(IndividuoIHEA individuo) {
        return funcion.obtener_I0(individuo);
    }

    /**
     * realiza un mejoramiento al individuo utilizando add y swap.
     *
     * @param original
     * @return
     */
    protected IndividuoIHEA descent(IndividuoIHEA original) { ////////////OPCION DE MEJORAR EN TIEMPO (for)
        IndividuoIHEA mejor = (IndividuoIHEA) original.clone();
        int intentosDescent = 20;
        while (intentosDescent-- >= 0) {
            IndividuoIHEA individuo = (IndividuoIHEA) mejor.clone();
            //ADD
            add_factible(individuo);
            if (individuo.compareTo(mejor) > 0) {
                mejor = individuo;
            }
            individuo = (IndividuoIHEA) mejor.clone();
            //SWAP
            swap(individuo);
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
    protected IndividuoIHEA add(IndividuoIHEA individuo) {
        individuo = individuo.clone();
        List<Integer> listaI0 = elementosFuera(individuo);
        cambiarValorAleatoriamente(individuo, listaI0, 1);
        return individuo;
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo de manera tal que
     * el individuo resultante sea factible
     *
     * @param individuo
     * @return indice del elemento adicionado
     */
    protected int add_factible(IndividuoIHEA individuo) {
        List<Integer> listaI0 = elementosFuera(individuo);
        listaI0 = funcion.filtrarPorFactibles(listaI0, individuo);
        return cambiarValorAleatoriamente(individuo, listaI0, 1);
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
    private int cambiarValorAleatoriamente(IndividuoIHEA individuo, List<Integer> listaIndices, int valor) {
        if (listaIndices.isEmpty()) {
            return -1;
        }
        int indice = Aleatorio.nextInt(listaIndices.size());
        individuo.set(listaIndices.get(indice), valor);
        return indice;
    }

    /**
     * realiza un intercambio, agrega un elemento y remueve otro que esta fuera
     * y dentro correspondientemente, de manera aleatoria dentro de individuo,
     * siempre y cuando se pueda, es decir, que se pueda agragar un elemento
     * aleatorio despues de haber removido uno, de lo cntrario no se hace nada.
     * se hace 10 intentos de busqueda de elementos aptos para realizar el
     * intercambio.
     *
     * @param individuo_original a quien se le realiza el intercambio
     * @return
     */
    public IndividuoIHEA swap(IndividuoIHEA individuo_original) {
        IndividuoIHEA individuo = individuo_original.clone();
        int intentosIntercambio = 10;
        List<Integer> listaItemFuera;
        List<Integer> listaItemDentro;
        boolean hayElementosF;
        int contador = intentosIntercambio;
        do { // intentos de busqueda
            listaItemDentro = elementosDentro(individuo);
            int tamLDentro = listaItemDentro.size();
            int aleatorioD = Aleatorio.nextInt(tamLDentro);
            int posicionD = listaItemDentro.get(aleatorioD);
            // sacar elemento dentro de la mochila
            individuo.set(posicionD, 0);
            // obtener lista de elementos dentro de la mochila pero que caben dentro de esta
            listaItemFuera = elementosFuera(individuo);
            // verificar si hay elementos
            hayElementosF = listaItemFuera.isEmpty();
            if (hayElementosF) { // si no hay elementos fuera de la mochila que quepan
                individuo.set(posicionD, 0); // agregar elemento anteriormente removido
            }
        } while (hayElementosF && contador-- >= 0);
        // salir si no hay elementos para intercambiar
        if (hayElementosF) {
            return individuo;
        }
        int tamLDentro = listaItemFuera.size();
        int aleatorioF = Aleatorio.nextInt(tamLDentro);
        int posicionF = listaItemFuera.get(aleatorioF);
        // agregar elemento dentro de la mochila
        individuo.set(posicionF, 1);
        return individuo;
    }

//    /**
//     * realiza un cambio, agrega o remueve un elemento que esta fuera o dentro
//     * correspondientemente, de manera aleatoria dentro de individuo
//     *
//     * @param individuo_original a quien se le realiza el cambio
//     * @return
//     */
//    public IndividuoIHEA adicion(IndividuoIHEA individuo_original) {
//        IndividuoIHEA individuo = individuo_original.clone();
//        List listaFuera = elementosNoSeleccionados(individuo);
//        int posicion;
//        int aleatorio;
//        // tipo de cambio aleatorio y de acuerdo a si la lista correspondiente tiene algun indice
//        if ((Aleatorio.nextBoolean() || listaDentro.isEmpty()) && !listaFuera.isEmpty()) {
//            // elegir posicion aleatoria
//            aleatorio = Aleatorio.nextInt(listaFuera.size());
//            // posicion del elemento dentro la mochila 
//            posicion = (int) listaFuera.get(aleatorio);
//            // realizar cambio
//            individuo.set(posicion, 1);
//        } else {
//            // elegir posicion aleatoria
//            aleatorio = Aleatorio.nextInt(listaDentro.size());
//            // posicion del elemento dentro la mochila 
//            posicion = (int) listaDentro.get(aleatorio);
//            // realizar cambio
//            individuo.set(posicion, 0);
//        }
//        return individuo;
//    }
    /**
     * optinene la dimension o número de elementos(unos) que hay en el
     * individuo.
     *
     * @param individuo
     * @return
     */
    protected int dimensionHiperplano(IndividuoIHEA individuo) {
        return individuo.elementosSeleccionados().size();
    }

    /**
     * obtiene la lista de indices de cada item de individuo ordenados, de
     * acuerdo al parametro ascendente, por densidad (p/w).
     *
     * @param individuo
     * @return
     */
    private List<Integer> listaIndicesOrdenadosPorBeneficio(List<Integer> listaIndices, IndividuoIHEA individuo, boolean ascendente) {
        // almacen de todas las densidades
        double[] densidades = new double[individuo.getDimension()];
        // calcular densidades
        for (Integer indice : listaIndices) {
            // calcular densidad solo de los elementos seleccionados
            densidades[indice] = funcion.densidad(indice, individuo);
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
