package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Rastrigin extends Funcion {

    public Rastrigin(double limite, int dimension, boolean maximizar) {
        super("RASTRIGIN", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado +=  valores[i]*valores[i] -10*Math.cos(2*Math.PI*valores[i])+10;
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "(x**2 -10*cos(2*3.141592*x)+10) + (y**2 -10*cos(2*3.141592*y)+10)";
    }

}
