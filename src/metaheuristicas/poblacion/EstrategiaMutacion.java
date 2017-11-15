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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;
import tweaks.Tweak;
import tweaks.Tweak_1;
import tweaks.Tweak_B_HC;
import tweaks.Tweak_Direccional;
import tweaks.Tweak_Explotacion;
import tweaks.Tweak_GeneraNuevo;

/**
 *
 * @author debian
 */
public class EstrategiaMutacion  extends Estrategia {

    Tweak tweak;
    Random rand;
    double ancho = 0.5;

    public EstrategiaMutacion(int tamPoblacion) {
        super(tamPoblacion, 2);
        setNombreEstrategia("SoloMutacion");
    }

    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo,Poblacion poblacion, Random rand) {
        this.rand = rand;
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        for (Punto punto : poblacion) {
            genDescendientes(punto, nuevaGeneracion);
        }
        poblacion = mezclar(poblacion, nuevaGeneracion);
        return poblacion;
    }

    private Poblacion mezclar(Poblacion poblacion, Poblacion genDescendientes) {
        Poblacion pob = poblacion;
        for (Punto descen : genDescendientes) {
            poblacion.add(descen);
        }
        return pob;
    }

    private void genDescendientes(Punto padre, Poblacion nuevaGeneracion) {
        for (int i = 0; i < getNumDescendientes(); i++) {
            nuevaGeneracion.add(mutar(padre));
        }
    }

    public Punto mutar(Punto punto) {
        tweak = new Tweak_Explotacion(ancho);
        return tweak.tweak(punto, rand);
    }
}
