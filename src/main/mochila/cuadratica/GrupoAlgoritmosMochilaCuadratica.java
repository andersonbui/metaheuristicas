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

import main.mochila.cuadratica.utilidades.InstanciaAlgoritmo;
import main.Grupo;
import static main.mochila.cuadratica.GrupoAlgoritmosMochilaCuadratica.AlgoritmoOpion.JSGVNS_GAR;
import main.mochila.cuadratica.IHEA.IHEA_AjusteVariables.IHEA_AV;
import main.mochila.cuadratica.IHEA.IHEA_GAR.FuncionMochilaIHEA_GAR;
import main.mochila.cuadratica.IHEA.IHEA_GAR.IHEA_GAR;
import main.mochila.cuadratica.IHEA.IHEA_M1.IHEA_M1;
import main.mochila.cuadratica.IHEA.IHEA_M2.IHEA_M2;
import main.mochila.cuadratica.IHEA.IHEA_M3.IHEA_M3;
import main.mochila.cuadratica.IHEA.IHEA_M4.FuncionMochilaIHEA_M4;
import main.mochila.cuadratica.IHEA.IHEA_M4.IHEA_M4;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.sgvns.SGVNS_M3.FuncionMochilaSGVNS_M3;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;
//import main.mochila.cuadratica.sgvns.SGVNS;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_ajustado.FuncionMochilaIHEA_A;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_ajustado.IteratedHyperplaneExplorationAlgoritm_A;
import main.mochila.cuadratica.sgvns.FuncionJSGVNS;
import main.mochila.cuadratica.sgvns.JSGVNS;
import main.mochila.cuadratica.sgvns.SGVNS_GAR.FuncionMochilaVNS_GAR;
import main.mochila.cuadratica.sgvns.SGVNS_GAR.JSGVNS_GAR;
import main.mochila.cuadratica.sgvns.SGVNS_M1.SGVNS_M1;
import main.mochila.cuadratica.sgvns.SGVNS_M2.SGVNS_M2;
import main.mochila.cuadratica.sgvns.SGVNS_M3.SGVNS_M3;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.FuncionSGVNS;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.FabricaAlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class GrupoAlgoritmosMochilaCuadratica extends Grupo {

    public static enum AlgoritmoOpion {
        IHEA, IHEA_VA, IHEA_GAR, IHEA_M1, IHEA_M2, IHEA_M3, IHEA_M4, IHEA_MT, SGVNS,SGVNS_M1,SGVNS_M2,SGVNS_M3, JSGVNS, JSGVNS_GAR
    };
    AlgoritmoOpion opcion;
    String parametrosAlgoritmo;

    /**
     *
     * @param instancias
     */
    public GrupoAlgoritmosMochilaCuadratica(InstanciaAlgoritmo instancias) {
        this();
        this.instancias = instancias;
        this.parametrosAlgoritmo = null;
    }

    public GrupoAlgoritmosMochilaCuadratica() {
        super("Mochila Cuadratica", 30);
        this.instancias = null;
    }

    /**
     *
     */
    @Override
    public void inicializar() {
        double[][] matrizBeneficios = instancias.getMatrizBeneficios();
        double capacidad = instancias.getCapacidad();
        double[] vectorPesos = instancias.getVectorPesos();
        Double maxGlobal = instancias.getMaxGlobal();
        int[] vecImayor = {20};
        switch (opcion) {
//
//        add(new FabricaAlgoritmoMetaheuristico() {
//            @Override
//            public AlgoritmoMetaheuristico obtener() {
//
//                FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
//                IteratedHyperplaneExplorationAlgoritm algotIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);
//                algotIHEA.setSaltar(false);
//                algotIHEA.setInstancias(InstanciaAlgoritmo);
//                algotIHEA.addNombre("IHEA");
//                return algotIHEA;
//            }
//        });
//
            case IHEA:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
                        algotIHEA.addNombre("Impl");

                        return algotIHEA;
                    }
                });
                break;
            case IHEA_M1:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_M1(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
                        algotIHEA.addNombre("M1");

                        return algotIHEA;
                    }
                });
                break;
            case IHEA_GAR:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA_GAR funcionHyperplanos = new FuncionMochilaIHEA_GAR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_GAR(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
                        algotIHEA.addNombre("GAR");
                        return algotIHEA;
                    }
                });
                break;
            case IHEA_M2:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {
                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_M2(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
                        algotIHEA.addNombre("M2");
                        return algotIHEA;
                    }
                });
                break;
            case IHEA_M3:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {
                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_M3(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
                        algotIHEA.addNombre("M3");
                        return algotIHEA;
                    }
                });
                break;
            case IHEA_M4:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {
                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA_M4(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_M4(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
                        algotIHEA.addNombre("M4");
                        return algotIHEA;
                    }
                });
                break;
            case IHEA_VA:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {

                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        funcionHyperplanos.setInstancias(instancias);
                        IteratedHyperplaneExplorationAlgoritm algotIHEA = new IHEA_AV(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setInstancias(instancias);
//                        algotIHEA.addNombre("_VA");

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
//                algotIHEA.setInstancias(InstanciaAlgoritmo);
//                algotIHEA.addNombre("IHEA_M");
//                return algotIHEA;
//            }
//        });
//////
            case IHEA_MT:
                add(new FabricaAlgoritmoMetaheuristico() {
                    @Override
                    public AlgoritmoMetaheuristico obtener() {
                        FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA_A(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                        IteratedHyperplaneExplorationAlgoritm_A algotIHEA = new IteratedHyperplaneExplorationAlgoritm_A(funcionHyperplanos);
                        algotIHEA.setCadenaParametros(parametrosAlgoritmo);
                        algotIHEA.inicializar();
                        algotIHEA.setSaltar(true);
                        algotIHEA.setInstancias(instancias);
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
//                    SGVNS algot = new SGVNS(funcionVns, maxIteraciones);
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
            case SGVNS:
                for (int intentos : vecImayor) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {
                            FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            SGVNS algot = new SGVNS(funcionVns, maxIteraciones);
                            //algot.setIntentosIntercambio(intentos);
//                            algot.setIntentosEncontrarMejor(intentos);
//                            algot.addNombre("-Int[" + intentos + "]");
                                algot.inicializar();
                            return algot;
                        }
                    });
                }
                break;
            ///////////////////////////
//////////////////////
                case SGVNS_M1:
                for (int intentos : vecImayor) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {
                            FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            SGVNS algot = new SGVNS_M1(funcionVns, maxIteraciones);
                            //algot.setIntentosIntercambio(intentos);
//                            algot.setIntentosEncontrarMejor(intentos);
//                            algot.addNombre("-Int[" + intentos + "]");
                                algot.inicializar();
                            return algot;
                        }
                    });
                }
                break;
            ///////////////////////////
