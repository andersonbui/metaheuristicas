package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Esfera extends Funcion {

    public Esfera(double limite, int dimension, boolean maximizar) {
        super("ESFERA", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x*x+y*y";
    }

}
