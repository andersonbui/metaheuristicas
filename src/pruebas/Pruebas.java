/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import funciones.Cubo;
import funciones.Esfera;
import funciones.Montana;
import funciones.SinXmasY;
import funciones.SinXporCosY;
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

    static int iteraciones;
    static int numMuestras;

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int dimension;
        List<AlgoritmoMetaheuristico> algoritmos = new ArrayList();

//        Scanner scan = new Scanner(new File("parametros.txt"));
        // lectura de parametros desde cadena
        Scanner scan = new Scanner("1\n10\n2\n100\n30\n");
        paso = scan.nextDouble();
        limite = scan.nextDouble();
        dimension = scan.nextInt();
        iteraciones = scan.nextInt();
        numMuestras = scan.nextInt();

//        algoritmos.add(new Hill_Climbing(new Esfera(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new Cubo(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new XmasY(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new SinXmasY(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new SinXporCosY(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new Montana(limite, dimension), paso));
        algoritmos.add(new Hill_Climbing(new Esfera(limite, dimension), paso, 10));
        algoritmos.add(new Hill_Climbing_anterior(new Esfera(limite, dimension), paso));
        correrAlgoritmos(algoritmos, true);
//        correrAlgoritmos(algoritmos, false);
//        List<Punto> listaRest = (new Hill_Climbing_anterior(new Esfera(limite, dimension),paso)).listaPosiblesSucesoresOrtogonales(new Punto(new double[]{1,9}),new double[]{0.1,0.3});
//        listaRest.forEach((object) -> {
//            System.out.println(" "+object);
//        });

//        Random rand = new Random(4);
//        for (double num : rand.doubles(20).toArray()) {
//            System.out.println("rand: "+num);
//        }
//        for (int i = 0; i < 10; i++) {
//            System.out.println("rand: " + rand.nextDouble());
//        }
    }

    public static void correrAlgoritmos(List<AlgoritmoMetaheuristico> algoritmos, boolean graficar) {
//        int semilla = -239;
        int semilla = -34;
//        long semilla = System.currentTimeMillis();
        Punto mejor;
        double promedioCalidad;
        List<Punto> listaRecorrido = null;
        List<PuntoRecorrido> listaMejores;
        for (AlgoritmoMetaheuristico algoritmo : algoritmos) {
            System.out.println("" + algoritmo.getNombre());
            listaMejores = new ArrayList();
            promedioCalidad = 0;
//            listaRecorrido = null;
            long tiempo_inicial = System.currentTimeMillis();
            for (int i = 0; i < numMuestras; i++) {
                listaRecorrido = new ArrayList();
                mejor = algoritmo.ejecutar(((i + 1) * semilla++), iteraciones, listaRecorrido);
                listaMejores.add(new PuntoRecorrido(mejor, listaRecorrido));
                promedioCalidad += mejor.getCalidad();
            }
            long tiempo_final = System.currentTimeMillis();

            promedioCalidad = promedioCalidad / numMuestras;
            Collections.sort(listaMejores);
            PuntoRecorrido puntoRecorrido = listaMejores.get(listaMejores.size() - 1);
            // graficacion 
            if (puntoRecorrido.getRecorrido() != null && graficar) {
                String titulo = algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                String nombreFile = General.CARPETA_TEMP + algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                nombreFile = nombreFile.replace('\u0020', '-');
                Archivo.abrir(nombreFile + ".dat");
                Archivo.escribir(puntoRecorrido.getRecorrido());
                Archivo.terminar();
                GraficoGnuPlot.plot(3, nombreFile, titulo,algoritmo.getFuncion());
            }
            System.out.println("t[" + (tiempo_final - tiempo_inicial) + " ms] "
                    + "; promedioCalidad:" + promedioCalidad + "; peor:{" + listaMejores.get(0)
                    + "}; mejor:{" + puntoRecorrido + "}");
        }

    }

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
