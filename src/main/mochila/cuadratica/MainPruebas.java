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

    public static void pruebasL(String[] args) throws FileNotFoundException, Exception {
        List<String[]> listaVectArgumentas = new ArrayList();
        if (args.length == 0) {
            String algorit = "IHEA_VA";
            int intentos = 10;
            // pruebas para definir parametro L
            for (int i = 10; i < 50; i = i + 5) {
                listaVectArgumentas.add(new String[]{"-e", "100", "--evresumen", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + i + ",mt=10"});
            }
            for (int i = 10; i < 50; i = i + 5) {
                listaVectArgumentas.add(new String[]{"-e", "200", "--evresumen", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + i + ",mt=10"});
            }
            for (int i = 10; i < 50; i = i + 5) {
                listaVectArgumentas.add(new String[]{"-e", "300", "--evresumen", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + i + ",mt=10"});
            }
//            for (int i = 10; i < 50; i = i + 5) {
//                listaVectArgumentas.add(new String[]{"-e", "1000","--evresumen", "--intentos", String.valueOf(intentos) ,"--depuracion", "-r", "IHEA_VA", "-g", "L=" + i + ",mt=10"});
//            }

//            listaVectArgumentas.add(new String[]{"-e", "1000","--evresumen", "--intentos", "10" ,"--depuracion", "-r", "IHEA_VA", "-g", "L=" + 15 + ",mt=15"});
        } else {
            listaVectArgumentas.add(args);
        }
        for (String[] vectArgumentos : listaVectArgumentas) {
            (new MochilaCuadraticaEjecucion()).ejecucion(vectArgumentos);
        }
    }

    public static void pruebasA(String[] args) throws FileNotFoundException, Exception {
        List<String[]> listaVectArgumentas = new ArrayList();
        if (args.length == 0) {
            String algorit = "IHEA_VA";
            int intentos = 50;
            int L = 20;
            
            listaVectArgumentas.add(new String[]{"-e", "100", "--evresumen", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
            listaVectArgumentas.add(new String[]{"-e", "200", "--evresumen", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
            listaVectArgumentas.add(new String[]{"-e", "300", "--evresumen", "--intentos", String.valueOf(intentos), "--depuracion", "-r", algorit, "-g", "L=" + L + ",mt=10"});
//                listaVectArgumentas.add(new String[]{"-e", "1000","--evresumen", "--intentos", String.valueOf(intentos) ,"--depuracion", "-r", "IHEA_VA", "-g", "L=" + i + ",mt=10"});

//            listaVectArgumentas.add(new String[]{"-e", "1000","--evresumen", "--intentos", "10" ,"--depuracion", "-r", "IHEA_VA", "-g", "L=" + 15 + ",mt=15"});
        } else {
            listaVectArgumentas.add(args);
        }
        for (String[] vectArgumentos : listaVectArgumentas) {
            (new MochilaCuadraticaEjecucion()).ejecucion(vectArgumentos);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, Exception {
        pruebasL(args);
//        pruebasA(args);

    }
}

//            for (int i = 10; i < 50; i=i+25) {
//                listaVectArgumentas.add(new String[]{"-e", "100", "-r", "IHEA_M3", "L="+ i +",mt=10,mt=5"});
//            }
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M2", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS","-g", "i_interc=7, i_encontrarM=11"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS","-g", "i_interc=7, i_encontrarM=11"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "JSGVNS","-g", "i_interc=7, i_encontrarM=11"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "JSGVNS_GAR","-g", "i_interc=7, i_encontrarM=11, SACUDIDA=0.0:0.5:0.5, SEQ_VND=0.0:0.1:0.9"});
//            listaVectArgumentas.add(new String[]{"-e", "100", "-r", "JSGVNS_GAR","-g", "i_interc=7, i_encontrarM=11, SACUDIDA=0.0:0.8:0.2"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS_M1","-g", "i_interc=7, i_encontrarM=11"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS_M2","-g", "i_interc=7, i_encontrarM=11"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS_M3","-g", "i_interc=7, i_encontrarM=11"});
//              listaVectArgumentas.add(new String[]{"-b","-r","JSGVNS_GAR","-g","i_interc=7, i_encontrarM=11, SACUDIDA=0.0:0.8:0.2","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR_juan/Resultados/Generico/instancia_D0704A_0.txt"}); //se detiene
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_GAR", "L=20,mt=10,ms=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M1", "L=20,mt=10,ms=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M1", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M4", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_VA", "L=2,mt=15,mt=5"});
//            args = new String[]{"--archivo", "/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/grupo1/jeu_100_25_1.txt", "jeu_100_25_1_salida.txt"};
//            args = new String[]{"--estandar"};
//            args = new String[]{"--estandar", " < /home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/grupo1/jeu_100_25_1.txt"};
//            args = new String[]{"-I","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/r_10_100_13.txt"};
//            listaVectArgumentas.add(new String[]{"-v","-r","IHEA_GAR","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/r_10_100_13.txt"});
//              listaVectArgumentas.add(new String[]{"-b","-r","IHEA_GAR","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/grupo1/jeu_100_100_2.txt"});
//              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","L=30,rcl=30,mt=50,ms=20","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0609I_0.txt"});
//              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","L=30,rcl=30,mt=5,ms=2,CONS=0.0:0.5:0.5,DESC=0.0:0.5:0.5,PERT=0.0:0.5:0.5,TABU=0.9:0.1:0.5","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0626H_0.txt"}); //se detiene
//              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","rcl=30,L=30,mi=65,ms=4,mt=10,CONS=0:0.2:0","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D077B_0.txt"}); //se detiene
//              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","L=20,mt=10,ms=5","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D0601A_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","IHEA2","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D632i_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","IHEA","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D632i_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","SGVNS", "rcl=30,L=20,mi=65,ms=4,mt=10","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0615F_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","IHEA_M1", "rcl=30,L=20,mi=65,ms=4,mt=10","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0615F_0.txt"});
//            listaVectArgumentas.add(new String[]{"-v","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D0626H_0.txt"});
