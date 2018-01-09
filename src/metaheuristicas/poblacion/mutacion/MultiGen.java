package metaheuristicas.poblacion.mutacion;

import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class MultiGen {

    double ancho;

    public MultiGen(double ancho) {
        this.ancho = ancho;
    }

    public Individuo mutar(Individuo punto, double limite, double probabilidad, boolean conBase) {
        double valor;
        for (int i = 0; i < punto.getDimension(); i++) {
            if (probabilidad > Aleatorio.nextDouble()) {
                valor = (conBase ? punto.getValor(i) : 0) + Aleatorio.nextDouble() * limite * 2 - limite;
                punto.set(i, valor);
            }
        }
        return punto;
    }
}
