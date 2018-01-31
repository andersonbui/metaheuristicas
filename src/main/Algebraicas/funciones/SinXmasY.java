package main.Algebraicas.funciones;

import metaheuristicas.Individuo;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class SinXmasY extends FuncionAlgebraica {

    public SinXmasY(double limite, int dimension, boolean maximizar) {
        super("SinXmasY", limite, dimension, maximizar);
        error = 0.01;
        minGlobal = -1;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += valores[i];
        }
        return Math.sin(resultado);
    }

    @Override
    public String toString() {
        return "sin(x+y)";
    }

}
