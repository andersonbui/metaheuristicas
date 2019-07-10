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
public abstract class ConjuntoInstancias {

    List<GrupoInstancias> instancias;
    int numGrupos;

    public ConjuntoInstancias() {
        instancias = new ArrayList();
    }

    @Override
    public String toString() {
        String nombreArchivoResultado = "";
        for (GrupoInstancias instancia : getInstancias()) {
            nombreArchivoResultado += instancia.toString() + "-";
        }
        return nombreArchivoResultado;
    }

    /**
     * lista de vectores de la siguiente forma [nombre_instancia,
     * nombre_completo_instancia]
     *
     * @return
     */
    public List<Instancia> getConjuntoInstancias() {
        List<Instancia> listaArchivos = new ArrayList();
        getInstancias().forEach((instancia) -> {
            listaArchivos.addAll(instancia.getInstancias());
        });
        return listaArchivos;
    }

    abstract public List<GrupoInstancias> getInstancias();

    public int getNumGrupos() {
        return numGrupos;
    }

    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }

    abstract public String getNombre();

    
}
