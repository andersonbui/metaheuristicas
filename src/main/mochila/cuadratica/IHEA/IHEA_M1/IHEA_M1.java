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
package main.mochila.cuadratica.IHEA.IHEA_M1;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.mochila.cuadratica.IHEA.IHEA_AjusteVariables.IHEA_AV;
import main.mochila.cuadratica.utilidades.Main_MetododosInicializacion;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;

/**
 *
 * @author debian
 */
public class IHEA_M1 extends IHEA_AV {

    public IHEA_M1(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
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
    @Override
    public List<Integer> determinarVariablesFijas(int dimensionHyp, IndividuoIHEA individuo, int lowerb) {
        // dimension de individuo
        double dimX = dimensionHyp;
        // tamaño de la mochila
        int n = individuo.getDimension();
        List<Integer> listaIndices = null;
        // numero de variables fijas
        int nf = 0;
//        nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
//        System.out.print(nf+" - ");
//        nf = (int) (0.860 * (lowerb + (getUb() - lowerb) / 2.0)); //general
        nf = (int) (0.99 * ((getUb() + lowerb) / 2.0) * (dimX *1.0/ getUb())); //300
//        nf = (int) (0.99 * ((getUb() + lowerb) / 2.0) * (dimX / getUb())); //300
//        nf = (int)(1.0 * ((getUb() + lowerb) / 2.0)); //300
//        nf = (int)(0.96*(lowerb + (getUb() - lowerb) / 2.0)); //100
//        System.out.println("- "+nf);
//        nf = (int)(1.20*(lowerb + (getUb() - lowerb) / 2.0)); //1000
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);
        listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);
        
//        listaIndices = new ArrayList<>();
//        List<Integer> listaInidicesResultado = (new Main_MetododosInicializacion()).ordenar(funcion);
////            nf = tamanio - nf;
//        for (int i = 0; i < nf; i++) {
//            listaIndices.add(listaInidicesResultado.get(i));
//        }
        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }

    int contador_ignora_max = 10;
    @Override
    protected IndividuoIHEA tabuSearchEngine(int L, IndividuoIHEA x_inicial, IndividuoIHEA x_referencia) {

        double porcentaje_ignora = 0.0000000000000005; //0000000000000002
        int contador_ignora = 0;
        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = -1;
        // 
        double fmax;
        tabu = new int[x_inicial.getDimension()][x_inicial.getDimension()];
        double frx = 0;
        double vcx;
        boolean bueno = false;
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

        // linea 8:
        int iterTabu = 1;

        double viol_capacidad;
        double calidadi;
        int iterMax = 0;
        double default_min = Double.NEGATIVE_INFINITY;
//        double default_min = Double.POSITIVE_INFINITY;

        while (iterMax < L / 2 && vmin != default_min && list_RL.size() < L) {

            vmin = default_min;
            fmax = -default_min;
            List<Integer> I0;
            List<Integer> I1;
            I0 = elementosFuera(x);
            I1 = elementosDentro(x);
            // linea 10:
            for (int j : I1) {
                viol_capacidad = funcion.getCapacidad() - x.pesar() + funcion.peso(j);
                calidadi = x.getCalidad() - funcion.contribucion(j, x);
                for (int i : I0) {
                    // linea 11:
                    // linea 12:
                    if (tabu[i][j] <= iterTabu) {
                        //contribucion
                        frx = calidadi + funcion.contribucion(i, x, j);
                        // peso del articulo
                        vcx = viol_capacidad - funcion.peso(i);
                        if (frx >= fmin) {
                            if ((vmin >= 0 && vcx > vmin && frx >= fmax) || (vmin < 0 && vcx > vmin) || (frx > fmax && vcx == vmin)) {
                                i_aster = i;
                                j_aster = j;
                                vmin = vcx;
                                fmax = frx;
                            }
                        }
                    }
                }
            }
            int i;
            Integer j;
            // linea 21:
            if (vmin != default_min) {

                // linea 22:
                x.set(j_aster, 0);
                x.set(i_aster, 1);
                frx = x.getCalidad();
                double vmax = funcion.getCapacidad() - x_aster.pesar();
//                if (vmin >= 0 && !(frx <= fmin && vmax >= vmin)) {
                if (vmin >= 0 && (frx > fmin || (frx == fmin && vmax < vmin))) {
                    double porcentaje = (1 - frx / fmin);
                    if (porcentaje < porcentaje_ignora) {
                        contador_ignora++;
//                        System.out.println("porcentaje: " + contador_ignora);
                    }
                    if ((contador_ignora > contador_ignora_max && porcentaje < porcentaje_ignora)) {
                        bueno = false;
                        iterMax += 2;
                    } else {
                        //if (vmin >= 0 && ((frx > fmin && vmax <= vmin) || (frx == fmin && vmax < vmin))) {
                        // linea 24:
                        iterMax = 0;
                        if (!bueno) {
                            list_RL.clear();
                        }
                        bueno = true;
    //                    fmin = rawFuncion(x);indice
                        fmin = frx;
                        x_aster = x.clone();
                        // linea 25:
                    }
                } else {
                    bueno = false;
                    iterMax += 2;
                }
                    list_RL.add(i_aster);
                    list_RL.add(j_aster);
                    //linea 26: actualizar estado tabu
                    iterTabu += 1;
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
                            tabu[list_RCS.get(0)][list_RCS.get(1)] = iterTabu+2;
                            tabu[list_RCS.get(1)][list_RCS.get(0)] = iterTabu+2;
                        }
                        i--;
                    }
            } else {
                iterMax += 1;
            }
        }
        return x_aster;

    }

}
