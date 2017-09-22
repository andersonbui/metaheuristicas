/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tweaks.Tweak;

/**
 *
 * @author debian
 */
public class Simulated_Annealing extends AlgoritmoMetaheuristico {

    /**
     * suiendo la colina temple simulado
     *
     * @param tweak
     */
    public Simulated_Annealing(Tweak tweak) {
        super("TEMPLE SIMULADO");
        this.tweak = tweak;
    }

    @Override
    public List<Punto> ejecutar(Punto inicial, double paso) {
        List<Punto> listaPuntosS = new ArrayList();
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        listaPuntosS.add(s);
        Punto r;
        double temperatura = 100;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak(s, paso);
            r.setCalidad(funcion.evaluar(r));

            if (r.compareTo(s) > 0 || Math.random() < Math.exp(s.getCalidad() - r.getCalidad() / temperatura)) {
//            if (r.compareTo(s) > 0 || 1 > Math.exp((r.getCalidad() - s.getCalidad()) / temperatura)) {
                s = r;
            }
//            temperatura = Math.sqrt(temperatura);
            temperatura = temperatura / 2;
            listaPuntosS.add(s);
        }
        return listaPuntosS;
    }
}
