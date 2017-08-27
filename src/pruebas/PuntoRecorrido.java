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
package pruebas;

import java.util.ArrayList;
import java.util.List;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class PuntoRecorrido implements Comparable<PuntoRecorrido> {
    public Punto mejorPunto;
    public List<Punto> recorrido;

    public PuntoRecorrido(Punto mejorPunto, List<Punto> recorrido) {
        this.mejorPunto = mejorPunto;
        this.recorrido = recorrido;
    }

    @Override
    public int compareTo(PuntoRecorrido o) {
        return this.mejorPunto.compareTo(o.mejorPunto);
    }

    public Punto getMejorPunto() {
        return mejorPunto;
    }

    public List<Punto> getRecorrido() {
        return recorrido;
    }

    @Override
    public String toString() {
        return mejorPunto.toString();
    }
    
}
