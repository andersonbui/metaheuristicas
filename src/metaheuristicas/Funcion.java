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
    protected double limite;
    protected int dimension;
    protected boolean maximizar;
    protected int contadorEvaluaciones;
    protected double minGlobal;
    protected double maxGlobal;
    protected double error;

    /**
     *
     * @param nombre
     * @param limite
     * @param dimension
     * @param maximizar
     */
    public Funcion(String nombre, double limite, int dimension, boolean maximizar) {
        this.nombre = nombre;
        this.limite = limite;
        this.dimension = dimension;
        this.maximizar = maximizar;
        this.maxGlobal = Double.MAX_VALUE;
        this.minGlobal = -maxGlobal;
        this.error = 0.001;
    }

    /**
     * identifica si punto tiene una calidad suficiente exigida por el error
     * aceptado en alcanzar el minimo o maximo global si existe, en cada caso si
     * se está buscando un minimo o maximo respectivamente.
     *
     * @param punto
     * @return
     */
    public boolean suficiente(Individuo punto) {
        double maxmin;
        Individuo i;
        if (maximizar) {
            maxmin = maxGlobal;
        } else {
            maxmin = minGlobal;
        }
        return Math.abs(punto.getCalidad() - maxmin) < error;
    }

    public double evaluar(Individuo p) {
        contadorEvaluaciones++;
        return 0;
    }

    public int getContadorEvaluaciones() {
        return contadorEvaluaciones;
    }

    public void reiniciarContadorEvaluaciones() {
        this.contadorEvaluaciones = 0;
    }

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

    protected Individuo limitar(Individuo punto) {
        Double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            valores[i] = limitar(valores[i]);
        }
        return punto;
    }

    public double limitar(double valor) {
        return limite < valor ? limite : -limite > valor ? -limite : valor;
    }

    public Individuo generarPunto() {
        Double[] valores = new Double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = limitar(Aleatorio.nextDouble() * getLimite() * 2 - getLimite());
        }
        Individuo individuo = new Individuo(this, valores, isMaximizar());
        return individuo;
    }

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

}
