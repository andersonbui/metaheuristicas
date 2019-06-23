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
package main.mochila.cuadratica.ConjuntoInstancias;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.utilidades.Instancia;

/**
 *
 * @author debian
 */
public class GrupoInstancias {

    public String ubicacion;
    public String base;
    public int ultimo;
    public int primero;
    public String nombregrupo;

    /**
     *
     * @param ubicacion
     * @param nombreBase nombre de la instancia del tipo "nombre_%d_nombre2", donde %d se itera desde primero hasta ultimo
     * @param ultimo
     * @param primero
     * @param nombregrupo
     */
    public GrupoInstancias(String ubicacion, String nombreBase, int primero, int ultimo, String nombregrupo) {
        this.ubicacion = ubicacion;
        this.base = nombreBase;
        this.ultimo = ultimo;
        this.primero = primero;
        this.nombregrupo = nombregrupo;
    }

    public String getNombreArchivoCompleto(int indice_instancia) {
        return String.format(ubicacion + base, indice_instancia);
    }

    public String getNombreArchivo(int indice_instancia) {
        return String.format(base, indice_instancia);
    }

    @Override
    public String toString() {
        return "-" + nombregrupo + '[' + primero + '-' + ultimo + ']';
    }

    /**
     * lista de nombre de archivos que contienen las instancias
     *
     * @return
     */
    public List<Instancia> getInstancias() {
        List<Instancia> listaArchivos = new ArrayList<>();
        String nombreCompletoArchivo;
        String nombreArchivo;
        for (int indice_instancia = primero; indice_instancia <= ultimo; indice_instancia++) {

            nombreCompletoArchivo = getNombreArchivoCompleto(indice_instancia);
            nombreArchivo = getNombreArchivo(indice_instancia);
            listaArchivos.add(new Instancia(nombreArchivo, nombreCompletoArchivo, base));
        }

        return listaArchivos;
    }

}
