package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class FreudensteinRoth extends Funcion2D {

    public FreudensteinRoth(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("FREUDENSTEINROTH", limite, dimension, maximizar);
        minGlobal = 0.; // en f=(5,4),f=(11.41...,−0.8968...)
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double suma = 0;
        double[] valores = punto.getValores();
//        for (int i = 0; i < valores.length-1; i++) {
        int i = 0;
//            double x1=,x2=valores[i+1];
        suma = Math.pow((-13 + valores[i] + ((5 - valores[i + 1]) * valores[i + 1] - 2) * valores[i + 1]), 2) + Math.pow((-29 + valores[i] + ((valores[i + 1] + 1) * valores[i + 1] - 14) * valores[i + 1]), 2);
//        }
        return suma;
    }

    @Override
    public String toString() {
        return "(-13+x+((5-y)*y-2)*y)**2+(-29+x+((y+1)*y-14)*y)**2";
    }

}
