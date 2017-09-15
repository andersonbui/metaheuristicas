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
package pruebas;

import funciones.Funcion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.General;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Utilidades {

    public static double calcularDesviacion(List<OptimoYRecorrido> lista, double promedio) {
        double suma = 0;
        double diferencia;
        for (OptimoYRecorrido puntoRecorrido : lista) {
            diferencia = puntoRecorrido.getPunto().getCalidad() - promedio;
            suma += diferencia * diferencia;
        }
        return Math.sqrt(suma / lista.size());
    }

    public static void imprimirConFormato(String funcion, String algoritmo, String dimension, String promIteraciones,
            String mejorOptimo, String peorOptimo, String promedioOptimos, String desviacionOpti, String tiempoPromedio) {
        System.out.format("%-13s%-30s%-12s%-20s%-20s%-20s%-20s%-20s%-20s\n", funcion, algoritmo, dimension, promIteraciones,
                mejorOptimo, peorOptimo, promedioOptimos, desviacionOpti, tiempoPromedio);
    }

    static public List<Punto> obtenerDatosRegresion(int numDatos, String nombreArchivo) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer(numDatos);
        List<Punto> listaPuntos = new ArrayList();
        int j;
        LeerArchivo.terminar();
        for (int i = 1; i < listaCadenas.size(); i++) {
            int posicionSubdivisiones = 0;
            String cadena = listaCadenas.get(i).replace(',', '.');

            cadena = eliminarEspaciosRepetidos(cadena);

            String[] vectSubdivisiones = cadena.split("" + '\u0020');
            double[] valoresPuntoActual = new double[vectSubdivisiones.length];
            valoresPuntoActual[0] = 1;
            for (j = 1; j < valoresPuntoActual.length; j++) {
//                System.out.print("[" + valoresPuntoActual.length + "]<" + vectSubdivisiones[posicionSubdivisiones] + ">");
                valoresPuntoActual[j] = Double.parseDouble(vectSubdivisiones[posicionSubdivisiones++].trim());
            }
            Punto puntoActual = new Punto(valoresPuntoActual);
            puntoActual.setCalidad(Double.parseDouble(vectSubdivisiones[posicionSubdivisiones++]));
            listaPuntos.add(puntoActual);
//            System.out.println("<" + puntoActual.toString() + ">");
        }
        return listaPuntos;
    }

    static public String eliminarEspaciosRepetidos(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        texto = "";
        while (tokens.hasMoreTokens()) {
            texto += " " + tokens.nextToken();
        }
        texto = texto.toString();
        texto = texto.trim();
        return texto;
    }

    public static void ejecutarAlgoritmosMasFunciones(List<AlgoritmoMetaheuristico> l_amgoritmos,
            List<Funcion> l_funciones, boolean graficaRecorrido, boolean graficaDispersion, int numMuestras, int iteraciones) {

        Punto p_optimo;
        int contadorAlgoritmos = 1;
        int contadorFunciones = 1;
        double promedioCalidad; // promedio de la calidad de los resultados del algoritmo en las numMuestras iteraciones
        List<Punto> listaRecorrido;// recorrido del algoritmo para graficar
        List<OptimoYRecorrido> listaOptimos;
        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "PROM. ITERACIONES", "MEJOR OPTIMO", "PEOR OPTIMO",
                "PROM OPTIMOS", "DESVIACION OPT", "TIEMPO PROM (ms)");
        for (Funcion funcion : l_funciones) {
            contadorFunciones++;
            for (AlgoritmoMetaheuristico algoritmo : l_amgoritmos) {
                contadorAlgoritmos++;
                //asignacion de la funcion a probar
                algoritmo.setFuncion(funcion);
                listaOptimos = new ArrayList();
                promedioCalidad = 0;
                long tiempo_inicial = System.currentTimeMillis();
                for (int i = 0; i < numMuestras; i++) {
                    listaRecorrido = new ArrayList();
                    Random rand = new Random((i * contadorAlgoritmos * contadorFunciones));
                    algoritmo.setRand(rand);

                    Punto inicial = algoritmo.generarPunto();
                    p_optimo = algoritmo.ejecutar(inicial, iteraciones, listaRecorrido);
                    listaOptimos.add(new OptimoYRecorrido(p_optimo, listaRecorrido));
                    promedioCalidad += p_optimo.getCalidad();
                }
                long tiempo_final = System.currentTimeMillis();

                promedioCalidad = promedioCalidad / numMuestras;
                //ordenamiento de lista por calida de menor a mayor
                Collections.sort(listaOptimos);
                // seleccion del punto optimo de mayor calidad
                OptimoYRecorrido mejorOptimo = listaOptimos.get(listaOptimos.size() - 1);
                // graficacion 
                if (mejorOptimo != null && graficaRecorrido) {
                    String titulo = algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                    String nombreFile = General.CARPETA_TEMP + algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                    nombreFile = nombreFile.replace('\u0020', '-');
                    EscribirArchivo.abrir(nombreFile + ".dat");
                    EscribirArchivo.escribir(mejorOptimo.getRecorrido());
                    EscribirArchivo.terminar();
                    GraficoGnuPlot.plot3D(nombreFile, titulo, algoritmo.getFuncion());
                }
                if (mejorOptimo != null && graficaDispersion) {
                    String titulo = algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                    String nombreFile = General.CARPETA_TEMP + "plot2D-" + algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                    nombreFile = nombreFile.replace('\u0020', '-');
                    EscribirArchivo.abrir(nombreFile + ".dat");
                    List<String> listaCalida = new ArrayList();
                    listaRecorrido = mejorOptimo.getRecorrido();
                    for (int i = 0; i < listaRecorrido.size(); i++) {
                        listaCalida.add(i + " " + listaRecorrido.get(i).getCalidad());
                    }
                    EscribirArchivo.escribir(listaCalida);
                    EscribirArchivo.terminar();
                    GraficoGnuPlot.plot2D(nombreFile, titulo, algoritmo.getFuncion());
                }
                imprimirConFormato(
                        algoritmo.getFuncion().getNombre(),
                        algoritmo.getNombre(),
                        "" + algoritmo.getFuncion().getDimension(),
                        "" + iteraciones,
                        "" + mejorOptimo.getPunto().getCalidadString(),
                        "" + listaOptimos.get(0).getPunto().getCalidadString(),
                        "" + Punto.formatear(promedioCalidad),
                        "" + Punto.formatear(calcularDesviacion(listaOptimos, promedioCalidad)),
                        "" + (tiempo_final - tiempo_inicial) / numMuestras);
                System.out.println("mejor punto: "+ mejorOptimo.getPunto().toString3());
            }
            System.out.println("");
        }
    }
}
