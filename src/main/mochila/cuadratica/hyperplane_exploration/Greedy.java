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
import java.util.Collections;
import java.util.List;
import main.mochila.cuadratica.Item;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
class Greedy {

    protected IndividuoIHEA ejecutar(IndividuoIHEA individuo, int rcl) {
        individuo = individuo.clone();
        List<Item> LRC = crearLRC(individuo, rcl);
        while (!LRC.isEmpty()) {
            int s = seleccionarElementoaleatorio(LRC);
            // incluida funcion gredy adapatada
            individuo.set(s, 1);
//            individuo = funcionGreedyAdaptada(individuo);
            LRC = crearLRC(individuo, rcl);
        }
        return individuo;
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
        Collections.sort(listItemNoSeleccionados);
        int tamL = listItemNoSeleccionados.size();
        int tamanio = Math.min(rcl, tamL);
        for (int i = 0; i < tamanio; i++) {
            LRC.add(listItemNoSeleccionados.get(i));
        }
        return LRC;
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

    private int seleccionarElementoaleatorio(List<Item> LRC) {
        int tamRCL = LRC.size();
        int posicion = Aleatorio.nextInt(tamRCL);
        return LRC.get(posicion).getIndice();
    }

}
