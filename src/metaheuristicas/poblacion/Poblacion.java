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
package metaheuristicas.poblacion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import metaheuristicas.Punto;
import pruebas.Utilidades;

/**
 *
 * @author debian
 */
public class Poblacion extends Punto {

    protected Punto mejor;
    List<Punto> poblacion;

    public Poblacion(int initialCapacity) {
        super();
        mejor = null;
        poblacion = new ArrayList(initialCapacity);
    }

    public void add(Punto element) {
        int pos = 0;

        if (mejor != null) {
            mejor = mejor.compareTo(element) >= 0 ? mejor : element;
        } else {
            mejor = element;
        }
//        List listapob = poblacion;
//        pos = Utilidades.indiceOrdenadamente(listapob, element, (Punto.ORDEN == 1));
        poblacion.add(pos, element);
    }

    public Punto getMejor() {
        return mejor;
    }

    public void setMejor(Punto mejor) {
        this.mejor = mejor;
    }

    public List<Punto> getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(List<Punto> poblacion) {
        this.poblacion = poblacion;
    }

    @Override
    public double[] getValores() {
        return mejor.getValores();
    }

    @Override
    public void setValores(double[] valores) {
        mejor.setValores(valores);
    }

    @Override
    public double getCalidad() {
        return mejor.getCalidad();
    }

    @Override
    public String getCalidadString() {
        return mejor.getCalidadString();
    }

    @Override
    public void setCalidad(double calidad) {
        mejor.setCalidad(calidad);
    }

    @Override
    public int compareTo(Punto otrop) {
        return mejor.compareTo(otrop);
    }

    public int size() {
        return poblacion.size();
    }

}
