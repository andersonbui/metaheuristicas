package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion2D;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Levi_N13 extends Funcion2D {

    public Levi_N13(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("Levi_N13", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double sin2_3pix = Math.pow(Math.sin(3 * Math.PI * valores[0]), 2);
        double sin2_3piy = 1 + Math.pow(Math.sin(3 * Math.PI * valores[1]), 2);
        double sin2_2piy = 1 + Math.pow(Math.sin(2 * Math.PI * valores[1]), 2);
        double x_12 = Math.pow(valores[0] - 1, 2);
        double y_12 = Math.pow(valores[1] - 1, 2);
        resultado = sin2_3pix + x_12 * sin2_3piy + y_12 * sin2_2piy;
        return resultado;
    }

    @Override
    public String toString() {
        return "(sin(3*x*pi))**2 + (x-1)**2*(1+(sin(3*pi*y))**2) + (y-1)**2*(1+(sin(2*pi*y))**2) ";
    }

}
