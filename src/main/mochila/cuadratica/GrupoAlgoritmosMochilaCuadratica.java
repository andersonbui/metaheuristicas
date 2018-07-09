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
package main.mochila.cuadratica;

import main.mochila.Grupo;
import main.mochila.cuadratica.anson.EstrategiaEvolucionDiferencialConGreedy;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy;
import main.mochila.cuadratica.graspBasadoMemoria.FuncionGraspTabuR;
import main.mochila.cuadratica.graspBasadoMemoria.GraspTabuReinicio;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.sgvns.FuncionSGVNS;
import main.mochila.cuadratica.sgvns.VNS;

/**
 *
 * @author debian
 */
public class GrupoAlgoritmosMochilaCuadratica extends Grupo {

    private final ParametrosCuadratica parametros;

    /**
     *
     * @param parametros
     * @param maxIteraciones
     */
    public GrupoAlgoritmosMochilaCuadratica(ParametrosCuadratica parametros, int maxIteraciones) {
        super(parametros, "Mochila Cuadratica", maxIteraciones);
        this.parametros = parametros;
        this.maxIteraciones = maxIteraciones;

    }

    @Override
    public void inicializar() {
        double[][] matrizBeneficios = parametros.matrizBeneficios;
        double capacidad = parametros.capacidad;
        double[] vectorPesos = parametros.vectorPesos;
        Double maxGlobal = parametros.maxGlobal;

        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        add(new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos));

        FuncionMochilaCuadraticaGreedy funcionEDG = new FuncionMochilaCuadraticaGreedy(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        add(new EstrategiaEvolucionDiferencialConGreedy(funcionEDG, maxIteraciones, 10));

        FuncionGraspTabuR funcionGreedy = new FuncionGraspTabuR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        add(new GraspTabuReinicio(funcionGreedy, maxIteraciones, 5, 4, 10, 20));

        FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        add(new VNS(funcionVns, maxIteraciones/2));

    }

}
