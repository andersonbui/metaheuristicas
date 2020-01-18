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
package main.mochila.cuadratica.IHEA.IHEA_GAR;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IHEA.IHEA_M1.IHEA_M1;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.utilidades.ComparacionIdeal;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class IHEA_GAR extends IHEA_M1 {

    public static enum OpcionVar {
        TABU, PERT, CONS, DESC
    };
    // porcentaje de iteraciones | central | no central 
    String tabu = "0.0:1.0:1.0";
    String pert = "0.0:1.0:1.0";
    String cons = "0.0:1.0:1.0";
    String desc = "0.0:1.0:1.0";

    public IHEA_GAR(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<IndividuoIHEA> iterateHiperplaneExploration(int L, int rcl, int maxIter) {

        List<IndividuoIHEA> recorrido = new ArrayList();
        /**
         * maximo tamanio de la lista de ejecución(lista que guarda todos los
         * atributos de los movimientos implementados), que sirve como condicion
         * determinacion.
         */
        // indice de las variables fijas para el problema restringido
        List<Integer> variablesFijas;
        //variable bandera.
        boolean solucionEncontrada;
        //dimension k.
        int k;
        /**
         * linea 3: x0 = GreedyRandomizedConstruction(rcl). solucion inicial
         */
        IndividuoIHEA indi = new IndividuoIHEA_GAR(getFuncion());
        getFuncion().setPorcentajeCentral(1);
        getFuncion().setPorcentajeNoCentral(1);
        IndividuoIHEA x_inicial = GreedyRandomizedConstruction(indi, getRcl());
        /**
         * linea 4: x0 = descent(x0). mejoramiento de la solución inicial
         */
        x_inicial = descent(x_inicial);
        /**
         * linea 5: x' = x0. x' represents the current solution.
         */
        IndividuoIHEA x_prima = x_inicial.clone();
        // linea 6: iter = 0
        iteraciones = 0;
        /**
         * linea 7: xb= x'. Xb records the best solution found in current round
         * of hyperplane exploration.
         */
        IndividuoIHEA x_mejorRondaHyper = x_prima.clone();
        /**
         * linea 8: x* = xb. x* records the global best solution.
         */
        IndividuoIHEA x_mejorGlobal = x_mejorRondaHyper.clone();
        //linea 9:
        int contadorEvaluaciones = 0;
        for (; iteraciones < maxIter; iteraciones++) {
//            boolean suficiente = funcion.suficiente(x_mejorGlobal);
//            if (suficiente) {
//                recorrido.add(x_mejorGlobal);
//                return recorrido;
//            }
            // linea 10:
            solucionEncontrada = true;
            // linea 11:
            k = dimensionHiperplano(x_prima);
            // linea 12: construct constrain problem CQKP[k]
            // linea 13: fase de exploracion
            // linea 14:
            while (solucionEncontrada) {
                // linea 14:
                variablesFijas = determinarVariablesFijas(k, x_prima, getLb());
                // linea 16: construct reduce constrain problem
                construirProblemaRestringidoReducido(variablesFijas, x_prima);
                //contar variables fijas pertenecientes al ideal
                // pruebas
                if (depuracion != null) {
                    contadorEvaluaciones++;
                    depuracion.evaluarVariablesFijas(instancias, variablesFijas, getFuncion().getDimension());
                }
                // linea 17: run TABU serach engine (L,x',xb)
//                long tiempo_inicial = System.currentTimeMillis();
//                contadortabu++;

                variable(OpcionVar.TABU, iteraciones, maxIter);
                x_prima = tabuSearchEngine(L, x_prima, x_mejorRondaHyper);

//                long tiempo_final = System.currentTimeMillis();
//                tiempototal += (tiempo_final - tiempo_inicial);
                // linea 18:
                if (x_prima.compareTo(x_mejorRondaHyper) > 0) {
                    // linea 19:
                    if (x_prima.pesar() <= funcion.getCapacidad()) {

                        x_mejorRondaHyper = x_prima.clone();
                        // linea 21
                        x_prima = addElemento(x_mejorRondaHyper);
                        // linea 20:
                        k = dimensionHiperplano(x_prima);
                        // linea 22: construct constrain problem CQKP[k]
                    }
                } else {
                    // linea 24:
                    solucionEncontrada = false;
                }
                
                //RESTABLECER
                getFuncion().setPorcentajeCentral(1);
                getFuncion().setPorcentajeNoCentral(1);
//                recorrido.addElemento(x_mejorGlobal);
                funcion.reiniciarVijarVariables();
            }
            //linea 27:
            if (x_mejorRondaHyper.compareTo(x_mejorGlobal) > 0) {
                // linea 28:
                x_mejorGlobal = x_mejorRondaHyper.clone();
            }
            // linea 30: fase de perturbacion
            // linea 31:

            if (iteraciones == maxIter / 2) {
            }
            x_prima = perturbacion(x_mejorRondaHyper, iteraciones);

            // linea 32:
            variable(OpcionVar.DESC, iteraciones, maxIter);
            x_prima = descent(x_prima);

            //RESTABLECER
            getFuncion().setPorcentajeCentral(1);
            getFuncion().setPorcentajeNoCentral(1);
            // linea 33:
            x_mejorRondaHyper = x_prima.clone();
            recorrido.add(x_mejorGlobal);
        }
        
        if (depuracion != null) {
            double cfn = depuracion.getContadorFijosFalsosNegativos() / contadorEvaluaciones;
            double cfp = depuracion.getContadorFijosFalsosPositivos() / contadorEvaluaciones;
            depuracion.setContadorFijosFalsosNegativos(cfn);
            depuracion.setContadorFijosFalsosPositivos(cfp);
        }

        return recorrido;
    }

    @Override
    public FuncionMochilaIHEA_GAR getFuncion() {
        return (FuncionMochilaIHEA_GAR) funcion;
    }

    public void variable(OpcionVar variable, int interaciones, int maxIter) {
        String[] cadenas = null;
        switch (variable) {
            case TABU:
                cadenas = tabu.split(":");
                break;
            case PERT:
                cadenas = pert.split(":");
                break;
            case CONS:
                cadenas = cons.split(":");
                break;
            case DESC:
                cadenas = desc.split(":");
                break;
            default:
                ;
        }
        if (cadenas != null) {
            try {
                double porcentajeInter = Double.parseDouble(cadenas[0]);
                double central = Double.parseDouble(cadenas[1]);
                double noCentral = Double.parseDouble(cadenas[2]);
                if (iteraciones == maxIter * porcentajeInter) {
                    getFuncion().setPorcentajeCentral(central);
                    getFuncion().setPorcentajeNoCentral(noCentral);
                }
            } catch (NumberFormatException e) {

            }
        }
    }

    @Override
    public void actualizarVarible(String nombre, String valor) {
        super.actualizarVarible(nombre, valor);
        switch (nombre) {
            case "TABU":
                tabu = valor;
                break;
            case "PERT":
                pert = valor;
                break;
            case "CONS":
                cons = valor;
                break;
            case "DESC":
                desc = valor;
                break;
        }
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
        int nf = (int) (getLb() + Math.max(0, (dimX - getLb()) * (1 - 1 / (0.008 * n))));

        int t = Math.min(getMt(), I1.size() - nf);
        t = Math.max(t, 0);
        int s = Math.min(getMs(), t);

        variable(OpcionVar.PERT, iteraciones, getMaxIteraciones());
        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad(I1, individuo, t, true);
        int posaleatoria;
        if (!listaIndices.isEmpty()) {
            for (int i = 0; listaIndices.size() > 0 && i < s; i++) {
                posaleatoria = Aleatorio.nextInt(listaIndices.size());
                individuo.set(listaIndices.remove(posaleatoria), 0);
            }
        }
        //RESTABLECER
        getFuncion().setPorcentajeCentral(1);
        getFuncion().setPorcentajeNoCentral(1);

        variable(OpcionVar.CONS, iteraciones, getMaxIteraciones());
        individuo = GreedyRandomizedConstruction(individuo, getRcl());

        //RESTABLECER
        getFuncion().setPorcentajeCentral(1);
        getFuncion().setPorcentajeNoCentral(1);

        return individuo;
    }
}
