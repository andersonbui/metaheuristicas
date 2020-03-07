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
package main.mochila.cuadratica.IHEA.IHEA_AjusteVariables;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class IHEA_AV extends IteratedHyperplaneExplorationAlgoritm {

    public IHEA_AV(FuncionMochilaIHEA funcion) {
        super(funcion);
        setNombre("IHEA_VA");
    }

    @Override
    public void inicializar() {
        setL(35);
        setMt(15);
//        setMs(3);
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected IndividuoIHEA perturbacion(IndividuoIHEA individuo, int iteraciones) {
        individuo = individuo.clone();
        List<Integer> I1 = elementosDentro(individuo);

        // dimension de individuo
        int dimX = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = getNumeroNF(individuo);

        int t = Math.min(getMt(), I1.size() - nf);
        t = Math.max(t, 0);
        int s = Math.min(getMs(), t);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad(I1, individuo, t, true);
        int posaleatoria;
//        List<Integer> list = new ArrayList();
        if (!listaIndices.isEmpty()) {
            for (int i = 0; listaIndices.size() > 0 && i < s; i++) {
                posaleatoria = Aleatorio.nextInt(listaIndices.size());
                Integer indice = listaIndices.remove(posaleatoria);
                individuo.set(indice, 0);
//                list.add(indice);
            }
//            posaleatoria = Aleatorio.nextInt(list.size());
//            Integer indice = list.remove(posaleatoria);
//            getTabu()[indice] = 4;
        }
        individuo = GreedyRandomizedConstruction(individuo, getRcl());
        return individuo;
    }
}
