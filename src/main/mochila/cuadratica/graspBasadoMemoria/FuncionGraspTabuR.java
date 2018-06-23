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

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author JUAN
 */
public class FuncionGraspTabuR extends FuncionMochilaCuadratica {

    /**
     *
     * @param matrizBeneficios
     * @param capacidad
     * @param vectorPesos
     * @param maxGlobal
     */
    public FuncionGraspTabuR(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal, 1);
    }

    /**
     * beneficio del elemento en la posicion: indice no seleccionado
     *
     * @param mochila
     * @param indice indice del elemento
     * @param obj_S
     * @param w_S
     * @return
     */
    public double voraz(IndividuoGen mochila, int indice, double obj_S, double w_S) {

        if (mochila.get(indice) == 1) {
            throw new IllegalArgumentException("El elemento en la posicion indice ya se encuentra en la mochila");
        }
        for (int i = 0; i < indice; i++) {
            obj_S += matrizBeneficios[i][indice] * mochila.get(i);
        }
        for (int k = indice + 1; k < vectorPesos.length; k++) {
            obj_S += matrizBeneficios[indice][k] * mochila.get(k);
        }
        return (obj_S + matrizBeneficios[indice][indice]) / (w_S + vectorPesos[indice]);
    }

    @Override
    public String toString() {
        return nombre;
    }

    public int upperBound() {
        int v;
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < vectorPesos.length; i++) {
            listaIndices.add(i);
        }
        //ordenacionde elementos
        listaIndices.sort((Integer o1, Integer o2) -> {
            Double peso1 = vectorPesos[o1];
            Double peso2 = vectorPesos[o2];
            // orden creciente
            return peso1.compareTo(peso2);
        });
        v = 0;
        int suma_V = 0;
        for (int i = 0; i < listaIndices.size(); i++) {
            suma_V += vectorPesos[listaIndices.get(i)];
            if (suma_V <= capacidad) {
                v++;
            } else {
                break;
            }
        }
        return v;
    }

    @Override
    public IndividuoCuadratico generarIndividuo() {
        IndividuoCuadratico nuevop = new IndividuoMochilaGraps(this);
        return nuevop;
    }

}
