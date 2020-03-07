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
package main.mochila.cuadratica.sgvns.SGVNS_M3;

import java.util.ArrayList;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.*;
import main.mochila.cuadratica.sgvns.SGVNS_M3.*;
import java.util.List;
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.utilidades.UtilCuadratica;

/**
 *
 * @author debian
 */
public class FuncionSGVNS_M3 extends FuncionSGVNS {
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
    
    private List<Integer> variablesFijas;

    public FuncionSGVNS_M3(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal == null ? null : maxGlobal);
        nombre = "FSGVNS_M3";
        lb = -1;
        ub = -1;
        variablesFijas = new ArrayList<>();
    }
    
    
    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
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
  
      public void fijarVariables(List<Integer> varFijas) {
        variablesFijas = varFijas;
    }

    public void reiniciarFijarVariables() {
        if (variablesFijas != null && !variablesFijas.isEmpty()) {
            variablesFijas.clear();
        }
    }
     public List<Integer> getVariablesFijas() {
        return variablesFijas;
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

}
