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

import metaheuristicas.funcion.FuncionGen;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.IndividuoGen;
import main.Utilidades;

/**
 *
 * @author debian
 */
public class Poblacion extends ArrayList<IndividuoGen> {

    private FuncionGen funcion;
    private int tamanioMaximo;
    private int generacion;

    /**
     *
     * @param funcion
     * @param tamanioMaximo
     */
    public Poblacion(FuncionGen funcion, int tamanioMaximo) {
//        mejor = null;
//        poblacion = new ArrayList(tamanioMaximo);
        this.tamanioMaximo = tamanioMaximo;
        this.funcion = funcion;
        this.generacion = 0;
    }

    @Override
    public boolean add(IndividuoGen element) {
        int pos = 0;
        element.setGeneracion(generacion);
//        if (mejor == null || mejor.compareTo(element) < 0) {
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

    public IndividuoGen getMejor() {
        return this.get(0);
    }

    public void evaluar() {
        this.forEach((punto) -> {
            punto.evaluar();
        });
    }

    public FuncionGen getFuncion() {
        return funcion;
    }

    public void setFuncion(FuncionGen funcion) {
        this.funcion = funcion;
    }

    @Override
    public Poblacion clone() {
        Poblacion copia = new Poblacion(funcion, tamanioMaximo);
        this.forEach((punto) -> {
            copia.add(punto.clone());
        });
        return copia;
    }

}
