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

import java.util.ArrayList;
import java.util.List;
import main.mochila.IndividuoMochila;

/**
 *
 * @author debian
 * @param <FuncionSGVNS>
 */
public class IndividuoCuadratico<FuncionSGVNS extends FuncionMochilaCuadratica> extends IndividuoMochila<FuncionSGVNS> {

    public IndividuoCuadratico(FuncionSGVNS funcion) {
        super(funcion);
        peso = 0;
    }

    public IndividuoCuadratico(FuncionSGVNS funcion, double[] valores) {
        super(funcion, valores);
    }

    @Override
    public double pesar() {
        return peso;
    }

    @Override
    public double evaluar() {
        return calidad;
    }

    /**
     * genera una lista de los elementos que estan fuera de la mochila
     *
     * @return
     */
    @Override
    public List elementosNoSeleccionados() {
        List listaI0 = new ArrayList();
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] == 0) {
                listaI0.add(i);
            }
        }
        return listaI0;
    }

    /**
     * genera una lista de los elementos que estan dentro de la mochila
     *
     * @return
     */
    @Override
    public List elementosSeleccionados() {
        List listaI1 = new ArrayList();
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] == 1) {
                listaI1.add(i);
            }
        }
        return listaI1;
    }

    @Override
    public  void set(int indice, double valor) {

        double valAnterior = get(indice);
        if (valAnterior == valor) {
            return;
        }
        //contribucion
        double contribucion = funcion.contribucion(indice, this);
        // peso del articulo
        double valorPeso = funcion.peso(indice);

        // incluir beneficio
        calidad += (-valAnterior + valor) * contribucion;
        // incluir peso del elemento
        peso += (-valAnterior + valor) * valorPeso;

        super.set(indice, valor);
    }

    @Override
    public IndividuoCuadratico clone() {
        IndividuoCuadratico ind = (IndividuoCuadratico) super.clone(); //To change body of generated methods, choose Tools | Templates.
        return ind;
    }

    @Override
    public String toString() {
        return "IndividuoMochila{" + "calidad=" + this.calidad + "; peso=" + pesar() + "; capacidad=" + funcion.getCapacidad() +'}';
    }
}
//    #######################
//    public class IndividuoCuadratico extends IndividuoMochila {
//
//        private List<Integer> I1;
//        private List<Integer> I0;
//
//        public IndividuoCuadratico(FuncionMochila funcion) {
//            super(funcion);
//            peso = 0;
//            I1 = new ArrayList();
//            I0 = new ArrayList();
//            for (int i = 0; i < funcion.getDimension(); i++) {
//                I0.add(i);
//            }
//        }
//
//        /**
//         * procedimeinto que obtiene la lista de los elementos seleccionados
//         * (I1) en individuo.
//         *
//         * @return
//         */
//        public List<Integer> obtener_I0() {
//            // Sacar listas de elementos seleccionados y no seleccionados
//            List<Integer> listaI0 = new ArrayList(I0);
//            return listaI0;
//        }
//
//        /**
//         * procedimeinto que obtiene la lista de los elementos seleccionados
//         * (I1) en individuo.
//         *
//         * @return
//         */
//        public List<Integer> obtener_I1() {
//            // Sacar listas de elementos seleccionados y no seleccionados
//            List<Integer> listaI1 = new ArrayList(I1);
//            return listaI1;
//        }
//
//        @Override
//        public double pesar() {
//            return peso;
//        }
//
//        @Override
//        public double evaluar() {
//            return calidad;
//        }
//
//        @Override
//        public void set(int indice, double valor) {
//            double valAnterior = get(indice);
//            if (valAnterior == valor) {
//                return;
//            }
//            if (valor == 0) {
//                boolean result = I1.remove((Integer) indice);
//                if (result) {
//                    I0.add((Integer) indice);
//                }
//            } else {
//                boolean result = I0.remove((Integer) indice);
//                if (result) {
//                    I1.add((Integer) indice);
//                }
//            }
//            double valorPeso;
//            double contribucion = 0;
//
//            for (int i : I1) {
//                if (i < indice) {
//                    //contribucion en la fila
//                    contribucion += matrizBeneficios[i][indice];
//                } else if (i > indice) {
//                    //contribucion en la columna
//                    contribucion += matrizBeneficios[indice][i];
//                }
//            }
//            //contribucion por si solo
//            contribucion += matrizBeneficios[indice][indice];
//            // peso del articulo
//            valorPeso = vectorPesos[indice];
//
//            // incluir beneficio
//            calidad += (-valAnterior + valor) * contribucion;
//            // incluir peso del elemento
//            peso += (-valAnterior + valor) * valorPeso;
//
//            super.set(indice, valor);
//        }
//
//        @Override
//        public IndividuoMochila clone() {
//            IndividuoCuadratico ind = (IndividuoCuadratico) super.clone(); //To change body of generated methods, choose Tools | Templates.
//            ind.I0 = new ArrayList(ind.I0);
//            ind.I1 = new ArrayList(ind.I1);
//            return ind;
//        }
//
//    }

//    public class IndividuoCuadratico extends IndividuoMochila {
//
//        private double peso;
//
//        public IndividuoCuadratico(FuncionMochila funcion) {
//            super(funcion);
//            peso = 0;
//        }
//
//        @Override
//        public double pesar() {
//            return peso;
//        }
//
//        @Override
//        public double evaluar() {
//            return calidad;
//        }
//
//        @Override
//        public void set(int indice, double valor) {
//
//            double valAnterior = get(indice);
//            if (valAnterior == valor) {
//                return;
//            }
//            double valorPeso;
//            double contribucion = 0;
//            //contribucion en la fila
//            for (int i = 0; i < indice; i++) {
//                contribucion += matrizBeneficios[i][indice] * get(i);
//            }
//            //contribucion en la columna
//            for (int k = indice + 1; k < vectorPesos.length; k++) {
//                contribucion += matrizBeneficios[indice][k] * get(k);
//            }
//            //contribucion por si solo
//            contribucion += matrizBeneficios[indice][indice];
//            // peso del articulo
//            valorPeso = vectorPesos[indice];
//
//            // incluir beneficio
//            calidad += (-valAnterior + valor) * contribucion;
//            // incluir peso del elemento
//            peso += (-valAnterior + valor) * valorPeso;
//
//            super.set(indice, valor);
//        }
//
//        @Override
//        public IndividuoMochila clone() {
//            IndividuoCuadratico ind = (IndividuoCuadratico) super.clone(); //To change body of generated methods, choose Tools | Templates.
//            return ind;
//        }
//
//    }
