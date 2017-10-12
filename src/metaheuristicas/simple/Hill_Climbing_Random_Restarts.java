/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas.simple;

import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class Hill_Climbing_Random_Restarts extends Hill_Climbing {

    /**
     * Busqueda aleatoria (Random search)
     *
     * @param tweak
     * @param iteraciones_HC_Interno
     */
    public Hill_Climbing_Random_Restarts(AlgoritmoMetaheuristico tweak, int iteraciones_HC_Interno) {
        super(null);
        // Hill climbing anidado
        AlgoritmoMetaheuristico am = new Hill_Climbing(tweak);
        this.tweak = am;
        nombre = "HC-RANDOM-RESTARTS";
        am.setIteraciones(iteraciones_HC_Interno);
    }

}
