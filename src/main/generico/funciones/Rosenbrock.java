package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Rosenbrock extends Funcion {

    public Rosenbrock(double limite, int dimension, boolean maximizar) {
        super("ROSENBROCK", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double suma = 0;
        Double[] valores = punto.getValores();
        for (int i = 0; i < valores.length - 1; i++) {
            suma += 100 * Math.pow((valores[i + 1] - Math.pow(valores[i], 2)), 2) + Math.pow((1 - valores[i]), 2);
        }
        return suma;
    }

    @Override
    public String toString() {
        return "100*(y - x**2)**2 + (1 - x)**2";
    }

}
