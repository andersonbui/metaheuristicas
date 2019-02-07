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

import java.util.Iterator;
import main.mochila.cuadratica.utilidades.ParametrosCuadratica;
import main.Grupo;
import main.mochila.cuadratica.anson.EstrategiaEvolucionDiferencialConGreedy;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy;
import main.mochila.cuadratica.graspBasadoMemoria.FuncionGraspTabuR;
import main.mochila.cuadratica.graspBasadoMemoria.GraspFundamental;
import main.mochila.cuadratica.graspBasadoMemoria.GraspTabuReinicio;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.sgvns.FuncionSGVNS;
import main.mochila.cuadratica.sgvns.IndividuoVNS;
import main.mochila.cuadratica.sgvns.VNS;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class GrupoAlgoritmosMochilaCuadratica extends Grupo {

    private final ParametrosCuadratica parametros;
    /**
     *
     * @param parametros
     */
    public GrupoAlgoritmosMochilaCuadratica(ParametrosCuadratica parametros) {
        super(parametros, "Mochila Cuadratica", 20);
        this.parametros = parametros;

    }

    @Override
    public void inicializar() {
        double[][] matrizBeneficios = parametros.getMatrizBeneficios();
        double capacidad = parametros.getCapacidad();
        double[] vectorPesos = parametros.getVectorPesos();
        Double maxGlobal = parametros.getMaxGlobal();

//        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);
//        algotIHEA.setSaltar(false);
//        algotIHEA.addNombre("IHEA");
//        add(algotIHEA);
//        funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        algot = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);
//        algot.setSaltar(true);
//        algot.addNombre("SS");
//        add(algot);
//        FuncionMochilaCuadraticaGreedy funcionEDG = new FuncionMochilaCuadraticaGreedy(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        add(new EstrategiaEvolucionDiferencialConGreedy(funcionEDG, maxIteraciones, 10));
//        FuncionGraspTabuR funcionGreedy = new FuncionGraspTabuR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        add(new GraspTabuReinicio(funcionGreedy, maxIteraciones, 5, 4, 10, 20));
/////////////////////
//        FuncionGraspTabuR funcionGreedy2 = new FuncionGraspTabuR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        add(new GraspFundamental(funcionGreedy2, 15, 4, 10, 20));
//////////////////////
//        FuncionGraspTabuR funcionGreedy3 = new FuncionGraspTabuR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//        add(new GraspFundamental(funcionGreedy3, 5, 4, 10, 20));
//
//////////////////////
        int[] vecImayor = {80};
        for (int intentos : vecImayor) {
            FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
            VNS algot = new VNS(funcionVns, maxIteraciones);
            //algot.setIntentosIntercambio(intentos);
            algot.setIntentosEncontrarMejor(intentos);
            algot.addNombre("-Int[" + intentos + "]");
            add(algot);
        }
///////////////////////////
//        int[] vecIintentos = {20};
//        for (int intentos : vecIintentos) {
//            FuncionSGVNS funcionVns2 = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//            AlgoritmoMetaheuristico algot = new VNS(funcionVns2, maxIteraciones * 7) {
//                @Override
//                public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
//                    int h = 1;
//                    IndividuoVNS s_inicial = individuoOriginal.clone();
//                    IndividuoVNS solEncontrada;
//                    while (h <= 2) {
//                        solEncontrada = encontrarMejor(s_inicial, h);
//                        if (solEncontrada.compareTo(s_inicial) < 0) {
//                            h = 1;
//                        } else {
//                            s_inicial = solEncontrada;
//                            h++;
//                        }
//                    }
//                    return s_inicial;
//                }
//            };
//            algot.setIteraciones(intentos);
//            algot.addNombre("-Martha-Int[" + intentos + "]");
//            add(algot);
//        }
///////////////////////////
//        int[] vecIntentosR = {12};
////        int[] vecIntentosR = {5, 10, 7, 12};
//
//        for (int intentos : vecIntentosR) {
//            FuncionSGVNS funcionVns2 = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//            AlgoritmoMetaheuristico algot = new VNS(funcionVns2, maxIteraciones * 2) {
//                @Override
//                public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
//                    int h = 1;
//                    IndividuoVNS s_inicial = individuoOriginal.clone();
//                    IndividuoVNS solEncontrada;
//                    int intentosR = 0;
//                    while (h <= 2) {
//                        solEncontrada = encontrarMejor(s_inicial, h);
//                        if (solEncontrada.compareTo(s_inicial) > 0) {
//                            s_inicial = solEncontrada;
//                            h = 1;
//                            intentosR++;
//                            if (intentosR > intentos) {
//                                h++;
//                                intentosR = 0;
//                            }
//                        } else {
//                            h++;
//                            intentosR = 0;
//                        }
//                    }
//                    return s_inicial;
//                }
//            };
//            algot.setIteraciones(intentos);
//            algot.addNombre("-juan-Int[" + intentos + "]");
//            add(algot);
//
//        }
//
    }

}
