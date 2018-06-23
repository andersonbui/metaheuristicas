package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class Montana extends FuncionAlgebraica {

    public Montana(double limite, int dimension, boolean maximizar) {
        super("MONTANA", limite, dimension, maximizar);
        minGlobal = -0.21723;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return Math.sin(resultado) * 1 / resultado;
    }

    @Override
    public String toString() {
        return "sin(x*x+y*y)*1/(x*x+y*y)";
    }

}
