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
package main.mochila.cuadratica.sgvns;

import java.util.ArrayList;
import java.util.List;
import main.Item;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import main.mochila.cuadratica.utilidades.UtilCuadratica;
import main.utilidades.Utilidades;

/**
 *
 * @author Juan Diaz PC
 */
public class JSGVNS extends SGVNS {

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

    private final List<Integer> variablesFijas;

    public JSGVNS(FuncionSGVNS funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
        variablesFijas = null;
        lb = -1;
        ub = -1;
    }
    
    /*Sacudida genera una solucion aleatoria y' realizando h(intentos) movimientos
    en el segundo vecindario (cambio) de la solucion y(s_inicial)*/
    private IndividuoVNS sacudida(IndividuoVNS s_inicial, int vecindario, int intentos) {
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
            if (mejoro) {
                s_inicial = aux;
                break;
            }
            s_inicial = aux;

        } while (intentos-- >= 0);
        return s_inicial;
    }
    
    /**
     * obtiene el lower bound
     *
     * @return
     */
    public int obtenerLowerBound() {
        if (lb == -1) {
            int[] lu_b = UtilCuadratica.optenerLowerUpper_Bound(funcion);
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
     * obtiene un subconjunto de elementos dentro de la mochila.
     *
     * @param mochila
     * @return lista de elementos fuera de la mochila
     */
    @Override
    public List<Integer> elementosDentro(IndividuoVNS mochila) {
        List listaI1 = mochila.elementosSeleccionados();
        if (this.variablesFijas != null && !this.variablesFijas.isEmpty()) {
            listaI1.removeAll(this.variablesFijas);
        }
        return listaI1;
    }
    
    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @param dimensionHyp
     * @param individuo
     * @param lowerb
     * @return
     */
    protected List<Integer> determinarVariablesFijas(int dimensionHyp, IndividuoVNS individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimensionHyp;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }
}
