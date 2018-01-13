/*
 * Copyright (C) 2018 debian
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
package metaheuristicas;

/**
 *
 * @author debian
 */
public abstract class Viajante implements Comparable<Viajante>, Cloneable {

    protected Funcion funcion;
    protected int generacion;
    protected boolean maximizar; //-1 minimizar, +1 maximizar

    public Viajante(Funcion funcion, boolean maximizar) {
        this.funcion = funcion;
        this.maximizar = maximizar;
    }

    public int getDimension() {
        return funcion.getDimension();
    }

    public abstract double getCalidad();

    @Override
    public abstract String toString();

    public abstract void evaluar();

    public abstract Individuo getOptimo();

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

}
