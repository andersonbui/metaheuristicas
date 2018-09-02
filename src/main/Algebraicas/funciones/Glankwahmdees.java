package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import main.Algebraicas.FuncionAlgebraica;

/**
 *
 * @author debian
 */
public class Glankwahmdees extends FuncionAlgebraica {

    public Glankwahmdees(double limite, int dimension, boolean maximizar) {
        super("GLANKWAHMDEES", limite, dimension, maximizar);
        minGlobal = 0.;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length - 1; i++) {
            double x1 = valores[i], x2 = valores[i + 1];
            suma = Math.pow((Math.pow(x1, 2) + x2 - 11), 2) + Math.pow((x1 + (Math.pow(x2, 2)) - 7), 2);
        }
        return suma;
    }

    @Override
    public String toString() {
        return "(x**2+y-11)**2 + (x+y**2-7)**2";
    }

}
