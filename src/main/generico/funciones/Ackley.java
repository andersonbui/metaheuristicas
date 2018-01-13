package main.generico.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;

/**
 *
 * @author debian
 */
public class Ackley extends Funcion {

    public Ackley(double limite, int dimension, boolean maximizar) {
        super("ACKLEY", limite, dimension, maximizar);
        minGlobal = 0;
    }

    @Override
    public double evaluar(Individuo punto) {
        super.evaluar(punto);
        double suma = 0;
        double suma2 = 0;
        double funct = 0;
        int n = punto.getValores().length;
        Double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            suma += valores[i] * valores[i];
        }
        for (int i = 0; i < valores.length; i++) {
            suma2 += Math.cos(2 * Math.PI * valores[i]);
        }
//        System.out.println("Math.exp(1):"+Math.exp(1)+";   Math.E"+Math.E);
        funct = -20 * Math.exp(-0.2 * Math.sqrt((1.0 / n) * suma)) - Math.exp((1.0 / n) * suma2) + 20 + Math.E;
        return funct;
    }

    @Override
    public String toString() {
        return "-20*exp(-0.2*sqrt(0.5*(x**2+y**2))) - exp(0.5*(cos(2*pi*x)+cos(2*pi*y))) + 20 + exp(1)";
    }

}
