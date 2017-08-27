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

    public Hill_Climbing(Funcion funcion, double paso) {
        super(funcion, "SUBIENDO LA COLINA");
        this.paso = paso;
        numSucesores = 1;
    }

    public Hill_Climbing(Funcion funcion, double paso, int numSucesores) {
        super(funcion, "SUBIENDO LA COLINA CON MAXIMA PENDIENTE");
        this.paso = paso;
        this.numSucesores = numSucesores > 0 ? numSucesores : 1;
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
        int index_numSucesores = numSucesores;
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < numSucesores; j++) { // generador candidatos a sucesor
                r_aux = tweak(s, i * j, paso);
                r_aux.setCalidad(funcion.evaluar(r_aux));
                if (r == null || r_aux.compareTo(r) > 0) {
                    r = r_aux;
                }
            }
            if (r != null && r.compareTo(s) > 0) {
                s = r;
            }
            if (listaPuntosS != null) {
                listaPuntosS.add(s);
            }
        }
        return s;
    }

}
