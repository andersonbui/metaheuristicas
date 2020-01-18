/*
 * Copyright (C) 2019 Juan Diaz PC
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
package main.mochila.cuadratica.sgvns.SGVNS_M2;

import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.FuncionSGVNS_Original;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;

/**
 *
 * @author Juan Diaz PC
 */
public class SGVNS_M2 extends SGVNS {

    public SGVNS_M2(FuncionSGVNS_Original funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
        this.setNombre("SGVNS_M2");
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }

    /*Sacudida genera una solucion aleatoria y' realizando h(intentos) movimientos
    en el primer vecindario (intercambio) de la solucion (s_inicial). Este metodo compara 
    la solucion en cuestion (aux), con la solucion actual (s_inicial). Si aux es mayor, actualiza a 
    s_inicial y se sale. Sino de igual forma actualiza a s_inicial pero esta vez continua la sacudida
    hasta h veces.*/
    @Override
    protected IndividuoVNS sacudida(IndividuoVNS s_inicial, int vecindario, int intentos) {
        IndividuoVNS aux;
        boolean mejoro;
        s_inicial = s_inicial.clone();
        intentos = Math.min(intentos, s_inicial.elementosSeleccionados().size() - 1);
        do {
            if (vecindario == 1) {
                aux = intercambio(s_inicial);
            } else {
                aux = cambio(s_inicial);
            }

            //MODIFICACION
            mejoro = aux.compareTo(s_inicial) > 0;
            s_inicial = aux;
            if (mejoro) {
                break;
            }
        } while (intentos-- >= 0);

        return s_inicial;
    }
}
