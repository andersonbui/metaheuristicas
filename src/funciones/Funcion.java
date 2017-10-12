/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public abstract class Funcion {

    protected String nombre;
    protected double limite;
    protected int dimension;

    public Funcion(String nombre, double limite, int dimension) {
        this.nombre = nombre;
        this.limite = limite;
        this.dimension = dimension;
    }

    public abstract double evaluar(Punto p);

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Punto limitar(Punto punto) {
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            valores[i] = limitar(valores[i]);
        }
        return punto;
    }

    public double limitar(double valor) {
        return limite < valor ? limite : -limite > valor ? -limite : valor;
    }

    public Punto generarPunto(Random rand) {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = limitar(rand.nextDouble() * getLimite() * 2 - getLimite());
        }
        return new Punto(valores);
    }
}
