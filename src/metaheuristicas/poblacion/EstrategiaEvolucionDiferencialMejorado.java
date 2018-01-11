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
import metaheuristicas.poblacion.seleccion.Ruleta;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialMejorado extends EstrategiaEvolucionDiferencial {

    public EstrategiaEvolucionDiferencialMejorado(int tamPoblacion) {
        super(tamPoblacion);
        nombreEstrategia = "EvolucionDiferencialMM";
        cr = 0.2;
        alfa = 0.8;
    }

    @Override
    protected Individuo mutar(Poblacion poblacion) {

        Individuo x1 = Ruleta.seleccionar(poblacion);
        Individuo x2 = Ruleta.seleccionar(poblacion);
        Individuo x3 = Ruleta.seleccionar(poblacion);
        poblacion.add(x1);
        poblacion.add(x2);
        poblacion.add(x3);

//        if (x1.compareTo(x2) < 0) {
//            Punto aux = x1;
//            x1 = x2;
//            x2 = aux;
//        }
        Individuo diferenciaX1X2 = resta(x1, x2);
        Individuo productoEscalar = multiplicacionPorEscalar(diferenciaX1X2, alfa);
        Individuo mutado = suma(x3, productoEscalar);
//        mutado.evaluar();
        return mutado;
    }

}
