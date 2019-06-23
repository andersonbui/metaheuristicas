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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author debian
 */
public class ConjuntoInstanciasResumenes extends ConjuntoInstancias {

    public ConjuntoInstanciasResumenes() {
    }

   @Override
    public List<GrupoInstancias> getInstancias() {
        String ubi_carpeta = System.getProperty("user.dir");
        ubi_carpeta += "/mochilaCuadratica/resumenes/";
        File carpeta = new File(ubi_carpeta);
        String[] listado = null;
        if (carpeta.isDirectory()) {
            listado = carpeta.list();
            int catidad = listado.length;
//            int catidad=28;
//            System.out.println("archivo: " + Arrays.toString(listado));
            instancias = new ArrayList();
            String nombre_item = "";
            for (int i = 0; i < catidad; i++) {
                nombre_item = listado[i];
                GrupoInstancias gi = new GrupoInstancias(ubi_carpeta, nombre_item, 1, 1, "resumen");
//                System.out.println(">: " + gi.getNombreArchivoCompleto(1));
                instancias.add(gi);
            }
        }
        return instancias;
    }
    
    @Override
    public String getNombre() {
        return "Resumenes";
    }
}
