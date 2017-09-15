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
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    double paso;
    int numSucesores;
    final private boolean reemplazo;

    /**
     * suiendo la colina (simple)
     *
     * @param paso
     */
    public Hill_Climbing(double paso) {
        super("SUBIENDO LA COLINA");
        this.paso = paso;
        numSucesores = 1;
        reemplazo = false;
    }

    /**
     * subiendola colina por maxima pendiente con (o sin) reemplazo, se acuerdo
     * a los parmetros
     *
     * @param paso
     * @param numSucesores
     * @param reemplazo
     */
    public Hill_Climbing(double paso, int numSucesores, boolean reemplazo) {
        super("SAHC MAXIMA PENDIENTE");
        this.paso = paso;
        this.numSucesores = numSucesores > 0 ? numSucesores : 1;
        this.reemplazo = reemplazo;
        if (reemplazo) {
            this.nombre = "SAHC-WR CON REEMPLAZO";
        }
    }

    @Override
    public Punto ejecutar(Punto inicial,int iteraciones, Collection listaPuntosS) {
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r = null;
        Punto r_aux;
        Punto mejor = s;
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < numSucesores; j++) { // generador candidatos a sucesor
                r_aux = tweak(s, getPaso());
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
            if (listaPuntosS != null) {
                listaPuntosS.add(mejor);
            }
        }
        return s;
    }

    public double getPaso() {
        return paso;
    }

    public void setPaso(double paso) {
        this.paso = paso;
    }
    
}
