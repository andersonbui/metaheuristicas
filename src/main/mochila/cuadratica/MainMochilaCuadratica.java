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
package main.mochila.cuadratica;

import main.GrupoInstancias;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.EjecutarGrupo;
import main.EjecutorAlgoritmo;
import main.ResultadoAlgoritmo;
import main.ResultadoGrupo;
import main.mochila.cuadratica.utilidades.LecturaParametrosCuadratica;
import main.mochila.cuadratica.utilidades.ParametrosCuadratica;
import main.utilidades.EscribirArchivo;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.IndividuoGen;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {

    public static void main(String[] args) throws FileNotFoundException, Exception {

        System.out.println("");
        int numIntentos;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
//        graficaDispercion2D = true;
        // iteraciones realizadas por los algoritmos
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numIntentos = 100;
        String nombreArchivo;
        String mensaje = "";
        List<ResultadoGrupo> listResultadosGrupos = new ArrayList();
        ConjuntoDInstancias datos = new ConjuntoDInstancias();
        String nombreArchivoResultado = datos.getNombreResultado();
        List<String[]> listaInstanc = datos.getConjuntoInstancias();
        
        StringBuilder sbCabecera = new StringBuilder();
        sbCabecera.append("----------------------------------------------------------.\n");
        sbCabecera.append("Numero Intentos:").append(numIntentos).append("\n");
        sbCabecera.append("NPI: Numero iteraciones promedio.\n");
        sbCabecera.append("TP: Tiempo Promedio.\n");
        sbCabecera.append("TE: Tasa de exito.\n");
        sbCabecera.append("DPR: Desviación porcentual relativa.\n");
        sbCabecera.append("----------------------------------------------------------.\n");

        List<HiloEjecucion> hilos = new ArrayList<>();
        String campos = formatearCabecera("FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
                "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
        sbCabecera.append(campos);

        EscribirArchivo archivo_resultados = new EscribirArchivo();
        archivo_resultados.abrir(nombreArchivoResultado);
        archivo_resultados.escribir(sbCabecera.toString());
        archivo_resultados.terminar();


        for (String[] instancia : listaInstanc) {
            String nombreInst = instancia[0];
            nombreArchivo = instancia[1];
            mensaje += "####";
            mensaje += "----Nombre archivo: " + nombreInst + "----\n";
            // dimension de los puntos;
            LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
            ParametrosCuadratica parametros = lpc.obtenerParametros(nombreArchivo);
            if (parametros == null) {
                System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                continue;
            }
            GrupoAlgoritmosMochilaCuadratica grupo = new GrupoAlgoritmosMochilaCuadratica(parametros);
            grupo.inicializar();
            EjecutarGrupo ejecutor = new EjecutarGrupo();
            // EJECUTAR ANALISIS
            ejecutor.setParametros(grupo, graficaRecorrido3D, graficaDispercion2D, numIntentos, nombreInst);
            // Multi-hilo
            hilos.add(new HiloEjecucion(parametros, ejecutor, mensaje));

            mensaje = "";
        }
        for (HiloEjecucion hilo : hilos) {
            hilo.start();
        }
        for (HiloEjecucion hilo : hilos) {
            hilo.join();

            archivo_resultados.abrir(nombreArchivoResultado, true);
            archivo_resultados.escribir(hilo.mensaje);

            ResultadoGrupo resultadoGrupo = hilo.resultadoGrupo;
            ParametrosCuadratica parametros = hilo.parametros;

            if (resultadoGrupo != null) {
                listResultadosGrupos.add(resultadoGrupo);
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();

                String stringResult = imprimirResultados(resultadoGrupo);
                archivo_resultados.escribir(stringResult);
                IndividuoGen individuo = resultadoGrupo.getMejorIndividuo();
                // comprobar calidad de la actua instancia y actualizar los archivos de instancias
                if (parametros.getMaxGlobal().isNaN()) {
                    if (parametros.getMaxGlobal().compareTo(individuo.getCalidad()) < 0) {
                        parametros.setMaxGlobal(individuo.getCalidad());
                        parametros.setVectorIdeal(vDouble_vInt(individuo.getValores()));

                        try {
                            lpc.actualizar(parametros.getNombreArchivo(), parametros);
                        } catch (Exception e) {
                            System.out.println("---fallo la actualizacion del maximo global:\n " + e.getLocalizedMessage());
                            System.out.println("---mensaje de error: \n " + e.getMessage());
                        }
                    } else if (parametros.getMaxGlobal().compareTo(individuo.getCalidad()) > 0) {
                        System.out.println("se encontro mejor");
                    }
                }
            } else {
                System.out.println("pailas");
            }
            archivo_resultados.terminar();
        }

        archivo_resultados.abrir(nombreArchivoResultado, true);
        String resumen = imprimirResumen(listResultadosGrupos);
        archivo_resultados.escribir(resumen);
        archivo_resultados.terminar();
    }

    static class HiloEjecucion extends Thread {

        private final ParametrosCuadratica parametros;
        private final EjecutarGrupo ejecutor;
        ResultadoGrupo resultadoGrupo;
        private final String mensaje;

        public HiloEjecucion(ParametrosCuadratica parametros, EjecutarGrupo ejecutor, String mensaje) {
            this.parametros = parametros;
            this.ejecutor = ejecutor;
            this.mensaje = mensaje;
        }

        @Override
        public void run() {
            resultadoGrupo = ejecutor.ejecutarGrupo();
        }
    }

    public static int[] vDouble_vInt(double[] vector) {
        int[] vectorInt = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            vectorInt[i] = (int) vector[i];
        }
        return vectorInt;
    }

    public static String imprimirResultados(ResultadoGrupo resultados) {
        StringBuilder sb = new StringBuilder();
        for (ResultadoAlgoritmo resultado : resultados) {

            AlgoritmoMetaheuristico algot = resultado.algoritmo;
            FuncionGen funcion = algot.getFuncion();
            String cad = formatearCabecera(
                    funcion.getNombre(),
                    algot.getNombre(),
                    formatear(funcion.getDimension()),
                    formatear(resultado.promedioIteraciones),
                    formatear((double) resultado.exitos),
                    formatear(resultado.mejorRecorrido.getMejorIndividuo().getCalidad()),
                    formatear(resultado.promedioCalidadOptimos),
                    formatear(resultado.desviacionCalidadOptimos),
                    formatear(resultado.tiempoTotal),
                    formatear(resultado.promedionumEvaluaciones));
//            System.out.println(cad);
            sb.append(cad);
        }
        return sb.toString();
    }

    public static String imprimirResumen(List<ResultadoGrupo> resultadosGrupos) {
        String resumen;
        StringBuilder sb = new StringBuilder("--------------------RESUMEN-----------------");
        ResultadoGrupo grupoAux = resultadosGrupos.get(0);
        int tamGrupoAux = grupoAux.size();
        double[] aciertos = new double[tamGrupoAux];
        double[] desviacion = new double[tamGrupoAux];
        double[] tiempos = new double[tamGrupoAux];
        for (int i = 0; i < resultadosGrupos.size(); i++) {
            ResultadoGrupo grupo = resultadosGrupos.get(i);
            for (int j = 0; j < tamGrupoAux; j++) {
                ResultadoAlgoritmo resultado = grupo.get(j);
                aciertos[j] += resultado.exitos;
                desviacion[j] += resultado.desviacionCalidadOptimos;
                tiempos[j] += resultado.tiempoTotal;
            }
        }
        StringBuilder cadena;
        sb.append("\n");
        for (int i = 0; i < tamGrupoAux; i++) {
            cadena = new StringBuilder();
            cadena.append("Algoritmo[").append(grupoAux.get(i).algoritmo.getNombre()).append("]\n");
            cadena.append("aciertos:").append(formatear(aciertos[i] * 100 / (double) resultadosGrupos.size())).append("%\n");
            cadena.append("desviacion:").append(formatear(desviacion[i] / (double) resultadosGrupos.size())).append("\n");
            cadena.append("tiempo promedio:").append(formatear(tiempos[i] / (double) resultadosGrupos.size())).append("ms\n");
            cadena.append("-----------\n");
            sb.append(cadena.toString());
        }
        resumen = sb.toString();
//        System.out.println(resumen);
        return resumen;
    }

    public static String formatearCabecera(String funcion, String algoritmo, String dimension, String promIteraciones,
            String mejorOptimo, String peorOptimo, String promedioOptimos, String desviacionOpti, String tiempoPromedio, String numEvaluaciones) {
        String cadena = String.format("%-20s|%-30s|%-10s|%-8s|%-8s|%-14s|%-12s|%-8s|%-10s|%-15s\n", funcion, algoritmo, dimension, promIteraciones,
                mejorOptimo, peorOptimo, promedioOptimos, desviacionOpti, tiempoPromedio, numEvaluaciones);
        return cadena;
    }

    public static String formatear(double valor) {
        if (valor > 100000) {
            return String.format("%-2.2f", valor);
        }
        if (valor < 10) {
            return String.format("%-1.6f", valor);
        } else {
            return String.format("%.2f", valor);
        }
    }

    public static String formatear(int valor) {
        if (valor > 100000) {
            return String.format("%-2E", 1.0 * valor);
        } else {
            return String.format("%4d" + "", valor);
        }
    }
}
