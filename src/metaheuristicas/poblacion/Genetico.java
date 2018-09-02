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

import main.Algebraicas.FuncionAlgebraica;
import metaheuristicas.IndividuoGen;
import metaheuristicas.poblacion.cruce.Cruce;
import metaheuristicas.poblacion.mutacion.MultiGen;

/**
 *
 * @author debian
 */
public class Genetico extends AlgoritmoEvolutivo {

    MultiGen mutacion;
    double ancho = 0.5;
    int[] mascaraCruce;

    private final int numPadres = 2;

    public Genetico(int tamPoblacion) {
        super(tamPoblacion, 2);
        nombre = "E_Genetica";
    }

    /**
     * genera hijos a partir de un padre y una madre y compiten en calidad entre
     * todos.
     *
     * @param numIndividuosElitismo
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo) {
        if (mascaraCruce == null) {
            int dimension = poblacion.getFuncion().getDimension();
            mascaraCruce = Cruce.mascaraDosPuntos(dimension / 2, dimension); // cruce unpunto  = dimension/2
        }
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        while (!poblacion.isEmpty()) {
            IndividuoGen[] padres = seleccionPadreMadre(poblacion);
            genDescendientes(padres[0], padres[1], nuevaGeneracion);
        }
        return nuevaGeneracion;
    }

    public IndividuoGen[] seleccionPadreMadre(Poblacion poblacion) {
        IndividuoGen[] padres = new IndividuoGen[numPadres];
        padres[0] = poblacion.remove(0);
        if (!poblacion.isEmpty()) {
            padres[1] = poblacion.remove(0);
        } else {
            padres[1] = padres[0];
        }

        return padres;
    }

    private void genDescendientes(IndividuoGen padre, IndividuoGen madre, Poblacion nuevaGeneracion) {
        IndividuoGen[] hijos = Cruce.cruzar(padre, madre, mascaraCruce);
        for (IndividuoGen hijo : hijos) {
            mutar(hijo);
            hijo.evaluar();
            nuevaGeneracion.add(hijo);
        }

//        nuevaGeneracion.add(madre);
//        nuevaGeneracion.add(padre);
    }

    private IndividuoGen mutar(IndividuoGen punto) {
        mutacion = new MultiGen(ancho);
        FuncionAlgebraica funcion =(FuncionAlgebraica) punto.getFuncion();
        return mutacion.mutar(punto, funcion.getLimite(), 0.2, true);
    }

}
