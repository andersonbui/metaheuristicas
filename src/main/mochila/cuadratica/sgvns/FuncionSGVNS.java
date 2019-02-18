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
package main.mochila.cuadratica.sgvns;

import java.util.List;
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;

/**
 *
 * @author debian
 */
public class FuncionSGVNS extends FuncionMochilaCuadratica {

    public FuncionSGVNS(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal == null ? null : maxGlobal, 1);
        nombre = "FSGVNS";
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @return
     */
    public double sacarEspacios(IndividuoVNS mochila) {
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
        IndividuoVNS indi =  (IndividuoVNS) individuo;
        List seleccionados = indi.elementosSeleccionados();
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
        List seleccionados = indi.elementosNoSeleccionados();
        seleccionados = filtrarPorFactibles(seleccionados, indi);
        return seleccionados;
    }
    
    @Override
    public double contribucion(int indice, IndividuoCuadratico mochila) {
        IndividuoVNS individuo = (IndividuoVNS) mochila;
        return individuo.getContribucion(indice);
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
    public IndividuoVNS generarIndividuo() {
        IndividuoVNS nuevop = new IndividuoVNS(this);
        return nuevop;
    }

}
