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

import com.sun.glass.ui.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import main.mochila.IndividuoMochila;

/**
 * TODO puede mejorar la compara de individuos al tener en cuenta tambien el
 * espacio sobrante, mayor esacio sobrante es mejor individuo si la calidad es
 * muy similar.
 *
 * @author debian
 * @param <FuncionSGVNS>
 */
public class IndividuoCuadratico<FuncionSGVNS extends FuncionMochilaCuadratica> extends IndividuoMochila<FuncionSGVNS> {

    double porc_vacio;

    public IndividuoCuadratico(FuncionSGVNS funcion) {
        super(funcion);
        porc_vacio = 0.5;
    }

    public IndividuoCuadratico(FuncionSGVNS funcion, double[] valores) {
        super(funcion, valores);
        porc_vacio = 0.5;
    }

    public IndividuoCuadratico(FuncionSGVNS funcion, double[] valores, double porc_vacio) {
        super(funcion, valores);
        this.porc_vacio = porc_vacio;
    }

    @Override
    public double pesar() {
        return peso;
    }

//    @Override
//    public double evaluar() {
////        double porcentajeEspacio = 1 - (pesar() / funcion.getCapacidad());
////        return calidad + calidad * porc_vacio * porcentajeEspacio;
//        return calidad + calidad;
//    }

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
    public void set(int indice, double valor) {

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

        valores[indice] = valor;
    }

    @Override
    public IndividuoCuadratico clone() {
        IndividuoCuadratico ind = (IndividuoCuadratico) super.clone(); //To change body of generated methods, choose Tools | Templates.
        return ind;
    }

    @Override
    public String toString() {
        return "IndividuoMochila{" + "calidad=" + this.calidad + "; peso=" + pesar() + "; capacidad=" + funcion.getCapacidad() + '}';
    }

    public String toStringIndicesOrdenados() {
        String cadena = "";
        List<Integer> listaIndicesSele = this.elementosSeleccionados();
        double[] beneficios = new double[getDimension()];
        listaIndicesSele.forEach((indice) -> {
            // calcular densidad solo de los elementos en listaIndices
            beneficios[indice] = funcion.densidad(indice, this);
        });
        Comparator<Integer> comparator = (Integer o1, Integer o2) -> {
            return Double.compare(beneficios[o1], beneficios[o2])*(-1);
        };
        Collections.sort(listaIndicesSele, comparator);

        for (Integer indice : listaIndicesSele) {
            cadena += indice + " ";
        }
        return cadena.trim();
    }

    @Override
    public FuncionMochilaCuadratica getFuncion() {
        return funcion;
    }
}
