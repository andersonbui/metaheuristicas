package main.mochila;

import metaheuristicas.funcion.FuncionGen;

/**
 * @author debian
 * @param <Individuo>
 */
public abstract class FuncionMochila<Individuo extends IndividuoMochila> extends FuncionGen<Individuo> {

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

    @Deprecated
    protected double obtenerPeso(Individuo mochila, double[] pesos) {
        double sumPesos = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumPesos += mochila.get(i) * pesos[i];
        }
        return sumPesos;
    }

    /**
     * obtiene el peso del i-esimo elemento
     *
     * @param i
     * @return
     */
    public double peso(int i) {
        return vectorPesos[i];
    }

    /**
     * obtiene el peso de la mochila completa, es decir, la suma de los pesos de
     * todos los elementos dentro de ella.
     *
     * @param mochila
     * @return
     */
    protected double obtenerPeso(Individuo mochila) {
        double sumPesos = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumPesos += mochila.get(i) * vectorPesos[i];
        }
        return sumPesos;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
