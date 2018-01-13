package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class SinXporY extends Funcion {

    public SinXporY(double limite, int dimension, boolean maximizar) {
        super("SinXporY", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado = 0;
        Double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado *= valores[i];
        }
        return Math.sin(resultado);
    }

    @Override
    public String toString() {
        return "sin(x*y)";
    }

}
