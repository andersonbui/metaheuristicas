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
package main.mochila.cuadratica.sgvns.SGVNS_M1;

import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.FuncionSGVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;

/**
 *
 * @author Juan Diaz PC
 */
public class SGVNS_M1 extends SGVNS {

    public SGVNS_M1(FuncionSGVNS funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override 
        public IndividuoVNS estructuraVecindarioSacudida(IndividuoVNS s_inicial, int intentos) {
        IndividuoVNS y = null;
        y = sacudida(s_inicial, 1, intentos);
        return y;
    }
}
