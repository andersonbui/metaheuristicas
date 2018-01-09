package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Griewank extends Funcion {

    public Griewank(double limite, int dimension, boolean maximizar) {
        super("GRIEWANT", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double suma = 0;
        double producto = 1;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            suma += valores[i] * valores[i];
        }
        for (int i = 0; i < valores.length; i++) {
            producto *= Math.cos(valores[i] / Math.sqrt(i + 1));
        }
        return (1 / 4000) * suma - producto + 1;
    }

    @Override
    public String toString() {
        return "(1/4000)*(x**2 + y**2)-cos(x)*cos(y/sqrt(2))+1";
    }

}
