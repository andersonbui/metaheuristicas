package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Cubo extends Funcion {

    public Cubo(double limite, int dimension, boolean maximizar) {
        super("CUBO", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 1;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado *= valores[i];
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x*y";
    }

}
