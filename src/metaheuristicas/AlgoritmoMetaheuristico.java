package metaheuristicas;

import funciones.Funcion;
import java.util.List;
import java.util.Random;

/**
 *
 * @author debian
 */
public abstract class AlgoritmoMetaheuristico {

    protected int iteraciones;
    protected String nombre;

    public AlgoritmoMetaheuristico(String nombre) {
        this.nombre = nombre;
        iteraciones = 1;
    }

    public AlgoritmoMetaheuristico() {
        this.nombre = "";
        iteraciones = 1;
    }

    public abstract List<Punto> ejecutar(Random rand, Funcion funcion);

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

}
