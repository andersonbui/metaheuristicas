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
package tweaks;

import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public abstract class Tweak {

    public abstract Punto tweak(Punto punto, Random rand);

    /**
     * variacion de un i entre [-ancho, ancho]
     *
     * @param i
     * @param ancho
     * @param rand
     * @return
     */
    public double genValor(double i, double ancho, Random rand) {
        return i + rand.nextDouble() * ancho * 2 - ancho;
    }
}
