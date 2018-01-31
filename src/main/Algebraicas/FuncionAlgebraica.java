/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Algebraicas;

import metaheuristicas.*;

/**
 *
 * @author debian
 */
public abstract class FuncionAlgebraica extends Funcion {

    protected double limite;

    /**
     *
     * @param nombre
     * @param limite
     * @param dimension
     * @param maximizar
     */
    public FuncionAlgebraica(String nombre, double limite, int dimension, boolean maximizar) {
        super(nombre, dimension, maximizar);
    }

    @Override
    public double evaluar(Individuo p) {
        contadorEvaluaciones++;
        return 0;
    }

    @Override
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    /**
     *
     * @param punto
     */
    @Override
    public void limitar(Individuo punto) {
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            valores[i] = limitar(valores[i]);
        }
    }

    /**
     *
     * @param valor
     * @return
     */
    protected double limitar(double valor) {
        return limite < valor ? limite : -limite > valor ? -limite : valor;
    }

    /**
     *
     * @return
     */
    @Override
    public Individuo generarIndividuo() {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = limitar(Aleatorio.nextDouble() * getLimite() * 2 - getLimite());
        }
        Individuo individuo = new Individuo(this, valores);
        return individuo;
    }

    @Override
    public abstract String toString();

}
