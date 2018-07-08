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
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.FuncionMochilaCuadratica;

/**
 *
 * @author debian
 */
public class FuncionMochilaIHEA extends FuncionMochilaCuadratica {

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

    private final List<Integer> variablesFijas;

    public FuncionMochilaIHEA(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal, 1);
        lb = -1;
        ub = -1;
        variablesFijas = new ArrayList();
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * obtiene los valores de lower bound y upper bound
     *
     * @return double [lower_bound, upper_bound]
     */
    protected int[] optenerLowerUpper_Bound() {
        int lowerB;
        int upperB;
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < this.vectorPesos.length; i++) {
            listaIndices.add(i);
        }
        //ordenacionde elementos
        listaIndices.sort((Integer o1, Integer o2) -> {
            Double peso1 = vectorPesos[o1];
            Double peso2 = vectorPesos[o2];
            // orden decreciente
            return -peso1.compareTo(peso2);
        });
        lowerB = 0;
        int suma_lb = 0;
        upperB = 0;
        int suma_ub = 0;
        for (int i = 0, j = listaIndices.size() - 1; i < listaIndices.size(); j--, i++) {
            suma_lb += vectorPesos[listaIndices.get(i)];
            if (suma_lb <= capacidad) {
                lowerB++;
            }
            suma_ub += vectorPesos[listaIndices.get(j)];
            if (suma_ub <= capacidad) {
                upperB++;
            }
        }
        return new int[]{lowerB, upperB};
    }

    /**
     * obtiene el lower bound
     *
     * @return
     */
    public int obtenerLowerBound() {
        if (lb == -1) {
            int[] lu_b = optenerLowerUpper_Bound();
            lb = lu_b[0];
            ub = lu_b[1];
        }
        return lb;
    }

    /**
     * obtiene el upper bound
     *
     * @return
     */
    public int obtenerUpperBound() {
        if (ub == -1) {
            obtenerLowerBound();
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
    public double violacionDeCapacidad(IndividuoMochila individuo) {
        return capacidad - individuo.pesar();
    }

//    /**
//     * checkea si la operacion swap que incluye a pos_add y pos_sacar como
//     * indices de elementos en individuo, produce un individuo factible
//     *
//     * @param pos_add
//     * @param pos_sacar
//     * @param individuo
//     * @return
//     */
//    boolean swapFactible(int pos_add, int pos_sacar, IndividuoIHEA individuo) {
//        if (!(individuo.get(pos_add) == 0 && individuo.get(pos_sacar) == 1)) {
//            throw new InvalidParameterException(
//                    "pos_add debe ser de un elemento no seleccionado, y"
//                    + " pos_sacar de un elemento seleccionado");
//        }
//        double espacio = sacarEspacios(individuo);
//        return espacio >= (vectorPesos[pos_add] - vectorPesos[pos_sacar]);
//    }
    /**
     * agrega elementos al espacio sobrante de la mochila
     *
     * @param mochila
     * @param listaOrdenada
     * @return
     */
    public IndividuoMochila limitarInferiormente(IndividuoMochila mochila, List<Integer> listaOrdenada) {
        double espacios = sacarEspacios((IndividuoIHEA) mochila);
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
                    espacios -= vectorPesos[pos];
                }
            }
        }
        return mochila;
    }

    /**
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @return
     */
    public double sacarEspacios(IndividuoIHEA mochila) {
        return capacidad - mochila.pesar();
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    public List<Integer> obtener_I1(IndividuoMochila individuo) {
        IndividuoIHEA indi = ((IndividuoIHEA) individuo);
        return indi.elementosSeleccionados();
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I0)
     * en individuo.
     *
     * @param individuo s * @return List de indices de elementos no
     * seleccionados
     * @return
     */
    public List<Integer> obtener_I0(IndividuoMochila individuo) {
        IndividuoIHEA indi = ((IndividuoIHEA) individuo);
        return indi.elementosNoSeleccionados();
    }

    /**
     *
     * @param mochila
     * @return
     */
    public double obtenerPeso(IndividuoIHEA mochila) {
        return mochila.pesar();
    }

    @Override
    public IndividuoIHEA generarIndividuo() {
        IndividuoIHEA nuevop = new IndividuoIHEA(this);
        return nuevop;
    }

    public void fijarVariables(IndividuoMochila individuo, int[] varFijas) {
        for (int varFija : varFijas) {
            variablesFijas.add(varFija);
        }
    }

    public void reiniciarVijarVariables() {
        variablesFijas.clear();
    }

    protected int getLb() {
        return lb;
    }

    protected int getUb() {
        return ub;
    }

    protected List<Integer> getVariablesFijas() {
        return variablesFijas;
    }

    

}
