package metaheuristicas.movimiento;

import metaheuristicas.funcion.Funcion;
import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

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
    public Individuo tweak(Individuo punto) {

        Individuo nuevop = (Individuo) punto.clone();
        Funcion funcion = punto.getFuncion();
        double[] valores = nuevop.getValores();
        // improve
        int index = Aleatorio.nextInt(valores.length);
        valores[index] = genValor(valores[index], bw);
        // end improve
        for (int i = 0; i < valores.length; i++) {
            if (Aleatorio.nextDouble() < beta) {
                valores[i] = genValor(0, funcion.getLimite());
            }
        }
        nuevop.evaluar();
        return nuevop;
    }

}
