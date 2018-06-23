package main.Algebraicas.funciones;

import metaheuristicas.IndividuoGen;
import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public class Shubert extends Funcion2D {

    public Shubert(double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super("SHUBERT", limite, dimension, maximizar);
        error = 0.01;
        minGlobal = -186.731; //[-10,10]
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        double resultado;
        double[] valores = punto.getValores();
        double jcosX = 0;
        double jcosY = 0;
        for (int j = 1; j < 5; j++) {
            jcosX += j * Math.cos((j + 1) * valores[0] + j);
            jcosY += j * Math.cos((j + 1) * valores[1] + j);
        }
        resultado = jcosX * jcosY;
//        for (int i = 1; i < valores.length; i++) {
//            double x1 = valores[i - 1];
//            double x2 = valores[i];
//            suma += (i * Math.cos((i + 1) * x1 + i)) * (i * Math.cos((i + 1) * x2 + i));
//        }
        return resultado;
    }

    @Override
    public String toString() {
        return "(cos(2*x+1)+2*cos(3*x+2)+3*cos(4*x+3)+4*cos(5*x+4)+5*cos(6*x+5))"
                + "*(cos(2*y+1)+2*cos(3*y+2)+3*cos(4*y+3)+4*cos(5*y+4)+5*cos(6*y+5))";
    }

}
