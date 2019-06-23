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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 4, 4)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 2, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 10, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 14, 14)); //1-20
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 5));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 4, 4)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 2, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 2, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 2, 2)); //1-10

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 2)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_25_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10)); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20)); //1-20
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_300_50_%d.txt", 1, 20)); //1-20
        
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo5000/","5000_25_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_25_%d.dat", 1, 1));
       // instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_25_%d.txt", 1, 10));//1,5,7
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_50_%d.txt", 1, 10));//3,5,7
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_75_%d.txt", 1, 10));//1-10//1,4,5,6,8
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_100_%d.txt", 1, 10));//1-10//1,5,6,7,8,9

        //dificiles
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_50_%d.txt", 8, 8));//3,5,7
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_75_%d.txt", 5, 5));//1-10//1,4,5,6,8
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_100_%d.txt", 8, 8));//1-10//1,5,6,7,8,9
//
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo2000/","2000_25_%d.dat", 1, 10));//1,5,7
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo2000/","2000_50_%d.dat", 1, 10));//3,5,7
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo2000/","2000_75_%d.dat", 1, 10));//1-10//1,4,5,6,8
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo2000/","2000_100_%d.dat", 1, 10));//1-10//1,4,5,6,8

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo5000/","5000_25_%d.txt", 1, 1));//1,5,7
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
        nombreArchivoResultado += "_IHEA_NAM.txt";

        return nombreArchivoResultado;
    }

    public String getNombreResultado2() {
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

    public void prepararInstanciasResumnenes() {
        String ubi_carpeta = System.getProperty("user.dir");
        ubi_carpeta += "/mochilaCuadratica/resumenes/";
        File carpeta = new File(ubi_carpeta);
        String[] listado = null;
        if (carpeta.isDirectory()) {
            listado = carpeta.list();
            int catidad=listado.length;
//            int catidad=28;
//            System.out.println("archivo: " + Arrays.toString(listado));
            instancias = new ArrayList();
            String nombre_item = "";
            for (int i = 0; i < catidad; i++) {
                nombre_item = listado[i];
                GrupoInstancias gi = new GrupoInstancias(ubi_carpeta, nombre_item, 1, 1);
//                System.out.println(">: " + gi.getNombreArchivoCompleto(1));
                instancias.add(gi);
            }
        }
//        instancias.add(new GrupoInstancias("mochilaCuadratica/resumenes/", "instancia_D0649D.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/resumenes/", "instancia_D0650E.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/resumenes/", "instancia_D331f_0.txt", 1, 1));
    }

    public void prepararTodos() {
        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10));
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
        
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "5000_25_%d.txt", 1, 1));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "1000_25_%d.dat", 1, 1));
        
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "r_10_100_%d.txt", 13, 13));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20));//1-20
    }
}
