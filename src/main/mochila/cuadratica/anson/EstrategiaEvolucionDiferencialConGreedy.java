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
package main.mochila.cuadratica.anson;

import main.mochila.estrategias.*;
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;
import metaheuristicas.poblacion.Poblacion;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialConGreedy extends EstrategiaEvolucionDiferencialBinariaPaper {

    FuncionMochilaCuadraticaGreedy funcionGreedy;

    public EstrategiaEvolucionDiferencialConGreedy(int tamPoblacion, FuncionMochilaCuadraticaGreedy funcionGreedy) {
        super(tamPoblacion);
        nombre = "EstrategiaEDB_PaperMM";
        cr = 0.2;
        alfa = 0.8;
        b = 20;
        this.funcionGreedy = funcionGreedy;
    }

    /**
     * genera hijos a partir de un padre y una madre y compiten en calidad entre
     * todos.
     *
     * @param numIndividuosElitismo
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo) {
        Poblacion siguienteGeneracion = poblacion.clone();
        siguienteGeneracion.aumentarGeneracion();
        siguienteGeneracion.clear();
        for (int k = 0; k < poblacion.size(); k++) {
            Individuo objetivo = poblacion.remove(k);
            // MUTACION
            Individuo mutado = mutar(poblacion);
            // CRUCE -> generacion del vector prueba
            Individuo individuoPrueba = cruce(objetivo, mutado, k);
            // SELECCION
            funcionGreedy.limitar(individuoPrueba);
            seleccion(objetivo, individuoPrueba, siguienteGeneracion);
            poblacion.add(objetivo);
//            siguienteGeneracion.add(objetivo);
        }
        return siguienteGeneracion;
    }

    @Override
    public Poblacion generarPoblacion(Funcion funcion) {
        Poblacion unaPoblacion = new Poblacion(funcion, tamPoblacion);
        Individuo p;
        for (int i = 0; i < tamPoblacion; i++) {
//            p = funcion.generarIndividuo();
            p = funcionGreedy.generarIndividuo();
            p.setFuncion(funcion);
            funcionGreedy.limitar(p); // comentado 
            p.evaluar();
            
            unaPoblacion.add(p);
        }
        return unaPoblacion;
    }

}
