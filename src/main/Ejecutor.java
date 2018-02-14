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
import gnuplot.Punto2D;
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
    public static int NUM_DECIMALES = 6;

    GraficoGnuPlot gnuplot;

    public Ejecutor() {
        gnuplot = new GraficoGnuPlot();
    }

    public void grafico3D(List<Punto> cd, String titulo, Funcion funcion) {
        if (cd == null) {
            throw new IllegalArgumentException(titulo);
        }
        if (!(cd.isEmpty())) {
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
        int mayorTamanio = Integer.MIN_VALUE;
        for (Recorrido item : listaRecorridos) {
            mayorTamanio = Math.max(item.getRecorridoCalidad().size(), mayorTamanio);
        }

        for (Recorrido itemRecorrido : listaRecorridos) {
            List<Punto> cd = itemRecorrido.getRecorridoCalidad();
            int amanioCD = cd.size();
            Punto2D ultimo = (Punto2D) cd.get(amanioCD - 1);
            for (int i = 0; i < (mayorTamanio - amanioCD); i++) {
                ultimo = (Punto2D) ultimo.clone();
                ultimo.setX(ultimo.getX() + 1);
                cd.add(ultimo);
            }
            gnuplot.addConjuntoDatos(cd, itemRecorrido.getNombreRecorrido() + "(" + formatear(itemRecorrido.getPromedioCalidad()) + ")");
        }

        gnuplot.plot2D(titulo);
        gnuplot.limpiar();
    }

    public List<Punto> convertirCD(List<Individuo> listaIndividuos) {
        List<Punto> cd = new ArrayList();
        if (!(listaIndividuos == null || listaIndividuos.isEmpty())) {
            for (int i = 0; i < listaIndividuos.size(); i++) {
                cd.add(new gnuplot.Punto2D((double) i, listaIndividuos.get(i).getCalidad()));
            }
        }
        return cd;
    }

    public List<Punto> convertir3D(List<Individuo> listPuntos) {
        List<Punto> cd = new ArrayList();
        listPuntos.forEach((individuo) -> {
            cd.add(new gnuplot.Punto3D(individuo.get(0), individuo.get(1), individuo.getCalidad()));
        });
        return cd;
    }

    public List<Punto> convertirCD(List<List<Individuo>> listaRecorridos, String titulo) {
        int mayorTamanio = Integer.MIN_VALUE;
        for (List item : listaRecorridos) {
            mayorTamanio = Math.max(item.size(), mayorTamanio);
        }
        double[] calidades = new double[mayorTamanio];
        double calidad = 0;
        for (List<Individuo> listaRecorrido : listaRecorridos) {
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

    public Recorrido ejecutar(AlgoritmoMetaheuristico algoritmo, Funcion funcion, int numeroPruebas, int iteraciones) {
        double promedioCalidad = 0; // promedio de la calidad de los resultados del algoritmo en las numMuestras iteraciones
        List<Individuo> recorridoIndividuos;// recorrido del algoritmo
        List<Individuo> mejorRecorrido = null;// recorrido del algoritmo
        List<Individuo> optimos = new ArrayList<>();
        List<List<Individuo>> listaRecorridosPruebas = new ArrayList();
        Individuo optimo;
        Double promedioIteraciones = 0.;
        Double tasaDeExito = 0.;

        algoritmo.setMaxIteraciones(iteraciones);
        long tiempo_inicial = System.currentTimeMillis();
        for (int i = 0; i < numeroPruebas; i++) {
            recorridoIndividuos = algoritmo.ejecutar(funcion);
            listaRecorridosPruebas.add(recorridoIndividuos);
        }
        long tiempo_final = System.currentTimeMillis();
        //obtener mejor recorridoIndividuo
        Individuo mejorOptimo = null;
        for (List<Individuo> recorrIndiItem : listaRecorridosPruebas) {
            optimo = recorrIndiItem.get(recorrIndiItem.size() - 1);
            tasaDeExito += funcion.suficiente(optimo) ? 1 : 0;
            promedioIteraciones += recorrIndiItem.size();
            if (mejorOptimo == null
                    || mejorOptimo.compareTo(optimo) < 0) {
                mejorRecorrido = recorrIndiItem;
                mejorOptimo = optimo;
            }
            optimos.add(optimo);
            promedioCalidad += optimo.getCalidad();
        }
        optimo = mejorRecorrido.get(mejorRecorrido.size() - 1);
        promedioCalidad = promedioCalidad / numeroPruebas;
        Individuo peorOptimo = mejorRecorrido.get(0);

        imprimirConFormato(
                funcion.getNombre(),
                algoritmo.getNombre(),
                "" + funcion.getDimension(),
                "" + String.format("%.2f", promedioIteraciones / numeroPruebas),
                "" + tasaDeExito,
                "" + formatear(optimo.getCalidad()),
                "" + String.format("%.2f", promedioCalidad),
                "" + String.format("%.4f", ((funcion.getOptimo() - optimo.getCalidad()) / funcion.getOptimo()) * 100),
                "" + (tiempo_final - tiempo_inicial) / numeroPruebas,
                "" + funcion.getContadorEvaluaciones() / numeroPruebas);
        //implimir mejor optimo
        System.out.println("caract Mejor: " + funcion.toString(optimo) + "\n");
//        System.out.println("Mejor: " + optimo.toStringInt()+"\n");

        return new Recorrido(convertirCD(listaRecorridosPruebas, ""), convertir3D(mejorRecorrido), promedioCalidad, algoritmo.getNombre() + "-" + funcion.getNombre());
//        return new Recorrido(convertirCD(mejorRecorrido), convertir3D(mejorRecorrido), promedioCalidad, algoritmo.getNombre() + "-" + funcion.getNombre());
    }

    public class Recorrido {

        private List<Punto> recorridoCalidad;
        private List<Punto> recorrido3D;
        private double promedioCalidad;
        private String nombreRecorrido;

        /**
         *
         * @param recorridoCalidad
         * @param recorrido3D
         * @param promedioCalidad
         * @param nombreRecorrido
         */
        public Recorrido(List<Punto> recorridoCalidad, List<Punto> recorrido3D, double promedioCalidad, String nombreRecorrido) {
            this.recorridoCalidad = recorridoCalidad;
            this.recorrido3D = recorrido3D;
            this.promedioCalidad = promedioCalidad;
            this.nombreRecorrido = nombreRecorrido;
        }

        public List<Punto> getRecorridoCalidad() {
            return recorridoCalidad;
        }

        public List<Punto> getRecorrido3D() {
            return recorrido3D;
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
        System.out.println("----------------------------------------------------------.");
        System.out.println("NPI: Numero iteraciones promedio.");
        System.out.println("TP: Tiempo Promedio.");
        System.out.println("TE: Tasa de exito.");
        System.out.println("DPR: Desviación porcentual relativa.");
        System.out.println("----------------------------------------------------------.");

        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
                "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
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
                            grafico3D(recorrido.getRecorrido3D(), titulo, funcion);
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
        System.out.format("%-20s|%-30s|%-10s|%-8s|%-8s|%-14s|%-12s|%-8s|%-10s|%-15s\n", funcion, algoritmo, dimension, promIteraciones,
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
//        if (valor > 100000) {
        return String.format("%-2." + NUM_DECIMALES + FORMATO_DOUBLE + "", valor);
//        } else {
//            return String.format("%.4f" + "", valor);
//        }
    }

}
