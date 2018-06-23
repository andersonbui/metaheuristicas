package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class Esfera extends FuncionAlgebraica {

    public Esfera(double limite, int dimension, boolean maximizar) {
        super("ESFERA", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x*x+y*y";
    }

}
