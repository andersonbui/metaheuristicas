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
package tweaks;

import java.util.ArrayList;
import java.util.List;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Tweak_GeneraNuevo extends Tweak {

    @Override
    public List<Punto> ejecutar(Punto punto, double radio) {
        List lista = new ArrayList();
        lista.add(funcion.generarPunto(rand));
        return lista;
    }

}
