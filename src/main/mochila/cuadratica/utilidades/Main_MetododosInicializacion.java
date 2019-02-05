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
import main.GrupoInstancias;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;

/**
 * prueba de metodos de inicializacion de individuo mochila cuadratica
 *
 * @author debian
 */
public class Main_MetododosInicializacion {

    private static FuncionMochilaIHEA funcion;

    public static void main(String[] args) {
        String nombreArchivo = "";
        List<GrupoInstancias> instancias = new ArrayList();
        double sumaParecidoUpperTotal = 0;
        double sumaParecidoLowerTotal = 0;
        double desv_sumaParecidoLowerTotal = 0;
        double sumaUltimoUpperTotal = 0;  // suma de todos los cocientes: ultimo/upperbound
        int contador = 0;
        int ultimoSeleccionado;

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10));

        for (GrupoInstancias instancia : instancias) {
            System.out.println("########################################################################");
            for (int indice_instancia = 1; indice_instancia <= instancia.ultimo; indice_instancia++) {
                ultimoSeleccionado = 0;
                nombreArchivo = instancia.getNombreArchivoCompleto(indice_instancia);
                
                LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
                ParametrosCuadratica parametros = pc.obtenerParametros(nombreArchivo);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }
                funcion = new FuncionMochilaIHEA(parametros.getMatrizBeneficios(), parametros.getCapacidad(), parametros.getVectorPesos(), parametros.getMaxGlobal());

                int[] valsIdeal = parametros.getVectorIdeal();
                if (valsIdeal == null) {
                    System.out.println("No hay ideal.");
                    continue;
                }
                IndividuoIHEA indiIdeal = new IndividuoIHEA(funcion);
                // crear individuo ideal
                for (int i = 0; i < valsIdeal.length; i++) {
                    if (valsIdeal[i] == 1) {
                        indiIdeal.set(i, 1);
//                        ultimoSeleccionado = i;
                    }
                }

                // lista de indices para ordenamiento
                List<Posicion> posiciones = new ArrayList();
                // crear estructura de comparacion
                for (int i = 0; i < funcion.getDimension(); i++) {
                    double peso = funcion.peso(i);
                    double relacion = funcion.relaciones(i);
                    double beneficio = funcion.beneficio(i);
                    posiciones.add(new Posicion(i, peso, relacion, beneficio));
                }
                // ordenar de acuerdo a la estrctura anterior
                Collections.sort(posiciones, (Posicion o1, Posicion o2) -> {
                    return comparar4(o1, o2);
                });
                // obtener ultimo de unos consecutivos en el individuo encontrado con respecto al ideal
                int cantidadSeleccionadosConsecutivos = 0;
                for (int i = 0; i < posiciones.size(); i++) {
                    int pos = posiciones.get(i).posicion;
                    if (valsIdeal[pos] == 1) {
                        cantidadSeleccionadosConsecutivos = i;
                    } else {
                        break;
                    }
                }
                // obtener la ultimo de unos en el individuo encontrado con respecto al ideal
                for (int i = 0; i < posiciones.size(); i++) {
                    int pos = posiciones.get(i).posicion;
                    if (valsIdeal[pos] == 1) {
                        ultimoSeleccionado = i;
                    }
                }

                IndividuoIHEA indiAlcanzado = funcion.generarIndividuo();
                // crear individuo de los n primeros elementos de la lista ordenada
                while (funcion.cabe(indiAlcanzado, posiciones.get(0).posicion)) {
                    indiAlcanzado.set(posiciones.remove(0).posicion, 1);
                }

                int parecido = indiAlcanzado.parecido(indiIdeal);
                int[] lu_bound = UtilCuadratica.optenerLowerUpper_Bound(funcion);
                int cantidadUnosIdeal = indiIdeal.elementosSeleccionados().size();
                double porcentaje = ((double) parecido) / cantidadUnosIdeal;
                double porc_csc_lb = (cantidadSeleccionadosConsecutivos) / (double) lu_bound[1];
                sumaParecidoUpperTotal += porcentaje;
                sumaParecidoLowerTotal += porc_csc_lb;
                desv_sumaParecidoLowerTotal += porc_csc_lb * porc_csc_lb;
                sumaUltimoUpperTotal += (ultimoSeleccionado / (double) lu_bound[1]);
                System.out.println("----------------------------------");
                System.out.println("nombre archivo: " + nombreArchivo);
                System.out.println("parecido(# unos alcanzado): " + parecido);
                System.out.println("# unos Ideal: " + cantidadUnosIdeal);
                System.out.println("(# unos alcanzado)/(total unos ideal): " + porcentaje);
                System.out.println("lowerB: " + lu_bound[0] + "; upperB: " + lu_bound[1]);
                System.out.println("ultimo seleccionado (posicion): " + ultimoSeleccionado
                        + ";\n (ultimo seleccionado)/upper: " + (ultimoSeleccionado / (double) lu_bound[1]));
                System.out.println("ultimo seleccionado consecutivo (posicion): " + cantidadSeleccionadosConsecutivos
                        + ";\n (ultimo seleccionado consecutivo)/lowerB: " + (cantidadSeleccionadosConsecutivos / (double) lu_bound[0]));
                System.out.println("calidad alcanzado: " + indiAlcanzado.getCalidad());
                System.out.println("calidad ideal: " + indiIdeal.getCalidad());
                System.out.println("% (calidad alcanzado)/(calidad ideal): " + (indiAlcanzado.getCalidad() / indiIdeal.getCalidad()));
                contador++;
            }
        }
        System.out.println("\n##############################################");
        System.out.println("promedio % exito = (# unos alcanzado)/(total unos ideal): " + (sumaParecidoUpperTotal / contador));
        System.out.println("promedio % (ultimo seleccionado)/(upperB): " + (sumaUltimoUpperTotal / contador));
        double promedio_sumaParecidoLowerTotal = (sumaParecidoLowerTotal / contador);
        desv_sumaParecidoLowerTotal = Math.sqrt((1.0 / contador) * (desv_sumaParecidoLowerTotal - contador * promedio_sumaParecidoLowerTotal * promedio_sumaParecidoLowerTotal));
        System.out.println("promedio (ultimo seleccionado consecutivo)/(lowerB): " + promedio_sumaParecidoLowerTotal);
        System.out.println("desviacion (ultimo seleccionado consecutivo)/(lowerB): " + desv_sumaParecidoLowerTotal);
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

    public static int comparar4(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / o1.peso;
        Double resultado2 = o2.beneficio / o2.peso;
        int result = -Double.compare(resultado1, resultado2);
        if (result == 0) {
            return Double.compare(o1.peso,  o2.peso);
        }
        return result;
    }
}
