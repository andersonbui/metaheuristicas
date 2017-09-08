/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import java.util.Collection;

/**
 *
 * @author debian
 */
public class Simulated_Annealing extends AlgoritmoMetaheuristico {

    double paso;
    int numSucesores;
    final private boolean reemplazo;

    /**
     * suiendo la colina temple simulado
     *
     * @param paso
     */
    public Simulated_Annealing(double paso) {
        super("TEMPLE SIMULADO");
        this.paso = paso;
        numSucesores = 1;
        reemplazo = false;
    }

    @Override
    public Punto ejecutar(Punto inicial, int iteraciones, Collection listaPuntosS) {
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r;
        double temperatura = 100;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak(s, paso);
            r.setCalidad(funcion.evaluar(r));
//            if (r.compareTo(s) > 0 || Math.random() < Math.exp((r.getCalidad() - s.getCalidad()) / temperatura)) {
            if (r.compareTo(s) > 0 || 1 > Math.exp((r.getCalidad() - s.getCalidad()) / temperatura)) {
                s = r;
            }
//            temperatura = Math.sqrt(temperatura);
            temperatura = temperatura/2;
            if (listaPuntosS != null) {
                listaPuntosS.add(s);
            }
        }
        return s;
    }
//
//    @Override
//    public Punto tweak(Punto punto, double paso) {
//        return punto;
//    }

}
