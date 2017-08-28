package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Ackley extends Funcion {

    public Ackley(double limite, int dimension) {
        super("ACKLEY", limite, dimension);
    }

    @Override
    public double evaluar(Punto punto) {
        double suma = 0;
        double suma2 = 0;
        double funct = 0;
        int n = punto.getValores().length;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            suma += valores[i] * valores[i];
        }
        for (int i = 0; i < valores.length; i++) {
            suma2 += Math.cos(2*Math.PI*valores[i]);
        }
        funct = -20*Math.exp(-0.2*Math.sqrt((1/n)*suma)) - Math.exp((1/n)*suma2) + 20 + Math.E;
        return (1 / 4000) * suma - suma2 + 1;
    }

    @Override
    public String toString() {
        return "-20*exp(-0.2*sqrt(0.5*(x**2+y**2))) - exp(0.5*(cos(2*pi*x)+cos(2*pi*y))) + 20 + exp(1)";
    }

}