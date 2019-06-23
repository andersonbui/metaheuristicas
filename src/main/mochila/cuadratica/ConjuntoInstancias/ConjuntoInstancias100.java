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
public class ConjuntoInstancias100 extends ConjuntoInstancias {

    public ConjuntoInstancias100() {
    }

    @Override
    public List<GrupoInstancias> getInstancias() {
        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10, "100_25")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10, "100_50")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10, "100_75")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10, "100_100")); //1-10
        return instancias;
    }

    @Override
    public String getNombre() {
        return "100";
    }
}
