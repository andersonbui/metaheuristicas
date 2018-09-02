package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class SinXporCosY extends FuncionAlgebraica {

    public SinXporCosY(double limite, int dimension, boolean maximizar) {
        super("SinXporCosY", limite, dimension, maximizar);
        minGlobal = -1.;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado = 1;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            if (i % 2 == 0) {
                resultado *= Math.sin(valores[i]);
            } else {
                resultado *= Math.cos(valores[i]);
            }
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "sin(x)*cos(y)";
    }

}
