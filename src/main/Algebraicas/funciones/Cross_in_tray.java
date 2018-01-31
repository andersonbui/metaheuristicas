package main.Algebraicas.funciones;

import metaheuristicas.Individuo;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Cross_in_tray extends Funcion2D {

    public Cross_in_tray(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("Cross-in-tray", limite, dimension, maximizar);
        minGlobal = -2.06261; //para limite entre [-10, 10]
        maxGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double x2 = valores[0] * valores[0];
        double y2 = valores[1] * valores[1];
        resultado = -0.0001 * Math.pow(Math.abs(Math.sin(valores[0]) * Math.sin(valores[1]) * Math.exp(Math.abs(100 - ((Math.sqrt(x2 + y2)) / (Math.PI)))))+1, 0.1);
        return resultado;
    }

    @Override
    public String toString() {
        return "-0.0001*(abs(sin(x)*sin(y)*exp(abs(100-(sqrt(x*x + y*y)/(pi)))))+1)**0.1";
    }

}
