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
public class ConjuntoInstancias2000 extends ConjuntoInstancias {

    public ConjuntoInstancias2000() {
    }

    @Override
    public List<GrupoInstancias> getInstancias() {
        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_25_%d.txt", 1, 10, "1000_25"));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_50_%d.txt", 1, 10, "1000_50"));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_75_%d.txt", 1, 10, "1000_75"));//1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_100_%d.txt", 1, 10, "1000_100"));//1-10
        instancias.forEach((instancia) -> {
            instancia.setConjunto(this);
        });
        return instancias;
    }

    @Override
    public String getNombre() {
        return "1000";
    }
}
