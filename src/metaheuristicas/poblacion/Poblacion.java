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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;
import pruebas.Utilidades;

/**
 *
 * @author debian
 */
public class Poblacion extends ArrayList<Punto> {

//    protected Punto mejor;
//    protected List<Punto> poblacion;
    private Funcion funcion;
    private int tamanioMaximo;
    private int generacion;

    /**
     *
     * @param funcion
     * @param tamanioMaximo
     */
    public Poblacion(Funcion funcion, int tamanioMaximo) {
//        mejor = null;
//        poblacion = new ArrayList(tamanioMaximo);
        this.tamanioMaximo = tamanioMaximo;
        this.funcion = funcion;
        this.generacion = 0;
    }

    @Override
    public boolean add(Punto element) {
        int pos = 0;
        element.setGeneracion(generacion);
//        if (mejor != null) {
//            mejor = mejor.compareTo(element) >= 0 ? mejor : element;
//        } else {
//            mejor = element;
//        }
        List listapob = this;
        pos = Utilidades.indiceOrdenadamente(listapob, element, false);
        super.add(pos, element);
        if (size() > tamanioMaximo) {
            remove(tamanioMaximo);
        }
        return true;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public void aumentarGeneracion() {
        this.generacion++;
    }
    public int getTamanioMaximo() {
        return tamanioMaximo;
    }

    public void setTamanioMaximo(int tamanioMaximo) {
        this.tamanioMaximo = tamanioMaximo;
    }

    public Punto getMejor() {
        return this.get(0);
    }

    public void evaluar() {
        for (Punto punto : this) {
            punto.setCalidad(funcion.evaluar(punto));
        }
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public static Poblacion generar(Funcion funcion, int tamanioPoblacion, Random rand) {
        Poblacion poblacion = new Poblacion(funcion, tamanioPoblacion);
        Punto p;
        for (int i = 0; i < tamanioPoblacion; i++) {
            p = Punto.generar(funcion, rand);
            poblacion.add(p);
        }
        return poblacion;
    }

    @Override
    public Poblacion clone() {
        Poblacion copia = new Poblacion(funcion, tamanioMaximo);
        for (Punto punto : this) {
            copia.add(punto.clone());
        }
        return copia;
    }

}
