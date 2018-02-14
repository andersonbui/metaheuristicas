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
public class GraspTabuReinicioMejorado extends GraspTabuReinicio {


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
    public GraspTabuReinicioMejorado(FuncionGreedy funcionGreedy, int sigma, int lamda, int gama, int beta, int t_min, int t_max) {
        super(funcionGreedy, sigma, lamda, gama, beta, t_min, t_max);
        nombre = "GraspTabuReinicio";
    }
//
//    /**
//     *
//     * @param listaTabu
//     * @param w
//     * @return
//     */
//    private boolean estaEnLista(List<ItemTabu> listaTabu, Individuo w) {
//        for (ItemTabu itemTabu : listaTabu) {
//            if (itemTabu.individuo.equals(w)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * @param listaTabu
//     * @param s
//     * @param t_min
//     * @param t_max
//     */
//    private void agregarAListaTabu(List<ItemTabu> listaTabu, Individuo s, int t_min, int t_max) {
//        int edad = t_min + Aleatorio.nextInt(t_max - t_min);
//        listaTabu.add(new ItemTabu(s, edad));
//    }
//
//    /**
//     *
//     * @param s: solucion optenida en el proceso Grasp
//     * @param t_max
//     * @param t_min
//     * @return
//     */
//    protected Individuo busquedaTabu(Individuo s, int t_min, int t_max) {
//        //Linea 1:
//        Individuo best = s;
//        Individuo r;
//        Individuo w;
//
//        List<ItemTabu> listaTabu = new ArrayList();
//        agregarAListaTabu(listaTabu, s, t_min, t_max);
//        for (int i = 0; i < k; i++) {
//            listaTabu.removeIf((ItemTabu itemIndividuo) -> {
//
//                return itemIndividuo.edadTabu < 1;
//            });
//            r = tweak(s);
//
//            for (int j = 0; j < listaTabu.size(); j++) {
//                w = tweak(s);
//
//                if (!estaEnLista(listaTabu, w) && w.compareTo(r) > 0 || estaEnLista(listaTabu, r)) {
//                    r = w;
//                }
//            }
//            if (!estaEnLista(listaTabu, r) && r.compareTo(s) > 0) {
//                s = r;
//                agregarAListaTabu(listaTabu, s, t_min, t_max);
//            }
//            if (s.compareTo(best) > 0) {
//                best = s;
//            }
//        }
////        iteraciones += k;
//        return best;
//    }
//
//    /**
//     *
//     * @param s
//     * @return
//     */
//    private Individuo tweak(Individuo s) {
//        Individuo individuoShift;
//        Individuo individuoSwap;
//
//        individuoShift = s.clone();
//        shift(individuoShift);
//
//        individuoSwap = s.clone();
//        swap(individuoSwap);
//
//        individuoShift.evaluar();
//        individuoSwap.evaluar();
//        if (individuoShift.compareTo(individuoSwap) > 0 && funcionGreedy.sacarEspacios(individuoShift) >= 0) {
//            return individuoShift;
//        } else if (funcionGreedy.sacarEspacios(individuoSwap) >= 0) {
//            return individuoSwap;
//        } else {
//            return s;
//        }
//    }

}
