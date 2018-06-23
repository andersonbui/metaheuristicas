package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class Cubo extends FuncionAlgebraica {

    public Cubo(double limite, int dimension, boolean maximizar) {
        super("CUBO", limite, dimension, maximizar);
        maxGlobal = Math.pow(limite, dimension);
        minGlobal = -maxGlobal;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado = 1;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado *= valores[i];
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x*y";
    }

}
