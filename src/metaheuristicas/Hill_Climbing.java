/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import funciones.Funcion;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author debian
 */
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    double paso;
    int numSucesores;
    private boolean reemplazo;

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

//
//    /**
//     * subiendola colina por maxima pendiente
//     *
//     * @param paso
//     * @param numSucesores
//     */
//    public Hill_Climbing(double paso, int numSucesores) {
//        super("HC CON MAXIMA PENDIENTE");
//        this.paso = paso;
//        this.numSucesores = numSucesores > 0 ? numSucesores : 1;
//    }
//    
    /**
     * subiendola colina por maxima pendiente con(o sin reemplazo) reemplazo
     *
     * @param paso
     * @param numSucesores
     */
    public Hill_Climbing(double paso, int numSucesores, boolean reemplazo) {
        super("HC MAXIMA PENDIENTE");
        this.paso = paso;
        this.numSucesores = numSucesores > 0 ? numSucesores : 1;
        this.reemplazo = reemplazo;
        if (reemplazo) {
            this.nombre = "HC-MP CON REEMPLAZO";
        }
    }
//    @Override
//    public Punto ejecutar(long semilla, int iteraciones, Collection listaPuntosS) {
//        Punto s = this.generarPunto(semilla);
//        s.setCalidad(funcion.evaluar(s));
//        if (listaPuntosS != null) {
//            listaPuntosS.add(s);
//        }
//        Punto r = null;
//        Punto r_aux;
//        int index_numSucesores = numSucesores;
//        for (int i = 0; i < iteraciones; i++) {
//            for (int j = 0; j < numSucesores; j++) { // generador candidatos a sucesor
//                r_aux = tweak(s, i * j, paso);
//                r_aux.setCalidad(funcion.evaluar(r_aux));
//                if (r == null || r_aux.compareTo(r) > 0) {
//                    r = r_aux;
//                }
//            }
//            if (r != null && r.compareTo(s) > 0) {
//                s = r;
//            }
//            if (listaPuntosS != null) {
//                listaPuntosS.add(s);
//            }
//        }
//        return s;
//    }

    @Override
    public Punto ejecutar(long semilla, int iteraciones, Collection listaPuntosS) {
        Punto s = this.generarPunto(semilla);
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r = null;
        Punto r_aux;
        Punto mejor = s;
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < numSucesores; j++) { // generador candidatos a sucesor
                r_aux = tweak(s, i * j*(semilla), paso);
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

}
