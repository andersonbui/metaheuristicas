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
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class DeGen {

    public Punto mutar(Punto punto, Random rand, double limite) {
        double valor;
        // improve
        int posicion = rand.nextInt(punto.getDimension());
        valor = rand.nextDouble() * limite * 2 - limite;
        punto.set(posicion, valor);
        punto.evaluar();
        return punto;
    }
}
