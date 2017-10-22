package metaheuristicas.poblacion.mutacion;

import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class MultiGen {

    double ancho;

    public MultiGen(double ancho) {
        this.ancho = ancho;
    }

    public Punto mutar(Punto punto, Random rand, double limite, double probabilidad, boolean conBase) {
        double valor;
        for (int i = 0; i < punto.getDimension(); i++) {
            if (probabilidad > rand.nextDouble()) {
                valor = (conBase ? punto.getValor(i) : 0) + rand.nextDouble() * limite * 2 - limite;
                punto.set(i, valor);
            }
        }
        punto.evaluar();
        return punto;
    }
}
