package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class ErrorCuadratico extends Funcion {

    public ErrorCuadratico(double limite, int dimension) {
        super("ERROR CUADRATICO", limite, dimension);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return resultado/valores.length;
    }

    @Override
    public String toString() {
        return "(x*x+y*y)/2";
    }

}
