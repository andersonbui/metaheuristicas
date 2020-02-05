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
package main.mochila.cuadratica.IHEA.hyperplane_exploration;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public class IndividuoIHEA extends IndividuoCuadratico<FuncionMochilaIHEA> {

    /**
     * guarda todos los indices de los elementos dentro de la mochila.
     */
    private List<Integer> I1;
    /**
     * guarda todos los indices de los elementos fuera de la mochila.
     */
    private List<Integer> I0;
    /**
     * guarda todas las contribuciones de todos los elementos, de acuerdo la
     * mochila actual. Las contribuciones se encuentran en el mismo orden que
     * los elementos correspondientes.
     */
    private int[] tabu;
    private double[] vec_contribucion;

    public IndividuoIHEA(FuncionMochilaIHEA funcion) {
        super(funcion);
        InicializarI0I1();
    }

    public IndividuoIHEA(FuncionMochilaIHEA funcion, double[] valores) {
        super(funcion);
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

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @return
     */
    @Override
    public List<Integer> elementosNoSeleccionados() {
        // Sacar listas de elementos seleccionados y no seleccionados
        List<Integer> listaFuera = new ArrayList();
        for (Integer integer : getI0()) {
            if (tabu[integer] <= 0) {
                listaFuera.add(integer);
            }
        }
        return listaFuera;
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
        return listaI1;
    }

    public int cantidadI1() {
        return this.I1.size();
    }

    public double getContribucion(int indice) {
        return vec_contribucion[indice];
    }

    @Override
    public double pesar() {
        return peso;
    }

    @Override
    public void set(int indice, double valor) {

        double valAnterior = get(indice);
        if (valAnterior == valor) {
//            System.out.println("valor igual al anterior");
            return;
        }
        if (valor == 0) {
            boolean result = I1.remove((Integer) indice);
            if (result) {
                I0.add((Integer) indice);
            } else {
                System.out.println("error anadiendo elemento de I1");
            }
        } else {
            boolean result = I0.remove((Integer) indice);
            if (result) {
                I1.add((Integer) indice);
            } else {
                System.out.println("error removiendo elemento de I0");
            }
        }
        double valorPeso;
        double unacontribucion = 0;

        // contribucion
        unacontribucion = funcion.contribucion(indice, this);
        // actualizacion de la contribucion de cada elemento
        for (int j = 0; j < vec_contribucion.length; j++) {
            if (j < indice) {
                vec_contribucion[j] += (-valAnterior + valor) * funcion.beneficio(j, indice);
            } else if (j > indice) {
                vec_contribucion[j] += (-valAnterior + valor) * funcion.beneficio(indice, j);
            }
        }
        // peso del articulo
        valorPeso = funcion.peso(indice);
        // incluir beneficio
        calidad += (-valAnterior + valor) * unacontribucion;
        // incluir peso del elemento
        peso += (-valAnterior + valor) * valorPeso;

        this.valores[indice] = valor;
    }

    public int compareTo(IndividuoIHEA otrop) {
        Double a_calidad = this.getCalidad();
        int orden = funcion.isMaximizar() ? 1 : -1;
        int comparacion = a_calidad.compareTo(otrop.getCalidad());
//        if (comparacion != 0) {
        return orden * comparacion;
//        }
//        return orden * Double.compare(peso, otrop.peso);
    }

    @Override
    public IndividuoIHEA clone() {
        IndividuoIHEA ind = (IndividuoIHEA) super.clone();
        ind.I0 = new ArrayList(ind.I0);
        ind.I1 = new ArrayList(ind.I1);
        ind.vec_contribucion = this.vec_contribucion.clone();
        return ind;
    }

    @Override
    public String toString() {
        return "IndividuoCuadratico{" + "calidad=" + this.calidad + "peso=" + pesar() + "capacidad=" + ((FuncionMochilaIHEA) funcion).getCapacidad() + '}';
    }

    protected List<Integer> getI1() {
        return I1;
    }

    protected void setI1(List<Integer> I1) {
        this.I1 = I1;
    }

    protected List<Integer> getI0() {
        return I0;
    }

    protected void setI0(List<Integer> I0) {
        this.I0 = I0;
    }

    protected double[] getVec_contribucion() {
        return vec_contribucion;
    }

    protected void setVec_contribucion(double[] vec_contribucion) {
        this.vec_contribucion = vec_contribucion;
    }

    public int[] getTabu() {
        return tabu;
    }

    public void setTabu(int[] tabu) {
        this.tabu = tabu;
    }

}
