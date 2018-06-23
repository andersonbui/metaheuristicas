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

import metaheuristicas.IndividuoGen;
import metaheuristicas.poblacion.seleccion.Ruleta;

/**
 *
 * @author debian
 */
public class EvolucionDiferencialMejorado extends EvolucionDiferencial {

    public EvolucionDiferencialMejorado(int tamPoblacion) {
        super(tamPoblacion);
        nombre = "EvolucionDiferencialMM";
        cr = 0.2;
        alfa = 0.8;
    }

    @Override
    protected IndividuoGen mutar(Poblacion poblacion) {

        IndividuoGen x1 = Ruleta.seleccionar(poblacion);
        IndividuoGen x2 = Ruleta.seleccionar(poblacion);
        IndividuoGen x3 = Ruleta.seleccionar(poblacion);
        poblacion.add(x1);
        poblacion.add(x2);
        poblacion.add(x3);

//        if (x1.compareTo(x2) < 0) {
//            Punto aux = x1;
//            x1 = x2;
//            x2 = aux;
//        }
        IndividuoGen diferenciaX1X2 = resta(x1, x2);
        IndividuoGen productoEscalar = multiplicacionPorEscalar(diferenciaX1X2, alfa);
        IndividuoGen mutado = suma(x3, productoEscalar);
//        mutado.evaluar();
        return mutado;
    }

}
