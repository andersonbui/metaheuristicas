package main.mochila;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;
import metaheuristicas.Aleatorio;

/**
 * @author debian
 */
public abstract class FuncionMochila extends Funcion {

    protected double prob_ceros;

    /**
     *
     * @param nombre
     * @param dimension
     * @param prob_ceros
     */
    public FuncionMochila(String nombre, int dimension, double prob_ceros) {
        super(nombre, dimension, true);
        this.prob_ceros = prob_ceros;
    }

    public double obtenerPeso(Individuo mochila, double[] pesos) {
        double sumPesos = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumPesos += mochila.get(i) * pesos[i];
        }
        return sumPesos;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public Individuo generarIndividuo() {
        Individuo nuevop = new Individuo(this);
        for (int i = 0; i < nuevop.getDimension(); i++) {
            if (Aleatorio.nextDouble() > prob_ceros) {
                nuevop.set(i, 1);
            }
        }
        return nuevop;
    }

}
