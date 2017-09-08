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
public class Hill_Climbing_Random_Restarts extends AlgoritmoMetaheuristico {

    private final int iteraciones_HC_Interno;
    private final double paso;

    /**
     * Busqueda aleatoria (Random search)
     *
     * @param iteraciones_HC_Interno
     * @param paso
     */
    public Hill_Climbing_Random_Restarts(int iteraciones_HC_Interno, double paso) {
        super("HC-RANDOM-RESTARTS");
        this.iteraciones_HC_Interno = iteraciones_HC_Interno;
        this.paso = paso;
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
    public Punto ejecutar(Punto inicial,int iteraciones, Collection listaPuntosS) {
        Punto best = inicial;
        best.setCalidad(funcion.evaluar(best));
        if (listaPuntosS != null) {
            listaPuntosS.add(best);
        }
        Punto s = best;
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < iteraciones_HC_Interno; j++) {
                r = tweak(s, paso);
                r.setCalidad(funcion.evaluar(r));
                if (r.compareTo(s) > 0) {
                    s = r;
                }
            }
//            s.setCalidad(funcion.evaluar(s));
            if (s.compareTo(best) > 0) {
                best = s;
            }
            if (listaPuntosS != null) {
                listaPuntosS.add(best);
            }
            s = this.generarPunto();
            s.setCalidad(funcion.evaluar(s));
        }
        return best;
    }

}
