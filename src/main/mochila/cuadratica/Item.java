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
package main.mochila.cuadratica;

/**
 * Clase que contiene la informacin del un Indice y una caracteristica de tipo
 * double
 */
public class Item implements Comparable<Item> {

    protected int indice;
    protected Double caracteristica;

    public Item(int indice, double calidad) {
        this.indice = indice;
        this.caracteristica = calidad;
    }

    public Double getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Double caracteristica) {
        this.caracteristica = caracteristica;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public int compareTo(Item otroItem) {
        return this.caracteristica.compareTo(otroItem.caracteristica);
    }

    @Override
    public String toString() {
        return "ic:[c: " + this.caracteristica + ",i:" + this.indice + "]"; 
    }

}
