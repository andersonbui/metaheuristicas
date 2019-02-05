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
package main.mochila.cuadratica.hyperplane_exploration.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import main.Item;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.hyperplane_exploration.UtilidadesIHEA;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class Greedy {

    double[] probabilidadBias;
    private final int rcl;

    /**
     *
     * @param rcl
     */
    public Greedy(int rcl) {
        this.rcl = rcl;
        probabilidadBias = calculoProbabilidadBiasRCL(rcl);
    }

    public IndividuoIHEA ejecutar(IndividuoIHEA individuo) {
        //TODO mejorar pasando la lista ya ordenada y no volver a ordenar
        individuo = individuo.clone();
//        List<Item> LRC = crearLRC(individuo, rcl);
        List<Item> LRC = crearLRC(individuo, rcl);

        while (!LRC.isEmpty()) {
            int s = seleccionarElementoConBias(LRC);
            // incluida funcion gredy adapatada
            individuo.set(s, 1);
//            individuo = funcionGreedyAdaptada(individuo);
            LRC = crearLRC(individuo, rcl);
        }
        return individuo;
    }

    /**
     * probabilidad de seleccion de cada elemento en LRC
     *
     * @param rcl
     * @return
     */
    private double[] calculoProbabilidadBiasRCL(int rcl) {
        double[] pBias = new double[rcl];
        double suma = 0;
        for (int j = 0; j < rcl; j++) {
            pBias[j] = 1.0 / Math.exp(j + 1);
            suma += pBias[j];
        }
        for (int j = 0; j < rcl; j++) {
            pBias[j] = pBias[j] / suma;
        }
        return pBias;
    }

    /**
     * Obtiene la lista restringida de candidatos(LRC), de longitud igual al
     * minimo entre (len,listItemNoSeleccionados.size), con los elementos de
     * mayor calidad de la listItemNoSeleccionados.
     *
     * @param individuo
     * @param rcl
     * @return
     */
    protected List<Item> crearLRC(IndividuoIHEA individuo, int rcl) {
        List<Item> listItemNoSeleccionados = obtenerItemsNoSeleccionadosViables(individuo);
        List<Item> LRC = new ArrayList();
        Collections.sort(listItemNoSeleccionados, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return -o1.compareTo(o2);
            }
        });
        int tamL = listItemNoSeleccionados.size();
        int tamanio = Math.min(rcl, tamL);
        for (int i = 0; i < tamanio; i++) {
            LRC.add(listItemNoSeleccionados.get(i));
        }
        return LRC;
    }

    protected List<Item> crearLRC2(IndividuoIHEA individuo, int rcl) {
        List<Item> listItemNoSeleccionados = obtenerItemsNoSeleccionadosViables(individuo);
        List<Item> LRC = new ArrayList();
        int tamL = listItemNoSeleccionados.size();
        int tamanio = Math.min(rcl, tamL);
        Item item_guardado;
        int indice_guardado = -1;
        for (int i = 0; i < tamanio; i++) {
            item_guardado = null;
            for (int h = 0; h < listItemNoSeleccionados.size(); h++) {
                if (item_guardado == null || listItemNoSeleccionados.get(h).compareTo(item_guardado) < 0) {
                    item_guardado = listItemNoSeleccionados.get(h);
                    indice_guardado = h;
                }
            }
            LRC.add(listItemNoSeleccionados.remove(indice_guardado));
        }
        return LRC;
    }

    protected List<Item> crearLRC3(IndividuoIHEA individuo, int rcl) {
        List<Item> listItemNoSeleccionados = obtenerItemsNoSeleccionadosViables(individuo);
        Comparator<Item> comparator = (Item o1, Item o2) -> {
            return o1.compareTo(o2);
        };
        List<Item> listItemResultado = UtilidadesIHEA.primeros3(listItemNoSeleccionados, comparator, rcl);
        return listItemResultado;
    }

    /**
     * Obtiene en una lista los item no seleccionados de mochila, tal que al ser
     * seleccionado cada uno, no se exceda la capacidad de esta.
     *
     * @param mochila
     * @return
     */
    private List<Item> obtenerItemsNoSeleccionadosViables(IndividuoIHEA individuo) {
        List<Integer> noSeleccionadosRS = individuo.elementosNoSeleccionados();
        FuncionMochilaIHEA funcion = (FuncionMochilaIHEA) individuo.getFuncion();
        // dejar los elementos que de ser ingresados hacen viable la solucion
        noSeleccionadosRS = funcion.filtrarPorFactibles(noSeleccionadosRS, individuo);
        List<Item> itemsCalidadNoSeleccionados = new ArrayList();
        //guarda la posicion y su respectiva contribucion
        noSeleccionadosRS.forEach((pos) -> {
            // Evalua la calidad de cada uno de los j-esimo elementos que pertenecen a R(S) con f2(Sj)
            itemsCalidadNoSeleccionados.add(new Item(pos, funcion.densidad(pos, individuo)));
        });
        return itemsCalidadNoSeleccionados;
    }

    /**
     * Obtiene el indice del elemento seleccionado aleatoriamente de acuerdo a
     * las probabilidades Bias almacenadas. br= 1(e**r), (1<= r<= |LRC|)
     *
     * @param LRC lista restringida de candidatos
     * @return indice del elemento en el individuo
     */
    private int seleccionarElementoConBias(List<Item> LRC) {
        double sum_prob = 0;
        double probabilidad = Aleatorio.nextDouble();
        if(LRC == null || LRC.isEmpty()) {
            return -1;
        }
        int minimo = Math.min(LRC.size(), probabilidadBias.length);
        for (int i = 0; i < minimo; i++) {
            sum_prob += probabilidadBias[i];
            if (probabilidad <= sum_prob) {
                return LRC.get(i).getIndice();
            }
        }
        return LRC.get(minimo-1).getIndice();
    }
}
