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
public class Random_Search extends AlgoritmoMetaheuristico {


    /**
     * Busqueda aleatoria (Random search)
     *
     */
    public Random_Search() {
        super("RANDOM SEARCH");
    }

//    /**
//     * subiendola colina por maxima pendiente con (o sin) reemplazo, se acuerdo
//     * a los parmetros
//     *
//     * @param paso
//     * @param numSucesores
//     * @param reemplazo
//     */
//    public RandomSearch(double paso, int numSucesores, boolean reemplazo) {
//        super("HC MAXIMA PENDIENTE");
//    }

    @Override
    public Punto ejecutar(Punto inicial, int iteraciones, Collection listaPuntosS) {
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            r = this.generarPunto();
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

}
