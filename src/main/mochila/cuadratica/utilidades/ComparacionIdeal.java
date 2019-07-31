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
import main.utilidades.Utilidades;

/**
 *
 * @author debian
 */
public class ComparacionIdeal {

    static TipoEstadistica[] tiposEstadist;
    static String formato = "-1.10";
//    static String formato = "-15.10";

    public ComparacionIdeal() {
    }

    public void imprimir() {

    }

    public static class TipoEstadistica {

        String corto;
        String largo;
        boolean estadisticas;

        public TipoEstadistica() {
            estadisticas = false;
        }
    }

    public static class DatoCalculo {

        private double[] valores;

        public DatoCalculo() {
            valores = new double[tiposEstadist.length];
        }

        public DatoCalculo(double[] valores) {
            this.valores = valores;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < valores.length; i++) {
                sb.append(String.format("%" + formato + "f|", valores[i]));
            }
            return sb.toString();
        }

        public double[] getValores() {
            return valores;
        }

        public void setValores(double[] valores) {
            this.valores = valores;
        }

    }

    public static Datos comparacion(instanciasAlgoritmo parametros, IndividuoCuadratico indiAlcanzado, List<Main_MetododosInicializacion.Posicion> posiciones) {
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
        if (tiposEstadist == null) {
            tiposEstadist = new TipoEstadistica[15];
        }
        int a = -1;
        int cantidadUnosAlcanzado = indiAlcanzado.parecido(indiIdeal);
        int[] lu_bound = UtilCuadratica.optenerLowerUpper_Bound(funcion);
        double lowerB = lu_bound[0];
        double upperB = lu_bound[1];
        int cantidadUnosIdeal = indiIdeal.elementosSeleccionados().size();
        double[] valores = new double[tiposEstadist.length];

//        System.out.println("----------------------------------");
//        System.out.println("nombre archivo: " + parametros.getNombreInstancia());
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "tamanio instancia";
        tiposEstadist[a].corto = "TI";
        valores[a] = valsIdeal.length;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "parecido (# unos alcanzado)";
        tiposEstadist[a].corto = "#UA";
        valores[a] = cantidadUnosAlcanzado;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "# unos Ideal";
        tiposEstadist[a].corto = "#UI";
        valores[a] = cantidadUnosIdeal;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "LowerBound";
        tiposEstadist[a].corto = "LB";
        valores[a] = lowerB;
        // 
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "UpperBound";
        tiposEstadist[a].corto = "UB";
        valores[a] = upperB;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "calidad alcanzado";
        tiposEstadist[a].corto = "CA";
        valores[a] = indiAlcanzado.getCalidad();
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "calidad ideal";
        tiposEstadist[a].corto = "CI";
        valores[a] = indiIdeal.getCalidad();
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "(calidad alcanzado)/(calidad ideal)";
        tiposEstadist[a].corto = "CA/CI";
        tiposEstadist[a].estadisticas = true;
        valores[a] = (indiAlcanzado.getCalidad() / indiIdeal.getCalidad());
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "(#_unos_alcanzado)/(#_unos_ideal)";
        tiposEstadist[a].corto = "#UA/#UI";
        tiposEstadist[a].estadisticas = true;
        valores[a] = ((double) cantidadUnosAlcanzado) / cantidadUnosIdeal;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "ultimo seleccionado (posicion)";
        tiposEstadist[a].corto = "US";
        valores[a] = ultimoSeleccionado;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "(ultimo seleccionado)/UpperBound";
        tiposEstadist[a].corto = "US/UB";
        tiposEstadist[a].estadisticas = true;
        valores[a] = (ultimoSeleccionado / (double) upperB);
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "(ultimoSeleccionado)/(lowerB + (upperB - lowerB) / 2)";
        tiposEstadist[a].corto = "US/prom(LB,UB)";
        tiposEstadist[a].estadisticas = true;
        valores[a] = (ultimoSeleccionado / (double) (lowerB + (upperB - (double) lowerB) / 2));
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "ultimo seleccionado consecutivo (posicion)";
        tiposEstadist[a].corto = "USC";
        valores[a] = cantidadSeleccionadosConsecutivos;
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "(cantidadSeleccionadosConsecutivos)/(lowerB + (upperB - lowerB) / 2)";
        tiposEstadist[a].corto = "CSC/prom(LB,UB)";
        tiposEstadist[a].estadisticas = true;
        valores[a] = (cantidadSeleccionadosConsecutivos / (double) (lowerB + (upperB - (double) lowerB) / 2));
        //
        a++;
        tiposEstadist[a] = new TipoEstadistica();
        tiposEstadist[a].largo = "(ultimo seleccionado consecutivo)/lowerB";
        tiposEstadist[a].corto = "USC/LB";
        tiposEstadist[a].estadisticas = true;
        double porc_csc_lb = (cantidadSeleccionadosConsecutivos) / (double) lowerB;
        valores[a] = porc_csc_lb;

        DatoCalculo calculo = new DatoCalculo(valores);
        //System.out.println(calculo);

        Datos datos = new Datos(indiAlcanzado.getCalidad(), indiIdeal.getCalidad(),
                cantidadUnosAlcanzado, cantidadUnosIdeal, lu_bound[0], lu_bound[1],
                cantidadSeleccionadosConsecutivos, ultimoSeleccionado, parametros, calculo);

        return datos;
    }

    public static String imprimirCabecera() {

        StringBuilder sb = new StringBuilder();

        for (TipoEstadistica tipoEst : tiposEstadist) {
            sb.append(String.format(tipoEst.corto + ": " + tipoEst.largo + "\n"));
        }
        sb.append("==================================\n");
        for (TipoEstadistica nombreEstaDistica : tiposEstadist) {
            sb.append(String.format(nombreEstaDistica.corto + "|"));
        }
        return sb.toString();
    }

    /**
     * cuenta el numero de elementos que si/no pertenercen a la solucion ideal
     *
     * @param parametros donde se encuentra el ideal
     * @param indices indices de los valores en los cuales se busca el valor
     * @param valor valor el cual se cuenta cuanta coincidencias existe en
     * individuo ideal
     * @return
     */
    public static int cuentaValorEnIdeal(instanciasAlgoritmo parametros, List<Integer> indices, int valor, String mensaje) {
        int contador = 0;
        if (indices == null || indices.size() == 0) {
            return 0;
        }
        int[] valsIdeal = parametros.getVectorIdeal();
        if (valsIdeal == null) {
//            System.out.println("No hay ideal.");
            return -1;
        }

        for (Integer i : indices) {
            if (valsIdeal[i] == valor) {
                contador++;
            }
        }

        if (contador > 0) {
            System.out.println("cuantos[" + valor + "] " + ": " + contador + " - " + mensaje);

        }
        return contador;
    }

    public static void estadisticas(List<Datos> listaDatos) {

        System.out.println("\n##############################################");
        for (int i = 0; i < tiposEstadist.length; i++) {
            if (tiposEstadist[i].estadisticas) {
                List<Double> lista = new ArrayList();
                for (int k = 0; k < listaDatos.size(); k++) {
                    lista.add(listaDatos.get(k).calculo.valores[i]);
                }
                double promedio = Utilidades.promedio(lista);
                System.out.println("promedio " + tiposEstadist[i].corto + ": " + promedio);
                System.out.println("desviacion " + tiposEstadist[i].corto + ": " + Utilidades.desviacion(lista, promedio));
            }
        }
    }

    public static class Datos {

        public instanciasAlgoritmo parametros;
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
                int upperB, int cantSelConsecutivo, int ultSeleccionado, instanciasAlgoritmo parametros, DatoCalculo calculo) {
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
