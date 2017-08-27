/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

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

//    public void setLimite(double limite) {
//        this.limite = limite;
//    }

    public int getDimension() {
        return dimension;
    }

//    public void setDimension(int dimension) {
//        this.dimension = dimension;
//    }

    public String getNombre() {
        return nombre;
    }

//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }

}
