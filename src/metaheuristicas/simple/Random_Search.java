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
public class Random_Search extends Hill_Climbing {

    /**
     * Busqueda aleatoria (Random search)
     *
     * @param tweak
     */
    public Random_Search(Tweak tweak) {
        super();
        nombre = "RANDOM SEARCH";
        this.tweak = tweak;
    }
}
