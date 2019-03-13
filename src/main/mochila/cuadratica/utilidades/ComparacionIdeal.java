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
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;
import static main.mochila.cuadratica.utilidades.Main_MetododosInicializacion.comparar4;
import static main.mochila.cuadratica.utilidades.Main_MetododosInicializacion.comparar3;
import static main.mochila.cuadratica.utilidades.Main_MetododosInicializacion.comparar2;
import static main.mochila.cuadratica.utilidades.Main_MetododosInicializacion.comparar;
import main.utilidades.Utilidades;

/**
 *
 * @author debian
 */
public class ComparacionIdeal {

    static String[] metricas;
//    static int[] estadisticas;

    public ComparacionIdeal() {
    }

    public void imprimir() {

    }

    static class DatoCalculo {

        double[] valores;

        public DatoCalculo() {
            valores = new double[metricas.length];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < valores.length; i++) {
                sb.append(metricas[i]).append(": ").append(valores[i]);
            }
            return sb.toString();
        }

    }

    public static Datos comparacion(ParametrosCuadratica parametros, IndividuoCuadratico indiAlcanzado, List<Main_MetododosInicializacion.Posicion> posiciones) {
        FuncionMochilaCuadratica funcion = (FuncionMochilaCuadratica) indiAlcanzado.getFuncion();
        int ultimoSeleccionado = 0;

        //si no hay individuo ideal
        int[] valsIdeal = parametros.getVectorIdeal();
        if (valsIdeal == null) {
            return null;
        }
        IndividuoCuadratico indiIdeal = funcion.generarIndividuo();
        // crear individuo ideal
        for (int i = 0; i < valsIdeal.length; i++) {
            indiIdeal.set(i, valsIdeal[i]);
        }

//        // lista de indices para ordenamiento
//        List<Main_MetododosInicializacion.Posicion> posiciones = new ArrayList();
//        // crear estructura de comparacion
//        for (int i = 0; i < funcion.getDimension(); i++) {
//            double peso = funcion.peso(i);
//            double relacion = funcion.relaciones(i);
//            double beneficio = funcion.beneficio(i);
//            posiciones.add(new Main_MetododosInicializacion.Posicion(i, peso, relacion, beneficio));
//        }
//
//        // ordenar de acuerdo a la estrctura anterior
//        Collections.sort(posiciones, (Main_MetododosInicializacion.Posicion o1, Main_MetododosInicializacion.Posicion o2) -> {
//            return comparar3(o1, o2);
//        });

        // obtener cantidad de unos consecutivos en el individuo encontrado con respecto al ideal
        int cantidadSeleccionadosConsecutivos = 0;
        for (int i = 0; i < posiciones.size(); i++) {
            int pos = posiciones.get(i).posicion;
            if (valsIdeal[pos] == 1) {
                cantidadSeleccionadosConsecutivos = i;
            } else {
                break;
            }
        }
        // obtener la cantidad de unos en el individuo encontrado con respecto al ideal
        for (int i = 0; i < posiciones.size(); i++) {
            int pos = posiciones.get(i).posicion;
            if (valsIdeal[pos] == 1) {
                ultimoSeleccionado = i;
            }
        }
        if(metricas == null){
            metricas = new String[5];
        }
        int a = -1;
        DatoCalculo calculo = new DatoCalculo();
        int cantidadUnosAlcanzado = indiAlcanzado.parecido(indiIdeal);
        int[] lu_bound = UtilCuadratica.optenerLowerUpper_Bound(funcion);
        double lowerB = lu_bound[0];
        double upperB = lu_bound[1];
        int cantidadUnosIdeal = indiIdeal.elementosSeleccionados().size();
        double porc_csc_lb = (cantidadSeleccionadosConsecutivos) / (double) lowerB;

        System.out.println("----------------------------------");
        System.out.println("nombre archivo: " + parametros.getNombreInstancia());
        //
        System.out.println("parecido (# unos alcanzado)" + cantidadUnosAlcanzado);
        //
        System.out.println("# unos Ideal" + cantidadUnosIdeal);
        //
        System.out.println("lowerB: " + lowerB + "; upperB: " + upperB);
        //
        System.out.println("calidad alcanzado: " + indiAlcanzado.getCalidad());
        //
        System.out.println("calidad ideal: " + indiIdeal.getCalidad());
        //
        a++;
        metricas[a] = "(calidad alcanzado)/(calidad ideal)";
        calculo.valores[a] = (indiAlcanzado.getCalidad() / indiIdeal.getCalidad());
        System.out.println(metricas[a] + ": " + calculo.valores[a]);
        //
        a++;
        metricas[a] = "(#_unos_alcanzado)/(#_unos_ideal)";
        calculo.valores[a] = ((double) cantidadUnosAlcanzado) / cantidadUnosIdeal;
        System.out.println(metricas[a] + ": " + calculo.valores[a]);
        //
        System.out.println("ultimo seleccionado (posicion): " + ultimoSeleccionado);
        //
        a++;
        metricas[a] = "(ultimo seleccionado)/upper";
        calculo.valores[a] = (ultimoSeleccionado / (double) upperB);
        System.out.println(metricas[a] + ": " + calculo.valores[a]);
        //
        a++;
        metricas[a] = "(cantidadSeleccionadosConsecutivos)/(lowerB + (upperB - lowerB) / 2)";
        calculo.valores[a] = (cantidadSeleccionadosConsecutivos / (double) (lowerB + (upperB - (double)lowerB) / 2));
        System.out.println(metricas[a] + ": " + calculo.valores[a]);
        //
        System.out.println("ultimo seleccionado consecutivo (posicion)"+ cantidadSeleccionadosConsecutivos);
        //
        a++;
        metricas[a] = "(ultimo seleccionado consecutivo)/lowerB";
        calculo.valores[a] = porc_csc_lb;
        System.out.println(metricas[a] + ": " + calculo.valores[a]);

        Datos datos = new Datos(indiAlcanzado.getCalidad(), indiIdeal.getCalidad(),
                cantidadUnosAlcanzado, cantidadUnosIdeal, lu_bound[0], lu_bound[1],
                cantidadSeleccionadosConsecutivos, ultimoSeleccionado, parametros, calculo);

        return datos;
    }

    /**
     * cuenta el numero de posiciones que no pertenercen a un uno(1) en el mejor
     * ideal
     *
     * @param parametros
     * @param indiAlcanzado
     * @return
     */
    public static int cuantosNoEstanEnMejor(ParametrosCuadratica parametros, List<Integer> indiAlcanzado) {
        int contador = 0;

        int[] valsIdeal = parametros.getVectorIdeal();
        if (valsIdeal == null) {
//            System.out.println("No hay ideal.");
            return -1;
        }
        List<Integer> posicionesUno = new ArrayList();
        // crear individuo ideal
        for (Integer i = 0; i < valsIdeal.length; i++) {
            if (valsIdeal[i] == 1) {
                posicionesUno.add(i);
//                        ultimoSeleccionado = i;
            }
        }

        for (Integer i : indiAlcanzado) {
            if (!posicionesUno.contains(i)) {
                contador++;
            }
        }
        return contador;
    }

    public static void estadisticas(List<Datos> listaDatos) {

        System.out.println("\n##############################################");
        for (int i = 0; i < metricas.length; i++) {
//            if (estadisticas[i] == 1) {
                List<Double> lista = new ArrayList();
                for (int k = 0; k < listaDatos.size(); k++) {
                    lista.add(listaDatos.get(k).calculo.valores[i]);
                }
                double promedio = Utilidades.promedio(lista);
                System.out.println("promedio " + metricas[i] + ": " + promedio);
                System.out.println("desviacion " + metricas[i] + ": " + Utilidades.desviacion(lista, promedio));
//            }
        }
    }

    public static class Datos {

        public ParametrosCuadratica parametros;
        public double calidadAlcanzado;
        public double calidadIdeal;
        public int cantidadUnosAlcanzado;
        public int cantidadUnosIdeal;
        public int lowerB;
        public int upperB;
        /**
         * cantidad Seleccionados Consecutivos
         */
        public int cantSelConsecutivo;
        /**
         * ultimo seleccionado
         */
        public int ultSeleccionado;
        public DatoCalculo calculo;

        /**
         *
         * @param calidadAlcanzado
         * @param calidadIdeal
         * @param cantidadUnosAlcanzado
         * @param cantidadUnosIdeal
         * @param lowerB
         * @param upperB
         * @param cantSelConsecutivo
         * @param ultSeleccionado
         * @param parametros
         * @param calculo
         */
        public Datos(double calidadAlcanzado, double calidadIdeal,
                int cantidadUnosAlcanzado, int cantidadUnosIdeal, int lowerB,
                int upperB, int cantSelConsecutivo, int ultSeleccionado, ParametrosCuadratica parametros, DatoCalculo calculo) {
            this.calidadAlcanzado = calidadAlcanzado;
            this.calidadIdeal = calidadIdeal;
            this.cantidadUnosAlcanzado = cantidadUnosAlcanzado;
            this.cantidadUnosIdeal = cantidadUnosIdeal;
            this.lowerB = lowerB;
            this.upperB = upperB;
            this.cantSelConsecutivo = cantSelConsecutivo;
            this.ultSeleccionado = ultSeleccionado;
            this.parametros = parametros;
            this.calculo = calculo;
        }

    }
}
