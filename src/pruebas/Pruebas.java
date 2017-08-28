/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import funciones.Ackley;
import funciones.Cubo;
import funciones.Esfera;
import funciones.Funcion;
import funciones.Griewank;
import funciones.Montana;
import funciones.SinXmasY;
import funciones.SinXporCosY;
import funciones.Piso;
import funciones.Rastrigin;
import funciones.Schwefel;
import funciones.XmasY;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Hill_Climbing;
import metaheuristicas.*;

/**
 *
 * @author debian
 */
public class Pruebas {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int dimension;
        int iteraciones;
        int numMuestras;
        List<AlgoritmoMetaheuristico> algoritmos = new ArrayList();

//        Scanner scan = new Scanner(new File("parametros.txt"));
        // lectura de parametros desde cadena
        Scanner scan = new Scanner("0,1\n20\n2\n1000\n10\n");
        paso = scan.nextDouble();
        limite = scan.nextDouble();
        dimension = scan.nextInt();
        iteraciones = scan.nextInt();
        numMuestras = scan.nextInt();

        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
        listaAlgoritmos.add(new Hill_Climbing(paso));
        listaAlgoritmos.add(new Hill_Climbing(paso, 10,false));
        listaAlgoritmos.add(new Hill_Climbing(paso, 10,true));
        listaAlgoritmos.add(new Hill_Climbing_direccional(paso));

        List<Funcion> listaFunciones = new ArrayList();
        listaFunciones.add(new Esfera(limite, dimension));
        listaFunciones.add(new Cubo(limite, dimension));
        listaFunciones.add(new XmasY(limite, dimension));
        listaFunciones.add(new SinXmasY(limite, dimension));
        listaFunciones.add(new SinXporCosY(limite, dimension));
        listaFunciones.add(new Montana(limite, dimension));
        listaFunciones.add(new Schwefel(limite, dimension));
        listaFunciones.add(new Rastrigin(limite, dimension));
        listaFunciones.add(new Griewank(limite, dimension));
        listaFunciones.add(new Ackley(limite, dimension));
        listaFunciones.add(new Piso(limite, dimension));

