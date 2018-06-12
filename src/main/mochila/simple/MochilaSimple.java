package main.mochila.simple;

import metaheuristicas.Individuo;
import metaheuristicas.funcion.Funcion;
import java.util.List;
import metaheuristicas.Aleatorio;

/**
 * @author debian
 */
public class MochilaSimple extends Funcion {

    private final List<double[]> w;
    private final double L;
    private final double capacidad;
    private final double prob_ceros;
    private Individuo mejor;

    public MochilaSimple(double capacidad, List<double[]> w, boolean maximizar) {
        super("MOCHILA", w.size(), maximizar);
        this.capacidad = capacidad;
        this.w = w;
        L = 1000; // grande y entero para garantizatr una buena penalizacion
        prob_ceros = 0.6;
    }

    @Override
    public double evaluar(Individuo punto) {
        limitar(punto);
        double result = obtenerPrecio(punto);
        return result;
    }

    public double obtenerPrecio(Individuo punto) {
        double sumWX = 0;
        double sumPX = 0;
        double result = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.get(i) * w.get(i)[0];
            sumPX += punto.get(i) * w.get(i)[1];
        }
        double resta = sumWX - capacidad;
        double max = resta > 0 ? resta : 0;
        double producto = L * max;
        result = sumPX - producto;
//        if (result > 1024) {
//            System.out.print("");
//        }
        return result;
    }

    public double obtenerPeso(Individuo punto) {
        double sumWX = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.get(i) * w.get(i)[0];
        }
        return sumWX;
    }

    @Override
    public void limitar(Individuo punto) {
        int posicion;
        while (obtenerPeso(punto) > capacidad) {
            posicion = mayor(punto);
            punto.set(posicion, 0);
        }
    }

    public int mayor(Individuo punto) {
        int mayor = 0;
        for (int i = 1; i < punto.getDimension(); i++) {
            if (mayor < 0 || punto.get(mayor) * w.get(mayor)[0] < punto.get(i) * w.get(i)[0]) {
                mayor = i;
            }
        }
        return mayor;
    }

    @Override
    public String toString() {
        return "x+y";
    }

    @Override
    public Individuo generarIndividuo() {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (Aleatorio.nextDouble() <= prob_ceros ? 0. : 1);
        }
        Individuo nuevop = new Individuo(this, valores);
        nuevop.evaluar();
        return nuevop;
    }

    public Individuo getMejor() {
        return mejor;
    }

    public void setMejor(Individuo mejor) {
        this.mejor = mejor;
    }

}
