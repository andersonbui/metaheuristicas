/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import funciones.Funcion;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author debian
 */
public abstract class AlgoritmoMetaheuristico {

    protected Funcion funcion;
    protected String nombre;
    protected Random rand;

    public AlgoritmoMetaheuristico(String nombre) {
        this.funcion = null;
        this.nombre = nombre;
    }
    
    /**
     *
     * @param inicial
     * @param iteraciones
     * @param listaPuntosS lista de todos los puntos de recorrido del algoritmo
     * @return
     */
    public abstract Punto ejecutar(Punto inicial, int iteraciones, Collection listaPuntosS);

    /**
     * variacion de un punto[i] entre [-paso, paso], para i=1,2,3,...,N donde N
     * = dimension(punto)
     *
     * @param punto
     * @param paso
     * @return
     */
    public Punto tweak(Punto punto, double paso) {
        Punto nuevop = (Punto) punto.clone();
        double[] valores = nuevop.getValores();
        for (int i = 0; i < valores.length; i++) {
//            valores[i] = limitar(valores[i] + rand.nextGaussian() * paso * 2 - paso);
            valores[i] = limitar(valores[i] + rand.nextDouble() * paso * 2 - paso);
        }
        return nuevop;
    }

    /**
     * variacion de un i entre [-ancho, ancho]
     *
     * @param i
     * @param ancho
     * @return
     */
    public double tweaki(double i, double ancho) {
        double nuevop = limitar(i + rand.nextDouble() * ancho * 2 - ancho);
        return nuevop;
    }

    protected double limitar(double valor) {
        double limite = funcion.getLimite();
        return limite < valor ? limite : -limite > valor ? -limite : valor;
    }

    public Punto generarPunto() {
        double[] valores = new double[funcion.getDimension()];
        for (int i = 0; i < valores.length; i++) {
//            valores[i] = limitar(rand.nextGaussian() * funcion.getLimite() * 2 - funcion.getLimite());
            valores[i] = limitar(rand.nextDouble() * funcion.getLimite() * 2 - funcion.getLimite());
        }
        return new Punto(valores);
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

}
