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
import java.util.Comparator;
import java.util.List;
import main.utilidades.Utilidades;
import main.Item;

/**
 *
 * @author debian
 */
public class UtilidadesIHEA {

    public static List<Item> primeros1(List<Item> listItemNoSeleccionados, int n, boolean minimo) {
        List<Item> LRC = new ArrayList();
        Collections.sort(listItemNoSeleccionados, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return (minimo ? 1 : -1) * o1.compareTo(o2);
            }
        });
        int tamL = listItemNoSeleccionados.size();
        int tamanio = Math.min(n, tamL);
        for (int i = 0; i < tamanio; i++) {
            LRC.add(listItemNoSeleccionados.get(i));
        }
        return LRC;
    }

    public static List<Item> primeros2(List<Item> listaItems, int n, boolean minimo) {
        List<Item> result = new ArrayList();
        int indice;
        List listaObj = result;
//        Item item;

        int tamListaItems = listaItems.size();
        int tamanio = Math.min(n, tamListaItems);
        for (Item item : listaItems) {
            indice = Utilidades.indiceOrdenadamente(listaObj, item, minimo, tamanio);
//            indice = Utilidades.indiceOrdenadamente(listaObj, item, minimo);
            if (indice >= 0) {
                listaObj.add(indice, item);
                while (listaObj.size() > tamanio) {
                    listaObj.remove(tamanio);
                }
            }

        }
        return result;
    }

    public static List primeros3(List listaElementos, Comparator comparator, int n) {
        List listaResult = new ArrayList();
        int indice;
        int tamListaItems = listaElementos.size();
        int tamanio = Math.min(n, tamListaItems);
        for (Object item : listaElementos) {
            indice = Utilidades.indiceOrdenadamente(listaResult, item, comparator,tamanio);
            if (indice >= 0) {
                listaResult.add(indice, item);
                while (listaResult.size() > tamanio) {
                    listaResult.remove(tamanio);
                }
            }
        }
        return listaResult;
    }

    public static List<Item> primeros(List<Item> listaIndices, int n, boolean minimo) {
        listaIndices = new ArrayList<>(listaIndices);
        List<Item> resultado = new ArrayList();
//        ascendente = !ascendente;
        int numero = Math.min(n, listaIndices.size());
        Item buscado;
        Item actual;
        int posicionGuardada = 0;
        for (int i = 0; i < numero; i++) {
            buscado = null;
            for (int k = 0; k < listaIndices.size(); k++) {
                actual = listaIndices.get(k);
                if ((buscado == null)
                        || ((minimo && buscado.compareTo(actual) > 0)
                        || (!minimo && buscado.compareTo(actual) < 0))) {
                    buscado = actual;
                    posicionGuardada = k;
                }
            }
            resultado.add(buscado);
            listaIndices.remove(posicionGuardada);
        }
        return resultado;
    }
    
}
