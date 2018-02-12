/*
 * Copyright (C) 2017 debian
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
package main.mochila.cuadratica.graspBasadoMemoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class GraspTabuReinicio extends GraspReinicio {

    public GraspTabuReinicio(FuncionGreedy funcionGreedy, int sigma, int lamda, int gama, int beta) {
        super(funcionGreedy, sigma, lamda, gama, beta);
        nombre = "TabuReinicio";
    }

    public List<Individuo> ejecutar(Funcion funcion) {
        //linea 1:
        Individuo LB = null;
        Individuo S = null;
        Individuo solParcialS = new Individuo(funcion);
        Individuo bestLB = solParcialS;
        //l: Tamaño maximo de la lista Tabu
        int t_min = 10;
        int t_max = 20;
        int cont = 0, m = 0;
        int minLen = gama;
        int maxLen = gama + beta;
        List<Individuo> listaRecorrido = new ArrayList();
        //Linea 2:
        while (cont != lamda && !funcion.suficiente(bestLB)) {
            //Linea 3: maxIteraciones

            for (k = m * sigma + 1; k <= (m + 1) * sigma; k++) {
                //Linea 4:
                S = faseConstruccion(minLen, maxLen, solParcialS);
                //Linea 5:
                S = faseBusquedaLocal(S);
                S.evaluar();
                s_sup_i.add(S);
                //Linea 6:
                if (bestLB == null || bestLB.compareTo(S) < 0) {
                    bestLB = S;
                }
                //Linea 7:
            }
            bestLB = busquedaTabu(bestLB, t_min, t_max);
            //Linea 8:
            if (LB == null || bestLB.compareTo(LB) < 0) {
                //Linea 9:
                LB = bestLB;
                //Linea 10:
                cont = 0;
            } else {
                //Linea 12:
                cont++;
                //Linea 13:
                minLen = maxLen + 1;
                //Linea 14:
                maxLen = maxLen + beta;
            }
            //linea 16: 
            solParcialS = definicionSolParcialS(funcion, m, sigma, Q);
            m++;
            listaRecorrido.add(bestLB);
        }
        iteraciones = m;
        return listaRecorrido;
    }

    public class ItemTabu {

        Individuo individuo;
        int edadTabu;

        public ItemTabu(Individuo individuo, int edadTabu) {
            this.individuo = individuo;
            this.edadTabu = edadTabu;
        }
    }

    private boolean estaEnLista(List<ItemTabu> listaTabu, Individuo w) {
        for (ItemTabu itemTabu : listaTabu) {
            if (itemTabu.individuo.equals(w)) {
                return true;
            }
        }
        return false;
    }

    private void agregarAListaTabu(List<ItemTabu> listaTabu, Individuo s, int t_min, int t_max) {
        int edad = t_min + Aleatorio.nextInt(t_max - t_min);
        listaTabu.add(new ItemTabu(s, edad));
    }

    /**
     *
     * @param s: solucion optenida en el proceso Grasp
     * @param t_max
     * @param t_min
     * @return
     */
    protected Individuo busquedaTabu(Individuo s, int t_min, int t_max) {
        //Linea 1:
        Individuo best = s;
        Individuo r = null;
        Individuo w = null;

        List<ItemTabu> listaTabu = new ArrayList();
        agregarAListaTabu(listaTabu, s, t_min, t_max);
        for (int i = 0; i < k; i++) {
            listaTabu.removeIf((ItemTabu itemIndividuo) -> {

                return itemIndividuo.edadTabu < 1;
            });
            r = tweak(s);

            for (int j = 0; j < listaTabu.size(); j++) {
                w = tweak(s);

                if (!estaEnLista(listaTabu, w) && w.compareTo(r) > 0 || estaEnLista(listaTabu, r)) {
                    r = w;
                }
            }
            if (!estaEnLista(listaTabu, r) && r.compareTo(s) > 0) {
                s = r;
                agregarAListaTabu(listaTabu, s, t_min, t_max);
            }
            if (s.compareTo(best) > 0) {
                best = s;
            }
        }
        return best;
    }

    /**
     *
     * @param s
     * @return
     */
    private Individuo tweak(Individuo s) {
        Individuo individuoShift;
        Individuo individuoSwap;

        individuoShift = s.clone();
        shift(individuoShift);

        individuoSwap = s.clone();
        swap(individuoSwap);

        individuoShift.evaluar();
        individuoSwap.evaluar();
        if (individuoShift.compareTo(individuoSwap) > 0 ) {
            return individuoShift;
        } else {
            return individuoSwap;
        }
    }

}
