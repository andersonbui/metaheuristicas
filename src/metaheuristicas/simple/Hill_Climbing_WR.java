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
package metaheuristicas.simple;

import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Hill_Climbing_WR extends Hill_Climbing {

    int numSucesores;

    /**
     * subiendola colina por maxima pendiente con (o sin) reemplazo, se acuerdo
     * a los parmetros
     *
     * @param tweak
     * @param numSucesores
     */
    public Hill_Climbing_WR(AlgoritmoMetaheuristico tweak, int numSucesores) {
        super(tweak);
        this.numSucesores = numSucesores > 0 ? numSucesores : 1;
        this.tweak = tweak;
        this.nombre = "SAHC-WR CON REEMPLAZO";
    }

    @Override
    public Punto tweak(Punto s) {
        Hill_Climbing hc = new Hill_Climbing((AlgoritmoMetaheuristico) this.tweak);
        hc.setRand(rand);
        hc.setFuncion(funcion);
        hc.setIteraciones(numSucesores);
        List<Punto> listaRes = hc.ejecutar(s);
        return listaRes.get(listaRes.size() - 1);
    }

}
