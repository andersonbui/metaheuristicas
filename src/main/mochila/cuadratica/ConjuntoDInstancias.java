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
package main.mochila.cuadratica;

import java.util.ArrayList;
import java.util.List;
import main.GrupoInstancias;
import main.mochila.cuadratica.utilidades.Instancia;

/**
 *
 * @author debian
 */
public class ConjuntoDInstancias {

    List<GrupoInstancias> instancias = new ArrayList();

    public ConjuntoDInstancias() {
        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 4, 4)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 2)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 2, 2)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 10, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 14, 14)); //1-20

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 5));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/","5000_25_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/","1000_25_%d.dat", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 4, 4)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 2, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_25_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 2, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 10, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20)); //1-20
//        //instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_50_%d.txt", 14, 14)); //1-20
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_50_%d.txt", 1, 20)); //1-20
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_25_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_75_%d.txt", 1, 10));//1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_100_%d.txt", 1, 10));//1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/","r_10_100_%d.txt", 13, 13));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_25_%d.txt", 1, 20));//1-20
//        //instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_50_%d.txt", 14, 14));//1-20
    }

    public String getNombreResultado() {
        String nombreArchivoResultado = "resultados/resultados--";
        for (GrupoInstancias instancia : instancias) {
            String base = instancia.base.replaceFirst("%d", "%s");
            nombreArchivoResultado += String.format(base, (instancia.inicio + "-" + instancia.ultimo)) + "_";
        }
        nombreArchivoResultado += ".txt";

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
        String nombreCompletoArchivo;
        String nombreArchivo;
        for (GrupoInstancias instancia : instancias) {
            for (int indice_instancia = instancia.inicio; indice_instancia <= instancia.ultimo; indice_instancia++) {

                nombreCompletoArchivo = instancia.getNombreArchivoCompleto(indice_instancia);
                nombreArchivo = instancia.getNombreArchivo(indice_instancia);

                listaArchivos.add(new Instancia(nombreArchivo, nombreCompletoArchivo, instancia.base));
            }
        }
        return listaArchivos;
    }

    public void prepararTodos() {
        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "5000_25_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "1000_25_%d.dat", 1, 1));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_25_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10)); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20)); //1-20
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 1, 20)); //1-20
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_50_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_75_%d.txt", 1, 10));//1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_100_%d.txt", 1, 10));//1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "r_10_100_%d.txt", 13, 13));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20));//1-20
    }
}
