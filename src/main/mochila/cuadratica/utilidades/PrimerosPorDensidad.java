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
import java.util.Comparator;
import java.util.List;
import main.utilidades.Utilidades;
import main.Item;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public class PrimerosPorDensidad {

    public List<Item> primeros1(List<Item> listItemNoSeleccionados, int n, boolean minimo) {
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
    
    /**
     * Obtinene los primeros n elementos de listaItems ordenados deacuerdo a "desc". 
     * desc (true: ascendente, False: descendente)
     * @param listaItems lista de origen de elementos
     * @param n cantidad de elementos a selecionar
     * @param desc orden de ordenamiento de lista
     * @return 
     */
    public List<Item> primeros2(final List<Item> listaItems, int n, boolean desc) {
        List<Item> result = new ArrayList();
        int indice;
        List listaObj = result;
        if(n < 0){
           throw  new IllegalArgumentException("n debe ser mayor que 0.");
        }
        int tamListaItems = listaItems.size();
        int tamanio = Math.min(n, tamListaItems);
        for (Item item : listaItems) {
            indice = Utilidades.indiceOrdenadamente(listaObj, item, desc, tamanio);
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

    public List primeros3(List listaElementos, Comparator comparator, int n) {
        List listaResult = new ArrayList();
        int indice;
        int tamListaItems = listaElementos.size();
        int tamanio = Math.min(n, tamListaItems);
        for (Object item : listaElementos) {
            indice = Utilidades.indiceOrdenadamente(listaResult, item, comparator, tamanio);
            if (indice >= 0) {
                listaResult.add(indice, item);
                while (listaResult.size() > tamanio) {
                    listaResult.remove(tamanio);
                }
            }
        }
        return listaResult;
    }

    private List<Item> primeros(List<Item> listaIndices, int n, boolean minimo) {
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

//    public List<Item> obtenerPrimerosAsc(List<Item> listaIndices, int n) {
//        return primeros(listaIndices,  n, true);
//        return primeros1(listaIndices,  n, true);
//        return primeros2(listaIndices,  n, true);
//        return primeros3(listaIndices,  n, true);
//    }
    
    /**
     * obtiene los n primeros indices de los items de mayor(minimo=true) o
     * menor(minimo=false) densidad (aporte/peso) de los elementos
     * (listaIndicces) en la mochila.
     *
     * @TODO considere uso de lista ordenada
     * @param listaIndices
     * @param mochila
     * @param n n-primeros indices
     * @param minimo
     * @return
     */
    public List<Integer> primerosPorDensidad2(final List<Integer> listaIndices, IndividuoCuadratico mochila, int n, boolean minimo) {
//        listaIndices = new ArrayList(listaIndices);
        List<Integer> resultado = new ArrayList();

        // almacen de todas las densidades
        List<Item> densidades = new ArrayList();

        FuncionMochilaCuadratica funcion = (FuncionMochilaCuadratica) mochila.getFuncion();
        // calcular densidades
        for (int indice : listaIndices) {
            densidades.add(new Item(indice, funcion.densidad(indice, mochila)));
        }
        List<Item> litems = primeros2(densidades, n, minimo);
        litems.forEach((item) -> {
            resultado.add(item.getIndice());
        });
        return resultado;
    }

    /**
     * obtiene los n primeros indices de los items de mayor(minimo=true) o
     * menor(minimo=false) densidad (aporte/peso) de los elementos
     * (listaIndicces) en la mochila.
     *
     * @TODO considere uso de lista ordenada
     * @param listaIndices
     * @param mochila
     * @param n n-primeros indices
     * @param minimo
     * @return
     */
    public List<Integer> primerosPorDensidad3(List<Integer> listaIndices, IndividuoCuadratico mochila, int n, boolean minimo) {
        listaIndices = new ArrayList(listaIndices);
        List<Integer> resultado;

        // almacen de todas las densidades
        double[] densidades = new double[mochila.getDimension()];

        FuncionMochilaCuadratica funcion = (FuncionMochilaCuadratica) mochila.getFuncion();
        // calcular densidades
        listaIndices.forEach((indice) -> {
            // calcular densidad solo de los elementos en listaIndices
            densidades[indice] = funcion.densidad(indice, mochila);
        });
        Comparator<Integer> comparator = (Integer o1, Integer o2) -> {
            return Double.compare(densidades[o1], densidades[o2]) * (minimo ? 1 : -1);
        };
        resultado = primeros3(listaIndices, comparator, n);

        return resultado;
    }

    /**
     * obtiene los n primeros indices de los items de mayor(minimo=true) o
     * menor(minimo=false) densidad (aporte/peso) de los elementos
     * (listaIndicces) en la mochila. El proceso se realiza mediante
     *
     * @TODO considere uso de lista ordenada
     * @param listaIndices
     * @param mochila
     * @param n n-primeros indices
     * @param minimo
     * @return
     */
    public List<Integer> primerosPorDensidad(List<Integer> listaIndices, IndividuoCuadratico mochila, int n, boolean minimo) {
        listaIndices = new ArrayList(listaIndices);
        List<Integer> resultado = new ArrayList();
        int indice_guardado = 0;
        double valor;

        // almacen de todas las densidades
        double[] densidades = new double[mochila.getDimension()];
        FuncionMochilaCuadratica funcion = (FuncionMochilaCuadratica) mochila.getFuncion();
        // calcular densidades
        listaIndices.forEach((indice) -> {
            // calcular densidad solo de los elementos en listaIndices
            densidades[indice] = funcion.densidad(indice, mochila);
        });
        int posicionGuardada;
        int indice;
        for (int i = 0; i < n; i++) {
            posicionGuardada = 0;
            indice_guardado = listaIndices.get(posicionGuardada);
            valor = densidades[indice_guardado];
            for (int k = 1; k < listaIndices.size(); k++) {
                indice = listaIndices.get(k);
                if ((minimo && valor > densidades[indice]) || (!minimo && valor < densidades[indice])) {
                    indice_guardado = indice;
                    valor = densidades[indice_guardado];
                    posicionGuardada = k;
                }
            }
            resultado.add(indice_guardado);
            listaIndices.remove(posicionGuardada);
        }
        return resultado;
    }
}
