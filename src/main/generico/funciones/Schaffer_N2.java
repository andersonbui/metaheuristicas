package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion2D;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Schaffer_N2 extends Funcion2D {

    public Schaffer_N2(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("SCHAFFER N2", limite, dimension, maximizar);
        // solo para dos dimensiones
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double x2 = valores[0] * valores[0];
        double y2 = valores[1] * valores[1];
        resultado = 0.5 + (Math.pow(Math.sin(x2 - y2), 2) - 0.5) / (Math.pow((1 + 0.001 * (x2 + y2)), 2));
        return resultado;
    }

    @Override
    public String toString() {
        return "0.5+((sin(x**2 - y**2))**2-0.5)/(((1+0.001*(x**2 + y**2)))**2)";
    }

}
