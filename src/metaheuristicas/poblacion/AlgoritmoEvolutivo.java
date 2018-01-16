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

import java.security.InvalidParameterException;
import metaheuristicas.Funcion;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Individuo;

/**
 * @author debian
 */
public abstract class AlgoritmoEvolutivo extends AlgoritmoMetaheuristico {

    protected int tamPoblacion;
    protected int numDescendientes;
    protected Poblacion poblacion;

    /**
     * @param tamPoblacion
     * @param numDescendientes
     */
    public AlgoritmoEvolutivo(int tamPoblacion, int numDescendientes) {
        super("Evolutivo");
        this.tamPoblacion = tamPoblacion;
        this.numDescendientes = numDescendientes;
    }

    @Override
    public List<Individuo> ejecutar(Funcion funcion) {
        poblacion = generarPoblacion(funcion);
        Individuo mejor = poblacion.getMejor();
        List<Individuo> recorrido = new ArrayList();
        recorrido.add(mejor);
        for (iteraciones = 0; iteraciones < maxIteraciones && !funcion.suficiente(mejor); iteraciones++) {
            poblacion = siguienteGeneracion(1);
            mejor = poblacion.getMejor();

            recorrido.add(mejor);
        }
        return recorrido;
    }

    public Poblacion generarPoblacion(Funcion funcion) {
        Poblacion unaPoblacion = new Poblacion(funcion, tamPoblacion);
        Individuo p;
        for (int i = 0; i < tamPoblacion; i++) {
            p = funcion.generarIndividuo();
            p.evaluar();
            unaPoblacion.add(p);
        }
        return unaPoblacion;
    }

    protected abstract Poblacion siguienteGeneracion(int elitismo);

    public void elitismo(Poblacion nueva, Poblacion actual, int numIndividuos) {
        if (numIndividuos > actual.size()) {
            throw new InvalidParameterException("numero de parametros invalido: numIndividuos <= atual.size()");
        }
        for (int i = 0; i < numIndividuos; i++) {
            nueva.add(actual.remove(0));
        }
    }

}
