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
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class IntercambiaDosGenes {

    public Individuo mutar(Individuo punto, Random rand) {
        int pos1;
        int pos2;
        double valor1;
        double valor2;
        pos1 = rand.nextInt(punto.getDimension());
        pos2 = rand.nextInt(punto.getDimension());
        valor1 = punto.getValor(pos1);
        valor2 = punto.getValor(pos2);

        punto.set(pos1, (valor2 == 1 ? 0 : 1));
        punto.set(pos2, (valor1 == 1 ? 0 : 1));

        punto.evaluar();
        return punto;
    }
}
