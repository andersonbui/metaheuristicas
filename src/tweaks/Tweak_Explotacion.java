package tweaks;

import funciones.Funcion;
import java.util.Random;
import metaheuristicas.Punto;

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
    public Punto tweak(Punto punto, Random rand) {
        Funcion funcion = punto.getFuncion();
        Punto nuevo = (Punto) punto.clone();
        double[] valores = nuevo.getValores();
        // improve
        int posicion = rand.nextInt(valores.length);
        valores[posicion] = funcion.limitar(genValor(valores[posicion], ancho, rand));
        nuevo.evaluar();
        return nuevo;
    }
}
