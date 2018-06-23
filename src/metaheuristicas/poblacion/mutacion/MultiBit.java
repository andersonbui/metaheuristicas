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
package metaheuristicas.poblacion.mutacion;

import java.util.Random;
import metaheuristicas.Aleatorio;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class MultiBit {

    /**
     *
     * @param punto
     * @param rand
     * @param probabilidad de ser mutado un bit
     * @return
     */
    public IndividuoGen mutar(IndividuoGen punto, double probabilidad) {
        int valor;
        for (int i = 0; i < punto.getDimension(); i++) {
            if (probabilidad > Aleatorio.nextDouble()) {
                valor = (int) punto.get(i);
                punto.set(i, (valor == 1 ? 0 : 1));
            }
        }
        punto.evaluar();
        return punto;
    }
}