        // EJECUTAR ANALISIS
        combinarAlgoritmoFuncion(listaAlgoritmos, listaFunciones, false, numMuestras, iteraciones);

    }

    public static void combinarAlgoritmoFuncion(List<AlgoritmoMetaheuristico> l_ams,
            List<Funcion> l_fns, boolean graficar, int numMuestras, int iteraciones) {

//        long semilla = System.currentTimeMillis();
        long semilla = -2;
        Punto p_optimo;
        int contadorAlgoritmos=1;
        int contadorFunciones=1;
        double promedioCalidad; // promedio de la calidad de los resultados del algoritmo en las numMuestras iteraciones
        List<Punto> listaRecorrido;// recorrido del algoritmo para graficar
        List<PuntoRecorrido> listaOptimos;
        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "PROM. ITERACIONES", "MEJOR OPTIMO", "PEOR OPTIMO",
                "PROM OPTIMOS", "DESVIACION OPT", "TIEMPO PROM (ms)");
        for (Funcion funcion : l_fns) {
            contadorFunciones++;
            for (AlgoritmoMetaheuristico algoritmo : l_ams) {
                contadorAlgoritmos++;
                //asignacion de la funcion a probar
                algoritmo.setFuncion(funcion);
//                System.out.println("" + algoritmo.getNombre() + " (" + algoritmo.getFuncion().getNombre() + ")");
                listaOptimos = new ArrayList();
                promedioCalidad = 0;
//            listaRecorrido = null;
                long tiempo_inicial = System.currentTimeMillis();
                for (int i = 0; i < numMuestras; i++) {
                    listaRecorrido = new ArrayList();
                    p_optimo = algoritmo.ejecutar((i*contadorAlgoritmos*contadorFunciones *semilla), iteraciones, listaRecorrido);
                    listaOptimos.add(new PuntoRecorrido(p_optimo, listaRecorrido));
                    promedioCalidad += p_optimo.getCalidad();
                }
                long tiempo_final = System.currentTimeMillis();

                promedioCalidad = promedioCalidad / numMuestras;
                Collections.sort(listaOptimos);
                PuntoRecorrido mejorOptimo = listaOptimos.get(listaOptimos.size() - 1);
//                for (PuntoRecorrido listaOptimo : listaOptimos) {
//                    System.out.println(">>"+listaOptimo);
//                }
//                System.out.println("");
                // graficacion 
                if (mejorOptimo != null && graficar) {
                    String titulo = algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                    String nombreFile = General.CARPETA_TEMP + algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                    nombreFile = nombreFile.replace('\u0020', '-');
                    Archivo.abrir(nombreFile + ".dat");
                    Archivo.escribir(mejorOptimo.getRecorrido());
                    Archivo.terminar();
                    GraficoGnuPlot.plot(3, nombreFile, titulo, algoritmo.getFuncion());
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
            }
            System.out.println("");
        }
    }

    public static double calcularDesviacion(List<PuntoRecorrido> lista, double promedio) {
        double suma = 0;
        double diferencia;
        for (PuntoRecorrido puntoRecorrido : lista) {
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

//    public static void correrAlgoritmos(List<AlgoritmoMetaheuristico> algoritmos, boolean graficar) {
////        int semilla = -239;
////        int semilla = 34;
//        long semilla = System.currentTimeMillis();
//        Punto mejor;
//        double promedioCalidad;
//        List<Punto> listaRecorrido;
//        List<PuntoRecorrido> listaMejores;
//        for (AlgoritmoMetaheuristico algoritmo : algoritmos) {
//            System.out.println("" + algoritmo.getNombre() + " (" + algoritmo.getFuncion().getNombre() + ")");
//            listaMejores = new ArrayList();
//            promedioCalidad = 0;
////            listaRecorrido = null;
//            long tiempo_inicial = System.currentTimeMillis();
//            for (int i = 0; i < numMuestras; i++) {
//                listaRecorrido = new ArrayList();
//                mejor = algoritmo.ejecutar(((i) * semilla), iteraciones, listaRecorrido);
//                listaMejores.add(new PuntoRecorrido(mejor, listaRecorrido));
//                promedioCalidad += mejor.getCalidad();
//            }
//            long tiempo_final = System.currentTimeMillis();
//
//            promedioCalidad = promedioCalidad / numMuestras;
//            Collections.sort(listaMejores);
//            PuntoRecorrido puntoRecorrido = listaMejores.get(listaMejores.size() - 1);
//            // graficacion 
//            if (puntoRecorrido != null && graficar) {
//                String titulo = algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
//                String nombreFile = General.CARPETA_TEMP + algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
//                nombreFile = nombreFile.replace('\u0020', '-');
//                Archivo.abrir(nombreFile + ".dat");
//                Archivo.escribir(puntoRecorrido.getRecorrido());
//                Archivo.terminar();
//                GraficoGnuPlot.plot(3, nombreFile, titulo, algoritmo.getFuncion());
//            }
//            System.out.println("t[" + (tiempo_final - tiempo_inicial) + " ms] "
//                    + "; promedioCalidad:" + promedioCalidad + "; peor:{" + listaMejores.get(0)
//                    + "}; mejor:{" + puntoRecorrido + "}");
//        }
//
//    }
//    public void prueba_escritura_archivo() {
//
//        Random rand = new Random();
////        for (int k = 0; k < 10; k++) {
//        double num = 0;
//        for (int i = 0; i < 1000; i++) {
//            num += rand.nextDouble();
//            Archivo.escribir("" + i + " " + num + " " + (num * num + i * i));
//        }
////        }
//        Archivo.terminar();
//    }
}
