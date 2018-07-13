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

import main.mochila.cuadratica.IndividuoCuadratico;
import main.mochila.cuadratica.UtilCuadratica;
import main.mochila.cuadratica.UtilCuadratica.Movimiento;
import metaheuristicas.Aleatorio;

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
     * @param lamda
     * @param gama
     * @param beta
     * @param t_min
     * @param t_max
     */
    public GraspTabuReinicio(FuncionGraspTabuR funcionGreedy, int lamda, int gama, int beta, int t_min, int t_max) {
        super(funcionGreedy, lamda, gama, beta);
        setFuncion(funcionGreedy);
        nombre = "GraspTabuReinicio";
        this.t_min = t_min;
        this.t_max = t_max;
    }

    @Override
    protected IndividuoCuadratico busquedaAdicional(IndividuoCuadratico bestLB) {
        return busquedaTabu(bestLB, t_min, t_max);
    }

    class ListaTabu {

        int[] lista;

        public ListaTabu(int tamanio) {
            lista = new int[tamanio];
        }

        /**
         *
         * @param listaTabu
         * @param indice_elemento
         * @param t_min
         * @param t_max
         */
        private void agregar(Movimiento indice_elemento, int t_min, int t_max) {
            int edad = t_min + Aleatorio.nextInt(t_max - t_min);
            lista[indice_elemento.i] = edad;
        }

        /**
         *
         * @param listaTabu
         * @param indice indice de un elemento
         * @return
         */
        public boolean esTabu(Movimiento elem, int edad) {
            return lista[elem.i] > edad || lista[elem.j] > edad;
        }
    }

    /**
     *
     * @param s: solucion optenida en el proceso Grasp
     * @param t_max
     * @param t_min
     * @return
     */
    protected IndividuoCuadratico busquedaTabu(IndividuoCuadratico s, int t_min, int t_max) {
        //Linea 1:
        IndividuoCuadratico best = s;
        IndividuoCuadratico r;
        IndividuoCuadratico w;
        Movimiento r_mov;
        Movimiento w_mov;
        int numTweaks = 15;
        int maxIteracionesTabu = 500; //500

        ListaTabu ltabu = new ListaTabu(s.getDimension());
        for (int iteracion = 1; iteracion < maxIteracionesTabu;) {

            r_mov = new Movimiento();
            r = tweak(s, r_mov);
            if (r == null) {
                r = s;
            }
            for (int j = 0; j < numTweaks; j++) {
                w_mov = new Movimiento();
                w = tweak(s, w_mov);

                if (w != null && ((!ltabu.esTabu(w_mov, iteracion) && w.compareTo(r) > 0)
                        || (r_mov.i < 0 || ltabu.esTabu(r_mov, iteracion)))) {
                    r_mov = w_mov;
                    r = w;
                }
            }
            if (r_mov.i >= 0 && !ltabu.esTabu(r_mov, iteracion) && r.compareTo(s) > 0) {
                s = r;
                ltabu.agregar(r_mov, t_min, t_max);
                if (s.compareTo(best) > 0) {
                    best = s;
                    iteracion = 0;
                } else {
                    iteracion++;
                }
            } else {
                iteracion++;
            }
        }
        iteraciones += k;
        return best;
    }

    /**
     * Tweak realiza el movimiento Shift o Swap a un individuo y compara el
     * resultado de ambos movimientos para retornanr el mejor individuo,
     * resultado de dichos movimientos.
     *
     * @param s
     * @return
     */
    private IndividuoCuadratico tweak(IndividuoCuadratico s, Movimiento cambio) {
        IndividuoCuadratico individuoShift;
        IndividuoCuadratico individuoSwap;
        Movimiento cambioSwap = new Movimiento();
        Movimiento cambioShift = new Movimiento();

        individuoShift = shift(s, cambioShift);
        individuoSwap = UtilCuadratica.swap(s, cambioSwap);

        if (individuoSwap == null || (cambioShift.i > 0 && individuoShift.compareTo(individuoSwap) > 0)) {
            if (individuoShift.compareTo(s) > 0) {
                cambio.i = cambioShift.i;
                cambio.j = cambioShift.j;
                return individuoShift;
            }
        } else if (individuoSwap.compareTo(s) > 0) {
            cambio.i = cambioSwap.i;
            cambio.j = cambioSwap.j;
            return individuoSwap;
        }
        return null;
    }

    /**
     * El movimient Shift adicina un elemento no seleccionado a la solución
     * actual o remueve un elemento seleccionado de esta.
     *
     * @param individuo
     * @param cambio
     * @return indice del elemento modificado
     */
    protected IndividuoCuadratico shift(IndividuoCuadratico individuo, Movimiento cambio) {
        individuo = individuo.clone();
        int aleatorio;
        int maxLen = individuo.getDimension();
        aleatorio = Aleatorio.nextInt(maxLen);
        boolean cabe = funcion.cabe(individuo, aleatorio);
        // el elemento esta agregado?
        if (individuo.get(aleatorio) == 1 || !cabe) {
            individuo.set(aleatorio, 0);
            cambio.i = aleatorio;
        } else // verificar que el elemento no sobrepase la capacidad de la mochila
        {
            if (funcion.cabe(individuo, aleatorio)) {
                individuo.set(aleatorio, 1);
                cambio.i = aleatorio;
            }
        }

        return individuo;
    }

}
