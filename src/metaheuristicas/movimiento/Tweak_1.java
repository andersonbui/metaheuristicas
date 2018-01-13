/*
 * Copyright (C) 2017 debian
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
package metaheuristicas.movimiento;

import metaheuristicas.Funcion;
import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Tweak_1 extends Tweak {

    double ancho;

    /**
     * genera un nuevo punto tomando un otro punto como base y añadiendo un
     * cambio aleatorio limitado por un ancho.
     *
     * @param ancho
     */
    public Tweak_1(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public Individuo tweak(Individuo punto) {
        Individuo nuevop = (Individuo) punto.clone();
        Funcion funcion = punto.getFuncion();
        Double[] valores = nuevop.getValores();
        for (int i = 0; i < valores.length; i++) {
            valores[i] = funcion.limitar(valores[i] + Aleatorio.nextDouble() * ancho * 2 - ancho);
        }
        return nuevop;
    }

    @Override
    public String toString() {
        return "Tweak_1{" + ancho + '}';
    }

}
