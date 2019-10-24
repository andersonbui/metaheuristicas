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
package main.mochila.cuadratica.IHEA.IHEA_GAR;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.*;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;

/**
 *
 * @author debian
 */
public class IndividuoIHEA_GAR extends IndividuoIHEA {

    public IndividuoIHEA_GAR(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    public IndividuoIHEA_GAR(FuncionMochilaIHEA funcion, double[] valores) {
        super(funcion, valores);
    }

    public FuncionMochilaIHEA_GAR getFuncion() {
        return (FuncionMochilaIHEA_GAR)funcion;
    }
    
    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    @Override
    public List<Integer> elementosNoSeleccionados() {
        // Sacar listas de elementos seleccionados y no seleccionados
        List<Integer> listaI0 = new ArrayList(getI0());
        return listaI0;
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    @Override
    public List<Integer> elementosSeleccionados() {
        // Sacar listas de elementos seleccionados y no seleccionados
        List<Integer> listaI1 = new ArrayList(getI1());
        return listaI1;
    }

    @Override
    public double getContribucion(int indice) {
        double contri = getVec_contribucion()[indice];
        double calidadCentral = getFuncion().beneficio(indice, indice);
        double porcentajeCentral = getFuncion().getPorcentajeCentral();
        double porcentajeNoCentral = getFuncion().getPorcentajeNoCentral();
        return (calidadCentral * porcentajeCentral) + ((contri - calidadCentral) * (porcentajeNoCentral));
    }

    @Override
    public double getCalidad() {
        return evaluar();
    }
    
   @Override
    public double evaluar() {
//        double longitud = getFuncion().logitud(this);
//        longitud = longitud / getFuncion().getDimension();
        double calidadCentral = 0;
        for (Integer integer : getI1()) {
            calidadCentral += getFuncion().beneficio(integer, integer);
        }
        double porcentajeCentral = getFuncion().getPorcentajeCentral();
        double porcentajeNoCentral = getFuncion().getPorcentajeNoCentral();
        return ((calidadCentral * porcentajeCentral) + ((calidad - calidadCentral) * (porcentajeNoCentral)));
    }

    @Override
    public IndividuoIHEA_GAR clone() {
        IndividuoIHEA_GAR ind = (IndividuoIHEA_GAR) super.clone();
        return ind;
    }

}
