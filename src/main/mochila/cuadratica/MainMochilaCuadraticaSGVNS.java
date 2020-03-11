/*
 * Copyright (C) 2017 debian
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
public class MainMochilaCuadraticaSGVNS {

    public static String tsalida = "v";
    public static String algoritmo = null;
    public static boolean depuracion = false;
    public static boolean evresumen = false;
    static String parametrosAlgoritmo;
    public static String nombreExperimento = "";

    public static void SGVNS_M1(String[] args) throws FileNotFoundException, Exception {

        List<String[]> listaVectArgumentas = new ArrayList();
        if (args.length == 0) {
            String[] algots = {"SGVNS_M1"};
//        String[] algots = {"IHEA_M","IHEA_M142","IHEA_M1423"};
            for (String algot : algots) {
                String algorit = algot;
                int intentos = 1;

                System.out.println(algorit);
//                listaVectArgumentas.add(new String[]{"-e", "100", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "200", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "300", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "1000","-v", "--intentos", String.valueOf(intentos) ,"--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                  listaVectArgumentas.add(new String[]{"-e", "1000","-v", "--intentos", String.valueOf(intentos),"--depuracion", "-r", algorit, "-g","i_interc=7, i_encontrarM=11"});
//                  listaVectArgumentas.add(new String[]{"-e", "1000","-v", "--intentos", String.valueOf(intentos),"--depuracion", "-r", algorit, "-g","i_interc=7, i_encontrarM=11, SACUDIDA=0.0:0.8:0.2"});
            }
        } else {
            listaVectArgumentas.add(args);
        }
        for (String[] vectArgumentos : listaVectArgumentas) {
            (new MochilaCuadraticaEjecucion()).ejecucion(vectArgumentos);
        }
    }

    public static void JSGVNS_GAR(String[] args) throws FileNotFoundException, Exception {

        List<String[]> listaVectArgumentas = new ArrayList();
        if (args.length == 0) {
            String[] algots = {"JSGVNS_GAR"};
//        String[] algots = {"IHEA_M","IHEA_M142","IHEA_M1423"};
            for (String algot : algots) {
                String algorit = algot;
                int intentos = 1;

                System.out.println(algorit);
//                listaVectArgumentas.add(new String[]{"-e", "100", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "200", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "300", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "1000","-v", "--intentos", String.valueOf(intentos) ,"--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
                listaVectArgumentas.add(new String[]{"-e", "1000", "-v", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "i_interc=7, i_encontrarM=11"});
            }
        } else {
            listaVectArgumentas.add(args);
        }
        for (String[] vectArgumentos : listaVectArgumentas) {
            (new MochilaCuadraticaEjecucion()).ejecucion(vectArgumentos);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, Exception {
        JSGVNS_GAR(args);
//        SGVNS_M1(args);
    }
}
