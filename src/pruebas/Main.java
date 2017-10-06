/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.simple.Random_Search;
import funciones.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.simple.Hill_Climbing;
import static pruebas.Utilidades.ejecutarAlgoritmosMasFunciones;
import tweaks.Tweak_1;
import tweaks.Tweak_Direccional;
import tweaks.Tweak_B_HC;
import tweaks.Tweak_GeneraNuevo;

/**
 *
 * @author debian
 * entregables 2 corte: 
 * 1- solucion del problemas de la mochila geneticos y PSO.
 * 2- escoger heuristica: saltos de rana revueltas, competencia imperialista, 
 *  optimizacion basado en corales, enjambre de peces
 *  explicar la metaheuristicas y por que se escogio la heuristica.
 * 
 * (no los que hemos visto en clase)
 * entrar a la biblioteca virtual sience direct o escopus o , buscar articulo de metaheuristicas
 * ejemplo: enrutamiento de vehiculos: grass, leerlo entenderlo, implementarlo y
 * optener una version que se parezca al articulo y comparacion con su implementacion con la heuristicas escogida
 * finalmente se hace una presentacion en video (subir a youtube) y compertir con el equipo.
 * fecha entrega para: 27/oct/17 
 *      
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int dimension;
        int iteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = true; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = true; // true para graficas de dispersion con gnuplot

//        Scanner scan = new Scanner(new File("parametros.txt"));
        // lectura de parametros desde cadena
//        Scanner scan = new Scanner("1\n100\n2\n5000\n5\n");
        // rango maximo de cambio en el tweak
        paso = 2;
        // limite de las funciones
        limite = 100;
        // dimension de los puntos
        dimension = 2;
        // iteraciones realizadas por los algoritmos
        iteraciones = 5000;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 5;

        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
        listaAlgoritmos.add(new Hill_Climbing(new Tweak_1()));
//        listaAlgoritmos.add(new Hill_Climbing_Random_Restarts(new Tweak_1(), 50000));
//        listaAlgoritmos.add(new Random_Search(new Tweak_GeneraNuevo()));
        listaAlgoritmos.add(new AlgoritmoEvolutivo(new Tweak_1()));
//        listaAlgoritmos.add(new Hill_Climbing(new Tweak_1(), 10,false)); // hill climbing maxima pendiente
//        listaAlgoritmos.add(new Hill_Climbing(paso, 10,true)); // hill climbing con reinicio aleatorio
//        listaAlgoritmos.add(new Hill_Climbing_direccional(new Tweak_Direccional()));
//        listaAlgoritmos.add(new Simulated_Annealing(new Tweak_1()));
//        listaAlgoritmos.add(new B_Hill_Climbing(new Tweak_B_HC()));

        List<Funcion> listaFunciones = new ArrayList();
        listaFunciones.add(new Esfera(limite, dimension));
//        listaFunciones.add(new Cubo(limite, dimension));
//        listaFunciones.add(new XmasY(limite, dimension));
//        listaFunciones.add(new SinXmasY(limite, dimension));
//        listaFunciones.add(new SinXporCosY(limite, dimension));
//        listaFunciones.add(new Montana(limite, dimension));
//        listaFunciones.add(new Schwefel(limite, dimension));
//        listaFunciones.add(new Rastrigin(limite, dimension));
//        listaFunciones.add(new Griewank(limite, dimension));
//        listaFunciones.add(new Ackley(limite, dimension));
//        listaFunciones.add(new Piso(limite, dimension));

        // EJECUTAR ANALISIS
        ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones, paso);

    }

}
