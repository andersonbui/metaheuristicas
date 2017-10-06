/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas.simple;

import java.util.ArrayList;
import java.util.List;
import tweaks.Tweak;

/**
 *
 * @author debian
 */
public class B_Hill_Climbing extends Hill_Climbing {

    /**
     * suiendo la colina (simple) beta = 0.5
     * @param tweak
     */
    public B_Hill_Climbing(Tweak tweak) {
        super();
        nombre = "B_HC";
        this.tweak = tweak;
    }
}
