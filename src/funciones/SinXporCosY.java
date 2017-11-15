package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class SinXporCosY extends Funcion {

    public SinXporCosY(double limite, int dimension, boolean maximizar) {
        super("SinXporCosY", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Punto punto) {
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
