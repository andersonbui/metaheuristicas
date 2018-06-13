package metaheuristicas;

import metaheuristicas.funcion.Funcion;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Ejecutor.formatear;

/**
 *
 * @author debian
 * @param <FuncionM>
 */
public class Individuo<FuncionM extends Funcion> implements Iterable<Double>, Comparable<Individuo>, Cloneable {

    protected double[] valores;
    protected double calidad;
    protected FuncionM funcion;
    protected int generacion;

    /**
     * @param funcion
     */
    public Individuo(FuncionM funcion) {
        this.funcion = funcion;
//        calidad = funcion.maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
        valores = new double[funcion.getDimension()];
        generacion = 0;
    }

    public Individuo(FuncionM funcion, double[] valores) {
        this.funcion = funcion;
        this.valores = valores;
//        calidad = funcion.maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
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
        this.valores[posicion] = valor;
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
        int orden = funcion.isMaximizar() ? 1 : -1;
        return orden * a_calidad.compareTo(otrop.getCalidad());
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            int posicion = 0;

            @Override
            public boolean hasNext() {
                return valores.length > posicion;
            }

            @Override
            public Double next() {
                return valores[posicion++];
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

    public double evaluar() {
        calidad = funcion.evaluacion(this);
        return calidad;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(FuncionM funcion) {
        this.funcion = funcion;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public boolean isMaximizar() {
        return funcion.isMaximizar();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individuo other = (Individuo) obj;
        if (!Arrays.equals(this.valores, other.valores)) {
            return false;
        }
        return true;
    }

}
