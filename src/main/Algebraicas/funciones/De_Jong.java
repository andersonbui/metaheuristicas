package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class De_Jong extends Funcion2D {

    public De_Jong(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("De_Jong", limite, dimension, maximizar);
        minGlobal = 0.;//[-2.048,2.048]
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double x12_x2 = valores[0] * valores[0] - valores[1];
        x12_x2 *= x12_x2;
        double uno_x1 = 1 - valores[0];
        uno_x1 *= uno_x1;
        resultado = 100 * x12_x2 + uno_x1;
        return resultado;
    }

    @Override
    public String toString() {
        return "100*(x*x-y)**2 + (1-x)**2";
    }

}
