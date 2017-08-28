/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import funciones.Funcion;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 *
 * @author debian
 */
public abstract class AlgoritmoMetaheuristico {

    protected Funcion funcion;
    protected String nombre;

    public AlgoritmoMetaheuristico( String nombre) {
        this.funcion = null;
        this.nombre = nombre;
    }

    /**
     *
     * @param semilla
     * @param iteraciones
     * @param listaPuntosS lista de todos los puntos de recorrido del algoritmo
     * @return
     */
    public abstract Punto ejecutar(long semilla, int iteraciones, Collection listaPuntosS);

    public Punto tweak(Punto punto, long semilla, double paso) {
        Random rand = new Random(semilla);
        Punto nuevop = (Punto) punto.clone();
        double[] valores = nuevop.getValores();
        for (int i = 0; i < valores.length; i++) {
//            valores[i] = limitar(valores[i] + rand.nextGaussian() * paso * 2 - paso);
            valores[i] = limitar(valores[i] + rand.nextDouble() * paso * 2 - paso);
        }
        return nuevop;
    }

    protected double limitar(double valor) {
        double limite = funcion.getLimite();
        return limite < valor ? limite : -limite > valor ? -limite : valor;
    }

    protected Punto generarPunto(long semilla) {
        double[] valores = new double[funcion.getDimension()];
        Random rand = new Random(semilla);

        for (int i = 0; i < valores.length; i++) {
//            valores[i] = limitar(rand.nextGaussian() * funcion.getLimite() * 2 - funcion.getLimite());
            valores[i] = limitar(rand.nextDouble()* funcion.getLimite() * 2 - funcion.getLimite());
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
    
}
