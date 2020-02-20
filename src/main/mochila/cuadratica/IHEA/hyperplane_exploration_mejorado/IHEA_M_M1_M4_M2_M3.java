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
package main.mochila.cuadratica.IHEA.hyperplane_exploration_mejorado;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;

/**
 *
 * @author debian
 */
public class IHEA_M_M1_M4_M2_M3 extends IHEA_M_M1_M4_M2 {

    public IHEA_M_M1_M4_M2_M3(FuncionMochilaIHEA funcion) {
        super(funcion);
    }
    
    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
        addNombre("-M143");
    }


    @Override
    public int getNumeroNF(IndividuoIHEA individuo) {
        // dimension de individuo
        int k = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getFuncion().getDimension();
        if(n<=0){
            System.out.println("");
        }
        int c = (int)individuo.getFuncion().getCapacidad();
        // klb+(k-klb)*(1-1/(n*0.010)) +(kub-k)/2 -(kub-(c*0.02))*0.02
         int nf = getLb() + (int)(Math.max(0, (k - getLb()))*(1-1.0/(n*0.010))) + (getUb() - k)/2 - (int)Math.max(0,(getUb() - (c*0.02))*.02); // mejor
        return nf;
    }

}
