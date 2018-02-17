/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

/**
 *
 * @author debian
 */
public abstract class Funcion {

    protected String nombre;
    protected int dimension;
    protected boolean maximizar; //-1 minimizar, +1 maximizar
    protected int contadorEvaluaciones;
    protected double minGlobal;
    protected double maxGlobal;
    protected double error;

//    /**
//     *
//     * @param nombre
//     * @param limite
//     * @param dimension
//     * @param maximizar
//     */
//    public Funcion(String nombre, double limite, int dimension, boolean maximizar) {
//        this.nombre = nombre;
//        this.dimension = dimension;
//        this.maximizar = maximizar;
//        this.maxGlobal = Double.MAX_VALUE;
//        this.minGlobal = -maxGlobal;
//        this.error = 0.001;
//        contadorEvaluaciones = 0;
//    }
    /**
     *
     * @param nombre
     * @param dimension
     * @param maximizar
     */
    public Funcion(String nombre, int dimension, boolean maximizar) {
        this.nombre = nombre;
        this.dimension = dimension;
        this.maximizar = maximizar;
        this.maxGlobal = Double.MAX_VALUE;
        this.minGlobal = -maxGlobal;
        this.error = 0.001;
        contadorEvaluaciones = 0;
    }

    /**
     * identifica si individuo tiene una calidad suficiente exigida por el error
     * aceptado en alcanzar el minimo o maximo global si existe, en cada caso si
     * se está buscando un minimo o maximo respectivamente.
     *
     * @param individuo
     * @return
     */
    public boolean suficiente(Individuo individuo) {
        double maxmin;
        Individuo i;
        if (maximizar) {
            maxmin = maxGlobal;
        } else {
            maxmin = minGlobal;
        }
        return Math.abs(individuo.getCalidad() - maxmin) < error;
    }

    public double getOptimoGlobal() {
        if (maximizar) {
            return maxGlobal;
        } else {
            return minGlobal;
        }
    }

    public double evaluar(Individuo p) {
        contadorEvaluaciones++;
        return 0;
    }

    public int getContadorEvaluaciones() {
        return contadorEvaluaciones;
    }

    public void setContadorEvaluaciones(int contadorEvaluaciones) {
        this.contadorEvaluaciones = contadorEvaluaciones;
    }

    public void reiniciarContadorEvaluaciones() {
        this.contadorEvaluaciones = 0;
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

    public void limitar(Individuo individuo) {

    }

    public abstract Individuo generarIndividuo();

    public boolean isMaximizar() {
        return maximizar;
    }

    public void setMaximizar(boolean maximizar) {
        this.maximizar = maximizar;
    }

    @Override
    public abstract String toString();

    public void siguiente() {
    }

    public boolean haySiguiente() {
        return false;
    }

    public void reiniciar() {
        contadorEvaluaciones = 0;
    }

    public String toString(Individuo individuo) {
        return "" + individuo.getCalidad() + "; max: " + individuo.isMaximizar();
    }

    public double getLimite() {
        return Double.MAX_VALUE;
    }
}
