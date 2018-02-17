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
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;

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
    public double voraz(Individuo mochila, int indice, double obj_S, double w_S) {

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

    /**
     * Verifica si el elemento en la posicion indice cabe en la mochila. true si
     * cabe, false si no.
     *
     * @param mochila
     * @param indice
     * @return
     */
    public boolean cabe(Individuo mochila, int indice) {
        return (capacidad - obtenerPeso(mochila) - vectorPesos[indice]) >= 0;
    }

    /**
     *
     * @param mochila
     * @return
     */
    public double obtenerPeso(Individuo mochila) {
        if (mochila instanceof IndividuoMochila) {
            return ((IndividuoMochila) mochila).getPeso();
        }
        return super.obtenerPeso(mochila, vectorPesos); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    /**
     * Obj(S): el valor de la funcion objetivo con respecto a S
     */
    public double evaluar(Individuo mochila) {

        if (mochila instanceof IndividuoMochila) {
            return ((IndividuoMochila) mochila).getCalidad();
        }
        return super.evaluar(mochila);
    }

    @Override
    public Individuo generarIndividuo() {
        Individuo nuevop = new Individuo(this);
        return nuevop;
    }

    public class IndividuoMochila extends Individuo {

        private double peso;

        public IndividuoMochila(Funcion funcion) {
            super(funcion);
            peso = 0;
        }

        public double getPeso() {
            return peso;
        }

        @Override
        public void set(int indice, double valor) {

            double valAnterior = get(indice);
            if (valAnterior == valor) {
                return;
            }
            double valorPeso;
            double contribucion = 0;
            //contribucion en la fila
            for (int i = 0; i < indice; i++) {
                contribucion += matrizBeneficios[i][indice] * get(i);
            }
            //contribucion en la columna
            for (int k = indice + 1; k < vectorPesos.length; k++) {
                contribucion += matrizBeneficios[indice][k] * get(k);
            }
            //contribucion por si solo
            contribucion += matrizBeneficios[indice][indice];
            // peso del articulo
            valorPeso = vectorPesos[indice];

            // incluir beneficio
            calidad = (-valAnterior + valor) * contribucion;
            // incluir peso del elemento
            peso = (-valAnterior + valor) * valorPeso;

            super.set(indice, valor);
        }

        @Override
        public Individuo clone() {
            IndividuoMochila ind = (IndividuoMochila) super.clone(); //To change body of generated methods, choose Tools | Templates.
            return ind;
        }

    }
}
