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
package metaheuristicas.poblacion.cruce;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class Cruce {

    /**
     *
     * @param padre
     * @param madre
     * @param mascaraCruce
     * @return
     */
    
    public static IndividuoGen[] cruzar(IndividuoGen padre, IndividuoGen madre, int[] mascaraCruce) {
        IndividuoGen hijoP = padre.clone();
        IndividuoGen hijoM = madre.clone();
        for (int i: mascaraCruce) {
            hijoM.set(i, padre.get(i));
            hijoP.set(i, madre.get(i));
        }
        return new IndividuoGen[]{hijoP, hijoM};
    }

    public static int[] mascaraDosPuntos(int puntoInicial, int puntoFinal) {
        int[] result = new int[puntoFinal - puntoInicial];

        for (int i = puntoInicial, j = 0; i < puntoFinal; i++, j++) {
            result[j] = i;
        }
        return result;
    }

    public static Integer[] mascaraAleatoria(int tamMaximo, Random rand) {
        List<Integer> lista = new ArrayList<>();

        for (int i = 0; i < tamMaximo; i++) {
            if (rand.nextBoolean()) {
                lista.add(i);
            }
        }
        return (Integer[]) lista.toArray();
    }
}
