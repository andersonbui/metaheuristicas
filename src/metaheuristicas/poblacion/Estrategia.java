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

import metaheuristicas.Funcion;
import java.security.InvalidParameterException;

/**
 *
 * @author debian
 */
public abstract class Estrategia {

    protected int tamPoblacion;
    protected int numDescendientes;
    protected String nombreEstrategia;

    public Estrategia(int tamPoblacion, int numDescendientes, String nombreEstrategia) {
        this.tamPoblacion = tamPoblacion;
        this.numDescendientes = numDescendientes;
        this.nombreEstrategia = nombreEstrategia;
    }

    public abstract Poblacion siguienteGeneracion(int numIndividuosElitismo, Poblacion poblacion);
    //<

    public int getTamPoblacion() {
        return tamPoblacion;
    }

    public void setTamPoblacion(int tamPoblacion) {
        this.tamPoblacion = tamPoblacion;
    }

    public int getNumDescendientes() {
        return numDescendientes;
    }

    public void setNumDescendientes(int numDescendientes) {
        this.numDescendientes = numDescendientes;
    }

    public String getNombreEstrategia() {
        return nombreEstrategia;
    }

    public void setNombreEstrategia(String nombreEstrategia) {
        this.nombreEstrategia = nombreEstrategia;
    }

    public Poblacion generarPoblacion(Funcion funcion) {
        return Poblacion.generar(funcion, tamPoblacion);
    }

    public void elitismo(Poblacion nueva, Poblacion actual, int numIndividuos) {
        if (numIndividuos > actual.size()) {
            throw new InvalidParameterException("numero de parametros invalido: numIndividuos <= atual.size()");
        }
        for (int i = 0; i < numIndividuos; i++) {
            nueva.add(actual.remove(0));
        }
    }

    public void siguiente() {

    }

    public void reiniciar() {

    }

    public boolean haySiguiente() {
        return false;
    }
}
