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
package main;

import java.util.List;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class OptimoYRecorrido implements Comparable<OptimoYRecorrido> {
    public Punto punto;
    public List<Punto> recorrido;

    public OptimoYRecorrido(Punto punto, List<Punto> recorrido) {
        this.punto = punto;
        this.recorrido = recorrido;
    }

    @Override
    public int compareTo(OptimoYRecorrido o) {
        return this.punto.compareTo(o.punto);
    }

    public Punto getPunto() {
        return punto;
    }

    public List<Punto> getRecorrido() {
        return recorrido;
    }

    @Override
    public String toString() {
        return punto.toString();
    }
    
}
