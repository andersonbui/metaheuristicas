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
package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Piso extends Funcion {

    public Piso(double limite, int dimension, boolean maximizar) {
        super("PISO", limite, dimension, maximizar);
    }
    double num = 0.5;

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        int resultado = 0;
        Double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            int piso = (int)(valores[i]+num);
            resultado += piso * piso;
        }
        return resultado;
    }

    @Override
    public String toString() {

        return "int(x+" + num + ")**2+int(y+" + num + ")**2";
    }
}
