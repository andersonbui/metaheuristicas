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
package main.mochila;

import java.util.List;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 * @param <Funcion>
 */
public abstract class IndividuoMochila<Funcion extends FuncionMochila> extends IndividuoGen<Funcion> {

    protected double peso;

    public IndividuoMochila(Funcion funcion) {
        super(funcion);
        peso = 0;
    }

    public IndividuoMochila(Funcion funcion, double[] valores) {
        super(funcion, valores);
    }

    @Override
    public IndividuoMochila clone() {
        IndividuoMochila punto = (IndividuoMochila) super.clone();
        punto.valores = valores.clone();
        return punto;
    }

    public double pesar() {
        return funcion.obtenerPeso(this);
    }

    @Override
    public String toString() {
        return "IndividuoMochila{" + "calidad=" + this.calidad + "peso=" + pesar() + '}';
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    public abstract List<Integer> elementosSeleccionados();

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I1)
     * en individuo.
     *
     * @return List de indices de elementos no seleccionados
     */
    public abstract List<Integer> elementosNoSeleccionados();

    
    public int parecido(IndividuoMochila individuo) {
        int suma = 0;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] == individuo.valores[i] && valores[i] == 1) {
                suma++;
            }
        }
        return suma;
    }
}
