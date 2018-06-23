package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class Rosenbrock extends FuncionAlgebraica {

    public Rosenbrock(double limite, int dimension, boolean maximizar) {
        super("ROSENBROCK", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length - 1; i++) {
            suma += 100 * Math.pow((valores[i + 1] - Math.pow(valores[i], 2)), 2) + Math.pow((1 - valores[i]), 2);
        }
        return suma;
    }

    @Override
    public String toString() {
        return "100*(y - x**2)**2 + (1 - x)**2";
    }

}
