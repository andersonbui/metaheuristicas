package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Cubo extends Funcion {

    public Cubo(double limite, int dimension, boolean maximizar) {
        super("CUBO", limite, dimension, maximizar);
        maxGlobal = Math.pow(limite, dimension);
        minGlobal = -maxGlobal;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado = 1;
        Double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado *= valores[i];
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x*y";
    }

}
