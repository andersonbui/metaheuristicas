/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Algebraicas;

import main.Algebraicas.funciones.Schwefel;
import main.Algebraicas.funciones.Rastrigin;
import main.Algebraicas.funciones.Styblinski_Tang;
import main.Algebraicas.funciones.Montana;
import metaheuristicas.Funcion;
import metaheuristicas.AlgoritmoMetaheuristico;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.Ejecutor;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EvolucionDiferencial;
import metaheuristicas.poblacion.EvolucionDiferencialMejorado;
import metaheuristicas.poblacion.Genetico;
import metaheuristicas.poblacion.GeneticoBinaria;
import metaheuristicas.poblacion.MLamda;
import metaheuristicas.poblacion.Mutacion;
import metaheuristicas.simple.Hill_Climbing;

/**
 *
 * @author debian entregables 2 corte: 1- solucion del problemas de la mochila
 * geneticos y PSO. 2- escoger heuristica: saltos de rana revueltas, competencia
 * imperialista, optimizacion basado en corales, enjambre de peces explicar la
 * metaheuristicas y por que se escogio la heuristica.
 *
 * (no los que hemos visto en clase) entrar a la biblioteca virtual sience
 * direct o escopus o , buscar articulo de metaheuristicas ejemplo: enrutamiento
 * de vehiculos: grass, leerlo entenderlo, implementarlo y optener una version
 * que se parezca al articulo y comparacion con su implementacion con la
 * heuristicas escogida finalmente se hace una presentacion en video (subir a
 * youtube) y compertir con el equipo. fecha entrega para: 27/oct/17
 *
 */
public class Main {

    public void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int dimension;
        int iteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para curvas de convergencia con gnuplot
//        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        boolean maximizar = false;
        // limite de las funciones
        limite = 10;
        // dimension de los puntos
        dimension = 2;
        // iteraciones realizadas por los algoritmos
        iteraciones = 1000;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 2;

        int tamPoblacion = 10;

        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
//        listaAlgoritmos.add(new Hill_Climbing_MPendiente(paso));
//        listaAlgoritmos.add(new Hill_Climbing_WR(paso,10));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaGeneticoBinaria(10)));
//        listaAlgoritmos.add(new Hill_Climbing_Random_Restarts(paso, 50));
//        listaAlgoritmos.add(new Random_Search());
//        listaAlgoritmos.add(new Hill_Climbing(1));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaGenetico(tamPoblacion)));
        listaAlgoritmos.add(new Mutacion(tamPoblacion));
        listaAlgoritmos.add(new MLamda(5, tamPoblacion));
        listaAlgoritmos.add(new EvolucionDiferencial(tamPoblacion));

//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaEvolucionDiferencialMejorado(100)));
//        listaAlgoritmos.add(new Hill_Climbing(new Tweak_1(paso), 10,false)); // hill climbing maxima pendiente
//        listaAlgoritmos.add(new Hill_Climbing(new Tweak_1(paso), 10,true)); // hill climbing con reinicio aleatorio
//        listaAlgoritmos.add(new Hill_Climbing_HO(paso));
//        listaAlgoritmos.add(new Simulated_Annealing(paso));
//        listaAlgoritmos.add(new B_Hill_Climbing(new Tweak_B_HC(paso)));
        List<Funcion> listaFunciones = new ArrayList();
// 2D
//        listaFunciones.add(new GoldsteinAndPrice(limite, dimension, maximizar));
//        listaFunciones.add(new Cross_in_tray(limite, dimension, maximizar));
//        listaFunciones.add(new Schaffer_N2(limite, dimension, maximizar));
//        listaFunciones.add(new Schaffer(limite, dimension, maximizar));
//        listaFunciones.add(new Six_peak_hunchback(limite, dimension, maximizar));
//        listaFunciones.add(new Schaffer_N4(limite, dimension, maximizar));
//        listaFunciones.add(new Holder_table(limite, dimension, maximizar));
//        listaFunciones.add(new Easom(limite, dimension, maximizar));
//        listaFunciones.add(new Levi_N13(limite, dimension, maximizar));
//        listaFunciones.add(new Bukin(limite, dimension, maximizar));
//        listaFunciones.add(new Shubert(limite, dimension, maximizar));
//        listaFunciones.add(new De_Jong(limite, dimension, maximizar));
//        listaFunciones.add(new FreudensteinRoth(limite, dimension, maximizar));
// MULTI_DIMENSIONAL
//        listaFunciones.add(new Cubo(limite, dimension, maximizar));
//        listaFunciones.add(new XmasY(limite, dimension, maximizar));
//        listaFunciones.add(new SinXmasY(limite, dimension, maximizar));
//        listaFunciones.add(new SinXporCosY(limite, dimension, maximizar));
//        listaFunciones.add(new Esfera(limite, dimension, maximizar));
//        listaFunciones.add(new Piso(limite, dimension, maximizar));
//        listaFunciones.add(new Glankwahmdees(limite, dimension, maximizar));
//        listaFunciones.add(new Rosenbrock(limite, dimension, maximizar));
        listaFunciones.add(new Styblinski_Tang(limite, dimension, maximizar));
        listaFunciones.add(new Montana(limite, dimension, maximizar));
        listaFunciones.add(new Schwefel(limite, dimension, maximizar));
        listaFunciones.add(new Rastrigin(limite, dimension, maximizar));
//        listaFunciones.add(new Griewank(limite, dimension, maximizar));
//        listaFunciones.add(new Ackley(limite, dimension, maximizar));

        // EJECUTAR ANALISIS
        (new Ejecutor()).ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones);

    }

}
