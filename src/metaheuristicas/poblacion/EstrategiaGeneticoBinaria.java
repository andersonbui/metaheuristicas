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

import funciones.Funcion;
import java.util.Random;
import metaheuristicas.Punto;
import metaheuristicas.poblacion.cruce.Cruce;
import metaheuristicas.poblacion.mutacion.MultiBit;

/**
 *
 * @author debian
 */
public class EstrategiaGeneticoBinaria extends Estrategia {

    MultiBit mutacion;
    double ancho = 1;
    int[] mascaraCruce;
    private final int numPadres = 2;

    public EstrategiaGeneticoBinaria(int tamPoblacion) {
        super(tamPoblacion, 2);
        setNombreEstrategia("EstrategiaGeneticaBinaria");
    }

    /**
     * genera hijos a partir de un padre y una madre y compiten en calidad entre
     * todos.
     *
     * @param numIndividuosElitismo
     * @param poblacion
     * @param rand
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo, Poblacion poblacion, Random rand) {
        if (mascaraCruce == null) {
            int dimension = poblacion.getFuncion().getDimension();
            mascaraCruce = Cruce.mascaraDosPuntos(dimension / 2, dimension); // cruce unpunto  = dimension/2
        }
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        while (!poblacion.isEmpty()) {
            Punto[] padres = seleccionPadreMadre(poblacion);
            genDescendientes(padres[0], padres[1], nuevaGeneracion, rand);
        }
        return nuevaGeneracion;
    }

    public Punto[] seleccionPadreMadre(Poblacion poblacion) {
        Punto[] padres = new Punto[numPadres];
        padres[0] = poblacion.remove(0);
        if (!poblacion.isEmpty()) {
            padres[1] = poblacion.remove(0);
        } else {
            padres[1] = padres[0];
        }

        return padres;
    }

    private void genDescendientes(Punto padre, Punto madre, Poblacion nuevaGeneracion, Random rand) {
        Punto[] hijos = Cruce.cruzar(padre, madre, mascaraCruce);
        for (Punto hijo : hijos) {
            mutar(hijo, rand);
            nuevaGeneracion.add(hijo);
        }
    }

    private Punto mutar(Punto punto, Random rand) {
        mutacion = new MultiBit();
        return mutacion.mutar(punto, rand, 0.2);
    }

    @Override
    public Poblacion generarPoblacion(Funcion funcion, Random rand) {
        Poblacion poblacion = new Poblacion(funcion, getTamPoblacion());
        Punto p;
        for (int i = 0; i < getTamPoblacion(); i++) {
            p = funcion.generarPunto(rand);
            poblacion.add(p);
        }
        return poblacion;
    }
}
