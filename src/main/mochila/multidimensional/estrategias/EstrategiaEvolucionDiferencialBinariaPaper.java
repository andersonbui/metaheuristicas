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
package main.mochila.multidimensional.estrategias;

import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencial;
import metaheuristicas.poblacion.Poblacion;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialBinariaPaper extends EstrategiaEvolucionDiferencial {

    private double b;

    public EstrategiaEvolucionDiferencialBinariaPaper(int tamPoblacion) {
        super(tamPoblacion);
        nombreEstrategia = "EstrategiaEDB_Paper";
        cr = 0.2;
        alfa = 0.8;
        b = 20;
    }

    @Override
    protected Individuo mutar(Poblacion poblacion) {
        Individuo mutado = super.mutar(poblacion);
        //variacion para problema de la mochila
        normalizar(mutado);
        return mutado;
    }

    public void normalizar(Individuo punto) {
        for (int i = 0; i < punto.getDimension(); i++) {
            double valor = punto.getValor(i);
            valor = Aleatorio.nextDouble() <= sig(valor) ? 1 : 0;
            punto.set(i, valor);
        }
    }

    public double sig(double x) {
        double result = 1 + Math.exp(-2 * b * (x - 0.5) / (1 + 2 * alfa));
        result = 1 / result;
        return result;

    }
}
