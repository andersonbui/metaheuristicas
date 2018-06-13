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
import main.mochila.IndividuoMochila;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class GraspFundamental extends GraspReinicio {

    /**
     *
     * @param funcionGreedy
     * @param sigma
     * @param lamda
     * @param gama
     * @param beta
     */
    public GraspFundamental(FuncionGraspTabuR funcionGreedy, int sigma, int lamda, int gama, int beta) {
        super(funcionGreedy, lamda, gama, beta);
        nombre = "GraspFund";

    }

    /**
     * GRASP CON REINICIOS BASADO EN LA MEMORIA
     *
     * numLanda es el numero de reinicios usados para intentar mejorar bestLB
     * numDelta es el numero de iteraciones GRASp
     *
     * @return
     */
    @Override
    public List<Individuo> ejecutar() {
        //linea 1:
        IndividuoMochila LB = null;
        IndividuoMochila S = null;
        IndividuoMochila solParcialS = new IndividuoMochila(funcion);
        IndividuoMochila bestLB = solParcialS;
        int cont = 0;
        int m = 0;
        int minLen = gama;
        int maxLen = gama + beta;
        List<Individuo> listaRecorrido = new ArrayList();
        for (k = m * sigma + 1; k <= (m + 1) * sigma; k++) {
            //Linea 4:
            S = faseConstruccion(minLen, maxLen, solParcialS);
            //Linea 5:
            S = faseBusquedaLocal(S);
            S.evaluar();
            s_sup_i.add(S);
            //Linea 6:
            if (bestLB.compareTo(S) < 0) {
                bestLB = S;
            }
            //Linea 7:
            listaRecorrido.add(bestLB);
        }
        //linea 16: 
        iteraciones = k;
        return listaRecorrido;
    }

}
