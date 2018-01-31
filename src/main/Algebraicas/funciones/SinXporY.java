package main.Algebraicas.funciones;

import metaheuristicas.Individuo;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class SinXporY extends FuncionAlgebraica {

    public SinXporY(double limite, int dimension, boolean maximizar) {
        super("SinXporY", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado *= valores[i];
        }
        return Math.sin(resultado);
    }

    @Override
    public String toString() {
        return "sin(x*y)";
    }

}
