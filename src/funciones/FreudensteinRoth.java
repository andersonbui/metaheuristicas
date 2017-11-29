package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class FreudensteinRoth extends Funcion {

    public FreudensteinRoth(double limite, int dimension, boolean maximizar) {
        super("FREUDENSTEINROTH", limite, dimension, maximizar);
    }

    @Override
     public double evaluar(Punto punto) {
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length-1; i++) {
//            double x1=,x2=valores[i+1];
            suma = Math.pow((-13+valores[i]+((5-valores[i+1])*valores[i+1]-2)*valores[i+1]),2) + Math.pow(((29+valores[i]+(valores[i+1]+1)*valores[i+1]-14)*valores[i+1]),2);
        }
        return suma;
    }

    @Override
    public String toString() {
        return "(-13+x+((5-y)*y-2)*y)**2+(-29+x+((y+1)*y-14)*y)**2";
    }

}
