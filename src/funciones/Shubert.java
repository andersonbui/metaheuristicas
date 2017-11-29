package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Shubert extends Funcion {

    public Shubert(double limite, int dimension, boolean maximizar) {
        super("SHUBERT", limite, dimension, maximizar);
    }

    @Override
     public double evaluar(Punto punto) {
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length-1; i++) {
            double x1=valores[i],x2=valores[i+1];
            suma += (i*Math.cos(i+1)*x1+i)*(i*Math.cos(i+1)*x2+i);
        }
        return suma;
    }

    @Override
    public String toString() {
        return "";
    }

}
