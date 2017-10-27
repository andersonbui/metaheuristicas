package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class XmasY extends Funcion {

    public XmasY(double limite, int dimension, boolean maximizar) {
        super("XmasY", limite, dimension, maximizar);
    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += valores[i];
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "x+y";
    }

}
