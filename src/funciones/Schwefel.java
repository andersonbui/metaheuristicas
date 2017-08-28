package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Schwefel extends Funcion {

    public Schwefel(double limite, int dimension) {
        super("SCHWEFEL", limite, dimension);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        double sum_tmp;
        for (int i = 0; i < valores.length; i++) {
            sum_tmp = 0;
            for (int j = 0; j <= i; j++) {
                sum_tmp += valores[j];
            }
            resultado += sum_tmp * sum_tmp;
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x**2+(y+x)**2";
    }

}