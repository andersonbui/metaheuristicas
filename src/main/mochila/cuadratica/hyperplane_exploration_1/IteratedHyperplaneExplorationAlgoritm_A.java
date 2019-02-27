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
package main.mochila.cuadratica.hyperplane_exploration_1;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.hyperplane_exploration.*;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;

/**
 *
 * @author debian
 */
public class IteratedHyperplaneExplorationAlgoritm_A extends IteratedHyperplaneExplorationAlgoritm {

    protected int ub; // lower bown

    public IteratedHyperplaneExplorationAlgoritm_A(FuncionMochilaIHEA funcion) {
        super(funcion);
        ub = funcion.obtenerUpperBound();
    }

    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @TODO realizar el calculo tambien con el LB solo, es decir, (nf=lowerb)
     * @param dimensionHyp
     * @param individuo
     * @param lowerb
     * @return
     */
    protected List<Integer> determinarMalasVariablesFijas(int dimensionHyp, IndividuoIHEA individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimensionHyp;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
        nf = 15;
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosFuera(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, true);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }

    /**
     *
     * @param varFijas
     * @param individuoActual
     */
    @Override
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas, IndividuoIHEA individuoActual) {
        int tam = Math.min(individuoActual.getDimension() - (int) (ub), individuoActual.getDimension());
        tam = 10;
        List<Integer> malas = determinarMalasVariablesFijas(dimensionHiperplano(individuoActual), individuoActual, tam);
        funcion.fijarVariables(individuoActual, varFijas);
        funcion.fijarVariablesMalas(individuoActual, malas);
    }

    @Override
    protected IndividuoIHEA tabuSearchEngine(int L, IndividuoIHEA x_inicial, IndividuoIHEA x_referencia) {

        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = 0;
        //
        double fmax;
        tabu = new int[x_inicial.getDimension()][x_inicial.getDimension()];
        double frx = 0;
        double vcx;

        // residual cancellation sequence list
        List<Integer> list_RCS = new ArrayList();
        // inicializa el tamaño de la lista de ejecucion
        List<Integer> list_RL = new ArrayList();
        // almacena el valor de la funcion objetivo de la actual mejor solución factible global
        double fmin = x_referencia.evaluar();
        // x*: recuerda la mejor solucion encontrada hasta el momento
        IndividuoIHEA x_aster = x_referencia;
        // linea 6; tamanio lista RL
//        int erl = 0;
        // linea 7:
        IndividuoIHEA x = x_inicial.clone();
        int i_aster = 0;
        int j_aster = 0;

        int imax = -1;
        int jmax = -1;
        double vmax = -1;
        // linea 8:
        int iterTabu = 1;

        double viol_capacidad;
        double calidadi;
        int iterMax = 0;
        while ((iterMax < L / 2) && (vmin != Double.POSITIVE_INFINITY || list_RL.size() < L)) {

            vmin = Double.POSITIVE_INFINITY;
            fmax = Double.NEGATIVE_INFINITY;
            imax = -1;
            jmax = -1;
            List<Integer> I0;
            List<Integer> I1;
            I0 = elementosFuera(x);
            I1 = elementosDentro(x);
            vmax = -1;
            // linea 10:
            for (int j : I1) {
                viol_capacidad = funcion.getCapacidad() - x.pesar() + funcion.peso(j);
                calidadi = x.getCalidad() - funcion.contribucion(j, x);
                saltar = false;
                for (int i : I0) {
                    // linea 11:
                    // linea 12:
                    if (tabu[i][j] <= iterTabu - 1) {
                        //contribucion
                        frx = calidadi + funcion.contribucion(i, x, j);
                        // peso del articulo
                        vcx = viol_capacidad - funcion.peso(i);

                        if ((frx > fmin) && (((vcx < vmin)) || ((vcx == vmin) && (frx >= fmax)))) {
                            i_aster = i;
                            j_aster = j;
                            vmin = vcx;
                            fmax = frx;
                        }

                        if (frx > fmin && vcx >= 0) {
                            imax = i;
                            jmax = j;
                            fmin = frx;
                            vmax = vcx;
                            saltar = true;
                            break;
                        }
                        contadorIntercambios++;
                    }
                }
                if (vmax >= 0) {
                    break;
                }
            }
            int i;
            Integer j;
            // linea 21:
            if (vmax >= 0) {
                x.set(imax, 1);
                x.set(jmax, 0);
                // linea 24:
                //iterMax = 0;
                list_RL.clear();
                fmin = rawFuncion(x);
                x_aster = x.clone();
                // linea 25:
                imax = -1;
                jmax = -1;
//                iterMax = 0;
            } else if (vmin != Double.POSITIVE_INFINITY) {

                // linea 22:
                x.set(i_aster, 1);
                x.set(j_aster, 0);
                if (vmin == 0) { // ############################# ==
                    // linea 24:
                    iterMax = 0;
                    list_RL.clear();
                    fmin = rawFuncion(x);
                    x_aster = x.clone();
                    // linea 25:
                } else {
                    //linea 26: actualizar estado tabu
                    iterTabu += 1;
                    list_RL.add(i_aster);
                    list_RL.add(j_aster);
                    iterMax += 2;
                    // linea 27:
                    i = list_RL.size() - 1;
                    // linea 28:
                    list_RCS.clear();
                    while (i >= 0) {
                        // linea 29:
                        j = list_RL.get(i);
                        if (list_RCS.contains(j)) {
                            boolean ret = list_RCS.remove(j);
                        } else {
                            list_RCS.add(j);
                        }
                        if (list_RCS.size() == 2) {
                            tabu[list_RCS.get(0)][list_RCS.get(1)] = iterTabu;
                            tabu[list_RCS.get(1)][list_RCS.get(0)] = iterTabu;
                        }
                        i--;
                    }
                }
            } else {
                iterMax += 1;
            }
        }
        return x_aster;

    }
    
    
}
