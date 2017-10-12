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
public class Hill_Climbing_direccional extends Hill_Climbing {
    
    public Hill_Climbing_direccional(AlgoritmoMetaheuristico tweak) {
        super(tweak);
        nombre = "HC MAXIMA H_ORTOGONALES";
    }
    
}
