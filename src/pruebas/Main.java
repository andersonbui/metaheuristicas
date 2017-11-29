/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import metaheuristicas.AlgoritmoMetaheuristico;
import funciones.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.poblacion.AlgoritmoEvolutivo;
import metaheuristicas.poblacion.EstrategiaEvolucionDiferencial;
import metaheuristicas.poblacion.EstrategiaGenetico;
import metaheuristicas.poblacion.EstrategiaGeneticoBinaria;
import metaheuristicas.poblacion.EstrategiaMLamda;
import metaheuristicas.poblacion.EstrategiaMutacion;
import metaheuristicas.simple.Hill_Climbing;
import static pruebas.Utilidades.ejecutarAlgoritmosMasFunciones;

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

    public static void main(String[] args) throws FileNotFoundException, Exception {
        double paso;
        double limite;
        int dimension;
        int iteraciones;
        int numMuestras;
        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
        graficaRecorrido3D = true;
        graficaDispercion2D = true;
        boolean maximizar = false;
        // rango maximo de cambio en el tweak
        paso = 1;
        // limite de las funciones
        limite = 100;
        // dimension de los puntos
        dimension = 2;
        // iteraciones realizadas por los algoritmos
        iteraciones = 100;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        numMuestras = 5;

        List<AlgoritmoMetaheuristico> listaAlgoritmos = new ArrayList();
//        listaAlgoritmos.add(new Hill_Climbing_MPendiente(paso));
//        listaAlgoritmos.add(new Hill_Climbing_WR(paso,10));
        listaAlgoritmos.add(new Hill_Climbing(paso));
//        listaAlgoritmos.add(new Hill_Climbing_Random_Restarts(paso, 50));
//        listaAlgoritmos.add(new Random_Search());
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaGenetico(10)));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaGeneticoBinaria(10)));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaMutacion(10)));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaMLamda(5, 10)));
//        listaAlgoritmos.add(new AlgoritmoEvolutivo(new EstrategiaEvolucionDiferencial(10)));
//        listaAlgoritmos.add(new Hill_Climbing(new Tweak_1(paso), 10,false)); // hill climbing maxima pendiente
//        listaAlgoritmos.add(new Hill_Climbing(new Tweak_1(paso), 10,true)); // hill climbing con reinicio aleatorio
//        listaAlgoritmos.add(new Hill_Climbing_HO(paso));
//        listaAlgoritmos.add(new Simulated_Annealing(paso));
//        listaAlgoritmos.add(new B_Hill_Climbing(new Tweak_B_HC(paso)));

        List<Funcion> listaFunciones = new ArrayList();
        listaFunciones.add(new Esfera(limite, dimension, maximizar));
        listaFunciones.add(new FreudensteinRoth(limite, dimension, maximizar));
        listaFunciones.add(new Glankwahmdees(limite, dimension, maximizar));
        listaFunciones.add(new GoldsteinAndPrice(limite, dimension, maximizar));
        listaFunciones.add(new Rosenbrocks(limite, dimension, maximizar));
        listaFunciones.add(new SchafferF6(limite, dimension, maximizar));
        listaFunciones.add(new Shubert(limite, dimension, maximizar));
//        listaFunciones.add(new Cubo(limite, dimension));
//        listaFunciones.add(new XmasY(limite, dimension));
//        listaFunciones.add(new SinXmasY(limite, dimension));
//        listaFunciones.add(new SinXporCosY(limite, dimension));
//        listaFunciones.add(new Montana(limite, dimension));
//        listaFunciones.add(new Schwefel(limite, dimension));
//        listaFunciones.add(new Rastrigin(limite, dimension));
//        listaFunciones.add(new Griewank(limite, dimension));
//        listaFunciones.add(new Ackley(limite, dimension, maximizar));
//        listaFunciones.add(new Piso(limite, dimension, maximizar));

        // EJECUTAR ANALISIS
        ejecutarAlgoritmosMasFunciones(listaAlgoritmos, listaFunciones, graficaRecorrido3D, graficaDispercion2D, numMuestras, iteraciones, paso);

    }

}
