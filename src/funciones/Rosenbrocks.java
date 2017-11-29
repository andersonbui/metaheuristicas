package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Rosenbrocks extends Funcion {

    public Rosenbrocks(double limite, int dimension, boolean maximizar) {
        super("ROSENBROCKS", limite, dimension, maximizar);
    }

    @Override
     public double evaluar(Punto punto) {
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length-1; i++) {
            suma += 100 * Math.pow((Math.pow(valores[i],2) - valores[i+1] + 1),2) + Math.pow((1-valores[i]),2);
        }
        return suma;
    }

    @Override
    public String toString() {
        return "100*(x - y^2)^2 + (1 - x)^2";
    }

}
