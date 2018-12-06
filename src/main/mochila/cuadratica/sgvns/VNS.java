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
package main.mochila.cuadratica.sgvns;

import java.util.ArrayList;
import java.util.List;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class VNS extends AlgoritmoMetaheuristico<FuncionSGVNS, IndividuoVNS> {

    double alpha;
    int intentosEncontrarMejor;
    int intentosIntercambio; // intentos de busqueda de elementos aptos para realizar intercambio
    int hMax;

    /**
     *
     * @param funcion
     * @param maxIteraciones
     */
    public VNS(FuncionSGVNS funcion, int maxIteraciones) {
        this.funcion = funcion;
        alpha = 1.0 / 30.0;
        this.maxIteraciones = maxIteraciones;
        nombre = "SGVNS";
        intentosIntercambio = 15;
        intentosEncontrarMejor = 20;
        hMax=5;
    }
    
    
    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getIntentosEncontrarMejor() {
        return intentosEncontrarMejor;
    }

    public void setIntentosEncontrarMejor(int intentosEncontrarMejor) {
        this.intentosEncontrarMejor = intentosEncontrarMejor;
    }

    public int getIntentosIntercambio() {
        return intentosIntercambio;
    }

    public void setIntentosIntercambio(int intentosIntercambio) {
        this.intentosIntercambio = intentosIntercambio;
    }

    /**
     *
     * @param funcion
     * @param maxIteraciones
     * @param alpha
     * @param hMax
     * @param intentosIntercambio
     * @param nombreAdicional
     * @param intentosEncontrarMejor
     */
    public VNS(FuncionSGVNS funcion, int maxIteraciones, double alpha, int hMax, int intentosIntercambio, int intentosEncontrarMejor, String nombreAdicional) {
        this.funcion = funcion;
        this.alpha = alpha;
        this.maxIteraciones = maxIteraciones;
        this.nombre = "SGVNS" + nombreAdicional;
        this.hMax = hMax;
        this.intentosIntercambio=intentosIntercambio;
        this.intentosEncontrarMejor=intentosEncontrarMejor;
    }

    /**
     *
     * @return
     */
    @Override
    public List<IndividuoVNS> ejecutar() {
        List<IndividuoVNS> recorrido = new ArrayList();
        IndividuoVNS y = solucionInicial();
        IndividuoVNS y_best = y;
        for (iteraciones = 0; iteraciones < maxIteraciones; iteraciones++) {
            int h = 1;
            IndividuoVNS y_p;
            IndividuoVNS y_p2;
            while (h <= hMax) {
                y_p = sacudida(y, 1, h);
                y_p2 = seq_VND(y_p);
                if (y_p2.compareTo(y_best) > 0) {
                    y_best = y_p2;
                }
                if (y_p2.getCalidad() > (1 - alpha * distancia(y, y_p2)) * y.getCalidad()) {
                    y = y_p2;
                    h = 1;
                } else {
                    h++;
                }
            }
            recorrido.add(y_best);
        }
        return recorrido;
    }

    public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
        int h = 1;
        IndividuoVNS s_inicial = individuoOriginal.clone();
        IndividuoVNS solEncontrada;
        while (h <= 2) {
            solEncontrada = encontrarMejor(s_inicial, h);
            if (solEncontrada.compareTo(s_inicial) > 0) {
                s_inicial = solEncontrada;
                h = 1;
            } else {
                h++;
            }
        }
        return s_inicial;
    }

    /**
     * obtiene el subconjunto de elementos fuera de la mochila, pero que
     * individualmente quepan dentro de esta.
     *
     * @param mochila
     * @return lista de elementos fuera de la mochila
     */
    public List<Integer> elementosFueraYCaben(IndividuoVNS mochila) {
        List listaI0 = mochila.elementosNoSeleccionados();
        listaI0 = funcion.filtrarPorFactibles(listaI0, mochila);
        return listaI0;
    }

    /**
     * obtiene un subconjunto de elementos dentro de la mochila.
     *
     * @param mochila
     * @return lista de elementos fuera de la mochila
     */
    public List<Integer> elementosDentro(IndividuoVNS mochila) {
        List listaI1 = mochila.elementosSeleccionados();
        return listaI1;
    }

    /**
     * realiza un cambio, agrega o remueve un elemento que esta fuera o dentro
     * correspondientemente, de manera aleatoria dentro de individuo
     *
     * @param individuo_original a quien se le realiza el cambio
     * @return
     */
    public IndividuoVNS cambio(IndividuoVNS individuo_original) {
        IndividuoVNS individuo = individuo_original.clone();
        List listaFuera = elementosFueraYCaben(individuo);
        List listaDentro = elementosDentro(individuo);
        int posicion;
        int aleatorio;
        // tipo de cambio aleatorio y de acuerdo a si la lista correspondiente tiene algun indice
        if ((Aleatorio.nextBoolean() || listaDentro.isEmpty()) && !listaFuera.isEmpty()) {
            // elegir posicion aleatoria
            aleatorio = Aleatorio.nextInt(listaFuera.size());
            // posicion del elemento dentro la mochila 
            posicion = (int) listaFuera.get(aleatorio);
            // realizar cambio
            individuo.set(posicion, 1);
        } else if (!listaDentro.isEmpty()) {
            // elegir posicion aleatoria
            aleatorio = Aleatorio.nextInt(listaDentro.size());
            // posicion del elemento dentro la mochila 
            posicion = (int) listaDentro.get(aleatorio);
            // realizar cambio
            individuo.set(posicion, 0);
        }
        return individuo;
    }

    /**
     * realiza un intercambio, agrega un elemento y remueve otro que esta fuera
     * y dentro correspondientemente, de manera aleatoria dentro de individuo,
     * siempre y cuando se pueda, es decir, que se pueda agragar un elemento
     * aleatorio despues de haber removido uno, de lo contrario no se hace nada.
     * se hace 10 intentos de busqueda de elementos aptos para realizar el
     * intercambio.
     *
     * @param individuo_original a quien se le realiza el intercambio
     * @return
     */
    public IndividuoVNS intercambio(IndividuoVNS individuo_original) {
        IndividuoVNS individuo = individuo_original.clone();

        List<Integer> listaItemFuera;
        List<Integer> listaItemDentro;
        boolean noHayElementosFuera;
        int contador = intentosIntercambio;
        do { // intentos de busqueda
            listaItemDentro = elementosDentro(individuo);
            int tamLDentro = listaItemDentro.size();
            int aleatorioD = Aleatorio.nextInt(tamLDentro);
            Integer posicionD = listaItemDentro.get(aleatorioD);
            // sacar elemento dentro de la mochila
            individuo.set(posicionD, 0);
            // obtener lista de elementos dentro de la mochila pero que caben dentro de esta
            listaItemFuera = elementosFueraYCaben(individuo);
            listaItemFuera.remove(posicionD);
            // verificar si hay elementos
            noHayElementosFuera = listaItemFuera.isEmpty();

            if (noHayElementosFuera) { // si no hay elementos fuera de la mochila que quepan
                individuo.set(posicionD, 1); // agregar elemento anteriormente removido
            }
        } while (noHayElementosFuera && contador-- >= 0);
        // salir si no hay elementos para intercambiar
        if (noHayElementosFuera) {
            return individuo;
        }
        int tamLDentro = listaItemFuera.size();
        int aleatorioF = Aleatorio.nextInt(tamLDentro);
        int posicionF = listaItemFuera.get(aleatorioF);
        // agregar elemento dentro de la mochila
        individuo.set(posicionF, 1);

        return individuo;
    }

    public IndividuoVNS solucionInicial() {
        IndividuoVNS individuo = funcion.generarIndividuo();

        int pos_mayor = buscarMayor(individuo);

        while (pos_mayor >= 0) {
            individuo.set(pos_mayor, 1);
            pos_mayor = buscarMayor(individuo);
        }
        return individuo;
    }

    public int buscarMayor(IndividuoVNS individuo) {
        double[] valores = individuo.getValores();
        double densidadMayor = Double.NEGATIVE_INFINITY;
        double densidad;
        int posicionMayor = -1;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] == 0) {
                densidad = funcion.densidad(i, individuo);
                if (densidadMayor < densidad && funcion.cabe(individuo, i)) {
                    posicionMayor = i;
                    densidadMayor = densidad;
                }
            }
        }
        return posicionMayor;
    }

    protected IndividuoVNS encontrarMejor(IndividuoVNS s_inicial, int h) {
        IndividuoVNS aux;
        s_inicial = s_inicial.clone();
        boolean mejoro;
        int contador = intentosEncontrarMejor;
        do {
            if (h == 1) {
                aux = intercambio(s_inicial);
            } else {
                aux = cambio(s_inicial);
            }
            mejoro = aux.compareTo(s_inicial) > 0;
            if (mejoro) {
                s_inicial = aux;
            }
        } while (!mejoro && contador-- >= 0);
        return s_inicial;
    }

    private IndividuoVNS sacudida(IndividuoVNS s_inicial, int vecindario, int intentos) {
        IndividuoVNS aux;
        s_inicial = s_inicial.clone();
        do {
            if (vecindario == 1) {
                aux = intercambio(s_inicial);
            } else {
                aux = cambio(s_inicial);
            }
            s_inicial = aux;
        } while (intentos-- >= 0);
        return s_inicial;
    }

    private double distancia(IndividuoVNS y, IndividuoVNS y2) {
        double suma = 0;
        for (int i = 0; i < y.getDimension(); i++) {
            suma += y.get(i) - y2.get(i);
//            suma += Math.abs(y.get(i) - y2.get(i));
        }
        return suma / y.getDimension();
    }
}
