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
import java.util.List;
import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class GraspTabuReinicio extends GraspReinicio {

    protected final int t_min;
    protected final int t_max;

    /**
     *
     * @param funcionGreedy
     * @param sigma
     * @param lamda
     * @param gama
     * @param beta
     * @param t_min
     * @param t_max
     */
    public GraspTabuReinicio(FuncionGreedy funcionGreedy, int sigma, int lamda, int gama, int beta, int t_min, int t_max) {
        super(funcionGreedy, sigma, lamda, gama, beta);
        nombre = "GraspTabuReinicio";
        this.t_min = t_min;
        this.t_max = t_max;
    }

    @Override
    protected Individuo busquedaAdicional(Individuo bestLB) {
        return busquedaTabu(bestLB, t_min, t_max);
    }

    public class ItemTabu {

        int indice_elemento;
        int edadTabu;

        public ItemTabu(int indice_elemento, int edadTabu) {
            this.indice_elemento = indice_elemento;
            this.edadTabu = edadTabu;
        }
    }

    public class IndividuoElemento {

        int indice_entro;
        int indice_salio;
        Individuo individuo;

        public IndividuoElemento(int indice_entro, int indice_salio, Individuo individuo) {
            this.indice_entro = indice_entro;
            this.indice_salio = indice_salio;
            this.individuo = individuo;
        }
    }

    /**
     *
     * @param listaTabu
     * @param indice indice de un elemento
     * @return
     */
    private boolean estaEnLista(List<ItemTabu> listaTabu, int indice) {
        for (ItemTabu itemTabu : listaTabu) {
            if (itemTabu.indice_elemento == indice) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param listaTabu
     * @param indice_elemento
     * @param t_min
     * @param t_max
     */
    private void agregarAListaTabu(List<ItemTabu> listaTabu, int indice_elemento, int t_min, int t_max) {
        int edad = t_min + Aleatorio.nextInt(t_max - t_min);
        listaTabu.add(new ItemTabu(indice_elemento, edad));
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
        Individuo r;
        Individuo w;
        IndividuoElemento rTweak_r;
        IndividuoElemento rTweak_w;
        int numTweaks = 15;
        int maxIteraciones = 10; //500

        List<ItemTabu> listaTabu = new ArrayList();
        for (int i = 0; i < maxIteraciones;) {
            listaTabu.removeIf((ItemTabu itemIndividuo) -> {

                return itemIndividuo.edadTabu < 1;
            });
            rTweak_r = tweak(s);
            r = rTweak_r.individuo;
            for (int j = 0; j < numTweaks; j++) {
                rTweak_w = tweak(s);
                w = rTweak_w.individuo;

                if ((!estaEnLista(listaTabu, rTweak_w.indice_entro)
                        && !estaEnLista(listaTabu, rTweak_w.indice_salio)
                        && w.compareTo(r) > 0)
                        || (estaEnLista(listaTabu, rTweak_r.indice_entro)
                        && estaEnLista(listaTabu, rTweak_r.indice_salio))) {
                    r = w;
                }
            }
            if (!(estaEnLista(listaTabu, rTweak_r.indice_entro)
                    && estaEnLista(listaTabu, rTweak_r.indice_salio))
                    && r.compareTo(s) > 0) {
                s = r;
                if (rTweak_r.indice_entro >= 0) {
                    agregarAListaTabu(listaTabu, rTweak_r.indice_entro, t_min, t_max);
                }
                if (s.compareTo(best) > 0) {
                    best = s;
                    i = 0;
                } else {
                    i++;
                }
            }
            for (ItemTabu itemTabu : listaTabu) {
                itemTabu.edadTabu--;
            }
        }
        iteraciones += k;
        return best;
    }

    /**
     *
     * @param s
     * @return
     */
    private IndividuoElemento tweak(Individuo s) {
        Individuo individuoShift;
        Individuo individuoSwap;

        individuoShift = s.clone();
        int i_shift = shift2(individuoShift);

        individuoSwap = s.clone();
        int[] i_swap = swap2(individuoSwap);

        individuoShift.evaluar();
        individuoSwap.evaluar();

        if (individuoShift.compareTo(individuoSwap) > 0 && i_shift > 0) {
            if (individuoShift.compareTo(s) > 0) {
                return new IndividuoElemento(i_shift, -1, individuoShift);
            }
        } else if (individuoSwap.compareTo(s) > 0 && i_swap[0] > 0 && i_swap[1] > 0) {
            return new IndividuoElemento(i_swap[0], i_swap[1], individuoSwap);
        }
        return new IndividuoElemento(-1, -1, s);
    }

    /**
     * El movimient Shift adicina un elemento no seleccionado a la solución
     * actual o remueve un elemento seleccionado de esta.
     *
     * @param individuo
     * @return indice del elemento modificado
     */
    protected int shift2(Individuo individuo) {
        int aleatorio;
        int maxLen = individuo.getDimension();
        int posModificado = -1;
        aleatorio = Aleatorio.nextInt(maxLen);
        // el elemento esta agregado?
        if (individuo.get(aleatorio) == 1) {
            individuo.set(aleatorio, 0);
            posModificado = aleatorio;
        } else { //3135231933
            // verificar que el elemento no sobrepase la capacidad de la mochila
            if (funcionGreedy.cabe(individuo, aleatorio)) {
                individuo.set(aleatorio, 1);
                posModificado = aleatorio;
            }
        }

        return posModificado;
    }

    /**
     * El movimiento Swap reemplaza un elemento seleccionado por uno no
     * seleccionado
     *
     * @param individuo
     * @return int[]{posAgregado, posSalio};
     */
    protected int[] swap2(Individuo individuo) {
        List<Integer> listaItemSelect = obtenerItemsSelecionados(individuo);
        int maxLenS = listaItemSelect.size();
        int aleatrioS = Aleatorio.nextInt(maxLenS);

        individuo.set(listaItemSelect.get(aleatrioS), 0);

        int posAgregado = -1;
        int posSalio = -1;

        List<Integer> listaItemNoSelect = obtenerItemsNoSelecionados(individuo);
        int maxLenNS = listaItemNoSelect.size();
        int aleatrioNS = Aleatorio.nextInt(maxLenNS);

        if (funcionGreedy.cabe(individuo, listaItemNoSelect.get(aleatrioNS))) {
            posAgregado = listaItemNoSelect.get(aleatrioNS);
            posSalio = listaItemSelect.get(aleatrioS);
            individuo.set(posAgregado, 1);
        } else {
            individuo.set(listaItemSelect.get(aleatrioS), 1);
        }
//            lisNS[0,1,3]-> 1
//            [0,0,1,0,1]->2
        return new int[]{posAgregado, posSalio};
    }
}
