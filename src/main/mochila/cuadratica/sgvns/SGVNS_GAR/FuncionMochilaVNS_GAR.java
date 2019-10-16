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
package main.mochila.cuadratica.sgvns.SGVNS_GAR;

import main.mochila.cuadratica.sgvns.FuncionJSGVNS;
import main.mochila.cuadratica.sgvns.IndividuoVNS;


/**
 *
 * @author debian
 */
public class FuncionMochilaVNS_GAR extends FuncionJSGVNS {
    
    private double porcentajeCentral = 1;
    private double porcentajeNoCentral = 1;

    public FuncionMochilaVNS_GAR(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
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
    public IndividuoVNS generarIndividuo() {
        IndividuoVNS nuevop = new IndividuoVNS(this);
        return nuevop;
    }

}
