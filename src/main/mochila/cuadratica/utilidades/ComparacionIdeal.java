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

/**
 *
 * @author debian
 */
public class ComparacionIdeal {

    public static Datos comparacion(ParametrosCuadratica parametros, IndividuoCuadratico indiAlcanzado) {
        FuncionMochilaCuadratica funcion = (FuncionMochilaCuadratica) indiAlcanzado.getFuncion();
        String nombreArchivo = parametros.getNombreArchivo();
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

        // lista de indices para ordenamiento
        List<Main_MetododosInicializacion.Posicion> posiciones = new ArrayList();
        // crear estructura de comparacion
        for (int i = 0; i < funcion.getDimension(); i++) {
            double peso = funcion.peso(i);
            double relacion = funcion.relaciones(i);
            double beneficio = funcion.beneficio(i);
            posiciones.add(new Main_MetododosInicializacion.Posicion(i, peso, relacion, beneficio));
        }

        // ordenar de acuerdo a la estrctura anterior
        Collections.sort(posiciones, (Main_MetododosInicializacion.Posicion o1, Main_MetododosInicializacion.Posicion o2) -> {
            return comparar4(o1, o2);
        });
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
        int cantidadUnosAlcanzado = indiAlcanzado.parecido(indiIdeal);
        int[] lu_bound = UtilCuadratica.optenerLowerUpper_Bound(funcion);
        double lowerB = lu_bound[0];
        double upperB = lu_bound[1];
        int cantidadUnosIdeal = indiIdeal.elementosSeleccionados().size();
        double porc_csc_lb = (cantidadSeleccionadosConsecutivos) / (double) lowerB;
        double porcentajeParecido = ((double) cantidadUnosAlcanzado) / cantidadUnosIdeal;
        System.out.println("----------------------------------");
        System.out.println("nombre archivo: " + nombreArchivo);
        System.out.println("parecido(# unos alcanzado): " + cantidadUnosAlcanzado);
        System.out.println("# unos Ideal: " + cantidadUnosIdeal);
        System.out.println("(# unos alcanzado)/(total unos ideal): " + porcentajeParecido);
        System.out.println("lowerB: " + lowerB + "; upperB: " + upperB);
        System.out.println("ultimo seleccionado (posicion): " + ultimoSeleccionado
                + ";\n (ultimo seleccionado)/upper: " + (ultimoSeleccionado / (double) upperB));
        System.out.println("ultimo seleccionado consecutivo (posicion): " + cantidadSeleccionadosConsecutivos
                + ";\n (ultimo seleccionado consecutivo)/lowerB: " + porc_csc_lb);
        System.out.println("calidad alcanzado: " + indiAlcanzado.getCalidad());
        System.out.println("calidad ideal: " + indiIdeal.getCalidad());
        System.out.println("% (calidad alcanzado)/(calidad ideal): " + (indiAlcanzado.getCalidad() / indiIdeal.getCalidad()));
//        contador++;

        Datos datos = new Datos(indiAlcanzado.getCalidad(), indiIdeal.getCalidad(),
                cantidadUnosAlcanzado, cantidadUnosIdeal, lu_bound[0], lu_bound[1],
                cantidadSeleccionadosConsecutivos, ultimoSeleccionado);

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
        double porcen_calidad_a_i = 0;
        double porcen_cantidadUnos_a_i = 0;
        double csc_ub;
        double promedio_csc_ub = 0;
        double desv_csc_ub = 0;
        double us_ub;
        double promedio_us_ub = 0;
        double desv_us_ub = 0;
        int contador = 0;
        for (Datos dato : listaDatos) {
            porcen_calidad_a_i += dato.calidadAlcanzado / dato.calidadIdeal;
            porcen_cantidadUnos_a_i += dato.cantidadUnosAlcanzado / (double) dato.cantidadUnosIdeal;
            us_ub = dato.ultSeleccionado / (double) dato.upperB;
            promedio_us_ub += us_ub;
            desv_us_ub += us_ub * us_ub;

            csc_ub = dato.cantSelConsecutivo / (double) dato.upperB;
            promedio_csc_ub += csc_ub;
            desv_csc_ub += csc_ub * csc_ub;

            contador++;
        }
        System.out.println("\n##############################################");
        System.out.println("promedio (calidad alcanzado)/(calidad ideal): " + (porcen_calidad_a_i / contador));
        System.out.println("promedio (# unos alcanzado)/(total unos ideal): " + (porcen_cantidadUnos_a_i / contador));
        promedio_us_ub = (promedio_us_ub / contador);
        desv_us_ub = Math.sqrt((1.0 / contador) * (desv_us_ub - contador * promedio_us_ub * promedio_us_ub));
        System.out.println("promedio (ultimo seleccionado)/(upperB): " + (promedio_us_ub));
        System.out.println("desviacion (ultimo seleccionado)/(upperB): " + desv_us_ub);
        promedio_csc_ub = (promedio_csc_ub / contador);
        desv_csc_ub = Math.sqrt((1.0 / contador) * (desv_csc_ub - contador * promedio_csc_ub * promedio_csc_ub));
        System.out.println("promedio (ultimo seleccionado consecutivo)/(upperB): " + promedio_csc_ub);
        System.out.println("desviacion (ultimo seleccionado consecutivo)/(upperB): " + desv_csc_ub);
    }

    public static class Datos {

        public double calidadAlcanzado;
        public double calidadIdeal;
        public int cantidadUnosAlcanzado;
        public int cantidadUnosIdeal;
        public int lowerB;
        public int upperB;
        /**
         * cantidad Seleccionados Consecutivos
         */
        public double cantSelConsecutivo;
        /**
         * ultimo seleccionado
         */
        public int ultSeleccionado;

        public Datos(double calidadAlcanzado, double calidadIdeal,
                int cantidadUnosAlcanzado, int cantidadUnosIdeal, int lowerB,
                int upperB, double cantSelConsecutivo, int ultSeleccionado) {
            this.calidadAlcanzado = calidadAlcanzado;
            this.calidadIdeal = calidadIdeal;
            this.cantidadUnosAlcanzado = cantidadUnosAlcanzado;
            this.cantidadUnosIdeal = cantidadUnosIdeal;
            this.lowerB = lowerB;
            this.upperB = upperB;
            this.cantSelConsecutivo = cantSelConsecutivo;
            this.ultSeleccionado = ultSeleccionado;
        }

    }
}
