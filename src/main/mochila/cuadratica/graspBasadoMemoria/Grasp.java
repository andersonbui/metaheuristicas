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

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Grasp extends AlgoritmoMetaheuristico {

    /**
     * almacena los optimos de cada iteracion de Grasp
     */
    Individuo[] s_sup_i = new Individuo[iteraciones];
    /**
     * Lista con los vectores de la suma de los anteriores optimos locales optenidos en las primeras k-1
     * iteraciones de grasp
     */
    List<int[]> Q;
    /**
     * f2(S,j): funcion de criterio de ordenamiento de los elementos no
     * seleccionados
     */
    FuncionGreedy funcionGreedy;
    /**
     * contador de iteraciones
     */
    int k;
    int gama;

    public Grasp(String nombre, FuncionGreedy funcionGreedy, int gama) {
        super(nombre);
        this.funcionGreedy = funcionGreedy;
        Q = new ArrayList();
        this.gama = gama;
    }

    @Override

//    public List<Individuo> ejecutar(Funcion funcion) {
//        Individuo individuo = null;
//        Individuo best = null;
//        int maxLen=0,minLen=0;
//        List<Individuo> listaRecorrido = new ArrayList();
//        for (k = 0; k < iteraciones; k++) {
//            individuo = faseConstruccion(minLen, maxLen, individuo);
//            individuo = faseBusquedaLocal(individuo);
//            if (best == null || best.compareTo(individuo) < 0) {
//                best = individuo;
//            }
//            listaRecorrido.add(best);
//        }
//        return listaRecorrido;
//    }   
    /**
     * GRASP CON REINICIOS BASADO EN LA MEMORIA
     *
     * numLanda es el numero de reinicios usados para intentar mejorar bestLB
     * numDelta es el numero de iteraciones GRASp
     *
     */
    public List<Individuo> ejecutar(Funcion funcion) {
        //linea 1:
        Individuo LB = null;
        Individuo S = null;
        Individuo bestLB = null;
        Individuo solParcialS = null;
        int numLanda = 10;
        int cont = 0, m = 0;
        int num_y = 0, num_b = 0;
        int minLen = num_y;
        int maxLen = num_y + num_b;
        List<Individuo> listaRecorrido = new ArrayList();
        //Linea 2:
        while (cont != numLanda) {
            //Linea 3: maxIteracionesen 

            for (k = 0; k < (m + 1) * gama; k++) {
                //Linea 4:
                S = faseConstruccion(minLen, maxLen, solParcialS);
                //Linea 5:
                LB = faseBusquedaLocal(S);
                LB.evaluar();
                //Linea 6:
                if (bestLB == null || bestLB.compareTo(LB) < 0) {
                    bestLB = LB;
                }
                //Linea 7:
            }
            //Linea 8:
            if (bestLB.compareTo(LB) < 0) {
                //Linea 9:
                bestLB = LB;
                //Linea 10:
                cont = 0;
            } else {
                //Linea 12:
                cont++;
                //Linea 13:
                minLen = maxLen + 1;
                //Linea 14:
                maxLen = maxLen + num_b;
            }
            //linea 16: 
            solParcialS = definicionSolParcialS(funcion, m, gama);
            m++;
            listaRecorrido.add(bestLB);
        }
        return listaRecorrido;
    }

    /**
     * Definicion de la solucion parcial S- linea 16 GRASP(r)
     *
     * @param funcion
     * @param m
     * @param gama
     */
    private Individuo definicionSolParcialS(Funcion funcion, int m, int gama) {
        int u = m * gama;
        Individuo individuo = new Individuo(funcion);
        int[] Q_u1 = Q.get(u + 1);
        for (int i = 0; i < individuo.getDimension(); i++) {
            if (Q_u1[i] == u) {
                individuo.set(i, 1);
            }
        }
        return individuo;
    }

    /**
     * Clase interna ItemCalidad que cntiene la informacin del Indice y la
     * Calidad de un individuo especifico
     */
    private class ItemCalidad implements Comparable<ItemCalidad> {

        private final int indice;
        private Double calidad;

        public ItemCalidad(int indice, double calidad) {
            this.indice = indice;
            this.calidad = calidad;
        }

        @Override
        /**
         * 
         */
        public int compareTo(ItemCalidad otroItem) {
            return this.calidad.compareTo(otroItem.calidad);
        }
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
    private Individuo faseConstruccion(int minLen, int maxLen, Individuo solParcialS) {
        //Linea 1:
        Individuo S = solParcialS;
        int l = 1;
        int len = 0;
        //Qk es un vector de enteros con la suma de los optimos locales s^i obtenido en el k-1 para k>1 iteraciones GRASP
        calculoQk();
        //R(S): noSeleccionadosRS es el conjunto de indece de elementos no seleccionados que caben en la mochila con respecto a S
        List<Integer> noSeleccionadosRS = obtenerItemNoSelecionado(S);
        //
        List<ItemCalidad> itemsCalidadNoSeleccionados = new ArrayList();
        //
        List<ItemCalidad> listaLRC;
        //Linea 2:
        while (!noSeleccionadosRS.isEmpty()) {
            //Linea 3
            for (Integer pos : noSeleccionadosRS) {
                //Linea 4: Evalua la calidad de cada uno de los j-esimo elementos que pertenecen a R(S) con f2(Sj)
                itemsCalidadNoSeleccionados.add(new ItemCalidad(pos, funcionGreedy.voraz(S, pos)));
            }
            //linea 6: Selecciona el tamaño de la LRC aleatoriamente Len del rango [MinLen, MaxLen];
            len = minLen + Aleatorio.nextInt(maxLen - minLen);
            //linea 7: Define RCL^kl as the subset of RCL con los elementos de de mayor calidad de R(S)
            listaLRC = obtenerLRC(itemsCalidadNoSeleccionados, len);
            //linea 8: Qkl escalar utilizado para garantizar que la sumatoria de la probabilidad qkl sub j sea =1
            int Qkl = calcularQkl(listaLRC);
            //linea 9-11: qkl es la probabilidad de seleccion del j-esimo elemento de la LRC
            double[] qkl = probabilidadSeleccionLRC(listaLRC, Qkl);
            //linea 12:
            S.set(seleccionItemDeLRC(Qkl, qkl, listaLRC).indice, 1);
            //linea 13:
            l++;
        }
        return S;
    }

    /**
     *
     * @param s
     * @return
     */
    private Individuo faseBusquedaLocal(Individuo s) {
        //Linea 1:
        double solucionS = funcionGreedy.evaluar(s), solucionShift = 0, solucionSwap = 0;
        //Linea 2:
        boolean termina = false;
        //Linea 3:
        while (termina != false) {
            //Linea 4: Explora el vecindario de S con movimientos Shift y swap 
            shift(s);
            solucionShift = funcionGreedy.evaluar(shift(s));
            swap(s);
            solucionSwap = funcionGreedy.evaluar(swap(s));
            //Linea 5: si encuentra una mejora realiza el mejor movimiento Shift o Swap  
            if (solucionShift > solucionS) {
                if (solucionShift > solucionSwap) {
                    s = shift(s);
                }
            } else if (solucionSwap > solucionS) {
                if (solucionSwap > solucionS) {
                    s = swap(s);
                }
            } //Linea 7: 
            else {
                termina = true;
            }
        }
        //Linea 11:
        return s;
    }

    /**
     *
     * @param listItemNoSeleccionados
     * @param len
     * @return
     */
    private List<ItemCalidad> obtenerLRC(List<ItemCalidad> listItemNoSeleccionados, int len) {
        List<ItemCalidad> LRC = new ArrayList();
        Collections.sort(listItemNoSeleccionados);
        int tamanio = Math.min(len, listItemNoSeleccionados.size());
        for (int i = 0; i < tamanio; i++) {
            LRC.add(listItemNoSeleccionados.get(i));
        }
        return LRC;
    }

    /**
     *
     * @param individuo
     * @return
     */
    private List<Integer> obtenerItemNoSelecionado(Individuo individuo) {
        List<Integer> itemsNoSeleccionado = new ArrayList();
        for (int i = 0; i < individuo.getDimension(); i++) {
            if (individuo.get(i) == 0) {
                if (funcionGreedy.cabe(individuo, i)) {
                    itemsNoSeleccionado.add(i);
                }
            }
        }
        return itemsNoSeleccionado;
    }

    /**
     *
     * @param individuo
     * @return
     */
    private List<Integer> obtenerItemSelecionado(Individuo individuo) {
        List<Integer> itemsSeleccionado = new ArrayList();
        for (int i = 0; i < individuo.getDimension(); i++) {
            if (individuo.get(i) == 1) {
                itemsSeleccionado.add(i);
            }
        }
        return itemsSeleccionado;
    }

    /**
     *
     */
    private void calculoQk() {
        double[] valores;
        if (k > 0) {
            //se obtienen el valor del optimo local de la posisicion [k-1]
            valores = s_sup_i[k - 1].getValores();
            int[] qk_1 = Q.get(k - 1).clone();
            for (int i = 0; i < Q.size(); i++) {
                qk_1[i] += valores[i];
            }
            Q.add(k, qk_1);
        } else {
            Q.add(new int[funcionGreedy.getDimension()]);
        }
    }

    /**
     * [1,1,0,0,0] [1,1,0,1,0] [0,1,0,0,0] ejemplo: qk[2,3,0,1,0];
     * Ind[0,0,1,1,0]; LRC[1,4]->i (0,1)->j;
     *
     * @param listaLRC
     * @return
     */
    private int calcularQkl(List<ItemCalidad> listaLRC) {
        int sum = 0;
        for (ItemCalidad item : listaLRC) {
            int i = item.indice;
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
    private double[] probabilidadSeleccionLRC(List<ItemCalidad> listaLRC, int Qkl) {
        double[] qkl = new double[listaLRC.size()];
        for (int j = 0; j < listaLRC.size(); j++) {
            int i = listaLRC.get(j).indice;
            qkl[j] = (k - Q.get(k)[i]) / Qkl;
        }
        return qkl;
    }

    /**
     * un int m es seleccionado aletoriamente de [1,Qkl], si cumple la condición
     * de probabilidad m/Qkl el elemento i^kl(js)
     *
     * @param Qkl
     * @param qkl
     * @param listaLRC
     * @return
     */
    private ItemCalidad seleccionItemDeLRC(int Qkl, double[] qkl, List<ItemCalidad> listaLRC) {
        int m;
        double prob, sum_qkl = 0;
        m = 1 + Aleatorio.nextInt(Qkl - 1);
        prob = m / (double) Qkl;
        for (int i = 0; i < qkl.length; i++) {
            sum_qkl += qkl[i];
            if (sum_qkl < prob) {
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
     * @param listItemNoSeleccionados
     * @return
     */
    private Individuo shift(Individuo individuo) {
        int aleatorio = 0, maxLen = individuo.getDimension();
        aleatorio = Aleatorio.nextInt(maxLen);
        if (individuo.get(aleatorio) == 1) {
            individuo.set(aleatorio, 0);
        } else {
            individuo.set(aleatorio, 1);
        }

        return individuo;
    }

    /**
     * El movimiento Swap reemplaza un elemento seleccionado por uno no
     * seleccionado
     *
     * @param individuo
     * @param listItemNoSeleccionados
     * @return
     */
    private Individuo swap(Individuo individuo) {

        int maxLen = individuo.getDimension();
        int aleatorio = Aleatorio.nextInt(maxLen);
        List<Integer> listaItemNoSelect = new ArrayList();
        for (int i = 0; i < individuo.getDimension(); i++) {
            if (individuo.get(i) == 0) {
                listaItemNoSelect.add(i);
            }
        }
        int maxLenNS = listaItemNoSelect.size();
        int aleatrioNS = Aleatorio.nextInt(maxLenNS);
        if (individuo.get(aleatorio) == 1) {
            individuo.set(aleatorio, listaItemNoSelect.get(aleatrioNS));
        }
//            lisNS[0,1,3]-> 1
//            [0,0,1,0,1]->2
        return individuo;
    }

}
