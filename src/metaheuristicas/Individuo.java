package metaheuristicas;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Ejecutor.formatear;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaHyperplaneExploration;

/**
 *
 * @author debian
 */
public class Individuo implements Iterable<Double>, Comparable<Individuo>, Cloneable {

    protected double[] valores;
    protected double calidad;
    protected Funcion funcion;
    protected int generacion;

    /**
     *
     * @param funcion
     */
    public Individuo(Funcion funcion) {
        this.funcion = funcion;
        calidad = funcion.maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
        valores = new double[funcion.dimension];
        generacion = 0;
    }

    public Individuo(Funcion funcion, double[] valores) {
        this.funcion = funcion;
        this.valores = valores;
        calidad = funcion.maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
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
        evaluar();
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
        int orden = funcion.maximizar ? 1 : -1;
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
    public double peso;

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public double evaluar() {
        calidad = funcion.evaluar(this);
        return calidad;
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
        return funcion.isMaximizar();
    }

}