package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Styblinski_Tang extends Funcion {

    public Styblinski_Tang(double limite, int dimension, boolean maximizar) {
        super("Styblinski-Tang", limite, dimension, maximizar);
        minGlobal = -39.16617 * dimension;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            double x2 = valores[i] * valores[i];
            double x4 = x2 * x2;
            suma += x4 - 16 * x2 + 5 * valores[i];
        }
        return suma / 2;
    }

    @Override
    public String toString() {
        return "(x**4 - 16*x*x + 5*x + y**4 - 16*y*y + 5*y)/2";
    }

}
