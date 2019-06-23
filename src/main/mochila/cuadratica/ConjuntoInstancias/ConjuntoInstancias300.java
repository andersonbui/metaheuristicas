/*
 * Copyright (C) 2019 debian
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
package main.mochila.cuadratica.ConjuntoInstancias;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author debian
 */
public class ConjuntoInstancias300 extends ConjuntoInstancias {

    public ConjuntoInstancias300() {
    }

    @Override
    public List<GrupoInstancias> getInstancias() {
        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20, "300_25")); //1-20
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 1, 20, "300_50")); //1-20
        return instancias;
    }

    @Override
    public String getNombre() {
        return "300";
    }
}
