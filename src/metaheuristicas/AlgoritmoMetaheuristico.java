package metaheuristicas;

import java.util.List;

/**
 *
 * @author debian
 */
public abstract class AlgoritmoMetaheuristico {

    protected int iteraciones;
    protected int maxIteraciones;
    protected String nombre;
    protected Funcion funcion;

    public AlgoritmoMetaheuristico() {
        this.nombre = "";
        iteraciones = 0;
    }

    public abstract List<Individuo> ejecutar(Funcion funcion);

    /**
     * permite hacer pequeñas modificaciones y que se ejecuten con estas por
     * cada iteracionde siguiente. Por ejemplo el cambio de un parametro en un
     * rango
     */
    public void siguiente() {
    }

    public boolean haySiguiente() {
        return false;
    }

    public void reiniciar() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public int getMaxIteraciones() {
        return maxIteraciones;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setMaxIteraciones(int maxIteraciones) {
        this.maxIteraciones = maxIteraciones;
    }

    public int getEvaluaciones() {
        return funcion.getContadorEvaluaciones();
    }

}
