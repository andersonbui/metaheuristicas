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

import metaheuristicas.Funcion;
import gnuplot.GraficoGnuPlot;
import gnuplot.Punto;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Ejecutor {

    public static String FORMATO_DOUBLE = "E";
    public static int NUM_DECIMALES = 4;

    GraficoGnuPlot gnuplot;

    public Ejecutor() {
        gnuplot = new GraficoGnuPlot();
    }

    public void grafico3D(List<Individuo> recorrido, String titulo) {
        if (recorrido == null) {
            throw new IllegalArgumentException(titulo);
        }
        if (!(recorrido == null || recorrido.isEmpty())) {
            ArrayList<gnuplot.Punto> cd = new ArrayList();
            recorrido.forEach((individuo) -> {
                cd.add(new gnuplot.Punto3D(individuo.get(0), individuo.get(1), individuo.getCalidad()));
            });
            Funcion funcion = recorrido.get(0).getFuncion();
//                gnuplot.addFuncionAritmetica(funcion.toString());
            gnuplot.addFuncionAritmetica(funcion.toString());
            gnuplot.setXrange(funcion.getLimite());
            gnuplot.setYrange(funcion.getLimite());
            gnuplot.addConjuntoDatos(cd, funcion.getNombre());
            gnuplot.plot3D(titulo);

            gnuplot.limpiar();
        }
    }

    /**
     * crea Grafica de convergencia de la calidad de todos los puntos en cada
     * recorrido de listaRecorridos, cada uno con color y descripcion
     * correspondiente para poder ser identificados independientemente.
     *
     * @param listaRecorridos lista de recorridos graficados
     * @param titulo nombre de la grafica
     */
    public void grafico2D(List<Recorrido> listaRecorridos, String titulo) {
        int menorTamanio = Integer.MAX_VALUE;
        for (Recorrido item : listaRecorridos) {
            menorTamanio = Math.min(item.getRecorrido().size(), menorTamanio);
        }
        for (Recorrido itemRecorrido : listaRecorridos) {
            List<Individuo> listaIndividuos = itemRecorrido.getRecorrido();
            if (!(listaIndividuos == null || listaIndividuos.isEmpty())) {
                List<Punto> cd = new ArrayList();
//                for (int i = 0; i < listaIndividuos.size(); i++) {
                for (int i = 0; i < menorTamanio; i++) {
                    cd.add(new gnuplot.Punto2D((double) i, listaIndividuos.get(i).getCalidad()));
                }
                gnuplot.addConjuntoDatos(cd, itemRecorrido.getNombreRecorrido() + "(" + formatear(itemRecorrido.getPromedioCalidad()) + ")");
            }
        }

        gnuplot.plot2D(titulo);
        gnuplot.limpiar();
    }

    public Recorrido ejecutar(AlgoritmoMetaheuristico algoritmo, Funcion funcion, int numeroPruebas, int iteraciones) {
        double promedioCalidad = 0; // promedio de la calidad de los resultados del algoritmo en las numMuestras iteraciones
        List<Individuo> recorrido;// recorrido del algoritmo
        List<Individuo> mejorRecorrido = null;// recorrido del algoritmo
        List<Individuo> optimos = new ArrayList<>();
        Individuo optimo;

        algoritmo.setMaxIteraciones(iteraciones);
        long tiempo_inicial = System.currentTimeMillis();
        for (int i = 0; i < numeroPruebas; i++) {
            recorrido = algoritmo.ejecutar(funcion);
            optimo = recorrido.get(recorrido.size() - 1);
            if (mejorRecorrido == null
                    || mejorRecorrido.get(mejorRecorrido.size() - 1).compareTo(optimo) < 0) {
                mejorRecorrido = recorrido;
            }
            optimos.add(optimo);
            promedioCalidad += optimo.getCalidad();
        }
        long tiempo_final = System.currentTimeMillis();
        optimo = mejorRecorrido.get(mejorRecorrido.size() - 1);
        promedioCalidad = promedioCalidad / numeroPruebas;
        Individuo peor = mejorRecorrido.get(0);

        imprimirConFormato(
                funcion.getNombre(),
                algoritmo.getNombre(),
                "" + funcion.getDimension(),
                "" + mejorRecorrido.size(),
                "" + formatear(optimo.getCalidad()),
                "" + formatear(peor.getCalidad()),
                "" + formatear(promedioCalidad),
                "" + formatear(calcularDesviacion(optimos, promedioCalidad)),
                "" + (tiempo_final - tiempo_inicial) / numeroPruebas,
                "" + funcion.getContadorEvaluaciones() / numeroPruebas);
        //implimir mejor optimo
        System.out.println("caract Mejor: " + funcion.toString(optimo) + "\n");
//        System.out.println("Mejor: " + optimo.toStringInt()+"\n");

        return new Recorrido(mejorRecorrido, promedioCalidad, algoritmo.getNombre() + "-" + funcion.getNombre());
    }

    public class Recorrido {

        private List<Individuo> recorrido;
        private double promedioCalidad;
        private String nombreRecorrido;

        public Recorrido(List<Individuo> recorrido, double promedioCalidad, String nombreRecorrido) {
            this.recorrido = recorrido;
            this.promedioCalidad = promedioCalidad;
            this.nombreRecorrido = nombreRecorrido;
        }

        public List<Individuo> getRecorrido() {
            return recorrido;
        }

        public void setRecorrido(List<Individuo> recorrido) {
            this.recorrido = recorrido;
        }

        public double getPromedioCalidad() {
            return promedioCalidad;
        }

        public void setPromedioCalidad(double promedioCalidad) {
            this.promedioCalidad = promedioCalidad;
        }

        public String getNombreRecorrido() {
            return nombreRecorrido;
        }

        public void setNombreRecorrido(String nombreAlgoritmo) {
            this.nombreRecorrido = nombreAlgoritmo;
        }

    }

    public void ejecutarAlgoritmosMasFunciones(List<AlgoritmoMetaheuristico> l_amgoritmos,
            List<Funcion> l_funciones, boolean graficaRecorrido, boolean graficaConvergencia,
            int numeroPruebas, int iteraciones) {

//        System.out.println("Para modificar el numero de desimales mostrados en los resultados(por defecto 1), modificar el valor del atributo metaheuristicas.General.NUM_DECIMALES");
        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "PROM. ITERACIONES", "MEJOR OPTIMO", "PEOR OPTIMO",
                "PROM OPTIMOS", "DESVIACION OPT", "TIEMPO PROM (ms)", "EVALUACIONES");
        List<Recorrido> listaRecorridos; // para grafica de convergencia
        String titulo;
        for (Funcion funcion : l_funciones) {
            listaRecorridos = new ArrayList();
            for (;;) {
                /**
                 * ciclo para los diferentes planteamientos de una misma funcion
                 */

                titulo = "(" + funcion.getNombre() + ")";
                for (AlgoritmoMetaheuristico algoritmo : l_amgoritmos) {

                    for (;;) {
                        /**
                         * ciclo para los diferentes modificaciones de un mismo
                         * algoritmo
                         */
                        Recorrido recorrido = ejecutar(algoritmo, funcion, numeroPruebas, iteraciones);
                        listaRecorridos.add(recorrido);
                        if (graficaRecorrido) {
                            grafico3D(recorrido.getRecorrido(), titulo);
                        }
                        if (algoritmo.haySiguiente()) {
                            algoritmo.siguiente();
                        } else {
                            funcion.reiniciarContadorEvaluaciones();
                            algoritmo.reiniciar();
                            break;
                        }
                    }
                }
                if (funcion.haySiguiente()) {
                    funcion.siguiente();
                } else {
                    funcion.reiniciar();
                    break;
                }
            }
            if (graficaConvergencia) {
                grafico2D(listaRecorridos, titulo);
            }
            System.out.println("");
        }
    }

    public static void imprimirConFormato(String funcion, String algoritmo, String dimension, String promIteraciones,
            String mejorOptimo, String peorOptimo, String promedioOptimos, String desviacionOpti, String tiempoPromedio, String numEvaluaciones) {
        System.out.format("%-20s|%-30s|%-10s|%-18s|%-13s|%-12s|%-12s|%-14s|%-16s|%-15s\n", funcion, algoritmo, dimension, promIteraciones,
                mejorOptimo, peorOptimo, promedioOptimos, desviacionOpti, tiempoPromedio, numEvaluaciones);
    }

    public static double calcularDesviacion(List<Individuo> lista, double promedio) {
        double suma = 0;
        double diferencia;
        for (Individuo item : lista) {
            diferencia = item.getCalidad() - promedio;
            suma += diferencia * diferencia;
        }
        return Math.sqrt(suma / (lista.size() - 1));
    }

    public static String formatear(double valor) {
        return String.format("%." + NUM_DECIMALES + FORMATO_DOUBLE + "", valor);
    }

}
