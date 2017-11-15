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
package metaheuristicas.poblacion.seleccion;

import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Torneo implements Seleccion {

    private final int numParticipantes;

    /**
     * Torneo selecciona el individuo mas apto de entre numParticipantes
     * escogidos al azar de una poblacion.
     *
     * @param numParticipantes
     */
    public Torneo(int numParticipantes) {
        this.numParticipantes = numParticipantes;

    }

    /**
     * selecciona el individuo mas apto de entre numParticipantes escogidos al
     * azar de poblacion, luego son removidos todos de esta.
     *
     * @param poblacion
     * @param rand
     * @return el individuo mas apto de entre los numParticipantes escodigos.
     */
    @Override
    public Punto seleccionar(List<Punto> poblacion, Random rand) {
        //Los cromosomas con mayor valor de aptitud tendran mayor probabilidad de ser seleccionados
        Punto escogido;
        Punto masApto = null;
        int posicion;
        for (int j = 0; j < numParticipantes; j++) {
            posicion = rand.nextInt(numParticipantes);
            escogido = poblacion.remove(posicion);
            if (masApto == null || masApto.compareTo(escogido) < 0) {
                masApto = escogido;
            }
        }
        return masApto;
    }

}
