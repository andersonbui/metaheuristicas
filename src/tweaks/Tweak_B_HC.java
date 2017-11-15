package tweaks;

import funciones.Funcion;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Tweak_B_HC extends Tweak {

    double paso;
    double beta = 0.5;
    double bw = 0.6;

    double ancho;

    public Tweak_B_HC(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public Punto tweak(Punto punto, Random rand) {

        Punto nuevop = (Punto) punto.clone();
        Funcion funcion = punto.getFuncion();
        double[] valores = nuevop.getValores();
        // improve
        int index = rand.nextInt(valores.length);
        valores[index] = genValor(valores[index], bw, rand);
        // end improve
        for (int i = 0; i < valores.length; i++) {
            if (rand.nextDouble() < beta) {
                valores[i] = genValor(0, funcion.getLimite(), rand);
            }
        }
        nuevop.evaluar();
        return nuevop;
    }

}
