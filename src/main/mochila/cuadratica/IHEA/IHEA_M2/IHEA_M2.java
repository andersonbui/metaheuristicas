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
package main.mochila.cuadratica.IHEA.IHEA_M2;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.IHEA_AjusteVariables.IHEA_AV;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import static main.mochila.cuadratica.utilidades.UtilCuadratica.swap;

/**
 *
 * @author debian
 */
public class IHEA_M2 extends IHEA_AV {

    public IHEA_M2(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*Aplica la estructura de vecindario h a la s_inicial, un numero de intentos 
    determinado (intentosEncontrarMejor) y va comparando si s_inicial mejora*/
    protected IndividuoIHEA encontrarMejor(IndividuoIHEA s_inicial, int h) {
        IndividuoIHEA aux;
        s_inicial = s_inicial.clone();
        boolean mejoro = false;
        int contador = 3;
        do {
            if (h == 1) {
                aux = (IndividuoIHEA) swap(s_inicial);
            } else {
                aux = (IndividuoIHEA) add_factible(s_inicial);
            }
            if (aux != null) {
                mejoro = s_inicial.compareTo(aux) < 0;
                if (mejoro) {
                    s_inicial = aux;
                    break;
                }
            }
        } while (contador-- >= 0);
        return s_inicial;
    }

    /**
     * realiza un mejoramiento al individuo utilizando addElemento y swap. tipo
     * vns
     *
     * @param original
     * @return
     */
    @Override
    protected IndividuoIHEA descent(IndividuoIHEA original) { ////////////OPCION DE MEJORAR EN TIEMPO (for)
        //Variable para el tipo de estructura de vecindario
        int h = 1;
        IndividuoIHEA s_inicial = original.clone();
        IndividuoIHEA solEncontrada;
        while (h <= 2) {
            //Aplica una estructura de vecindario h a la s_inicial para mejorar
            solEncontrada = encontrarMejor(s_inicial, h);
            /*Si mejora s_inicial permanece en h1 (si h=1) o se devuelve a h1 encaso que este en h2 (h=2),
            valida que no sea un optimo local(osea que exista una mejor solucion)*/
            if (solEncontrada.compareTo(s_inicial) > 0) {
                s_inicial = solEncontrada;
                h = 1;
            } else {
                h++;
            }
        }
        return s_inicial;
    }


}
