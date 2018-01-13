package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion2D;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Six_peak_hunchback extends Funcion2D {

    public Six_peak_hunchback(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("Six_peak_hunchback", limite, dimension, maximizar);
        // solo para dos dimensiones
        minGlobal = -1.031628; //[-3,3]
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado;
        Double[] valores = punto.getValores();
        double x2 = valores[0] * valores[0];
        double y2 = valores[1] * valores[1];
        resultado = (4 - 2.1 * x2 + (1 / 3) * x2 * x2) * x2 + valores[0] * valores[1]
                + (-4 + 4 * y2) * y2;
        return resultado;
    }

    @Override
    public String toString() {
        return "(4-2.1*x*x + (1/3)*x**4)*x*x + x*y + (-4 + 4*y*y)*y*y";
    }

}
