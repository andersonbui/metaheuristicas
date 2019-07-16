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
import java.util.List;
import main.mochila.cuadratica.utilidades.ParametrosInstancia;
import main.Grupo;
import main.mochila.cuadratica.IHEA.IHEA_AjusteVariables.IHEA_AV;
import main.mochila.cuadratica.IHEA.IHEA_M1.IHEA_M1;
import main.mochila.cuadratica.anson.EstrategiaEvolucionDiferencialConGreedy;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy;
import main.mochila.cuadratica.graspBasadoMemoria.FuncionGraspTabuR;
import main.mochila.cuadratica.graspBasadoMemoria.GraspFundamental;
import main.mochila.cuadratica.graspBasadoMemoria.GraspTabuReinicio;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_mejorado.FuncionMochilaIHEA_M;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_mejorado.IteratedHyperplaneExplorationAlgoritm_M;
import main.mochila.cuadratica.sgvns.FuncionJSGVNS;
import main.mochila.cuadratica.sgvns.FuncionSGVNS;
import main.mochila.cuadratica.sgvns.IndividuoVNS;
import main.mochila.cuadratica.sgvns.JSGVNS;
import main.mochila.cuadratica.sgvns.SGVNS;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_ajustado.FuncionMochilaIHEA_A;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_ajustado.IteratedHyperplaneExplorationAlgoritm_A;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.FabricaAlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class GrupoAlgoritmosMochilaCuadratica extends Grupo {

    public static enum AlgoritmoOpion {
        OPCION_IHEA, OPCION_IHEA_VA, OPCION_IHEA_M1, OPCION_IHEA_MT, OPCION_SGVNS, OPCION_JSGVNS
    };
    AlgoritmoOpion opcion;
    List argsAlgoritmo;

    /**
     *
     * @param instancias
     */
    public GrupoAlgoritmosMochilaCuadratica(ParametrosInstancia instancias) {
        this();
        this.instancias = instancias;
    }

    public GrupoAlgoritmosMochilaCuadratica() {
        super("Mochila Cuadratica", 20);
        this.instancias = null;
    }

    /**
     *
     * @param opcion
     */
    @Override
    public void inicializar() {
        double[][] matrizBeneficios = instancias.getMatrizBeneficios();
        double capacidad = instancias.getCapacidad();
        double[] vectorPesos = instancias.getVectorPesos();
        Double maxGlobal = instancias.getMaxGlobal();
        switch (opcion) {
//
//        add(new FabricaAlgoritmoMetaheuristico() {
//            @Override
//            public AlgoritmoMetaheuristico obtener() {
//
//                FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//                IteratedHyperplaneExplorationAlgoritm algotIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);
//                algotIHEA.setSaltar(false);
//                algotIHEA.setParametros(instancias);
//                algotIHEA.addNombre("OPCION_IHEA");
//                return algotIHEA;
//            }
//        });
//
            case OPCION_IHEA:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);
                        algotIHEA.inicializar();
                        algotIHEA.setParametros(instancias);
                        algotIHEA.addNombre("Impl");

                        return algotIHEA;
                    }
                });
                break;
            case OPCION_IHEA_M1:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_M1(funcionHyperplanos);
                        algotIHEA.inicializar();
                        algotIHEA.setParametros(instancias);
                        algotIHEA.addNombre("M1");

                        return algotIHEA;
                    }
                });
                break;
            case OPCION_IHEA_VA:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_AV(funcionHyperplanos);
                        algotIHEA.inicializar();
                        algotIHEA.setParametros(instancias);
                        algotIHEA.addNombre("IHEA_VA");

                        return algotIHEA;
                    }
                });
                break;
////
//        add(new FabricaAlgoritmoMetaheuristico() {
//            @Override
//            public AlgoritmoMetaheuristico obtener() {
//                FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA_M(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//                IteratedHyperplaneExplorationAlgoritm_M algotIHEA = new IteratedHyperplaneExplorationAlgoritm_M(funcionHyperplanos);
//                algotIHEA.setSaltar(true);
//                algotIHEA.setParametros(instancias);
//                algotIHEA.addNombre("IHEA_M");
//                return algotIHEA;
//            }
//        });
//////
            case OPCION_IHEA_MT:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {
                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA_A(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        IteratedHyperplaneExplorationAlgoritm_A algotIHEA = new IteratedHyperplaneExplorationAlgoritm_A(funcionHyperplanos);
                        algotIHEA.inicializar();
                        algotIHEA.setSaltar(true);
                        algotIHEA.setParametros(instancias);
                        algotIHEA.addNombre("IHEA_MT");
                        return algotIHEA;
                    }
                });
                break;
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
//        int[] vecImayor = {20};
//        for (int intentos : vecImayor) {
//            add(new FabricaAlgoritmoMetaheuristico() {
//                @Override
//                public AlgoritmoMetaheuristico obtener() {
//
//                    FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//                    OPCION_SGVNS algot = new OPCION_SGVNS(funcionVns, maxIteraciones);
//                    //algot.setIntentosIntercambio(intentos);
//                    algot.setIntentosEncontrarMejor(intentos);
//                    algot.addNombre("-Int[" + intentos + "]");
//                    return algot;
//                }
//            });
//
//        }
///////////////////////////
//////////////////////
            case OPCION_JSGVNS:
                int[] vecImayor = {20};
                for (int intentos : vecImayor) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {

                            FuncionJSGVNS funcionVns = new FuncionJSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            JSGVNS algot = new JSGVNS(funcionVns, maxIteraciones);
                            //algot.setIntentosIntercambio(intentos);
                            algot.setIntentosEncontrarMejor(intentos);
                            algot.addNombre("-Int[" + intentos + "]");
                            return algot;
                        }
                    });

                }
                break;
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

    public AlgoritmoOpion getOpcion() {
        return opcion;
    }

    public void setOpcion(AlgoritmoOpion opcion) {
        this.opcion = opcion;
    }

}
