package metaheuristicas;

import metaheuristicas.funcion.FuncionGen;
import java.util.List;

/**
 *
 * @author debian
 * @param <Funcion>
 * @param <Individuo>
 */
public abstract class AlgoritmoMetaheuristico<Funcion extends FuncionGen, Individuo extends IndividuoGen> {

    protected int iteraciones;
    protected int maxIteraciones;
    protected String nombre;
    protected Funcion funcion;

    public AlgoritmoMetaheuristico() {
        this.nombre = "";
        iteraciones = 0;
    }

    public abstract List<Individuo> ejecutar();

    public void addNombre(String nombrecito){
        this.nombre+=nombrecito;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void renovar() {
        iteraciones = 0;
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
