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
package main.mochila.cuadratica.sgvns;

import java.util.List;
import main.mochila.IndividuoMochila;

/**
 *
 * @author Juan Diaz PC
 */
public class FuncionJSGVNS extends FuncionSGVNS{
    
    private List<Integer> variablesFijas;
    
    public FuncionJSGVNS(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        variablesFijas = null;
        this.nombre ="FJSGVNS";
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
//        if (this.variablesFijas != null && !this.variablesFijas.isEmpty()) {
//            seleccionados.removeAll(this.variablesFijas);
//        }
        return seleccionados;
    }
    
    public void fijarVariables(List<Integer> varFijas) {
        variablesFijas = varFijas;
    }

    public void reiniciarVijarVariables() {
        variablesFijas.clear();
    }

    protected List<Integer> getVariablesFijas() {
        return variablesFijas;
    }

    
}
