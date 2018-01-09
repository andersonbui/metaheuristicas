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
import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Ruleta {

    /**
     * selecciona un individuo en proporcion a su actitud y lo remueve de
     * poblacion. Los cromosomas con mayor valor de aptitud tendran mayor
     * probabilidad de ser seleccionados
     *
     * @param poblacion
     * @return
     */
    public static Individuo seleccionar(List<Individuo> poblacion) {
        //Los cromosomas con mayor valor de aptitud tendran mayor probabilidad de ser seleccionados
        double totalCalidad = 0;
        double[] acomuladoPuntos = new double[poblacion.size()];
        int i = 0;
        double menor = 0;
        for (Individuo punto : poblacion) {
            if (menor > punto.getCalidad()) {
                menor = punto.getCalidad();
            }
        }
        menor = menor < 0 ? -menor : 0;
        for (Individuo punto : poblacion) {
            totalCalidad += punto.getCalidad()+menor;
            acomuladoPuntos[i++] = totalCalidad;
        }
        Individuo escogido = null;
        double valorAleatorio = Aleatorio.nextDouble() * totalCalidad;
        for (int j = 0; j < acomuladoPuntos.length; j++) {
            if (valorAleatorio <= acomuladoPuntos[j]) {
                escogido = poblacion.remove(j);
                break;
            }

        }
        return escogido;
    }

}
