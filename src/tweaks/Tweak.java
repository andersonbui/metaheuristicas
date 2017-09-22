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

import funciones.Funcion;
import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public abstract class Tweak {

    protected Random rand;
    protected Funcion funcion;

    public abstract List<Punto> ejecutar(Punto p, double paso);

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    
      /**
     * variacion de un i entre [-ancho, ancho]
     *
     * @param i
     * @param ancho
     * @return
     */
    public double tweaki(double i, double ancho) {
        double nuevop = funcion.limitar(i + rand.nextDouble() * ancho * 2 - ancho);
        return nuevop;
    }
}
