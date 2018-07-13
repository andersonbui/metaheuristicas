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
package main.mochila.cuadratica.utilidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.mochila.cuadratica.GrupoInstancias;
import main.mochila.cuadratica.LecturaParametrosCuadratica;
import main.mochila.cuadratica.ParametrosCuadratica;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;

/**
 *
 * @author debian
 */
public class Main_MetododosInicializacion {

    private static FuncionMochilaIHEA funcion;

    //lim,rango,prob_ceros,poblacion, iteraciones
    //lim,20,0.90,20
//        nombreArchivo = "mochilaCuadratica/1000_25_1.dat";
//        nombreArchivo = "mochilaCuadratica/5000_25_1.txt";
//        listaParametros = UtilCuadratica.obtenerDatosMochilaCuadratica("mochilaCuadratica/1000_25_1.dat");
//        nombreArchivo = "mochilaCuadratica/jeu_200_75_6.txt";
//        //lim,15,0.99,20
//        nombreArchivo = "mochilaCuadratica/jeu_300_25_10.txt";
//        //si-no,15,0.99,20
//        nombreArchivo = "mochilaCuadratica/jeu_300_50_2.txt";
//        //lim,15,0.99,10,14
//        nombreArchivo = "mochilaCuadratica/jeu_100_25_2.txt";
//        //no,15,0.99,20,32
//        //si,15,0.90-93,20,31
    //lim,15,0.99,5->,1
//        nombreArchivo = "mochilaCuadratica/r_10_100_13.txt";
    public static void main(String[] args) {
        String nombreArchivo = "";
        List<GrupoInstancias> instancias = new ArrayList();
        double sumaTotal = 0;
        int contador = 0;

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_100_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_200_75_%d.txt", 1, 10));

        for (GrupoInstancias instancia : instancias) {
            System.out.println("########################################################################");
            for (int indice_instancia = 1; indice_instancia <= instancia.cantidad; indice_instancia++) {
                nombreArchivo = String.format(instancia.base, indice_instancia);

                LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
                ParametrosCuadratica parametros = pc.obtenerParametros(nombreArchivo);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }
                funcion = new FuncionMochilaIHEA(parametros.getMatrizBeneficios(), parametros.getCapacidad(), parametros.getVectorPesos(), parametros.getMaxGlobal());

                IndividuoIHEA indi = funcion.generarIndividuo();
                int[] ideal = parametros.getVectorIdeal();
                if (ideal == null) {
                    System.out.println("No hay ideal.");
                    continue;
                }
                IndividuoIHEA indiIdeal = new IndividuoIHEA(funcion);
                for (int i = 0; i < ideal.length; i++) {
                    if (ideal[i] == 1) {
//                System.out.println("");
                        indiIdeal.set(i, ideal[i]);
                    }
                }

                // lista de indices para ordenamiento
                List<Posicion> posiciones = new ArrayList();
                // aumentar la matriz y relizar sumatorias por fila y columna
                for (int i = 0; i < funcion.getDimension(); i++) {
                    double peso = funcion.peso(i);
                    double relacion = funcion.relaciones(i);
                    double beneficio = funcion.beneficio(i);
                    posiciones.add(new Posicion(i, peso, relacion, beneficio));
                }
                // encontrar orden

                Collections.sort(posiciones, (Posicion o1, Posicion o2) -> {
                    return comparar2(o1, o2);
//            return -Double.compare(funcion.beneficio(o1.posicion) / funcion.peso(o1.posicion), funcion.beneficio(o2.posicion) / funcion.peso(o2.posicion));
                });

                while (funcion.cabe(indi, posiciones.get(0).posicion)) {
                    indi.set(posiciones.remove(0).posicion, 1);
                }

                int parecido = indi.parecido(indiIdeal);
                double porcentaje = ((double) parecido) / indiIdeal.elementosSeleccionados().size();
                System.out.println("----------------------------------");
                System.out.println("nombre archivo: " + nombreArchivo);
                System.out.println("parecido: " + parecido);
                System.out.println("calidad alcanzado: " + indi.getCalidad());
                System.out.println("calidad ideal: " + indiIdeal.getCalidad());
                System.out.println("porcentanje parecido: " + porcentaje);
                sumaTotal += porcentaje;
                contador++;
            }
        }
        System.out.println("\n##############################################");
        System.out.println("promedio porcentaje exito metodo: " + (sumaTotal / contador));
    }

    public static class Posicion {

        public int posicion;
        public double peso;
        public double relacion;
        public double beneficio;

        public Posicion(int posicion, double peso, double relacion, double beneficio) {
            this.posicion = posicion;
            this.peso = peso;
            this.relacion = relacion;
            this.beneficio = beneficio;
        }

    }

    public static int comparar3(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio * o1.beneficio / (o1.relacion * o1.peso);
        Double resultado2 = o2.beneficio * o2.beneficio / (o2.relacion * o2.peso);
        return -resultado1.compareTo(resultado2);
    }

    public static int comparar(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / (o1.relacion * o1.peso);
        Double resultado2 = o2.beneficio / (o2.relacion * o2.peso);
        return -resultado1.compareTo(resultado2);
    }

    public static int comparar2(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / o1.peso;
        Double resultado2 = o2.beneficio / o2.peso;
        return -Double.compare(resultado1, resultado2);
    }
}
