package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion2D;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class GoldsteinAndPrice extends Funcion2D {

    public GoldsteinAndPrice(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("GOLDSTEINANDPRICE", limite, dimension, maximizar);
        error = 0.01;
        minGlobal = 3;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double suma = 0;
        double[] valores = punto.getValores();
//        for (int i = 0; i < valores.length - 1; i++) {
        //suma += Math.pow((Math.pow(valores[i],2) - valores[i+1] + 1),2) + Math.pow((1-valores[i]),2);
        int i = 0;
        double x1 = valores[i];
        double x2 = valores[i + 1];
        double x12 = x1 * x1;
        double x22 = x2 * x2;
        double x1x2 = x1 * x2;
        suma = (1 + Math.pow((x1 + x2 + 1), 2) * (19 - 14 * x1 + 3 * x12 - 14 * x2 + 6 * x1x2 + 3 * x22)) * (30 + Math.pow((2 * x1 - 3 * x2), 2) * (18 - 32 * x1 + 12 * x12 + 48 * x2 - 36 * x1x2 + 27 * x22));
//        }
        return suma;
    }

    @Override
    public String toString() {
        return "(1+(x+y+1)**2*(19-14*x+3*x**2-14*y+6*x*y+3*y**2))*(30+(2*x-3*y)**2*(18-32*x+12*x**2+48*y-36*x*y+27*y**2))";
    }

}
