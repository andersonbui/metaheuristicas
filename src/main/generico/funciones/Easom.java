package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion2D;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Easom extends Funcion2D {

    public Easom(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("Easom", limite, dimension, maximizar);
        minGlobal = -1;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado;
        Double[] valores = punto.getValores();
        double x_pi2 = valores[0] - Math.PI;
        x_pi2 *= x_pi2;
        double y_pi2 = valores[1] - Math.PI;
        y_pi2 *= y_pi2;
        resultado = -Math.cos(valores[0]) * Math.cos(valores[1]) * Math.exp(-(x_pi2 + y_pi2));
        return resultado;
    }

    @Override
    public String toString() {
        return "-cos(x)*cos(y)*exp(-((x - pi)**2 + (y - pi)**2))";
    }

}
