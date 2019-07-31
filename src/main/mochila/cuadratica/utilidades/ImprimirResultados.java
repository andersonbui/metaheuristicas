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
package main.mochila.cuadratica.utilidades;

import main.utilidades.EscribirArchivo;

/**
 *
 * @author debian
 */
public class ImprimirResultados {

    private final String nombreArchivoResultado;
    EscribirArchivo archivo_resultados = new EscribirArchivo();
    boolean aniadir;

    /**
     *
     * @param nombreArchivoResultados
     */
    public ImprimirResultados(String nombreArchivoResultados) {
        this.nombreArchivoResultado = nombreArchivoResultados;
        aniadir = false;
    }

    public void imprimir(String cadena) {

        boolean sepudo = archivo_resultados.abrir(nombreArchivoResultado, aniadir);
        if (sepudo) {
            archivo_resultados.escribir("\n" + cadena);
            archivo_resultados.terminar();
        }
        System.out.println(cadena);
        if (!aniadir) {
            aniadir = !aniadir;
        }
    }

}
