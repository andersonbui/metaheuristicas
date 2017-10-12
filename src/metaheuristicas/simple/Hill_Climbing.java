/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas.simple;

import metaheuristicas.Punto;
import metaheuristicas.AlgoritmoMetaheuristico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author debian
 */
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    /**
     * suiendo la colina (simple)
     *
     * @param tweak
     */
    public Hill_Climbing(AlgoritmoMetaheuristico tweak) {
        super("SUBIENDO LA COLINA");
        this.tweak = tweak;
    }

    @Override
    public List<Punto> ejecutar(Punto inicial) {
        List<Punto> listaPuntosS = new ArrayList();
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        listaPuntosS.add(s);
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak(s);
            r.setCalidad(funcion.evaluar(r));
            if (r.compareTo(s) > 0) {
                s = r;
            }
            listaPuntosS.add(s);
        }
        return listaPuntosS;
    }
}
