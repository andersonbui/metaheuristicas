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
import java.util.List;
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

    public static void main(String[] args) throws FileNotFoundException {
        double paso;
        double limite;
        int dimension;
        List<AlgoritmoMetaheuristico> algoritmos = new ArrayList();

//        Scanner scan = new Scanner(new File("parametros.txt"));
        Scanner scan = new Scanner("0,1\n5\n2\n80\n");
        paso = scan.nextDouble();
        limite = scan.nextDouble();
        dimension = scan.nextInt();
        iteraciones = scan.nextInt();

        algoritmos.add(new Hill_Climbing(new Esfera(limite, dimension), paso));
        algoritmos.add(new Hill_Climbing(new Cubo(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new XmasY(limite, dimension), paso));
//        algoritmos.add(new Hill_Climbing(new SinXmasY(limite, dimension), paso));
        algoritmos.add(new Hill_Climbing(new SinXporCosY(limite, dimension), paso));
        algoritmos.add(new Hill_Climbing(new Montana(limite, dimension), paso));

        correrAlgoritmos(algoritmos, true);

    }

    public static void correrAlgoritmos(List<AlgoritmoMetaheuristico> algoritmos, boolean graficar) {
        int semilla = -239;
//        int semilla = 2;
        Punto mejor;
        List<Punto> listaRecorrido;
        for (AlgoritmoMetaheuristico algoritmo : algoritmos) {
            listaRecorrido = new ArrayList();
//            listaRecorrido = null;
            long tiempo_inicial = System.currentTimeMillis();
            mejor = algoritmo.ejecutar(semilla++, iteraciones, listaRecorrido);
            long tiempo_final = System.currentTimeMillis();

            // graficacion 
            if (listaRecorrido != null && graficar) {
                String nombreFile = General.CARPETA_TEMP + algoritmo.getNombre() + "-(" + algoritmo.getFuncion().getNombre() + ")";
                nombreFile = nombreFile.replace('\u0020', '-');
                Archivo.abrir(nombreFile + ".dat");
                Archivo.escribir(listaRecorrido);
                Archivo.terminar();
                GraficoGnuPlot.plot(3, nombreFile, algoritmo.getFuncion());
            }
            System.out.println("t[" + (tiempo_final - tiempo_inicial) + " ms]: mejor: " + mejor);
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
