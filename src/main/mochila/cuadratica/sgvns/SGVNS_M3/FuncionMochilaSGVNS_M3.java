/*
 * Copyright (C) 2019 Juan Diaz PC
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
package main.mochila.cuadratica.sgvns.SGVNS_M3;

import java.util.ArrayList;
import java.util.List;
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.FuncionSGVNS_Original;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;

/**
 *
 * @author Juan Diaz PC
 */
public class FuncionMochilaSGVNS_M3 extends FuncionSGVNS_Original {

    private List<Integer> variablesFijas;
    private List<Integer> variablesFijasMalas;

    public FuncionMochilaSGVNS_M3(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        variablesFijas = null;
        variablesFijasMalas = new ArrayList<>();
        this.nombre = "FJSGVNS";
    }

    public void fijarVariablesMalas(List<Integer> varFijas) {
        variablesFijasMalas = varFijas;
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    @Override
    public List<Integer> obtener_I1(IndividuoMochila individuo) {
        IndividuoVNS indi = ((IndividuoVNS) individuo);
        List seleccionados = indi.elementosSeleccionados();
        if (this.variablesFijas != null && !this.variablesFijas.isEmpty()) {
            seleccionados.removeAll(this.variablesFijas);
        }
        return seleccionados;
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I0)
     * en individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    @Override
    public List<Integer> obtener_I0(IndividuoMochila individuo) {
        IndividuoVNS indi = ((IndividuoVNS) individuo);
        List noseleccionados = indi.elementosNoSeleccionados();
        if (this.variablesFijas != null && !this.variablesFijasMalas.isEmpty()) {
            noseleccionados.removeAll(this.variablesFijasMalas);
        }
        return noseleccionados;
    }

    public void fijarVariables(List<Integer> varFijas) {
        variablesFijas = varFijas;
    }

    public void reiniciarFijarVariables() {
        if (variablesFijas != null && !variablesFijas.isEmpty()) {
            variablesFijas.clear();
        }
    }

    public void reiniciarFijarVariablesUpperb() {
        variablesFijasMalas.clear();
    }

    protected List<Integer> getVariablesFijas() {
        return variablesFijas;
    }

}
