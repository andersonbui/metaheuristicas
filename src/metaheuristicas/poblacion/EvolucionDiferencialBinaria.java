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
package metaheuristicas.poblacion;

import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class EvolucionDiferencialBinaria extends EvolucionDiferencial {

    public EvolucionDiferencialBinaria(int tamPoblacion) {
        super(tamPoblacion);
        nombre = "EstrategiaEvolucionDB";
        cr = 0.5;
        alfa = 1.4;
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
            double valor = punto.get(i);
            valor = Aleatorio.nextDouble() <= sig(valor) ? 1 : 0;
            punto.set(i, valor);
        }
        punto.evaluar();
    }

    public double sig(double x) {
        double result = 1 + Math.exp(-x);
        result = 1 / result;
        return result;

    }
}
