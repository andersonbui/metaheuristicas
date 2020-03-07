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
package main.mochila.cuadratica.IHEA.IHEA_M4;

import main.mochila.cuadratica.IHEA.hyperplane_exploration_mejorado.*;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import java.util.ArrayList;
import java.util.List;
import main.mochila.IndividuoMochila;

/**
 *
 * @author debian
 */
public class FuncionMochilaIHEA_M4 extends FuncionMochilaIHEA {

    private List<Integer> variablesIgnoradas;

    public FuncionMochilaIHEA_M4(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        variablesIgnoradas = null;
    }

    public void fijarVariablesMalas(List<Integer> varFijas) {
        variablesIgnoradas = varFijas;
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I0)
     * en individuo.
     *
     * @param individuo s * @return List de indices de elementos no
     * seleccionados
     * @return
     */
    @Override
    public List<Integer> obtener_I0(IndividuoMochila individuo) {
        IndividuoIHEA indi = ((IndividuoIHEA) individuo);
        List noseleccionados = indi.elementosNoSeleccionados();
        if (this.variablesIgnoradas != null && !this.variablesIgnoradas.isEmpty()) {
            noseleccionados.removeAll(this.variablesIgnoradas);
        }
        return noseleccionados;
    }

    @Override
    public void reiniciarFijarVariables() {

        if (variablesIgnoradas != null) {
            variablesIgnoradas.clear();
        }
        variablesIgnoradas = null;
        getVariablesFijas().clear();
    }

    public List<Integer> getVariablesIgnoradas() {
        return variablesIgnoradas;
    }
    
}
