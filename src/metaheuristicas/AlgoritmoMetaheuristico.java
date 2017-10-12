package metaheuristicas;

import funciones.Funcion;
import java.util.List;
import java.util.Random;

/**
 *
 * @author debian
 */
public abstract class AlgoritmoMetaheuristico {

    protected Random rand;
    protected Funcion funcion;
    protected int iteraciones;
    protected String nombre;
    protected AlgoritmoMetaheuristico tweak;

    public AlgoritmoMetaheuristico(String nombre) {
        this.nombre = nombre;
        iteraciones = 1;
    }

    public AlgoritmoMetaheuristico() {
        this.nombre = "";
        iteraciones = 1;
    }

    public Punto tweak(Punto punto) {
        tweak.setRand(rand);
        List<Punto> p_nuevo = tweak.ejecutar(punto);
        return funcion.limitar(p_nuevo.get(p_nuevo.size() - 1));
    }

    public abstract List<Punto> ejecutar(Punto p);

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

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
        if (tweak != null) {
            tweak.setFuncion(funcion);
        }
    }

    public AlgoritmoMetaheuristico getTweak() {
        return tweak;
    }

    public void setTweak(AlgoritmoMetaheuristico tweak) {
        this.tweak = tweak;
        if (tweak != null) {
            this.tweak.setRand(rand);
        }
    }

    /**
     * variacion de un i entre [-ancho, ancho]
     *
     * @param i
     * @param ancho
     * @return
     */
    public double tweaki(double i, double ancho) {
        double nuevop = funcion.limitar(i + rand.nextDouble() * ancho * 2 - ancho);
        return nuevop;
    }
}
