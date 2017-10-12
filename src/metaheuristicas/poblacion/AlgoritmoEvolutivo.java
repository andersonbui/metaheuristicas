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
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class AlgoritmoEvolutivo extends AlgoritmoMetaheuristico {

    int tamanioPoblacion;
    int numDescendientes;

    public AlgoritmoEvolutivo(AlgoritmoMetaheuristico tweak) {
        super("Evolutivo");
        this.tweak = tweak;
        numDescendientes = 2;
        tamanioPoblacion = 10;
    }

    @Override
    public List<Punto> ejecutar(Punto p) {
        Poblacion poblacion = generarPoblacionInicial();
        Punto mejor = obtenerMejor(poblacion);
        List<Punto> resultado = new ArrayList();
        resultado.add(mejor);
        for (int i = 0; i < iteraciones; i++) {
            poblacion = mezclar(poblacion, genDescendientes(new Punto[]{mejor}));
            mejor = obtenerMejor(poblacion);
            resultado.add(mejor);
        }
        return resultado;
    }

    private Poblacion mezclar(Poblacion poblacion, Poblacion genDescendientes) {
        Poblacion pob = poblacion;
        for (Punto descen : genDescendientes.getPoblacion()) {
            poblacion.add(descen);
        }
        return pob;
    }

    protected Punto obtenerMejor(Poblacion poblacion) {
        return poblacion.getMejor();
    }

    protected Poblacion generarPoblacionInicial() {
        Poblacion poblacion = new Poblacion(tamanioPoblacion);
        Punto p;
        for (int i = 0; i < tamanioPoblacion; i++) {
            p = funcion.generarPunto(rand);
            p.setCalidad(funcion.evaluar(p));
            poblacion.add(p);
        }
        return poblacion;
    }

    private Poblacion genDescendientes(Punto[] padres) {
        Poblacion hijos = new Poblacion(numDescendientes);
        Punto nuevo;
        for (int i = 0; i < numDescendientes; i++) {
            nuevo = tweak(padres[0]);
            nuevo.setCalidad(funcion.evaluar(nuevo));
            hijos.add(nuevo);
            
        }
        return hijos;
    }

}
