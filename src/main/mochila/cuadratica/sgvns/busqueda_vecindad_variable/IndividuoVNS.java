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
package main.mochila.cuadratica.sgvns.busqueda_vecindad_variable;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public final class IndividuoVNS extends IndividuoCuadratico<FuncionSGVNS_Original> {

    private List<Integer> I1;
    private List<Integer> I0;
    private double[] vec_contribucion;

    public IndividuoVNS(FuncionSGVNS_Original funcion) {
        super(funcion);
//        peso = 0;
//        I1 = new ArrayList();
//        I0 = new ArrayList();
//        for (int i = 0; i < funcion.getDimension(); i++) {
//            I0.add(i);
//        }
        InicializarI0I1();
    }

    public IndividuoVNS(FuncionSGVNS_Original funcion, double[] valores) {
        super(funcion);
//        peso = 0;
//        I1 = new ArrayList();
//        I0 = new ArrayList();
//        for (int i = 0; i < funcion.getDimension(); i++) {
//            I0.add(i);
//        }
        InicializarI0I1();
        for (int i = 0; i < valores.length; i++) {
            this.set(i, valores[i]);
        }
    }

    private void InicializarI0I1() {
        vec_contribucion = new double[funcion.getDimension()];
        for (int i = 0; i < vec_contribucion.length; i++) {
            vec_contribucion[i] = funcion.beneficio(i, i);
        }
        I1 = new ArrayList();
        I0 = new ArrayList();
        for (int i = 0; i < funcion.getDimension(); i++) {
            I0.add(i);
        }
    }

    public double getContribucion(int indice) {
        return vec_contribucion[indice];
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    public List<Integer> obtener_I0() {
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
    public List<Integer> obtener_I1() {
        // Sacar listas de elementos seleccionados y no seleccionados
        List<Integer> listaI1 = new ArrayList(I1);
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
            if (result) {
                I0.add((Integer) indice);
            }
        } else {
            boolean result = I0.remove((Integer) indice);
            if (result) {
                I1.add((Integer) indice);
            }
        }
        double valorPeso;
        double contribucion = 0;
        //contribucion
        contribucion = funcion.contribucion(indice, this);
// actualizacion de la contribucion de cada elemento
        for (int j = 0; j < vec_contribucion.length; j++) {
            if (j < indice) {
                vec_contribucion[j] += (-valAnterior + valor) * funcion.beneficio(j, indice);
            } else if (j > indice) {
                vec_contribucion[j] += (-valAnterior + valor) * funcion.beneficio(indice, j);
            }
        }
//        //contribucion por si solo
//        contribucion += funcion.beneficio(indice, indice);
        // fin contribucion

        // peso del articulo
        valorPeso = funcion.peso(indice);

        // incluir beneficio
        calidad += (-valAnterior + valor) * contribucion;
        // incluir peso del elemento
        peso += (-valAnterior + valor) * valorPeso;

        this.valores[indice] = valor;
    }

    @Override
    public IndividuoVNS clone() {
        IndividuoVNS ind = (IndividuoVNS) super.clone(); //To change body of generated methods, choose Tools | Templates.
        ind.I0 = new ArrayList(ind.I0);
        ind.I1 = new ArrayList(ind.I1);
        ind.vec_contribucion = this.vec_contribucion.clone();
        return ind;
    }

}
