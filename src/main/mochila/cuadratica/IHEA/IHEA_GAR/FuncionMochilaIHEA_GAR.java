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
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public class FuncionMochilaIHEA_GAR extends FuncionMochilaIHEA {
    
    private double porcentajeCentral = 1;
    private double porcentajeNoCentral = 1;

    public FuncionMochilaIHEA_GAR(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal == null ? null : maxGlobal);
    }

    public double getPorcentajeCentral() {
        return porcentajeCentral;
    }

    public void setPorcentajeCentral(double porcentajeCentral) {
        this.porcentajeCentral = porcentajeCentral;
    }

    public double getPorcentajeNoCentral() {
        return porcentajeNoCentral;
    }

    public void setPorcentajeNoCentral(double porcentajeNoCentral) {
        this.porcentajeNoCentral = porcentajeNoCentral;
    }

    @Override
    public IndividuoIHEA generarIndividuo() {
        IndividuoIHEA nuevop = new IndividuoIHEA(this);
        return nuevop;
    }

    @Override
    public double contribucion(int indice, IndividuoCuadratico mochila) {
        IndividuoIHEA individuo = (IndividuoIHEA) mochila;
        return individuo.getContribucion(indice);
    }

    @Override
    public double contribucion(int indice, IndividuoCuadratico mochila, Integer sin_k) {
        double contribMejor = contribucion(indice, mochila);
        double beneficioQuitar = indice > sin_k ? this.beneficio(sin_k, indice) : this.beneficio(indice, sin_k);
        contribMejor -= beneficioQuitar;
        return contribMejor;
    }
}
