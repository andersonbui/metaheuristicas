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

import funciones.Funcion;
import java.security.InvalidParameterException;
import java.util.Random;

/**
 *
 * @author debian
 */
public abstract class Estrategia {

    private int tamPoblacion;
    private int numDescendientes;
    private String nombreEstrategia;

    public Estrategia(int tamPoblacion, int numDescendientes) {
        this.tamPoblacion = tamPoblacion;
        this.numDescendientes = numDescendientes;
    }

    public abstract Poblacion siguienteGeneracion(int numIndividuosElitismo, Poblacion poblacion, Random rand);

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

    public Poblacion generarPoblacion(Funcion funcion, Random rand) {
        return Poblacion.generar(funcion, tamPoblacion, rand);
    }

    public void elitismo(Poblacion nueva, Poblacion actual, int numIndividuos) {
        if (numIndividuos > actual.size()) {
            throw new InvalidParameterException("numero de parametros invalido: numIndividuos <= atual.size()");
        }
        for (int i = 0; i < numIndividuos; i++) {
            nueva.add(actual.remove(0));
        }
    }
}
