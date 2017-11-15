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
import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;
import tweaks.Tweak;
import tweaks.Tweak_Explotacion;

/**
 *
 * @author debian
 */
public class EstrategiaMLamda extends Estrategia {

    Tweak tweak;
    Random rand;
    double ancho = 0.5;

    public EstrategiaMLamda(int miu, int lamda) {
        super(lamda, miu);
        setNombreEstrategia("MiuLamda");
    }

    public int getMiu() {
        return getNumDescendientes();
    }

    public int getLamda() {
        return getTamPoblacion();
    }

    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo, Poblacion poblacion, Random rand) {
        this.rand = rand;
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        List<Punto> padresEscogidos = escogerPadres(getMiu(), poblacion);
        genDescendientes(padresEscogidos, nuevaGeneracion);
        return nuevaGeneracion;
    }

    private Poblacion mezclar(Poblacion poblacion, List<Punto> genDescendientes) {
        Poblacion pob = poblacion;
        for (Punto descen : genDescendientes) {
            poblacion.add(descen);
        }
        return pob;
    }

    private void genDescendientes(List<Punto> padres, Poblacion nuevaGeneracion) {
        for (Punto padre : padres) {
            for (int i = 0; i < getLamda() / getMiu(); i++) {
                nuevaGeneracion.add(mutar(padre));
            }
//            nuevaGeneracion.add(padre); // Miu +lambda
        }
    }

    public Punto mutar(Punto punto) {
        tweak = new Tweak_Explotacion(ancho);
        return tweak.tweak(punto, rand);
    }

    private List<Punto> escogerPadres(int miu, Poblacion poblacion) {
//        List<Punto> padres = poblacion.getPoblacion().subList(0, miu);
        //Los cromosomas con mayor valor de aptitud tendran mayor probabilidad de ser seleccionados
        double totalCalidad = 0;
        double[] acomuladoPuntos = new double[poblacion.size()];
        int i = 0;
        for (Punto punto : poblacion) {
            acomuladoPuntos[i++] = totalCalidad;
            totalCalidad += punto.getCalidad();
        }
        List<Punto> padres = new ArrayList();
        for (int k = 0; k < miu; k++) {
            double valorAleatorio = rand.nextDouble() * totalCalidad;
            for (int j = 0; j < acomuladoPuntos.length; j++) {
                if (valorAleatorio >= acomuladoPuntos[j]) {
                    padres.add(poblacion.get(j));
                    break;
                }
            }
        }
        return padres;
    }
}
