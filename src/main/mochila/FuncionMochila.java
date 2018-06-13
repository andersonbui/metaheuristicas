package main.mochila;

import metaheuristicas.Individuo;
import metaheuristicas.funcion.Funcion;
import metaheuristicas.Aleatorio;

/**
 * @author debian
 */
public abstract class FuncionMochila extends Funcion<IndividuoMochila> {

    protected double prob_ceros;
    protected double[] vectorPesos;

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

    protected double obtenerPeso(Individuo mochila, double[] pesos) {
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
    public IndividuoMochila generarIndividuo() {
        IndividuoMochila nuevop = new IndividuoMochila(this);
        for (int i = 0; i < nuevop.getDimension(); i++) {
            if (Aleatorio.nextDouble() > prob_ceros) {
                nuevop.set(i, 1);
            }
        }
        return nuevop;
    }

}
