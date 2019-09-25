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

/**
 *
 * @author debian
 */
public class FuncionMochilaIHEA_GAR extends FuncionMochilaIHEA {
    
    private double porcentajeCentral = 1;
    private double porcentajeNoCentral = 1;

    public FuncionMochilaIHEA_GAR(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
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
    public IndividuoIHEA_GAR generarIndividuo() {
        IndividuoIHEA_GAR nuevop = new IndividuoIHEA_GAR(this);
        return nuevop;
    }

}
