package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Holder_table extends Funcion2D {

    public Holder_table(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("Holder-table", limite, dimension, maximizar);
        minGlobal = -19.2085; //para limite entre [-10, 10]
        maxGlobal = 0;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double x2 = valores[0] * valores[0];
        double y2 = valores[1] * valores[1];
        resultado = -Math.abs(Math.sin(valores[0]) * Math.cos(valores[1]) * Math.exp(Math.abs(1 - ((Math.sqrt(x2 + y2)) / (Math.PI)))));
        return resultado;
    }

    @Override
    public String toString() {
        return "-abs(sin(x)*cos(y)*exp(abs(1-(sqrt(x*x + y*y)/(pi)))))";
    }

}
