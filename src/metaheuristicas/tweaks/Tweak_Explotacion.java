package metaheuristicas.tweaks;

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
        Funcion funcion = punto.getFuncion();
        Individuo nuevo = (Individuo) punto.clone();
        double[] valores = nuevo.getValores();
        // improve
        int posicion = Aleatorio.nextInt(valores.length);
        valores[posicion] = funcion.limitar(genValor(valores[posicion], ancho));
        nuevo.evaluar();
        return nuevo;
    }
}
