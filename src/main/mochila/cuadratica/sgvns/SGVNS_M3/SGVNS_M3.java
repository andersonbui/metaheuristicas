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
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import main.mochila.cuadratica.utilidades.UtilCuadratica;

/**
 *
 * @author Juan Diaz PC
 */
public class SGVNS_M3 extends SGVNS {

    public SGVNS_M3(FuncionMochilaSGVNS_M3 funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
    }
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
    

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
        lb = -1;
        ub = -1;
        lb = obtenerLowerBound();
        ub = obtenerUpperBound();
    }
    
    
    /**
     * obtiene el lower bound
     *
     * @return
     */
    public int obtenerLowerBound() {
        if (lb == -1) {
            int[] lu_b = UtilCuadratica.optenerLowerUpper_Bound(getFuncion());
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
    public int obtenerUpperBound() {
        if (ub == -1) {
            obtenerLowerBound();
        }
        return ub;
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
//        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
//MODIFICACION
        int nf = (int) (lowerb);
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }

    protected List<Integer> determinarVariablesFijasUpperBound(int dimension, IndividuoVNS individuo, int upperb) {
        // dimension de individuo
        int dimX = dimension;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
//        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
//MODIFICACION
        int nf = (int) (upperb);
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosFueraYCaben(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }

    /**
     *
     * @param varFijas
     */
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas) {
        ((FuncionMochilaSGVNS_M3)getFuncion()).fijarVariables(varFijas);
    }
    
//    
//    public FuncionMochilaSGVNS_M3 getFuncion() {
//        return (FuncionMochilaSGVNS_M3) funcion;
//    }
}
