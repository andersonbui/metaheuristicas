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
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.IndividuoGen;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {

    public static void main(String[] args) throws FileNotFoundException, Exception {

        int maxIteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        // numero de individuos porpoblacion
        // iteraciones realizadas por los algoritmos
        maxIteraciones = 100;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 100;
        List<ResultadoGrupo> listResultadosGrupos = new ArrayList();
        String nombreArchivo;
        List<GrupoInstancias> instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_100_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 5, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/","5000_25_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/","1000_25_%d.dat", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_100_75_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_100_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_75_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_25_%d.txt", 1, 20));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_50_%d.txt", 1, 20));
//          instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_100_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/","r_10_100_%d.txt", 13, 13));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_25_%d.txt", 1, 20));//1-20
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_50_%d.txt", 1, 2));//1-20

        System.out.println("----------------------------------------------------------.");
        System.out.println("NPI: Numero iteraciones promedio.");
        System.out.println("TP: Tiempo Promedio.");
        System.out.println("TE: Tasa de exito.");
        System.out.println("DPR: Desviación porcentual relativa.");
        System.out.println("----------------------------------------------------------.");

        List<HiloEjecucion> hilos = new ArrayList<>();
        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
                "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
        String mensaje = "";
        for (GrupoInstancias instancia : instancias) {
            
            mensaje += "####";
            for (int indice_instancia = instancia.inicio; indice_instancia <= instancia.cantidad; indice_instancia++) {
                nombreArchivo = instancia.getNombreArchivoCompleto(indice_instancia);

                mensaje += "----Nombre archivo: " + String.format(instancia.base, indice_instancia) + "----\n";
                // dimension de los puntos;
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
                ParametrosCuadratica parametros = lpc.obtenerParametros(nombreArchivo);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }
                GrupoAlgoritmosMochilaCuadratica grupo = new GrupoAlgoritmosMochilaCuadratica(parametros, maxIteraciones);
                grupo.inicializar();
                EjecutarGrupo ejecutor = new EjecutarGrupo();
                // EJECUTAR ANALISIS
                ejecutor.setParametros(grupo, graficaRecorrido3D, graficaDispercion2D, numMuestras, instancia.getNombreArchivo(indice_instancia));
                // Multi-hilo
                hilos.add(new HiloEjecucion(parametros, ejecutor, mensaje));
                
                mensaje = "";
            }
        }
        for (HiloEjecucion hilo : hilos) {
            hilo.start();
        }
        for (HiloEjecucion hilo : hilos) {
            hilo.join();
            System.out.print(hilo.mensaje);
            
            ResultadoGrupo resultadoGrupo = hilo.resultadoGrupo;
            ParametrosCuadratica parametros = hilo.parametros;
            
            listResultadosGrupos.add(resultadoGrupo);
            LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
            imprimirResultados(resultadoGrupo);
            IndividuoGen individuo = resultadoGrupo.getMejorIndividuo();
            // comprobar calidad de la actua instancia y actualizar los archivos de instancias
            if (parametros.getMaxGlobal() == null || parametros.getMaxGlobal().compareTo(individuo.getCalidad()) < 0) {
                parametros.setMaxGlobal(individuo.getCalidad());
                parametros.setVectorIdeal(vDouble_vInt(individuo.getValores()));
                lpc.actualizar(parametros.getNombreArchivo(), parametros);
            }
        }

        imprimirResumen(listResultadosGrupos);
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

    public static void imprimirResultados(ResultadoGrupo resultados) {

        for (ResultadoAlgoritmo resultado : resultados) {

            AlgoritmoMetaheuristico algot = resultado.algoritmo;
            FuncionGen funcion = algot.getFuncion();
            imprimirConFormato(
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
        }
    }

    public static void imprimirResumen(List<ResultadoGrupo> resultadosGrupos) {
        System.out.println("--------------------RESUMEN-----------------");
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
        for (int i = 0; i < tamGrupoAux; i++) {
            System.out.println("Algoritmo[" + grupoAux.get(i).algoritmo.getNombre() + "]");
            System.out.println("aciertos:" + formatear(aciertos[i] * 100 / (double) resultadosGrupos.size()) + "%");
            System.out.println("desviacion:" + formatear(desviacion[i] / (double) resultadosGrupos.size()) + "");
            System.out.println("tiempo promedio:" + formatear(tiempos[i] / (double) resultadosGrupos.size()) + "ms");
            System.out.println("-------");
        }
    }

    public static void imprimirConFormato(String funcion, String algoritmo, String dimension, String promIteraciones,
            String mejorOptimo, String peorOptimo, String promedioOptimos, String desviacionOpti, String tiempoPromedio, String numEvaluaciones) {
        System.out.format("%-20s|%-30s|%-10s|%-8s|%-8s|%-14s|%-12s|%-8s|%-10s|%-15s\n", funcion, algoritmo, dimension, promIteraciones,
                mejorOptimo, peorOptimo, promedioOptimos, desviacionOpti, tiempoPromedio, numEvaluaciones);
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