//////////////////////
                case SGVNS_M2:
                for (int intentos : vecImayor) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {
                            FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            SGVNS algot = new SGVNS_M2(funcionVns, maxIteraciones);
                            //algot.setIntentosIntercambio(intentos);
//                            algot.setIntentosEncontrarMejor(intentos);
//                            algot.addNombre("-Int[" + intentos + "]");
                                algot.inicializar();
                            return algot;
                        }
                    });
                }
                break;
            ///////////////////////////
//////////////////////
                case SGVNS_M3:
                for (int intentos : vecImayor) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {
                            FuncionSGVNS funcionVns = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            SGVNS algot = new SGVNS_M3(funcionVns, maxIteraciones);
                            //algot.setIntentosIntercambio(intentos);
//                            algot.setIntentosEncontrarMejor(intentos);
//                            algot.addNombre("-Int[" + intentos + "]");
                              algot.inicializar();
                            return algot;
                        }
                    });
                }
                break;
            ///////////////////////////
//////////////////////
            case JSGVNS:
                double[] vecPenalizacion = {0.5};
                for (double intentos : vecPenalizacion) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {
                            FuncionJSGVNS funcionVns = new FuncionJSGVNS(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            JSGVNS algot = new JSGVNS(funcionVns, maxIteraciones);
                            algot.setCadenaParametros(parametrosAlgoritmo);
                            algot.inicializar();
                            //algot.setIntentosIntercambio(intentos);
//                            algot.setPenalizacion(intentos);
                            algot.setIntentosEncontrarMejor(20);
                            algot.addNombre("-Int[" + intentos + "]");
                            return algot;
                        }
                    });

                }
                break;
///////////////////////////
            //////////////
            case JSGVNS_GAR:
                double[] vecPenalizacion_GAR = {0.5};
                for (double intentos : vecPenalizacion_GAR) {
                    add(new FabricaAlgoritmoMetaheuristico() {
                        @Override
                        public AlgoritmoMetaheuristico obtener() {
                            FuncionMochilaVNS_GAR funcionVns = new FuncionMochilaVNS_GAR(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                            JSGVNS_GAR algot = new JSGVNS_GAR(funcionVns, maxIteraciones);
                             algot.setCadenaParametros(parametrosAlgoritmo);
                             algot.inicializar();
                            //algot.setIntentosIntercambio(intentos);
//                            algot.setPenalizacion(intentos);
                            algot.setIntentosEncontrarMejor(20);
                            algot.addNombre("-Int[" + intentos + "]");
                            return algot;
                        }
                    });

                }
                break;
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
/////////////////////////////
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

    public String getParametrosAlgoritmo() {
        return parametrosAlgoritmo;
    }

    public void setParametrosAlgoritmo(String parametrosAlgoritmo) {
        this.parametrosAlgoritmo = parametrosAlgoritmo;
    }
}
