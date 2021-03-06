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
package main.mochila.cuadratica.graspBasadoMemoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.mochila.cuadratica.IndividuoCuadratico;
import main.Item;
import main.mochila.cuadratica.utilidades.UtilCuadratica;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class GraspReinicio extends AlgoritmoMetaheuristico<FuncionGraspTabuR, IndividuoCuadratico> {

    /**
     * almacena los optimos de cada iteracion de Grasp
     */
    List<IndividuoCuadratico> s_sup_i;
    /**
     * Lista con los vectores de la suma de los anteriores optimos locales
     * optenidos en las primeras k-1 iteraciones de grasp
     */
    List<int[]> Q;
    /**
     * contador de iteraciones grasp k
     */
    protected int k;
    protected final int sigma;
    protected final int lamda;
    protected final int gama;
    protected final int beta;

    /**
     *
     * @param funcion
     * @param lamda: numero de iteraciones GRASP
     * @param gama
     * @param beta
     */
    public GraspReinicio(FuncionGraspTabuR funcion, int lamda, int gama, int beta) {
        super();
        nombre = "GraspReinicio";
        this.funcion = funcion;
        Q = new ArrayList();
        s_sup_i = new ArrayList();
        this.sigma = calculo_Sigma();
        this.lamda = lamda;
        this.gama = gama;
        this.beta = beta;
    }

    /**
     * GRASP CON REINICIOS BASADO EN LA MEMORIA
     *
     * numLanda es el numero de reinicios usados para intentar mejorar bestLB
     * numDelta es el numero de iteraciones GRASp
     *
     * @return
     */
    @Override
    public List<IndividuoCuadratico> ejecutar() {
        //linea 1:
        IndividuoCuadratico LB = null;
        IndividuoCuadratico S = null;
        IndividuoCuadratico solParcialS = funcion.generarIndividuo();
//        solParcialS.evaluar();
        IndividuoCuadratico bestLB = solParcialS;

        int cont = 0, m = 0;
        int minLen = gama;
        int maxLen = gama + beta;
        List<IndividuoCuadratico> listaRecorrido = new ArrayList();
        //Linea 2:
        while (cont != lamda && !funcion.suficiente(bestLB)) {
            //Linea 3: maxIteraciones

            for (k = m * sigma + 1; k <= (m + 1) * sigma; k++) {
                //Linea 4:
                //@TODO cosiderar una sacudida de solucion parcial
                S = faseConstruccion(minLen, maxLen, solParcialS);
                //Linea 5:

                S = faseBusquedaLocal(S);
//                s_sup_i.add(S);
                //Linea 6:
                if (bestLB == null || bestLB.compareTo(S) < 0) {
                    bestLB = S;
                }
                s_sup_i.add(bestLB);
                //Linea 7:
            }
            bestLB = busquedaAdicional(bestLB);
//            s_sup_i.set(s_sup_i.size()-1,bestLB);
            //Linea 8:
            if (LB == null || bestLB.compareTo(LB) < 0) {
                //Linea 9:
                LB = bestLB;
                //Linea 10:
                cont = 0;
            } else {
                //Linea 12:
                cont++;
                //Linea 13:
                minLen = maxLen + 1;
                //Linea 14:
                maxLen = maxLen + beta;
            }
            //linea 16: 
            solParcialS = definicionSolParcialS(funcion, m, sigma, Q);
//            solParcialS.evaluar();
            m++;
            listaRecorrido.add(bestLB);
        }
        iteraciones += k;
        return listaRecorrido;
    }

    /**
     * Calculo sigma es utilizada para inicializar el parametro numero de
     * iteraciones GRASP (sigma) segun el paper
     *
     * @return
     */
    public int calculo_Sigma() {
        //Numero de variables 
        int n = funcion.getDimension();
        //Obtencion de Upper Bound
        int v = funcion.upperBound();
        return Math.max((Math.min(v, n - v)), n / 4);
    }

    /**
     * Definicion de la solucion parcial S- linea 16 GRASP(r)
     *
     * @param funcion
     * @param m
     * @param sigma
     * @param lista_Q
     * @return
     */
    protected IndividuoCuadratico definicionSolParcialS(FuncionGraspTabuR funcion, int m, int sigma, List<int[]> lista_Q) {

        int u = m * sigma;
        IndividuoMochilaGraps individuo = (IndividuoMochilaGraps) funcion.generarIndividuo();
        int[] Q_u1 = lista_Q.get(u + 1);
        if (u != 0) {// modificacion
            for (int i = 0; i < individuo.getDimension(); i++) {
                if (Q_u1[i] == u) {
                    individuo.set(i, 1);
                }
            }
        }
        return individuo;
    }

    protected IndividuoCuadratico busquedaAdicional(IndividuoCuadratico bestLB) {
        return bestLB;
    }

    private double obtenerPeso(IndividuoCuadratico solucion) {
        return solucion.pesar();
    }

    /**
     * Algorithm 2.Construction phase: CP(MinLen, MaxLen, S) Se realiza una fase
     * de construcción para construir una solución factible del QKP para cada
     * iteración de GRASP.
     *
     * @param minLen parametro usado para definir la longitud de la LRC
     * @param maxLen
     * @param solParcialS
     * @return
     */
    protected IndividuoCuadratico faseConstruccion(int minLen, int maxLen, IndividuoCuadratico solParcialS) {
        //Linea 1:
//        IndividuoCuadratico S = solParcialS.clone();
        IndividuoCuadratico S = solParcialS;
        int l = 1;
        int len;
        //Qk es un vector de enteros con la suma de los optimos locales s^i obtenido en el k-1 para k>1 iteraciones GRASP;
        calculoQk();
        //R(S): noSeleccionadosRS es el conjunto de indece de elementos no seleccionados que caben en la mochila con respecto a S
        List<Integer> noSeleccionadosRS = obtenerItemsNoSelecionadosFiltrados(S);
        //
        List<Item> itemsCalidadNoSeleccionados = new ArrayList();
        //
        List<Item> listaLRC;
        //Linea 2:
        while (!noSeleccionadosRS.isEmpty()) {
            itemsCalidadNoSeleccionados.clear();
            double obj_S = S.getCalidad();
            double w_S = obtenerPeso(S);
            //Linea 3
            for (Integer pos : noSeleccionadosRS) {
                //Linea 4: Evalua la calidad de cada uno de los j-esimo elementos que pertenecen a R(S) con f2(Sj)
                itemsCalidadNoSeleccionados.add(new Item(pos, -funcion.voraz(S, pos, obj_S, w_S)));
            }
            //luci 3148795 206
            //linea 6: Selecciona el tamaño de la LRC aleatoriamente Len del rango [MinLen, MaxLen];
            len = minLen + Aleatorio.nextInt(maxLen - minLen);
            //linea 7: Define RCL^kl as the subset of RCL con los elementos de de mayor calidad de R(S);
            listaLRC = obtenerLRC(itemsCalidadNoSeleccionados, len);
            //linea 8: Qkl escalar utilizado para garantizar que la sumatoria de la probabilidad qkl sub j sea =1;
            int Qkl = calcularQkl(listaLRC);
            //linea 9-11: qkl es la probabilidad de seleccion del j-esimo elemento de la LRC;
            double[] qkl = probabilidadSeleccionLRC(listaLRC, Qkl);
            //linea 12:
            S.set(seleccionItemDeLRC(Qkl, qkl, listaLRC).getIndice(), 1);
//            S.evaluar();
            //linea 13:
            l++;

            noSeleccionadosRS = obtenerItemsNoSelecionadosFiltrados(S);
        }
        return S;
    }

    /**
     *
     * @param s
     * @return
     */
    protected IndividuoCuadratico faseBusquedaLocal(IndividuoCuadratico s) {
        //Linea 1:
        IndividuoCuadratico solucionShift;
        IndividuoCuadratico solucionSwap;
        //Linea 2:
        boolean termina = true;
        //Linea 3:
        while (termina) {
            //Linea 4: Explora el vecindario de S con movimientos Shift y swap 
            solucionShift = shift(s);

            solucionSwap = UtilCuadratica.swap(s);
            //Linea 5: si encuentra una mejora realiza el mejor movimiento Shift o Swap  
            if (solucionShift != null && solucionShift.compareTo(s) > 0) {
                if (solucionShift.compareTo(solucionSwap) > 0) {
                    s = solucionShift;
                } else {
                    s = solucionSwap;
                }
            } else if (solucionSwap != null && solucionSwap.compareTo(s) > 0) {
                s = solucionSwap;
            } //Linea 7: 
            else {
                break;
            }
        }
        //Linea 11:
        return s;
    }

    /**
     * Obtiene la lista restringida de candidatos(LRC), de longitud igual al
     * minimo entre (len,listItemNoSeleccionados.size), con los elementos de
     * mayor calidad de la listItemNoSeleccionados.
     *
     * @param listItemNoSeleccionados
     * @param len
     * @return
     */
    protected List<Item> obtenerLRC(List<Item> listItemNoSeleccionados, int len) {
        List<Item> LRC = new ArrayList();
        //@TODO sort poco eficiente para este uso
        Collections.sort(listItemNoSeleccionados);
        int tamL = listItemNoSeleccionados.size();
        int tamanio = Math.min(len, tamL);
        for (int i = 0; i < tamanio; i++) {
            LRC.add(listItemNoSeleccionados.get(i));
        }
        return LRC;
        //len 8
    }

    /**
     * Obtiene en una lista los item no seleccionados de mochila, tal que al ser
     * seleccionado cada uno, no se exceda la capacidad de esta.
     *
     * @param mochila
     * @return
     */
    protected List<Integer> obtenerItemsNoSelecionadosFiltrados(IndividuoCuadratico mochila) {
        List<Integer> itemsNoSeleccionado = new ArrayList();
        for (int i = 0; i < mochila.getDimension(); i++) {
            if (mochila.get(i) == 0) {
                if (funcion.cabe(mochila, i)) {
                    itemsNoSeleccionado.add(i);
                }
            }
        }
        return itemsNoSeleccionado;
    }

    /**
     * Obtiene los items no seleccionados actualmente en la mochila
     *
     * @param mochila
     * @return
     */
    protected List<Integer> obtenerItemsSelecionados(IndividuoCuadratico mochila) {
        List<Integer> itemsSeleccionado = new ArrayList();
        for (int i = 0; i < mochila.getDimension(); i++) {
            if (mochila.get(i) == 1) {
                itemsSeleccionado.add(i);
            }
        }
        return itemsSeleccionado;
    }

    /**
     *
     */
    protected void calculoQk() {
        double[] valores;
        if (k > 1) {
            //se obtienen el valor del optimo local de la posisicion [k-1]
            valores = s_sup_i.get(k - 2).getValores();
            int[] qk_1 = Q.get(k - 1).clone();
            for (int i = 0; i < qk_1.length; i++) {
                qk_1[i] += valores[i];
            }
            Q.add(qk_1);
        } else {
            Q.clear();
            //@TODO considerar inicializar uno de estos vectores en 1 cada elemento
            Q.add(new int[funcion.getDimension()]);
            Q.add(new int[funcion.getDimension()]);
        }
    }

    /**
     * [1,1,0,0,0] [1,1,0,1,0] [0,1,0,0,0] ejemplo: qk[2,3,0,1,0];
     * Individuo[0,0,1,1,0]; LRC[1,4]->i (0,1)->j; Qkl es la sumatoria de los
     * optimos locales. Este es utilizado para garantizar que la sumatoria de la
     * probabilidad qkl[j] sea igual a 1.
     *
     * @param listaLRC
     * @return
     */
    protected int calcularQkl(List<Item> listaLRC) {
        int sum = 0;
        for (Item item : listaLRC) {
            int i = item.getIndice();
            sum += k - Q.get(k)[i];
        }
        return sum;
    }

    /**
     * probabilidad de seleccion de cada elemento en LRC
     *
     * @param listaLRC
     * @param Qkl
     * @return
     */
    protected double[] probabilidadSeleccionLRC(List<Item> listaLRC, int Qkl) {
        double[] qkl = new double[listaLRC.size()];
        int indice;

        if (Qkl == 0) {
            double div = 1.0 / listaLRC.size();//mismo porcentaje de eleccion para Qkl==0
            for (int j = 0; j < listaLRC.size(); j++) {
                qkl[j] = div;
            }
        } else {
            for (int j = 0; j < listaLRC.size(); j++) {
                indice = listaLRC.get(j).getIndice();
                qkl[j] = (k - Q.get(k)[indice]) / (double) Qkl;
            }
        }
        return qkl;
    }

    /**
     * un int m es seleccionado aletoriamente de [1,Qkl], si cumple la condición
     * de probabilidad m/Qkl, el elemento i^kl(js) es seleccionado
     *
     * @param Qkl
     * @param qkl
     * @param listaLRC
     * @return
     */
    protected Item seleccionItemDeLRC(int Qkl, double[] qkl, List<Item> listaLRC) {
        int m;
        double prob, sum_qkl = 0;
        m = 1 + (Qkl > 1 ? Aleatorio.nextInt(Qkl - 1) : 0);
//        m = 1;
        prob = m / (double) Qkl;
        for (int i = 0; i < qkl.length; i++) {
            sum_qkl += qkl[i];
            if (prob <= sum_qkl) {
                return listaLRC.get(i);
            }
        }
        return null;
    }

    /**
     * El movimient Shift adicina un elemento no seleccionado a la solución
     * actual o remueve un elemento seleccionado de esta.
     *
     * @param individuo
     * @return
     */
    protected IndividuoCuadratico shift(IndividuoCuadratico individuo) {
        individuo = individuo.clone();
        int aleatorio;
        int maxLen = individuo.getDimension();
        aleatorio = Aleatorio.nextInt(maxLen);
        if (individuo.get(aleatorio) == 1) {
            individuo.set(aleatorio, 0);
        } else if (funcion.cabe(individuo, aleatorio)) {
            individuo.set(aleatorio, 1);
        }
        return individuo;
    }

}
