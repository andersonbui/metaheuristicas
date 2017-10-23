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

import funciones.Funcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Punto;

/**
 * @author debian
 */
public class AlgoritmoEvolutivo extends AlgoritmoMetaheuristico {

    Estrategia estrategia;

    /**
     * @param estrategia
     */
    public AlgoritmoEvolutivo(Estrategia estrategia) {
        super("Evolutivo");
        this.estrategia = estrategia;
        setNombre(estrategia.getNombreEstrategia());
    }

    @Override
    public List<Punto> ejecutar(Random rand, Funcion funcion) {
        Poblacion poblacion = estrategia.generarPoblacion(funcion, rand);
        Punto mejor = poblacion.getMejor();
        List<Punto> recorrido = new ArrayList();
        recorrido.add(mejor);
        for (int i = 0; i < iteraciones; i++) {
            poblacion = estrategia.siguienteGeneracion(1, poblacion, rand);
            mejor = poblacion.getMejor();
            
            recorrido.add(mejor);
        }
        return recorrido;
    }
}
