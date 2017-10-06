package metaheuristicas;

import funciones.Funcion;
import java.util.List;
import java.util.Random;
import tweaks.Tweak;

/**
 *
 * @author debian
 */
public abstract class AlgoritmoMetaheuristico extends Tweak {

    protected int iteraciones;
    protected String nombre;
    protected Tweak tweak;

    public AlgoritmoMetaheuristico(String nombre) {
        super();
        this.nombre = nombre;
        iteraciones = 1;
    }

    public Punto tweak(Punto punto, double paso) {
        tweak.setRand(rand);
        List<Punto> p_nuevo = tweak.ejecutar(punto, paso);
        return funcion.limitar(p_nuevo.get(p_nuevo.size() - 1));
    }

    @Override
    public abstract List<Punto> ejecutar(Punto p, double paso);

    public String getNombre() {
        return nombre;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public Tweak getTweak() {
        return tweak;
    }

    public void setTweak(Tweak tweak) {
        this.tweak = tweak;
        this.tweak.setRand(rand);
    }

    @Override
    public void setFuncion(Funcion funcion) {
        super.setFuncion(funcion);
        tweak.setFuncion(funcion);
    }

    @Override
    public void setRand(Random rand) {
        tweak.setRand(rand);
        super.setRand(rand);
    }

}
