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
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Utilidades {


    static public List<Individuo> obtenerDatosRegresion(int numDatos, String nombreArchivo, boolean maximizar) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer(numDatos);
        List<Individuo> listaPuntos = new ArrayList();
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
            Individuo puntoActual = new Individuo(null, valoresPuntoActual, maximizar);
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



    public static void ejecutarAlgoritmosMasFuncionesED(List<AlgoritmoMetaheuristico> l_amgoritmos,
            List<Funcion> l_funciones, boolean graficaRecorrido, boolean graficaDispersion, int numMuestras, int iteraciones, double paso) {
//
//        Individuo p_optimo;
//        int contadorAlgoritmos = 1;
//        int contadorFunciones = 1;
//        double promedioCalidad; // promedio de la calidad de los resultados del algoritmo en las numMuestras iteraciones
//        List<Individuo> listaRecorrido;// recorrido del algoritmo para graficar
//        List<OptimoYRecorrido> listaOptimos;
////        System.out.println("Para modificar el numero de desimales mostrados en los resultados(por defecto 1), modificar el valor del atributo metaheuristicas.General.NUM_DECIMALES");
//        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "PROM. ITERACIONES", "MEJOR OPTIMO", "PEOR OPTIMO",
//                "PROM OPTIMOS", "DESVIACION OPT", "TIEMPO PROM (ms)");
//        for (Funcion funcion : l_funciones) {
//            contadorFunciones++;
//            for (AlgoritmoMetaheuristico algoritmo : l_amgoritmos) {
//                contadorAlgoritmos++;
//                //asignacion de la funcion a probar
//                listaOptimos = new ArrayList();
//                promedioCalidad = 0;
//                long tiempo_inicial = System.currentTimeMillis();
//                for (int i = 0; i < numMuestras; i++) {
//                    listaRecorrido = new ArrayList();
//                    Random rand = new Random((i * contadorAlgoritmos * contadorFunciones));
//                    algoritmo.setIteraciones(iteraciones);
//                    listaRecorrido = algoritmo.ejecutar(rand, funcion);
//                    p_optimo = listaRecorrido.get(listaRecorrido.size() - 1);
//                    listaOptimos.add(new OptimoYRecorrido(p_optimo, listaRecorrido));
//                    promedioCalidad += p_optimo.getCalidad();
//                }
//                long tiempo_final = System.currentTimeMillis();
//
//                promedioCalidad = promedioCalidad / numMuestras;
//                //ordenamiento de lista por calida de menor a mayor
//                Collections.sort(listaOptimos);
//                // seleccion del punto optimo de mayor calidad
//                OptimoYRecorrido mejorOptimo = listaOptimos.get(listaOptimos.size() - 1);
//                // graficacion 
//                if (mejorOptimo != null && graficaRecorrido) {
//                    String titulo = algoritmo.getNombre() + "-(" + funcion.getNombre() + ")";
//                    String nombreFile = General.CARPETA_TEMP + algoritmo.getNombre() + "-(" + funcion.getNombre() + ")";
//                    nombreFile = nombreFile.replace('\u0020', '-');
//                    EscribirArchivo.abrir(nombreFile + ".dat");
//                    EscribirArchivo.escribir(mejorOptimo.getRecorrido());
//                    EscribirArchivo.terminar();
//                    (new GraficoGnuPlot()).plot3D(nombreFile);
//                }
//                if (mejorOptimo != null && graficaDispersion) {
//                    String titulo = algoritmo.getNombre() + "-(" + funcion.getNombre() + ")";
//                    String nombreFile = General.CARPETA_TEMP + "plot2D-" + algoritmo.getNombre() + "-(" + funcion.getNombre() + ")";
//                    nombreFile = nombreFile.replace('\u0020', '-');
//                    EscribirArchivo.abrir(nombreFile + ".dat");
//                    List<String> listaCalida = new ArrayList();
//                    listaRecorrido = mejorOptimo.getRecorrido();
//                    for (int i = 0; i < listaRecorrido.size(); i++) {
////                        listaCalida.add(listaRecorrido.get(i).getGeneracion() + " " + listaRecorrido.get(i).getCalidad());
//                        listaCalida.add(i + " " + listaRecorrido.get(i).getCalidad());
//                    }
//                    EscribirArchivo.escribir(listaCalida);
//                    EscribirArchivo.terminar();
//                    (new GraficoGnuPlot()).plot2D(nombreFile);
//                }
//                imprimirConFormato(funcion.getNombre(),
//                        algoritmo.getNombre(),
//                        "" + funcion.getDimension(),
//                        "" + iteraciones,
//                        "" + mejorOptimo.getPunto().getCalidadString(),
//                        "" + listaOptimos.get(0).getPunto().getCalidadString(),
//                        "" + formatear(promedioCalidad),
//                        "" + formatear(calcularDesviacion(listaOptimos, promedioCalidad)),
//                        "" + (tiempo_final - tiempo_inicial) / numMuestras);
//
//                Individuo mejor = mejorOptimo.getPunto();
//                MochilaMultidimensional func = (MochilaMultidimensional) funcion;
//
//                for (int i = 0; i < func.getCapacidades().length; i++) {
//                    System.out.println("Peso[" + i + "]: " + func.obtenerPeso(mejor, i));
//                }
//                System.out.println("Precio: " + func.obtenerPrecio(mejor));
//                //implimir mejor optimo
////                System.out.println("\n\nMejor optimo: " + mejorOptimo.getPunto().getCalidad());
////                System.out.println("mejor punto: "+ mejorOptimo.getPunto().toString3());
//            }
//            System.out.println("");
//        }
    }

    
    
    public static int indiceOrdenadamente(List<Object> lista, Comparable punto, boolean ascendente) {
        if (lista.isEmpty()) {
            return 0;
        }
        int pos;
        int sup = lista.size() - 1;
        int inf = 0;
        Object puntoPos;
        int comparacion;
        while (true) {
            pos = inf + (sup - inf) / 2;
            puntoPos = lista.get(pos);
            comparacion = punto.compareTo(puntoPos) * (ascendente ? 1 : -1);
            if (comparacion >= 0) {
                inf = pos + (ascendente ? 1 : 1);
            } else if (comparacion < 0) {

                sup = pos + (ascendente ? -1 : -1);
            }
            if (sup < inf || comparacion == 0) {
                return inf;
            }
        }
    }

}
