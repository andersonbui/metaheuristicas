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
package main.mochila.cuadratica.IHEA;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.utilidades.ComparacionIdeal;
import main.mochila.cuadratica.utilidades.InstanciaAlgoritmo;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import metaheuristicas.DepuracionInterface;

/**
 *
 * @author debian
 */
public class Depuracion implements DepuracionInterface {

    private double contadorFijosFalsosPositivos;
    private double contadorFijosFalsosNegativos;
    private int contadorIntercambios; // contador de intercambios dentro de la busqueda exaustiva de tabuSearch (estadistica)
    protected int tiempototal; // tiempo total que toma la busqueda tabu
    protected int contadortabu; // Contador de veces que se usa tabuSearch (estadistica)
    protected int klb;
    protected int kub;
    protected int n;
    protected int capacidad;
    protected int pesoTotal;
    protected List<DatoDep> listaDatos;

    public static class DatoDep {

        protected int k;
        protected int pesoActual;
        protected int unosConsecutivos;
        protected int cantidadSeleccionados;
        protected int unosCansecutivos2;
        protected double falsosPositivos;
        protected double falsosNegativos;
        protected int cantidadIgnoradosConsecutivos;
        private final int cantidadParecidoIdeal;

        public DatoDep(
                int k, int pesoActual,
                int unosConsecutivos,
                double falsosPositivos,
                double falsosNegativos,
                int cantidadSeleccionados,
                int unosCansecutivos2,
                int cantidadIgnoradosConsecutivos,
                int cantidadParecidoIdeal
        ) {
            this.k = k;
            this.pesoActual = pesoActual;
            this.unosConsecutivos = unosConsecutivos;
            this.falsosPositivos = falsosPositivos;
            this.falsosNegativos = falsosNegativos;
            this.cantidadSeleccionados = cantidadSeleccionados;
            this.unosCansecutivos2 = unosCansecutivos2;
            this.cantidadIgnoradosConsecutivos = cantidadIgnoradosConsecutivos;
            this.cantidadParecidoIdeal = cantidadParecidoIdeal;
        }

        @Override
        public String toString() {
            //FP|FN|k|PA|UC|CS|UC2|CIC|CPI
            return falsosPositivos + "|" + falsosNegativos + "|" + k + '|' + pesoActual + '|' + 
                    unosConsecutivos + '|' + cantidadSeleccionados + '|' + unosCansecutivos2 + '|' + 
                    cantidadIgnoradosConsecutivos + '|' + cantidadParecidoIdeal;
        }
    }

