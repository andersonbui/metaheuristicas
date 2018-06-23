package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class XmasY extends FuncionAlgebraica {

    public XmasY(double limite, int dimension, boolean maximizar) {
        super("XmasY", limite, dimension, maximizar);
        maxGlobal = 2 * limite;
        minGlobal = -maxGlobal;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += valores[i];
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x+y";
    }

}
