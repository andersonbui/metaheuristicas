/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas.simple;

import funciones.Funcion;
import metaheuristicas.Punto;
import metaheuristicas.AlgoritmoMetaheuristico;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tweaks.Tweak;
import tweaks.Tweak_1;

/**
 *
 * @author debian
 */
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    Tweak tweak;

    /**
     * suiendo la colina (simple)
     *
     * @param ancho
     */
    public Hill_Climbing(double ancho) {
        super("SUBIENDO LA COLINA");
        this.tweak = new Tweak_1(ancho);
    }

    @Override
    public List<Punto> ejecutar(Random rand, Funcion funcion) {
        List<Punto> listaPuntosS = new ArrayList();
        Punto s = Punto.generar(funcion, rand);
        s.setCalidad(funcion.evaluar(s));
        listaPuntosS.add(s);
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak.tweak(s, rand);
            r.setCalidad(funcion.evaluar(r));
            if (r.compareTo(s) > 0) {
                s = r;
            }
            listaPuntosS.add(s);
        }
        return listaPuntosS;
    }
}
