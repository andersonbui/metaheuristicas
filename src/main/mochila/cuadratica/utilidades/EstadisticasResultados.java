/*
 * Copyright (C) 2019 debian
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
package main.mochila.cuadratica.utilidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import main.ResultadoAlgoritmo;
import main.ResultadoGrupo;
import main.mochila.cuadratica.IndividuoCuadratico;
import main.mochila.cuadratica.MochilaCuadraticaEjecucion;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */
public class EstadisticasResultados {

    public RegistroResultadoGlobal buscar(List<RegistroResultadoGlobal> listaRegistros, String nombreAlg, String grupo) {
        RegistroResultadoGlobal result = null;
        for (RegistroResultadoGlobal listaRegistro : listaRegistros) {
            if (listaRegistro.algoritmo.equals(nombreAlg) && listaRegistro.grupo.equals(grupo)) {
                result = listaRegistro;
            }
        }
        return result;
    }

    public void estadisticas(List<ResultadoGrupo> listaResutadosGrupo) {

        List<RegistroResultadoGlobal> listaRegistros = new ArrayList<>();
        MochilaCuadraticaEjecucion mce = new MochilaCuadraticaEjecucion();

        InstanciaAlgoritmo parametros;
        IndividuoCuadratico mejorGlobal = null;
        StringBuilder sb = new StringBuilder();
        String nombreIns = "";
//        sb.append(sb)

        for (ResultadoGrupo resultadoGrupo : listaResutadosGrupo) {

//            StringBuilder sb = new StringBuilder();
            for (ResultadoAlgoritmo resultado : resultadoGrupo) {
                String nombreAlg = resultado.algoritmo.getNombre();
                String grupo = "grupo";
                RegistroResultadoGlobal lo = null;

                RegistroResultadoGlobal reg = buscar(listaRegistros, nombreAlg, grupo);
                if (reg == null) {
                    reg = new RegistroResultadoGlobal();
                    reg.grupo = grupo;
                    reg.algoritmo = nombreAlg;
                    reg.porcetajeAcierto = 0;
                    listaRegistros.add(reg);
                }
                reg.desviacion = resultado.desviacionCalidadOptimos;
                reg.intentos = (int)resultado.numPruebas;
                reg.porcetajeAcierto += resultado.exitos;

                AlgoritmoMetaheuristico algot = resultado.algoritmo;
                FuncionGen funcion = algot.getFuncion();
                String cad = mce.formatearCabecera(
                        resultadoGrupo.getInstancia(),
                        funcion.getNombre(),
                        algot.getNombre(),
                        UtilCuadratica.formatear(funcion.getDimension()),
                        UtilCuadratica.formatear(resultado.promedioIteraciones),
                        UtilCuadratica.formatear((double) resultado.exitos),
                        UtilCuadratica.formatear(resultado.mejorRecorrido.getMejorIndividuo().getCalidad()),
                        UtilCuadratica.formatear(resultado.promedioCalidadOptimos),
                        UtilCuadratica.formatear(resultado.desviacionCalidadOptimos),
                        UtilCuadratica.formatear(resultado.tiempoTotal),
                        UtilCuadratica.formatear(resultado.promedionumEvaluaciones)
                );
                sb.append(cad);
            }

            if (resultadoGrupo == null) {
                continue;
            }
            parametros = resultadoGrupo.getParametros();
            LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();

            String stringResult = mce.armarResultados(resultadoGrupo);
            if ("v".equals(mce.tsalida) || "b".equals(mce.tsalida)) {
                imprimir(stringResult);
            }
            IndividuoCuadratico individuo = (IndividuoCuadratico) resultadoGrupo.getMejorIndividuo();
            // almacenar mejor global
            if (mejorGlobal == null || mejorGlobal.compareTo(individuo) < 0) {
                mejorGlobal = individuo;
            }
            // comprobar calidad de la actua instancia y actualizar los archivos de InstanciaAlgoritmo
            {
                if (parametros.getMaxGlobal().isNaN() || parametros.getMaxGlobal().compareTo(individuo.getCalidad()) < 0) {
                    parametros.setMaxGlobal(individuo.getCalidad());
                    parametros.setVectorIdeal(mce.vDouble_vInt(individuo.getValores()));

                    try {
                        lpc.actualizar(parametros.getNombreArchivo(), parametros);
                    } catch (Exception e) {
                        System.err.println("---fallo la actualizacion del maximo global:\n " + e.getLocalizedMessage());
                        System.err.println("---mensaje de error: \n " + e.getMessage());
                    }
                } else if (parametros.getMaxGlobal().compareTo(individuo.getCalidad()) > 0) {
//                        System.out.println("No se encontro mejor");
                }
            }
        }
    }

    public void imprimir(String cadena) {
        System.out.println("carambolas");
    }

    public class RegistroResultadoGlobal {

        private String algoritmo;
        private int intentos;
        private String grupo;
        private int tam_grupo;
        private double tasaAciertos;
        private double tiempoPromedio;
        private double tiempoMinino;
        private double porcetajeAcierto;
        private double desviacion;
        private Calendar fecha;
        private String parametros;

    }

}
