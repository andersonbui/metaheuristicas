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
    Individuo[] s_supi = new Individuo[iteraciones];
    /**
     * suma de los anteriores optimos locales optenidos en las primeras k-1
     * iteraciones de grasp
     */
    int[] Qk;
    /**
     * f2(S,j): funcion de criterio de ordenamiento de los elementos no
     * seleccionados
     */
    FuncionGreedy funcionGreedy;
    /**
     * contador de iteraciones
     */
    int k;

    public Grasp(String nombre, FuncionGreedy funcionGreedy) {
        super(nombre);
        this.funcionGreedy = funcionGreedy;
        Qk = new int[funcionGreedy.getDimension()];
    }

    @Override

    public List<Individuo> ejecutar(Funcion funcion) {
        Individuo individuo = null;
        Individuo best = null;
        List<Individuo> listaRecorrido = new ArrayList();
        for (k = 0; k < iteraciones; k++) {
//            individuo = faseConstruccion();
            individuo = faseBusquedaLocal(individuo);
            if (best == null || best.compareTo(individuo) < 0) {
                best = individuo;
            }
            listaRecorrido.add(best);
        }
        return listaRecorrido;
    }

    private class ItemCalidad implements Comparable<ItemCalidad> {

        private int indice;
        private Double calidad;

        public ItemCalidad(int indice, double calidad) {
            this.indice = indice;
            this.calidad = calidad;
        }

        @Override
        public int compareTo(ItemCalidad otroItem) {
            return this.calidad.compareTo(otroItem.calidad);
        }

    }

    /**
     * Algorithm 2.Construction phase: CP(MinLen, MaxLen, S)
     *
     * @param minLen
     * @param maxLen
     * @param solParcialS
     * @return
     */
    private Individuo faseConstruccion(int minLen, int maxLen, Individuo solParcialS) {

        Individuo s = solParcialS;
        int l = 1;
        int len = 0;
        //R(S) elementos no seleccionados
        List<Integer> noSeleccionadosRS = obtenerItemNoSelecionado(s);
        List<ItemCalidad> itemsCalidadNoSeleccionados = new ArrayList();
        List<ItemCalidad> listaLRC;
        //Q^k sumatoria 1-(k-1) de optimos locales s^i
        if (k > 0) {
            sumarAOptimosLocales(s_supi[k - 1]);
        }

        while (!noSeleccionadosRS.isEmpty()) {
            for (Integer pos : noSeleccionadosRS) {
                itemsCalidadNoSeleccionados.add(new ItemCalidad(pos, funcionGreedy.voraz(s, pos)));
            }
            //linea 6: Select Len ndomly from[MinLen, MaxLen];
            len = minLen + Aleatorio.nextInt(maxLen - minLen);
            //linea 7: Define RCL^kl as the subset of RCL
            listaLRC = obtenerLRC(itemsCalidadNoSeleccionados, len);
            //linea 8:
            int Qkl = calcularQkl(listaLRC);
            //linea 9-11:
            double[] qkl = probabilidadSeleccionLRC(listaLRC, Qkl);
            //linea 12:

//            [--,---,-----]=1
//            [0-0.2,0.2-0.5,0.5-1],t=1,0..,2(|LRC|)
//            0.45
        }

        return null;
    }

    private Individuo faseBusquedaLocal(Individuo individuo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<ItemCalidad> obtenerLRC(List<ItemCalidad> listItemNoSeleccionados, int len) {
        List<ItemCalidad> LRC = new ArrayList();
        Collections.sort(listItemNoSeleccionados);
        int tamanio = Math.min(len, listItemNoSeleccionados.size());
        for (int i = 0; i < tamanio; i++) {
            LRC.add(listItemNoSeleccionados.get(i));
        }
        return LRC;
    }

    private List<Integer> obtenerItemNoSelecionado(Individuo individuo) {
        List<Integer> itemsNoSeleccionado = new ArrayList();
        for (int i = 0; i < individuo.getDimension(); i++) {
            if (individuo.get(i) == 0) {
                itemsNoSeleccionado.add(i);
            }
        }

        return itemsNoSeleccionado;
    }

    private void sumarAOptimosLocales(Individuo individuo) {
        for (int i = 0; i < Qk.length; i++) {
            Qk[i] += individuo.get(i);
        }
    }

    /**
     * ejemplo: qk[2,3,0,1,0]; Ind[0,0,1,1,0]; LRC[1,4]->i (0,1)->j;
     *
     * @param listaLRC
     * @return
     */
    private int calcularQkl(List<ItemCalidad> listaLRC) {
        int sum = 0;
        for (ItemCalidad item : listaLRC) {
            int i = item.indice;
            sum += k - Qk[i];
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
            qkl[j] = (k - Qk[i]) / Qkl;
        }
        return qkl;
    }

    private double seleccionItemLRC() {

        return 0.;
    }

}
