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
import metaheuristicas.movimiento.Tweak;
import metaheuristicas.movimiento.Tweak_Explotacion;

/**
 *
 * @author debian
 */
public class Mutacion extends AlgoritmoEvolutivo {

    Tweak tweak;
    double ancho = 0.5;

    public Mutacion(int tamPoblacion) {
        super(tamPoblacion, 2);
        nombre = "SoloMutacion";
    }

    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo) {
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        poblacion.forEach((punto) -> {
            genDescendientes(punto, nuevaGeneracion);
        });
        poblacion = mezclar(poblacion, nuevaGeneracion);
        return poblacion;
    }

    private Poblacion mezclar(Poblacion poblacion, Poblacion genDescendientes) {
        Poblacion pob = poblacion;
        genDescendientes.forEach((descen) -> {
            poblacion.add(descen);
        });
        return pob;
    }

    private void genDescendientes(Individuo padre, Poblacion nuevaGeneracion) {
        for (int i = 0; i < numDescendientes; i++) {
            nuevaGeneracion.add(mutar(padre));
        }
    }

    public Individuo mutar(Individuo punto) {
        tweak = new Tweak_Explotacion(ancho);
        return tweak.tweak(punto);
    }
}
