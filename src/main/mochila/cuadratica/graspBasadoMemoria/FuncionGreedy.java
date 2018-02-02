/*
 * Copyright (C) 2018 JUAN
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
package main.mochila.cuadratica.graspBasadoMemoria;

import main.mochila.cuadratica.FuncionMochilaCuadratica;
import metaheuristicas.Individuo;

/**
 *
 * @author JUAN
 */
public class FuncionGreedy extends FuncionMochilaCuadratica {

    /**
     *
     * @param matrizBeneficios
     * @param capacidad
     * @param vectorPesos
     * @param maxGlobal
     */
    public FuncionGreedy(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal, 1);
    }

    @Override
    /**
     * Obj(S): el valor de la funcion objetivo con respecto a S
     */
    public double evaluar(Individuo mochila) {
        super.evaluar(mochila);
        double sumaBeneficiosTotal = 0;
        for (int i = 0; i < mochila.getDimension(); i++) {
            for (int j = i; j < mochila.getDimension(); j++) {
                sumaBeneficiosTotal += matrizBeneficios[i][j] * mochila.get(j) * mochila.get(i);
            }
        }
        return sumaBeneficiosTotal;
    }

    /**
     * beneficio del elemento en la posicion: indice no seleccionado
     *
     * @param mochila
     * @param indice indice del elemento
     * @return
     */
    public double voraz(Individuo mochila, int indice) {
        double sumaBeneficiosTotal;
        double sumaPeso = 0;

        if (mochila.get(indice) == 1) {
            throw new IllegalArgumentException("El elemento en la posicion indice ya se encuentra en la mochila");
        }
        sumaBeneficiosTotal = evaluar(mochila);
        for (int i = 0; i < indice; i++) {
            sumaBeneficiosTotal += matrizBeneficios[i][indice] * mochila.get(i) * mochila.get(indice);
        }
        for (int k = indice + 1; k < vectorPesos.length; k++) {
            sumaBeneficiosTotal += matrizBeneficios[indice][k] * mochila.get(k) * mochila.get(indice);
        }
        return (sumaBeneficiosTotal + matrizBeneficios[indice][indice]) / (sumaPeso + vectorPesos[indice]);
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Verifica si el elemento en la posicion indice cabe en la mochila. true si
     * cabe, false si no.
     *
     * @param mochila
     * @param indice
     * @return
     */
    public boolean cabe(Individuo mochila, int indice) {
        return (capacidad - obtenerPeso(mochila, vectorPesos) - vectorPesos[indice]) >= 0;
    }

}
