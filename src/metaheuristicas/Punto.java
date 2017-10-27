package metaheuristicas;

import funciones.Funcion;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debian
 */
public class Punto implements Iterable<Double>, Comparable<Punto>, Cloneable {

    private double[] valores;
    private double calidad;
    private Funcion funcion;
    private int generacion;
    public boolean maximizar; //-1 minimizar, +1 maximizar

    /**
     *
     * @param funcion
     * @param maximizar
     */
    public Punto(Funcion funcion, boolean maximizar) {
        this.funcion = funcion;
        this.valores = null;
//        calidad = 0;
        generacion = 0;
        this.maximizar = maximizar;
        calidad = maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
    }

    public Punto(Funcion funcion, double[] valores, boolean maximizar) {
        this.valores = valores;
        this.funcion = funcion;
        calidad = 0;
        generacion = 0;
    }

    public Punto(Funcion funcion, double[] valores, double calidad, int generacion, boolean maximizar) {
        this.valores = valores;
        this.calidad = calidad;
        this.funcion = funcion;
        this.generacion = generacion;
    }

    public int getDimension() {
        return funcion.getDimension();
    }

    public double[] getValores() {
        return valores;
    }

    public void setValores(double[] valores) {
        this.valores = valores;
    }

    public double getValor(int posicion) {
        return valores[posicion];
    }

    public void set(int posicion, double valor) {
        this.valores[posicion] = funcion.limitar(valor);
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

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public int aumentarGeneracion() {
        return generacion++;
    }

    public String toString2() {
        return "Punto{" + toString() + '}';
    }

    public String toString3() {
        return toString().replace(' ', ';').replace('.', ',');
    }

    @Override
    public Punto clone() {
        try {
            Punto punto = (Punto) super.clone();
            punto.valores = valores.clone();
            return punto;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Punto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int compareTo(Punto otrop) {
        Double a_calidad = this.calidad;
        int orden = maximizar ? 1 : -1;
        return orden * a_calidad.compareTo(otrop.getCalidad());
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

    public void evaluar() {
        setCalidad(funcion.evaluar(this));
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public static Punto generar(Funcion funcion, Random rand) {
        return funcion.generarPunto(rand);
    }
}
