package metaheuristicas;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Ejecutor.formatear;

/**
 *
 * @author debian
 */
public class Individuo implements Iterable<Double>, Comparable<Individuo>, Cloneable {

    protected double[] valores;
    protected double calidad;
    protected Funcion funcion;
    protected int generacion;
    protected boolean maximizar; //-1 minimizar, +1 maximizar

    /**
     *
     * @param funcion
     * @param maximizar
     */
    public Individuo(Funcion funcion, boolean maximizar) {
        this.funcion = funcion;
        this.maximizar = maximizar;
        calidad = maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
        valores = new double[funcion.dimension];
        generacion = 0;
    }

    public Individuo(Funcion funcion, double[] valores, boolean maximizar) {
        this.funcion = funcion;
        this.maximizar = maximizar;
        this.valores = valores;
        calidad = 0;
        generacion = 0;
    }

    public double getCalidad() {
        return calidad;
    }

    public double[] getValores() {
        return valores;
    }

    public void setValores(double[] valores) {
        this.valores = valores;
    }

    public double get(int posicion) {
        return valores[posicion];
    }

    public void set(int posicion, double valor) {
        this.valores[posicion] = funcion.limitar(valor);
    }

    public int getDimension() {
        return funcion.getDimension();
    }

    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }

    public String toStringBinario() {
        String cadena = "";
        for (double valor : valores) {
            cadena += (int) valor;
        }
        return cadena;
    }

    @Override
    public Individuo clone() {
        try {
            Individuo punto = (Individuo) super.clone();
            punto.valores = valores.clone();
            return punto;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Individuo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int compareTo(Individuo otrop) {
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

    public String toStringInt() {
        String cadena = "";
        for (double valor : valores) {
            cadena += (int) valor + " ";
        }
        cadena += (int) calidad;
        return cadena;
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

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public boolean isMaximizar() {
        return maximizar;
    }

    public void setMaximizar(boolean maximizar) {
        this.maximizar = maximizar;
    }

}
