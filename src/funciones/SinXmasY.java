package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class SinXmasY extends Funcion {

    public SinXmasY(double limite, int dimension) {
        super("SinXmasY", limite, dimension);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += valores[i];
        }
        return Math.sin(resultado);
    }

    @Override
    public String toString() {
        return "sin(x+y)";
    }

}
