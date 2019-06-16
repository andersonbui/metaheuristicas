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
package main.mochila.cuadratica.hyperplane_exploration_ajustado;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.hyperplane_exploration.*;
import static main.mochila.cuadratica.utilidades.UtilCuadratica.swap;

/**
 *
 * @author debian
 */
public class IteratedHyperplaneExplorationAlgoritm_A extends IteratedHyperplaneExplorationAlgoritm {

    protected int ub; // lower bown

    protected boolean saltar;

    public IteratedHyperplaneExplorationAlgoritm_A(FuncionMochilaIHEA funcion) {
        super(funcion);
        ub = funcion.obtenerUpperBound();
        saltar = false;
        L = 20;
    }

    public boolean isSaltar() {
        return saltar;
    }

    public void setSaltar(boolean saltar) {
        this.saltar = saltar;
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

    @Override
    protected IndividuoIHEA tabuSearchEngine(int L, IndividuoIHEA x_inicial, IndividuoIHEA x_referencia) {
//        double[] vecValPesos = parametros.getVectorPesos();
//        double sumaPesos = UtilCuadratica.suma(vecValPesos);
//        double div_sp_c = sumaPesos / parametros.getCapacidad();// (suma pesos) / capacidad
//        if (div_sp_c <= 1.0 ) {
//            return super.tabuSearchEngine(L, x_inicial, x_referencia);
//        }
        return tabuSearchEngineAux(L, x_inicial, x_referencia);
    }

    protected IndividuoIHEA tabuSearchEngineAux(int L, IndividuoIHEA x_inicial, IndividuoIHEA x_referencia) {

        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = -1;
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
        double default_min = Double.NEGATIVE_INFINITY;
//        double default_min = Double.POSITIVE_INFINITY;

        while ((iterMax < L / 2) && (vmin != default_min && list_RL.size() < L)) {

            vmin = default_min;
            fmax = -default_min;
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
                    if (tabu[i][j] != iterTabu) {
                        //contribucion
                        frx = calidadi + funcion.contribucion(i, x, j);
                        // peso del articulo
                        vcx = viol_capacidad - funcion.peso(i);

                        if ((frx >= fmin && vcx > vmin) || (frx > fmin && vcx >= vmin)) {
                            i_aster = i;
                            j_aster = j;
                            vmin = vcx;
                            fmax = frx;
                        }
//                        if ((frx >= fmin) && (((vcx >= vmin)) || ((vcx == vmin) && (frx >= fmax)))) {
//                            i_aster = i;
//                            j_aster = j;
//                            vmin = vcx;
//                            fmax = frx;
//                        }
//                        if ((frx >= fmin) && (((vcx < vmin)) || ((vcx == vmin) && (frx >= fmax)))) {
//                            i_aster = i;
//                            j_aster = j;
//                            vmin = vcx;
//                            fmax = frx;
//                        }

//                        if (frx > fmin && vcx >= 0) {
//                            imax = i;
//                            jmax = j;
//                            fmin = frx;
//                            vmax = vcx;
//                            saltar = true;
//                            break;
//                        }
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
            } else if (vmin != default_min) {

                // linea 22:
                x.set(i_aster, 1);
                x.set(j_aster, 0);
                if (vmin >= 0 && fmax > fmin) { // ############################# ==
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
