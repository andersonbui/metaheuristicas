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
import main.mochila.FuncionMochila;
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.UtilCuadratica;

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
     * obtiene el lower bound
     *
     * @return
     */
    public int obtenerLowerBound() {
        if (lb == -1) {
            int[] lu_b = UtilCuadratica.optenerLowerUpper_Bound(this);
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
        List seleccionados = indi.elementosSeleccionados();
        seleccionados.removeAll(this.variablesFijas);
        return seleccionados;
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
