package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Esfera extends Funcion {

    public Esfera(double limite, int dimension) {
        super("ESFERA", limite, dimension);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x*x+y*y";
    }

}
