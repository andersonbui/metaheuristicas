package metaheuristicas;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Ejecutor.formatear;

/**
 *
 * @author debian
 */
public class Individuo extends Viajante implements Iterable<Double>, Comparable<Individuo>, Cloneable {

    private double[] valores;
    private double calidad;

    /**
     *
     * @param funcion
     * @param maximizar
     */
    public Individuo(Funcion funcion, boolean maximizar) {
        super(funcion, maximizar);
        this.valores = null;
//        calidad = 0;
        calidad = maximizar ? Double.MIN_VALUE : Double.MAX_VALUE;
    }

    public Individuo(Funcion funcion, double[] valores, boolean maximizar) {
        super(funcion, maximizar);
        this.valores = valores;
        calidad = 0;
    }

//    public Individuo(Funcion funcion, double[] valores, double calidad, int generacion, boolean maximizar) {
//        super(funcion, maximizar);
//        this.valores = valores;
//        this.calidad = calidad;
//        this.generacion = generacion;
//    }
    public double getCalidad() {
        return calidad;
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

//    public String getCalidadString() {
//        return formatear(calidad);
//    }

    public void setCalidad(double calidad) {
        this.calidad = calidad;
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

    @Override
    public void evaluar() {
        setCalidad(funcion.evaluar(this));
    }

    @Override
    public Individuo getOptimo() {
        return this;
    }

}