    public Depuracion() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        contadorFijosFalsosPositivos = 0;
        contadorFijosFalsosNegativos = 0;
        tiempototal = 0;
        contadortabu = 0;
        contadorIntercambios = 0;
        listaDatos = new ArrayList();

    }

    public double getContadorFijosFalsosPositivos() {
        return contadorFijosFalsosPositivos;
    }

    public void setContadorFijosFalsosPositivos(double contadorFijosFalsosPositivos) {
        this.contadorFijosFalsosPositivos = contadorFijosFalsosPositivos;
    }

    public double getContadorFijosFalsosNegativos() {
        return contadorFijosFalsosNegativos;
    }

    public void setContadorFijosFalsosNegativos(double contadorFijosFalsosNegativos) {
        this.contadorFijosFalsosNegativos = contadorFijosFalsosNegativos;
    }

    public int getContadorIntercambios() {
        return contadorIntercambios;
    }

    public void setContadorIntercambios(int contadorIntercambios) {
        this.contadorIntercambios = contadorIntercambios;
    }

    public int getTiempototal() {
        return tiempototal;
    }

    public void setTiempototal(int tiempototal) {
        this.tiempototal = tiempototal;
    }

    public int getContadortabu() {
        return contadortabu;
    }

    public void setContadortabu(int contadortabu) {
        this.contadortabu = contadortabu;
    }

    public void evaluarVariablesFijas(InstanciaAlgoritmo instancias, List<Integer> variablesFijas, int tamanio, IndividuoIHEA individuo) {
        int fpos = ComparacionIdeal.cuentaValorEnIdeal(instancias, variablesFijas, 0);// "cuanto son ceros"
        if (fpos > 0) {
            setContadorFijosFalsosPositivos(fpos + getContadorFijosFalsosPositivos());
        }

        List<Integer> variablesNoFijas = new ArrayList<>();
        for (int i = 0; i < tamanio; i++) {
            variablesNoFijas.add(i);
        }
        variablesNoFijas.removeAll(variablesFijas);

        int fneg = ComparacionIdeal.cuentaValorEnIdeal(instancias, variablesNoFijas, 1);// "cuanto son unos"
        if (fneg > 0) {
            setContadorFijosFalsosNegativos(fneg + getContadorFijosFalsosNegativos());
        }

        if (instancias.getVectorIdeal() != null) {
            individuo.toStringBinarioEspaciado();
            // items seleccionados
            List<Integer> seleccionados = individuo.elementosSeleccionados();
            // obtener los primeros nf indices de los elementos más densos
            List<Integer> seleccOrdenados = (new PrimerosPorDensidad()).primerosPorDensidad2(seleccionados, individuo, seleccionados.size(), false);
            // obtener cantidad de unos consecutivos en el individuo encontrado con respecto al ideal
            int cantidadSeleccionadosConsecutivos = ComparacionIdeal.cuentaValorEnIdealConsecutivos(instancias, seleccOrdenados, 1);

            // obtener los primeros nf indices de los elementos con mayor beneficio crudo
            List<Integer> seleccOrdenadosCrudo = (new PrimerosPorDensidad()).primerosPorBeneficioCrudo(seleccionados, individuo, seleccionados.size(), false);
            // obtener cantidad de unos consecutivos en el individuo encontrado con respecto al ideal
            int cantidadSeleccionadosConsecutivosBenefCrudo = ComparacionIdeal.cuentaValorEnIdealConsecutivos(instancias, seleccOrdenadosCrudo, 1);
            
            seleccOrdenados = (new PrimerosPorDensidad()).primerosPorPeso(seleccionados, individuo, seleccionados.size(), false);
            int unosConsecutivos2 = ComparacionIdeal.cuentaValorEnIdealConsecutivos(instancias, seleccOrdenados, 1);

            int cantidadParecidoIdeal = ComparacionIdeal.contarValores(instancias.getVectorIdeal(), seleccOrdenados, 1);
            
            List<Integer> NOSeleccionados = individuo.elementosNoSeleccionados();
            List<Integer> seleccNOOrdenados = (new PrimerosPorDensidad()).primerosPorDensidad2(NOSeleccionados, individuo, NOSeleccionados.size(), true);
            int cantidadIgnoradosConsecutivos = ComparacionIdeal.contarValoresConsecutivos(instancias.getVectorIdeal(), seleccNOOrdenados, 0);
            
            this.addDatosActual(
                    seleccionados.size(),
                    (int) individuo.pesar(),
                    cantidadSeleccionadosConsecutivos,
                    fpos, fneg,
                    variablesFijas.size(),
                    unosConsecutivos2,
                    cantidadIgnoradosConsecutivos,
                    cantidadParecidoIdeal
            );

        }
    }

    @Override
    public String imprimir() {
        return this.imprimirDatos();
//        return this.toString();
    }

    @Override
    public String toString() {
        //FP|FN|n|klb|kub|c|PT
        return contadorFijosFalsosPositivos + "|" + contadorFijosFalsosNegativos + "|" + n + '|' + klb + '|' + kub + '|' + capacidad + '|' + pesoTotal;
    }

    public int getKlb() {
        return klb;
    }

    public void setKlb(int klb) {
        this.klb = klb;
    }

    public int getKub() {
        return kub;
    }

    public void setKub(int kub) {
        this.kub = kub;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void addDatosActual(int k, int pesoActual, int unosConsecutivos, double falsosPositivos, double falsosNegativos, int cantidadSeleccionados, int cantiSelecPeso,
            int cerosConsecutivos,
            int cantidadParecidoIdeal) {
        listaDatos.add(new DatoDep(k, pesoActual, unosConsecutivos, falsosPositivos, falsosNegativos, cantidadSeleccionados, cantiSelecPeso, cerosConsecutivos, cantidadParecidoIdeal));
    }

    public String imprimirDatos() {
        //FP|FN|k|PA|UC|CS|UC2|CIC|CPI|n|klb|kub|c|PT|
        StringBuilder sb = new StringBuilder();
        for (DatoDep dato : listaDatos) {
            sb.append(dato.toString()).append('|').append(n).append('|').append(klb).append('|').
                    append(kub).append('|').append(capacidad).append('|').append(pesoTotal).append('\n');
        }
        return sb.toString();
    }

    public int getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(int pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

}
