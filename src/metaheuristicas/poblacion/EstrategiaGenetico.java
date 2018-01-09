/*
 * Copyright (C) 2017 debian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package metaheuristicas.poblacion;

import metaheuristicas.Individuo;
import metaheuristicas.poblacion.cruce.Cruce;
import metaheuristicas.poblacion.mutacion.MultiGen;

/**
 *
 * @author debian
 */
public class EstrategiaGenetico extends Estrategia {

    MultiGen mutacion;
    double ancho = 0.5;
    int[] mascaraCruce;
    boolean competirPadres;

    private final int numPadres = 2;

    public EstrategiaGenetico(int tamPoblacion) {
        super(tamPoblacion, 2, "E_Genetica");
        competirPadres = false;
    }

    /**
     * genera hijos a partir de un padre y una madre y compiten en calidad entre
     * todos.
     *
     * @param numIndividuosElitismo
     * @param poblacion
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo, Poblacion poblacion) {
        if (mascaraCruce == null) {
            int dimension = poblacion.getFuncion().getDimension();
            mascaraCruce = Cruce.mascaraDosPuntos(dimension / 2, dimension); // cruce unpunto  = dimension/2
        }
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        while (!poblacion.isEmpty()) {
            Individuo[] padres = seleccionPadreMadre(poblacion);
            genDescendientes(padres[0], padres[1], nuevaGeneracion);
        }
        return nuevaGeneracion;
    }

    public Individuo[] seleccionPadreMadre(Poblacion poblacion) {
        Individuo[] padres = new Individuo[numPadres];
        padres[0] = poblacion.remove(0);
        if (!poblacion.isEmpty()) {
            padres[1] = poblacion.remove(0);
        } else {
            padres[1] = padres[0];
        }

        return padres;
    }

    private void genDescendientes(Individuo padre, Individuo madre, Poblacion nuevaGeneracion) {
        Individuo[] hijos = Cruce.cruzar(padre, madre, mascaraCruce);
        for (Individuo hijo : hijos) {
            mutar(hijo);
            hijo.evaluar();
            nuevaGeneracion.add(hijo);
        }
        if (competirPadres) {
            nuevaGeneracion.add(madre);
            nuevaGeneracion.add(padre);
        }
    }

    private Individuo mutar(Individuo punto) {
        mutacion = new MultiGen(ancho);
        return mutacion.mutar(punto, punto.getFuncion().getLimite(), 0.2, true);
    }

    @Override
    public boolean haySiguiente() {
        setNombreEstrategia("E_Genetica_padres");
        return !competirPadres;
    }

    @Override
    public void siguiente() {
        competirPadres = true;
    }

    @Override
    public void reiniciar() {
        setNombreEstrategia("E_Genetica");
        competirPadres = false;
    }

}
