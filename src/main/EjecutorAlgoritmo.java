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
package main;

import metaheuristicas.funcion.FuncionGen;
import gnuplot.GraficoGnuPlot;
import gnuplot.Punto;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.FabricaAlgoritmoMetaheuristico;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class EjecutorAlgoritmo {

    public static String FORMATO_DOUBLE = "f";
    public static int NUM_DECIMALES = 6;
    private FabricaAlgoritmoMetaheuristico fabricaAlgoritmo;
    private int numeroPruebas;
    private int maxIteraciones;

    GraficoGnuPlot gnuplot;

    public EjecutorAlgoritmo() {
        gnuplot = new GraficoGnuPlot();
    }

    public FabricaAlgoritmoMetaheuristico getAlgoritmo() {
        return fabricaAlgoritmo;
    }

    public void setAlgoritmo(FabricaAlgoritmoMetaheuristico algoritmo) {
        this.fabricaAlgoritmo = algoritmo;
    }

    public int getNumeroPruebas() {
        return numeroPruebas;
    }

    public void setNumeroPruebas(int numeroPruebas) {
        this.numeroPruebas = numeroPruebas;
    }

    public int getMaxIteraciones() {
        return maxIteraciones;
    }

    public void setMaxIteraciones(int maxIteraciones) {
        this.maxIteraciones = maxIteraciones;
    }

    public List<Punto> convertirCD(List<IndividuoGen> listaIndividuos) {
        List<Punto> cd = new ArrayList();
        if (!(listaIndividuos == null || listaIndividuos.isEmpty())) {
            for (int i = 0; i < listaIndividuos.size(); i++) {
                cd.add(new gnuplot.Punto2D((double) i, listaIndividuos.get(i).getCalidad()));
            }
        }
        return cd;
    }

    public List<Punto> convertir3D(List<IndividuoGen> listPuntos) {
        List<Punto> cd = new ArrayList();
        listPuntos.forEach((individuo) -> {
            cd.add(new gnuplot.Punto3D(individuo.get(0), individuo.get(1), individuo.getCalidad()));
        });
        return cd;
    }

    public List<Punto> convertirCD(List<List<IndividuoGen>> listaRecorridos, String titulo) {
        int mayorTamanio = Integer.MIN_VALUE;
        for (List item : listaRecorridos) {
            mayorTamanio = Math.max(item.size(), mayorTamanio);
        }
        double[] calidades = new double[mayorTamanio];
        double calidad = 0;
        for (List<IndividuoGen> listaRecorrido : listaRecorridos) {
            for (int i = 0; i < mayorTamanio; i++) {
                if (i < listaRecorrido.size()) {
                    calidad = listaRecorrido.get(i).getCalidad();
                }
                calidades[i] += calidad;
            }
        }
        List<Punto> cd = new ArrayList();
        for (int i = 0; i < calidades.length; i++) {
            cd.add(new gnuplot.Punto2D((double) i, calidades[i] / (listaRecorridos.size())));
        }
        return cd;
    }

    public final ResultadoAlgoritmo ejecutar() {
        double promedioCalidad = 0; // promedio de la calidad de los resultados del fabricaAlgoritmo en las numMuestras iteraciones
        List<IndividuoGen> mejorRecorrido = null;// recorrido del fabricaAlgoritmo
//        List<IndividuoGen> optimos = new ArrayList<>();
        List<List<IndividuoGen>> listaRecorridosPruebas = new ArrayList();;
        IndividuoGen optimo;
        Double promedioIteraciones = 0.;
        float tasaDeExito = 0;
        List<IndividuoGen> recorridoIndividuos; // recorrido del fabricaAlgoritmo
        AlgoritmoMetaheuristico unAlgoritmo = fabricaAlgoritmo.obtener();
        FuncionGen funcion = unAlgoritmo.getFuncion();
        long tiempo_inicial = System.currentTimeMillis();
        for (int i = 0; i < numeroPruebas; i++) {
            recorridoIndividuos = unAlgoritmo.ejecutar();
            if (recorridoIndividuos != null) {
                listaRecorridosPruebas.add(recorridoIndividuos);
            }
        }
        long tiempo_final = System.currentTimeMillis();
        long tiempo = tiempo_final - tiempo_inicial;

        IndividuoGen mejorOptimo = null;
        IndividuoGen peorOptimo = null;
        if (!listaRecorridosPruebas.isEmpty()) {
            for (List<IndividuoGen> recorrIndiItem : listaRecorridosPruebas) {
                optimo = recorrIndiItem.get(recorrIndiItem.size() - 1);
                tasaDeExito += funcion.suficiente(optimo) ? 1 : 0;
                promedioIteraciones += recorrIndiItem.size();
                if (mejorOptimo == null
                        || mejorOptimo.compareTo(optimo) < 0) {
                    mejorRecorrido = recorrIndiItem;
                    mejorOptimo = optimo;
                }
                if (peorOptimo == null
                        || peorOptimo.compareTo(optimo) > 0) {
                    peorOptimo = optimo;
                }
                promedioCalidad += optimo.getCalidad();
            }

            optimo = mejorRecorrido.get(mejorRecorrido.size() - 1);
            promedioCalidad = promedioCalidad / numeroPruebas;
            tasaDeExito /= numeroPruebas;
            promedioIteraciones /= numeroPruebas;
            tiempo /= numeroPruebas;

            //implimir mejor optimo
            return new ResultadoAlgoritmo(
                    unAlgoritmo,
                    promedioIteraciones,
                    numeroPruebas,
                    tasaDeExito,
                    promedioCalidad,
                    ((funcion.getOptimoGlobal() - promedioCalidad) / funcion.getOptimoGlobal()) * 100,
                    tiempo,
                    funcion.getContadorEvaluaciones(),
                    new Recorrido(convertirCD(listaRecorridosPruebas, ""), convertir3D(mejorRecorrido), promedioCalidad, unAlgoritmo.getNombre() + "-" + funcion.getNombre(), optimo));
        }
        return null;
    }

    public Recorrido ejecutarAlgoritmosMasFunciones(List<AlgoritmoMetaheuristico> l_amgoritmos,
            List<FuncionGen> l_funciones, boolean graficaRecorrido, boolean graficaConvergencia,
            int numeroPruebas, int iteraciones) {
//
//        Recorrido mejorRecorrido = null;
////        System.out.println("Para modificar el numero de desimales mostrados en los resultados(por defecto 1), modificar el valor del atributo metaheuristicas.General.NUM_DECIMALES");
//
////        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
////                "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
//        List<Recorrido> listaRecorridos; // para grafica de convergencia
//        String titulo;
//        for (FuncionGen funcion : l_funciones) {
//            listaRecorridos = new ArrayList();
//            for (;;) {
//                /**
//                 * ciclo para los diferentes planteamientos de una misma funcion
//                 */
//                titulo = "(" + funcion.getNombre() + ")";
//                for (AlgoritmoMetaheuristico fabricaAlgoritmo : l_amgoritmos) {
//
//                    for (;;) {
//                        /**
//                         * ciclo para los diferentes modificaciones de un mismo
//                         * fabricaAlgoritmo
//                         */
//                        ResultadoAlgoritmo resultado = ejecutar(fabricaAlgoritmo, numeroPruebas, iteraciones);
//                        Recorrido recorrido = resultado.mejorRecorrido;
//                        listaRecorridos.add(recorrido);
//                        if (graficaRecorrido) {
//                            grafico3D(recorrido.getRecorrido3D(), titulo, funcion);
//                        }
//                    }
//                }
//                if (funcion.haySiguiente()) {
//                    funcion.siguiente();
//                } else {
//                    funcion.reiniciar();
//                    break;
//                }
//            }
//            if (graficaConvergencia) {
//                grafico2D(listaRecorridos, titulo);
//            }
//            System.out.println("");
//        }
//        return mejorRecorrido;
        return null;
    }

//    Grupo grupo;
//    boolean graficaRecorrido;
//    boolean graficaConvergencia;
//    int numeroPruebas;
//    String instancia;
}
