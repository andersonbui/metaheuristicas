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
import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;
import metaheuristicas.movimiento.Tweak;
import metaheuristicas.movimiento.Tweak_Explotacion;

/**
 *
 * @author debian
 */
public class MLamda extends AlgoritmoEvolutivo {

    Tweak tweak;
    double ancho = 0.5;

    /**
     *
     * @param miu numero de descendientes por individuo
     * @param lamda tamanio poblacion
     */
    public MLamda(int miu, int lamda) {
        super(lamda, miu);
        nombre = "MiuLamda";
    }

    public int getMiu() {
        return numDescendientes;
    }

    public int getLamda() {
        return tamPoblacion;
    }

    /**
     *
     * @param numIndividuosElitismo
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo) {
        Poblacion nuevaGeneracion = new Poblacion(poblacion.getFuncion(), poblacion.getTamanioMaximo());
        elitismo(nuevaGeneracion, poblacion, numIndividuosElitismo);
        List<Individuo> padresEscogidos = escogerPadres(getMiu(), poblacion);
        genDescendientes(padresEscogidos, nuevaGeneracion);
        return nuevaGeneracion;
    }

    private Poblacion mezclar(Poblacion poblacion, List<Individuo> genDescendientes) {
        Poblacion pob = poblacion;
        for (Individuo descen : genDescendientes) {
            poblacion.add(descen);
        }
        return pob;
    }

    private void genDescendientes(List<Individuo> padres, Poblacion nuevaGeneracion) {
        for (Individuo padre : padres) {
            for (int i = 0; i < getLamda() / getMiu(); i++) {
                nuevaGeneracion.add(mutar(padre));
            }
//            nuevaGeneracion.add(padre); // Miu +lambda
        }
    }

    public Individuo mutar(Individuo punto) {
        tweak = new Tweak_Explotacion(ancho);
        return tweak.tweak(punto);
    }

    private List<Individuo> escogerPadres(int miu, Poblacion poblacion) {
//        List<Punto> padres = poblacion.getPoblacion().subList(0, miu);
        //Los cromosomas con mayor valor de aptitud tendran mayor probabilidad de ser seleccionados
        double totalCalidad = 0;
        double[] acomuladoPuntos = new double[poblacion.size()];
        int i = 0;
        for (Individuo punto : poblacion) {
            acomuladoPuntos[i++] = totalCalidad;
            totalCalidad += punto.getCalidad();
        }
        List<Individuo> padres = new ArrayList();
        for (int k = 0; k < miu; k++) {
            double valorAleatorio = Aleatorio.nextDouble() * totalCalidad;
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
