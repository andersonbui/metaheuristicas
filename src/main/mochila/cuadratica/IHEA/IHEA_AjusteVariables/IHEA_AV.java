/*
 * Copyright (C) 2018 debian
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
package main.mochila.cuadratica.IHEA.IHEA_AjusteVariables;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;

/**
 *
 * @author debian
 */
public class IHEA_AV extends IteratedHyperplaneExplorationAlgoritm {

    public IHEA_AV(FuncionMochilaIHEA funcion) {
        super(funcion);
        ub = funcion.obtenerUpperBound();
        L = 20;
    }

}
