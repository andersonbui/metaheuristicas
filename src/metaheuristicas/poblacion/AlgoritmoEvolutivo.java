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

import metaheuristicas.Funcion;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Viajante;

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
    }

    @Override
    public List<Viajante> ejecutar(Funcion funcion) {
        Poblacion poblacion = estrategia.generarPoblacion(funcion);
        Viajante mejor = poblacion.getMejor();
        List<Viajante> recorrido = new ArrayList();
        recorrido.add(mejor);
        for (iteraciones = 0; iteraciones < maxIteraciones && !funcion.suficiente(mejor.getOptimo()); iteraciones++) {
            poblacion = estrategia.siguienteGeneracion(1, poblacion);
            mejor = poblacion.getMejor();

            recorrido.add(mejor);
        }
        return recorrido;
    }

    @Override
    public String getNombre() {
        return estrategia.getNombreEstrategia();
    }

    @Override
    public void siguiente() {
        estrategia.siguiente();
    }

    @Override
    public boolean haySiguiente() {
        return estrategia.haySiguiente();
    }

    @Override
    public void reiniciar() {
        estrategia.reiniciar();
    }

}
