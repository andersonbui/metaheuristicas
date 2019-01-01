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
package main.mochila.cuadratica.utilidades;

/**
 *
 * @author debian
 */
public class Posicion {

    public int posicion;
    public double peso;
    public double relacion;
    public double beneficio;

    public Posicion(int posicion, double peso, double relacion, double beneficio) {
        this.posicion = posicion;
        this.peso = peso;
        this.relacion = relacion;
        this.beneficio = beneficio;
    }
}
