package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion2D;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Bukin extends Funcion2D {

    public Bukin(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("Bukin", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double sqrt = 100 * Math.sqrt(Math.abs(valores[1] - 0.01 * valores[0] * valores[0]));
        double abs = 0.01 * Math.abs(valores[0] + 10);
        resultado = sqrt + abs;
        return resultado;
    }

    @Override
    public String toString() {
        return "100*sqrt(abs(y-0.01*x**2)) + 0.01*abs(x+10)";
    }

}
