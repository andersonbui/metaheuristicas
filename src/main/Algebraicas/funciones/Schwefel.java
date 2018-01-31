package main.Algebraicas.funciones;

import metaheuristicas.Individuo;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class Schwefel extends FuncionAlgebraica {

    public Schwefel(double limite, int dimension, boolean maximizar) {
        super("SCHWEFEL", limite, dimension, maximizar);
        error = 0.01;
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        double sum_tmp;
        for (int i = 0; i < valores.length; i++) {
            sum_tmp = 0;
            for (int j = 0; j <= i; j++) {
                sum_tmp += valores[j];
            }
            resultado += sum_tmp * sum_tmp;
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x**2+(y+x)**2";
    }

}