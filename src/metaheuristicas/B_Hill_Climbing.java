/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import java.util.Collection;
import java.util.Random;

/**
 *
 * @author debian
 */
public class B_Hill_Climbing extends AlgoritmoMetaheuristico {

    double paso;
    int numSucesores;
    double beta = 0.5;
    double bw = 0.6;

    /**
     * suiendo la colina (simple) beta = 0.5
     *
     * @param paso
     */
    public B_Hill_Climbing(double paso) {
        super("B_HC");
        this.paso = paso;
        numSucesores = 1;
    }

    @Override
    public Punto ejecutar(Punto inicial, int iteraciones, Collection listaPuntosS) {
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak(s, paso);
            r.setCalidad(funcion.evaluar(r));
            if (r.compareTo(s) > 0) {
                s = r;
            }
            if (listaPuntosS != null) {
                listaPuntosS.add(s);
            }
        }
        return s;
    }

    @Override
    public Punto tweak(Punto punto, double paso) {

        Punto nuevop = (Punto) punto.clone();
        double[] valores = nuevop.getValores();
        // improve
        int index = rand.nextInt(valores.length);
        valores[index] = tweaki(valores[index], bw);
        // end improve
        for (int i = 0; i < valores.length; i++) {
            if (rand.nextDouble() < beta) {
                valores[i] = tweaki(0, funcion.getLimite());
            }
        }
        return nuevop;
    }
}
