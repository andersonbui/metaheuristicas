package metaheuristicas.movimiento;

import metaheuristicas.Funcion;
import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Tweak_Explotacion extends Tweak {

    double ancho;

    public Tweak_Explotacion(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public Individuo tweak(Individuo punto) {
        Individuo nuevo = (Individuo) punto.clone();
        // improve
        int posicion = Aleatorio.nextInt(nuevo.getDimension());
        nuevo.set(posicion, genValor(nuevo.get(posicion), ancho));
        nuevo.evaluar();
        return nuevo;
    }
}
