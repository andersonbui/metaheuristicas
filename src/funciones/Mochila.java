package funciones;

import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Mochila extends Funcion {

    private final List<double[]> w;
    private final double L;
    private final double prob_ceros;

    public Mochila(double capacidad, List<double[]> w) {
        super("MOCHILA", capacidad, w.size());
        this.w = w;
        L = 1000; // grande y entero para garantizatr una buena penalizacion
        prob_ceros = 0.5;
    }

    @Override
    public double evaluar(Punto punto) {
        double sumWX = 0;
        double sumPX = 0;
        double result = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.getValor(i) * w.get(i)[0];
            sumPX += punto.getValor(i) * w.get(i)[1];
        }
        double resta = sumWX - limite;
        double max = resta > 0 ? resta : 0;
        double producto = L * max;
        result = sumPX - producto;
        if(result>1024){
            System.out.print("");
        }
        return result;
    }

    @Override
    public String toString() {
        return "x+y";
    }

    @Override
    public Punto generarPunto(Random rand) {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (rand.nextDouble() <= prob_ceros ? 0 : 1);
        }
        Punto nuevop = new Punto(this, valores);
        nuevop.evaluar();
        return nuevop;
    }

}
