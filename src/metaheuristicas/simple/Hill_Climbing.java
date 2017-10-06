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
import tweaks.Tweak;

/**
 *
 * @author debian
 */
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    int numSucesores;
    final private boolean reemplazo;

    /**
     * suiendo la colina (simple)
     *
     */
    public Hill_Climbing() {
        super("SUBIENDO LA COLINA");
        numSucesores = 1;
        this.tweak = tweak;
        reemplazo = false;
    }

    public Hill_Climbing(Tweak tweak) {
        super("SUBIENDO LA COLINA");
        numSucesores = 1;
        this.tweak = tweak;
        reemplazo = false;
    }

    /**
     * subiendola colina por maxima pendiente con (o sin) reemplazo, se acuerdo
     * a los parmetros
     *
     * @param tweak
     * @param numSucesores
     * @param reemplazo
     */
    public Hill_Climbing(Tweak tweak, int numSucesores, boolean reemplazo) {
        super("SAHC MAXIMA PENDIENTE");
        this.numSucesores = numSucesores > 0 ? numSucesores : 1;
        this.reemplazo = reemplazo;
        this.tweak = tweak;
        if (reemplazo) {
            this.nombre = "SAHC-WR CON REEMPLAZO";
        }
    }

    @Override
    public List<Punto> ejecutar(Punto inicial, double paso) {
        List<Punto> listaPuntosS = new ArrayList();
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        listaPuntosS.add(s);
        Punto r = null;
        Punto r_aux;
        Punto mejor = s;
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < numSucesores; j++) { // generador candidatos a sucesor
                r_aux = tweak(s, paso);
                r_aux.setCalidad(funcion.evaluar(r_aux));
                if (r == null || r_aux.compareTo(r) > 0) {
                    r = r_aux;
                }
            }
            s = r;
            if (s != null && s.compareTo(mejor) > 0) {
                mejor = r;
            }
            if (!reemplazo) {
                s = mejor;
            }
            listaPuntosS.add(mejor);
        }
        return listaPuntosS;
    }

}
