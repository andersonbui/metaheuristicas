/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas.simple;

import tweaks.Tweak;

/**
 *
 * @author debian
 */
public class Hill_Climbing_direccional extends Hill_Climbing {
    
    public Hill_Climbing_direccional(Tweak tweak) {
        super();
        nombre = "HC MAXIMA H_ORTOGONALES";
        this.tweak = tweak;
    }
    
}
