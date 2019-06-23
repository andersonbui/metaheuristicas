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
import java.util.Arrays;
import java.util.List;
import main.mochila.cuadratica.utilidades.Instancia;

/**
 *
 * @author debian
 */
public class ConjuntoInstanciasPruebas extends ConjuntoInstancias {

    public ConjuntoInstanciasPruebas() {
    }


    @Override
    public List<GrupoInstancias> getInstancias() {

        instancias = new ArrayList();
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 4, 4,"prueba")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 2,"prueba")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 2, 2,"prueba")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 10, 10,"prueba")); //1-10
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 14, 14,"prueba")); //1-20
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 5,"prueba"));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 4, 4,"prueba")); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 2, 2,"prueba")); //1-10
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/","jeu_200_50_%d.txt", 2, 2,"prueba")); //1-10

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo5000/","5000_25_%d.txt", 1, 1,"prueba"));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/","1000_25_%d.dat", 1, 1,"prueba"));

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
        return instancias;
    }

    @Override
    public String getNombre() {
        return "prueba";
    }
}
