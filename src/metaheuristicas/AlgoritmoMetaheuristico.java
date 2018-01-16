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

    public AlgoritmoMetaheuristico(String nombre) {
        this.nombre = nombre;
    }

    public abstract List<Individuo> ejecutar(Funcion funcion);

    public void siguiente() {

    }

    public boolean haySiguiente() {
        return false;
    }
    
    public void reiniciar(){
        
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

    public int getMaxIteraciones() {
        return maxIteraciones;
    }

    public void setMaxIteraciones(int maxIteraciones) {
        this.maxIteraciones = maxIteraciones;
    }

}
