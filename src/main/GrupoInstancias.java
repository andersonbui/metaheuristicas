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
package main;

/**
 *
 * @author debian
 */
public class GrupoInstancias {

    public String ubicacion;
    public String base;
    public int cantidad;
    public int inicio;

    /**
     *
     * @param ubicacion
     * @param base
     * @param cantidad
     * @param inicio
     */
    public GrupoInstancias(String ubicacion, String base, int inicio, int cantidad) {
        this.ubicacion = ubicacion;
        this.base = base;
        this.cantidad = cantidad;
        this.inicio = inicio;
    }

}