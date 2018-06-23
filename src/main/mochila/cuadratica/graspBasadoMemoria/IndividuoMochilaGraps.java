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
package main.mochila.cuadratica.graspBasadoMemoria;

import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public class IndividuoMochilaGraps extends IndividuoCuadratico<FuncionMochilaCuadratica> {

    public IndividuoMochilaGraps(FuncionMochilaCuadratica funcion) {
        super(funcion);
        peso = 0;
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
        double valorPeso;
        double contribucion = 0;
        //contribucion en la fila
        for (int i = 0; i < indice; i++) {
            contribucion += funcion.beneficio(i, indice) * get(i);
        }
        //contribucion en la columna
        for (int k = indice + 1; k < funcion.getDimension(); k++) {
            contribucion += funcion.beneficio(indice, k) * get(k);
        }
        //contribucion por si solo
        contribucion += funcion.beneficio(indice, indice);
        // peso del articulo
        valorPeso = funcion.peso(indice);

        // incluir beneficio
        calidad += (-valAnterior + valor) * contribucion;
        // incluir peso del elemento
        peso += (-valAnterior + valor) * valorPeso;
        this.valores[indice] = valor;
    }

}
