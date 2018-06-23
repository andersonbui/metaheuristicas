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
package main.mochila.cuadratica.hyperplane_exploration;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public class IndividuoIHEA extends IndividuoCuadratico<FuncionMochilaIHEA> {

    private List<Integer> I1;
    private List<Integer> I0;

    public IndividuoIHEA(FuncionMochilaIHEA funcion) {
        super(funcion);
        peso = 0;
        I1 = new ArrayList();
        I0 = new ArrayList();
        for (int i = 0; i < funcion.getDimension(); i++) {
            I0.add(i);
        }
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    @Override
    public List<Integer> elementosNoSeleccionados() {
        // Sacar listas de elementos seleccionados y no seleccionados
        List<Integer> listaI0 = new ArrayList(I0);
        return listaI0;
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    @Override
    public List<Integer> elementosSeleccionados() {
        // Sacar listas de elementos seleccionados y no seleccionados
        List<Integer> listaI1 = new ArrayList(I1);
        listaI1.removeAll(funcion.getVariablesFijas());
        return listaI1;
    }

    @Override
    public double pesar() {
        return peso;
    }

    @Override
    public double evaluar() {
        return calidad;
    }

    @Override
    public void set(int indice, double valor) {

        double valAnterior = get(indice);
        if (valAnterior == valor) {
            return;
        }
        if (valor == 0) {
            boolean result = I1.remove((Integer) indice);
            I0.add((Integer) indice);
        } else {
            boolean result = I0.remove((Integer) indice);
            I1.add((Integer) indice);
        }
        double valorPeso;
        double contribucion;
        //contribucion
        contribucion = funcion.contribucion(indice, this);

        // peso del articulo
        valorPeso = funcion.peso(indice);

        // incluir beneficio
        calidad += (-valAnterior + valor) * contribucion;
        // incluir peso del elemento
        peso += (-valAnterior + valor) * valorPeso;

        this.valores[indice] = valor;
    }

    @Override
    public IndividuoIHEA clone() {
        IndividuoIHEA ind = (IndividuoIHEA) super.clone(); //To change body of generated methods, choose Tools | Templates.
        ind.I0 = new ArrayList(ind.I0);
        ind.I1 = new ArrayList(ind.I1);
        return ind;
    }

    @Override
    public String toString() {
        return "IndividuoCuadratico{" + "calidad=" + this.calidad + "peso=" + pesar() + "capacidad=" + ((FuncionMochilaIHEA) funcion).getCapacidad() + '}';
    }
}
