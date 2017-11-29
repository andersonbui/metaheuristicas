package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Montana extends Funcion {

    public Montana(double limite, int dimension, boolean maximizar) {
        super("MONTANA", limite, dimension, maximizar);
    }

//    public Montana(double limite, int dimension) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public double evaluar(Punto punto) {
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return Math.sin(resultado)*1/resultado;
    }

    @Override
    public String toString() {
        return "sin(x*x+y*y)*1/(x*x+y*y)";
    }

}
