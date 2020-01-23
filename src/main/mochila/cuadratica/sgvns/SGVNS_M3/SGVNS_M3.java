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
package main.mochila.cuadratica.sgvns.SGVNS_M3;

import java.util.List;
import main.mochila.cuadratica.sgvns.FuncionJSGVNS;
import main.mochila.cuadratica.sgvns.JSGVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.FuncionSGVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;


/**
 *
 * @author Juan Diaz PC
 */
public class SGVNS_M3 extends SGVNS {

    public SGVNS_M3(FuncionSGVNS_M3 funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
        this.setNombre("SGVNS_M3");
    }

    List<Integer> variablesFijasLowerb;

    @Override
    public FuncionSGVNS_M3 getFuncion() {
        return (FuncionSGVNS_M3) funcion;
    }
    
    /**
     *
     * @param varFijas
     */
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas) {
        getFuncion().fijarVariables(varFijas);

    }

    //Va de un vecindario a otro si no encuentra una mejota a s_inicial
    @Override
    public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
        //Variable para el tipo de estructura de vecindario
        int h = 1;
        IndividuoVNS s_inicial = individuoOriginal.clone();
        IndividuoVNS solEncontrada;
        while (h <= 2) {
            variablesFijasLowerb = determinarVariablesFijasLowerBound(s_inicial.getDimension(), s_inicial, lb);
            construirProblemaRestringidoReducido(variablesFijasLowerb);
            //Aplica una estructura de vecindario h a la s_inicial para mejorar
            solEncontrada = encontrarMejor(s_inicial, h);
            getFuncion().reiniciarFijarVariables();
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
    
     /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @param dimension
     * @param individuo
     * @param lowerb
     * @return
     */
    protected List<Integer> determinarVariablesFijasLowerBound(int dimension, IndividuoVNS individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimension;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
//MODIFICACION
//        int nf = (int) (lowerb);
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }
}
