/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas.simple;

import metaheuristicas.funcion.FuncionGen;
import metaheuristicas.IndividuoGen;
import metaheuristicas.AlgoritmoMetaheuristico;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.IndividuoGen;
import metaheuristicas.movimiento.Tweak;
import metaheuristicas.movimiento.Tweak_1;

/**
 *
 * @author debian
 */
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    Tweak[] tweaks;
    int contadorTweak;
    Tweak tweak;

    /**
     * suiendo la colina (simple)
     *
     * @param ancho
     */
    public Hill_Climbing(double ancho) {
        super();
        nombre = "HC";
        this.tweaks = new Tweak[]{new Tweak_1(1.5), new Tweak_1(1), new Tweak_1(4), new Tweak_1(0.6)};
        contadorTweak = 0;
        tweak = tweaks[contadorTweak];
    }

    @Override
    public List<IndividuoGen> ejecutar() {
        List<IndividuoGen> optimos = new ArrayList();
        IndividuoGen s = funcion.generarIndividuo();
        s.evaluar();
        optimos.add(s);
        IndividuoGen r;
        for (iteraciones = 0; iteraciones < maxIteraciones && !funcion.suficiente(s); iteraciones++) {
            r = tweak.tweak(s);
            r.evaluar();
            if (r.compareTo(s) > 0) {
                s = r;
            }
            optimos.add(s);
        }
        return optimos;
    }
}
