/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import java.util.Iterator;

/**
 *
 * @author debian
 */
public class Punto implements Iterable<Double>, Comparable<Punto> {

    private double[] valores;
    private double calidad;

    public Punto(double[] valores) {
        this.valores = valores;
        calidad = 0;
    }

    public Punto(double[] valores, double calidad) {
        this.valores = valores;
        this.calidad = calidad;
    }

    public double[] getValores() {
        return valores;
    }

    public void setValores(double[] valores) {
        this.valores = valores;
    }

    public double getCalidad() {
        return calidad;
    }

    public String getCalidadString() {
        return formatear(calidad);
    }

    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }

    public String toString2() {

        return "Punto{" + toString() + '}';
    }

    public String toString3() {

        return toString().replace(' ', ';').replace('.', ',');
    }

    @Override
    public Punto clone() {
        return new Punto(valores.clone(), calidad);
    }

    @Override
    public int compareTo(Punto otrop) {
        int retorno = this.calidad > otrop.calidad ? 1 : this.calidad < otrop.calidad ? -1 : 0;
        return -retorno;
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            int posicion = 0;

            @Override
            public boolean hasNext() {
                return valores.length < posicion;
            }

            @Override
            public Double next() {
                return valores[posicion];
            }

        };
    }

    @Override
    public String toString() {
        String cadena = "";
        for (double valor : valores) {
            cadena += formatear(valor).replace(',', '.') + " ";
        }
        cadena += formatear(calidad).replace(',', '.');
        return cadena;
    }

    public static String formatear(double valor) {
        return String.format("%." + General.NUM_DECIMALES + General.FORMATO_DOUBLE + "", valor);
    }
}
