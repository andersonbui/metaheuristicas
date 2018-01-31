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
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class FuncionMochilaHyperplaneExploration extends FuncionMochilaCuadratica {

    /**
     * lower bound: minimo numero de elementos que llenarian la mochila sin que
     * haya espacio para uno mas.
     */
    protected int lb;
    /**
     * upper bound: maximo número de elementos que llenarian la mochila sin que
     * haya espacio para uno mas.
     */
    protected int ub;

    public FuncionMochilaHyperplaneExploration(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal, 1);
    }

    /**
     * Beneficio total que trae el elemento ubicado en indice dentro de mochila
     *
     * @param indice
     * @param mochila
     * @return
     */
    protected double contribucion(int indice, Individuo mochila) {
        double suma = 0;
        for (int i = 0; i < indice; i++) {
            suma += matrizBeneficios[i][indice] * mochila.get(indice) * mochila.get(i);
        }
        for (int k = indice + 1; k < vectorPesos.length; k++) {
            suma += matrizBeneficios[indice][k] * mochila.get(indice) * mochila.get(k);
        }
        return suma + matrizBeneficios[indice][indice];
    }

    /**
     * Calcula la densidad de beneficio de adicionar el elemento de la posicion
     * indice en la mochila, sobre el peso del elemento
     *
     * @param indice indice del objeto a adicionar
     * @param mochila
     * @return beneficio-elemento/peso-elemento
     */
    public double densidad(int indice, Individuo mochila) {
        return contribucion(indice, mochila) / vectorPesos[indice];
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * obtiene los valores de lower bound y upper bound
     */
    protected void optener_lb_ub() {
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < this.vectorPesos.length; i++) {
            listaIndices.add(i);
        }
        listaIndices.sort((Integer o1, Integer o2) -> {
            Double peso1 = vectorPesos[o1];
            Double peso2 = vectorPesos[o2];
            // orden decreciente
            return peso2.compareTo(peso1);
        });
        lb = 0;
        int suma_lb = 0;
        ub = 0;
        int suma_ub = 0;
        for (int i = 0, j = listaIndices.size() - 1; i < listaIndices.size(); j--, i++) {
            suma_lb += listaIndices.get(i);
            if (suma_lb <= capacidad) {
                lb++;
            }
            suma_ub += listaIndices.get(j);
            if (suma_ub <= capacidad) {
                ub++;
            }
        }
    }

    /**
     * obtiene el lower bound
     *
     * @return
     */
    public int obtener_lb() {
        if (lb == -1) {
            optener_lb_ub();
        }
        return lb;
    }

    /**
     * obtiene el upper bound
     *
     * @return
     */
    public int obtener_ub() {
        if (ub == -1) {
            optener_lb_ub();
        }
        return ub;
    }

    /**
     * calcula la diferencia entre la capacidad de la mochila y su peso total
     * actual. c: capacidad de la mochila; wi: peso del articulo en la posicion
     * i; n: numero de elementos; sw: sumatoria(wi); i=1,2,...,n
     *
     * @param individuo
     * @return c - sw
     */
    public double violacionDeCapacidad(Individuo individuo) {
        double peso = this.obtenerPeso(individuo, vectorPesos);
        return capacidad - peso;
    }

    public List<Integer> filtrarPorFactibles(List<Integer> listaIndices, Individuo individuo) {
        double espacio = sacarEspacios(individuo);
        List<Integer> listaFactibles = new ArrayList(listaIndices);
        listaFactibles.removeIf((Integer t1) -> {
            return espacio < vectorPesos[t1];
        });
        return listaFactibles;
    }

    /**
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @return
     */
    public double sacarEspacios(Individuo mochila) {
        return capacidad - obtenerPeso(mochila, vectorPesos);
    }

    /**
     * checkea si la operacion swap que incluye a pos_add y pos_sacar como
     * indices de elementos en individuo, produce un individuo factible
     *
     * @param pos_add
     * @param pos_sacar
     * @param individuo
     * @return
     */
    boolean swapFactible(int pos_add, int pos_sacar, Individuo individuo) {
        double espacio = sacarEspacios(individuo);
        espacio -= vectorPesos[pos_sacar];
        return capacidad > (espacio + vectorPesos[pos_add]);
    }

    ////////////////////////////////////////////////////////////////
    public Individuo limitarInferiormente(Individuo mochila, List<Integer> listaOrdenada) {
        double espacios = sacarEspacios(mochila);
        boolean cabeArticulo;
        // en todos los articulos
        for (int pos : listaOrdenada) {
            cabeArticulo = true;
            if (mochila.get(pos) == 0) {
                // dimension de la mochila
                if (espacios < vectorPesos[pos]) {
                    cabeArticulo = false;
                    break;
                }
                if (cabeArticulo) {
                    mochila.set(pos, 1);
                    espacios = sacarEspacios(mochila);
                }
            }
        }
        return mochila;
    }

    /**
     * beneficio del elemento en la posicion: indice
     *
     * @param indice indice del elemento
     * @return
     */
    public double beneficio(int indice) {
        return matrizBeneficios[indice][indice];
    }

}
