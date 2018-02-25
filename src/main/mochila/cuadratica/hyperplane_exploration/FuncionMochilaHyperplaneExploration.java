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

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class FuncionMochilaHyperplaneExploration extends FuncionMochilaCuadratica {

    /**
     * lower bound: minimo numero de elementos que llenarian la mochila sin que
     * haya espacio para uno mas.
     */
    protected int lb;
    /**
     * upper bound: maximo número de elementos que llenarian la mochila sin que
     * haya espacio para uno mas.
     */
    protected int ub;

    public FuncionMochilaHyperplaneExploration(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal, 1);
        lb = -1;
        ub = -1;
    }

    /**
     * Beneficio total que trae el elemento ubicado en indice dentro de mochila
     *
     * @param indice
     * @param mochila
     * @return
     */
    protected double contribucion(int indice, Individuo mochila) {
        double suma = 0;
        for (int i = 0; i < indice; i++) {
            suma += matrizBeneficios[i][indice] * mochila.get(i);
        }
        for (int k = indice + 1; k < vectorPesos.length; k++) {
            suma += matrizBeneficios[indice][k] * mochila.get(k);
        }
        return suma + matrizBeneficios[indice][indice];
    }

    /**
     * Calcula la densidad de beneficio de adicionar el elemento de la posicion
     * indice en la mochila, sobre el peso del elemento
     *
     * @param indice indice del objeto a adicionar
     * @param mochila
     * @return beneficio-elemento/peso-elemento
     */
    public double densidad(int indice, Individuo mochila) {
        return contribucion(indice, mochila) / vectorPesos[indice];
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * obtiene los valores de lower bound y upper bound
     *
     * @return double [lower_bound, upper_bound]
     */
    protected int[] optener_lb_ub() {
        int lowerB;
        int upperB;
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < this.vectorPesos.length; i++) {
            listaIndices.add(i);
        }
        //ordenacionde elementos
        listaIndices.sort((Integer o1, Integer o2) -> {
            Double peso1 = vectorPesos[o1];
            Double peso2 = vectorPesos[o2];
            // orden decreciente
            return -peso1.compareTo(peso2);
        });
        lowerB = 0;
        int suma_lb = 0;
        upperB = 0;
        int suma_ub = 0;
        for (int i = 0, j = listaIndices.size() - 1; i < listaIndices.size(); j--, i++) {
            suma_lb += vectorPesos[listaIndices.get(i)];
            if (suma_lb <= capacidad) {
                lowerB++;
            }
            suma_ub += vectorPesos[listaIndices.get(j)];
            if (suma_ub <= capacidad) {
                upperB++;
            }
        }
        return new int[]{lowerB, upperB};
    }

    /**
     * obtiene el lower bound
     *
     * @return
     */
    public int obtener_lb() {
        if (lb == -1) {
            int[] lu_b = optener_lb_ub();
            lb = lu_b[0];
            ub = lu_b[1];
        }
        return lb;
    }

    /**
     * obtiene el upper bound
     *
     * @return
     */
    public int obtener_ub() {
        if (ub == -1) {
            obtener_lb();
        }
        return ub;
    }

    /**
     * calcula la diferencia entre la capacidad de la mochila y su peso total
     * actual. c: capacidad de la mochila; wi: peso del articulo en la posicion
     * i; n: numero de elementos; sw: sumatoria(wi); i=1,2,...,n
     *
     * @param individuo
     * @return c - sw
     */
    public double violacionDeCapacidad(Individuo individuo) {
        double peso = this.obtenerPeso(individuo);
        return capacidad - peso;
    }

    /**
     * obtiene un subconjunto de listaIndices, tal que cada indice cabe en
     * mochila sin cobrepasar su capacidad.
     *
     * @param listaIndices
     * @param mochila
     * @return
     */
    public List<Integer> filtrarPorFactibles(List<Integer> listaIndices, Individuo mochila) {
        double espacio = sacarEspacios(mochila);
        List<Integer> listaFactibles = new ArrayList(listaIndices);
        listaFactibles.removeIf((Integer t1) -> {
            return espacio < vectorPesos[t1];
        });
        return listaFactibles;
    }

    /**
     * checkea si la operacion swap que incluye a pos_add y pos_sacar como
     * indices de elementos en individuo, produce un individuo factible
     *
     * @param pos_add
     * @param pos_sacar
     * @param individuo
     * @return
     */
    boolean swapFactible(int pos_add, int pos_sacar, Individuo individuo) {
        if (!(individuo.get(pos_add) == 0 && individuo.get(pos_sacar) == 1)) {
            throw new InvalidParameterException(
                    "pos_add debe ser de un elemento no seleccionado, y"
                    + " pos_sacar de un elemento seleccionado");
        }

        double espacio = sacarEspacios(individuo);
        return espacio >= (vectorPesos[pos_add] - vectorPesos[pos_sacar]);
    }

    ////////////////////////////////////////////////////////////////
    public Individuo limitarInferiormente(Individuo mochila, List<Integer> listaOrdenada) {
        double espacios = sacarEspacios(mochila);
        boolean cabeArticulo;
        // en todos los articulos
        for (int pos : listaOrdenada) {
            cabeArticulo = true;
            if (mochila.get(pos) == 0) {
                // dimension de la mochila
                if (espacios < vectorPesos[pos]) {
                    cabeArticulo = false;
                    break;
                }
                if (cabeArticulo) {
                    mochila.set(pos, 1);
                    espacios -= vectorPesos[pos];
                }
            }
        }
        return mochila;
    }

    /**
     * beneficio del elemento en la posicion: indice
     *
     * @param indice indice del elemento
     * @return
     */
    public double beneficio(int indice) {
        return matrizBeneficios[indice][indice];
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    public List<Integer> obtener_I1(Individuo individuo) {
        IndividuoMochila indi = ((IndividuoMochila) individuo);
        return indi.obtener_I1();
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I0)
     * en individuo.
     *
     * @param individuo s * @return List de indices de elementos no
     * seleccionados
     * @return
     */
    public List<Integer> obtener_I0(Individuo individuo) {
        IndividuoMochila indi = ((IndividuoMochila) individuo);
        return indi.obtener_I0();
    }

    /**
     *
     * @param mochila
     * @return
     */
    public double obtenerPeso(Individuo mochila) {
        return ((IndividuoMochila) mochila).getPeso();
    }

    /**
     * Obj(S): el valor de la funcion objetivo con respecto a S
     *
     * @param mochila
     * @return
     */
    @Override
    public double evaluar(Individuo mochila) {
        return ((IndividuoMochila) mochila).getCalidad();
    }

    @Override
    public Individuo generarIndividuo() {
        Individuo nuevop = new IndividuoMochila(this);
        return nuevop;
    }

    public void fijarVariables(Individuo individuo, int[] varFijas) {
        for (int varFija : varFijas) {
            variablesFijas.add(varFija);
        }
    }

    public void reiniciarVijarVariables() {
        variablesFijas.clear();
    }

    private List<Integer> variablesFijas;

    public class IndividuoMochila extends Individuo {

        private double peso;
        private List<Integer> I1;
        private List<Integer> I0;

        public IndividuoMochila(Funcion funcion) {
            super(funcion);
            variablesFijas = new ArrayList();
            peso = 0;
            I1 = new ArrayList();
            I0 = new ArrayList();
            for (int i = 0; i < funcion.getDimension(); i++) {
                I0.add(i);
            }
        }

        /**
         * procedimeinto que obtiene la lista de los elementos seleccionados
         * (I1) en individuo.
         *
         * @return
         */
        public List<Integer> obtener_I0() {
            // Sacar listas de elementos seleccionados y no seleccionados
            List<Integer> listaI0 = new ArrayList(I0);
            listaI0.removeAll(variablesFijas);
            return listaI0;
        }

        /**
         * procedimeinto que obtiene la lista de los elementos seleccionados
         * (I1) en individuo.
         *
         * @return
         */
        public List<Integer> obtener_I1() {
            // Sacar listas de elementos seleccionados y no seleccionados
            List<Integer> listaI1 = new ArrayList(I1);
            listaI1.removeAll(variablesFijas);
            return listaI1;
        }

        public double getPeso() {
            return peso;
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
            double contribucion = 0;
            //contribucion en la fila
            for (int i = 0; i < indice; i++) {
                contribucion += matrizBeneficios[i][indice] * get(i);
            }
            //contribucion en la columna
            for (int k = indice + 1; k < vectorPesos.length; k++) {
                contribucion += matrizBeneficios[indice][k] * get(k);
            }
            //contribucion por si solo
            contribucion += matrizBeneficios[indice][indice];
            // peso del articulo
            valorPeso = vectorPesos[indice];

            // incluir beneficio
            calidad += (-valAnterior + valor) * contribucion;
            // incluir peso del elemento
            peso += (-valAnterior + valor) * valorPeso;

            super.set(indice, valor);
        }

        @Override
        public Individuo clone() {
            IndividuoMochila ind = (IndividuoMochila) super.clone(); //To change body of generated methods, choose Tools | Templates.
            ind.I0 = new ArrayList(ind.I0);
            ind.I1 = new ArrayList(ind.I1);
            return ind;
        }

    }

}
