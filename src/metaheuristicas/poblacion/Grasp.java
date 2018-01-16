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
import main.mochila.multidimensional.funciones.FuncionMochilaGreedy;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Funcion;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Grasp extends AlgoritmoMetaheuristico {

    protected List<Integer> articulos = new ArrayList();
    FuncionMochilaGreedy funcionGreedy;

    public Grasp(int tamPoblacion, FuncionMochilaGreedy funcionGreedy) {
        super("Grasp");

        for (int i = 0; i < funcionGreedy.getDimension(); i++) {
            articulos.add(i);//i:indices de los articulos 
        }
        this.funcionGreedy = funcionGreedy;
        articulos.sort((Integer o1, Integer o2) -> {
            /**
             * comparar los indices, segun aptitud(w.get(o1).length - 1),
             * aptitud se encuentra en la ultima posicion de cada articulo
             * W.get(i),i=0,1,...,w.size();
             */
            // densidad de ganacia por unidad de peso
            Double aptotudO1 = funcionGreedy.funcionSeleccion(o1);
            Double aptotudO2 = funcionGreedy.funcionSeleccion(o2);
            // ganancia
//                int posicionAptitud = w.get(o1).length - 1;
//                Double aptotudO1 = w.get(o1)[posicionAptitud];
//                Double aptotudO2 = w.get(o2)[posicionAptitud];
            return aptotudO1.compareTo(aptotudO2);
        });
    }

    public Individuo voraz(Individuo individuo) {
        for (int i = 0; i < articulos.size() && !funcionGreedy.suficiente(individuo); i++) {
            int posMejor = articulos.get(i);
            individuo.set(posMejor, 1);
            if (!funcionGreedy.factible(individuo)) {
                individuo.set(posMejor, 0);
            }
        }
        individuo.evaluar();
        return individuo;
    }

    @Override
    public List<Individuo> ejecutar(Funcion funcion) {
        Individuo individuo = funcionGreedy.generarPunto(0);
        voraz(individuo);
        Byte c = 1;
        return null;
    }
}
