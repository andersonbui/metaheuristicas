/*
 * Copyright (C) 2020 debian
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
package main.mochila.cuadratica;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author debian
 */
public class MainPruebas {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        List<String[]> listaVectArgumentas = new ArrayList();

        if (args.length == 0) {

            // pruebas para definir parametro L
            for (int i = 10; i < 50; i = i + 4) {
//                listaVectArgumentas.add(new String[]{"-e", "1000","--evresumen", "--intentos", "10" ,"--depuracion", "-r", "IHEA_VA", "-g", "L=" + i + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e 1000", "-r", "IHEA_M3", "L=" + i + ",mt=10,mt=5"});
            }
                listaVectArgumentas.add(new String[]{"-e", "1000","--evresumen", "--intentos", "10" ,"--depuracion", "-r", "IHEA_VA", "-g", "L=" + 15 + ",mt=15"});

        } else {
            listaVectArgumentas.add(args);
        }
        for (String[] vectArgumentos : listaVectArgumentas) {
            (new MochilaCuadraticaEjecucion()).ejecucion(vectArgumentos);
        }
    }
}
