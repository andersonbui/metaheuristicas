package metaheuristicas;

import metaheuristicas.funcion.Funcion;
import java.util.List;

/**
 *
 * @author debian
 * @param <FuncionDef>
 */
public abstract class AlgoritmoMetaheuristico<FuncionDef extends Funcion> {

    protected int iteraciones;
    protected int maxIteraciones;
    protected String nombre;
    protected FuncionDef funcion;

    public AlgoritmoMetaheuristico() {
        this.nombre = "";
        iteraciones = 0;
    }

    public abstract List<Individuo> ejecutar();

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

    public void setFuncion(FuncionDef funcion) {
        this.funcion = funcion;
    }

    public int getMaxIteraciones() {
        return maxIteraciones;
    }

    public FuncionDef getFuncion() {
        return funcion;
    }

    public void setMaxIteraciones(int maxIteraciones) {
        this.maxIteraciones = maxIteraciones;
    }

    public int getEvaluaciones() {
        return funcion.getContadorEvaluaciones();
    }

}
