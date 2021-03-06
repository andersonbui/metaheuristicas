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
package main.mochila.cuadratica.sgvns.busqueda_vecindad_variable;

import java.util.ArrayList;
import java.util.List;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class SGVNS extends AlgoritmoMetaheuristico<FuncionSGVNS_Original, IndividuoVNS> {

    public double alpha;
    int intentosEncontrarMejor;
    int intentosIntercambio; // intentos de busqueda de elementos aptos para realizar intercambio
    public int hMax;
    /**
     * lower bound: minimo numero de elementos que llenarian la mochila sin que
     * haya espacio para uno mas.
     */
    protected int lb;
    /**
     * upper bound: maximo número de elementos que llenarian la mochila sin que
     * haya espacio para uno mas.
     */
    protected int ub;
    boolean inicializado;

    public SGVNS(FuncionSGVNS_Original funcion, int maxIteraciones) {
        super(funcion);
        inicializado = false;
        this.funcion = funcion;
        this.maxIteraciones = maxIteraciones;
    }

    public SGVNS(FuncionSGVNS_Original funcionSGVNS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void inicializar() {
        inicializado = true;
        alpha = 1.0 / 30.0;
        nombre = "SGVNS";
        intentosIntercambio = 15;
        intentosEncontrarMejor = 20;
        hMax = 5;

        if (getCadenaParametros() != null) {
            String[] arrayParametros = getCadenaParametros().split(",");
            for (String arrayParametro : arrayParametros) {
                String[] unParametro = arrayParametro.split("=");
                actualizarVarible(unParametro[0], Integer.parseInt(unParametro[1]));
//                System.out.println("unParametro[0]: " + unParametro[0] + "; unParametro[1]: " + unParametro[1]);
            }
        }
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

//    /**
//     *
//     * @param funcion
//     * @param maxIteraciones
//     * @param alpha
//     * @param hMax
//     * @param intentosIntercambio
//     * @param nombreAdicional
//     * @param intentosEncontrarMejor
//     */
//    public SGVNS(FuncionSGVNS funcion, int maxIteraciones, double alpha, int hMax, int intentosIntercambio, int intentosEncontrarMejor, String nombreAdicional) {
//        this.funcion = funcion;
//        this.alpha = alpha;
//        this.maxIteraciones = maxIteraciones;
//        this.nombre = "SGVNS" + nombreAdicional;
//        this.hMax = hMax;
//        this.intentosIntercambio = intentosIntercambio;
//        this.intentosEncontrarMejor = intentosEncontrarMejor;
//    }
    /**
     *
     * @return
     */
    @Override
    public List<IndividuoVNS> ejecutar() {
        //Lista para graficar
        List<IndividuoVNS> recorrido = new ArrayList();
        //Encuentra una solucion inicial y
        IndividuoVNS y = solucionInicial();
        //Almacena la solucion inicial como best
        IndividuoVNS y_best = y;
        //termina cuando encuentra la el optimo
        boolean suficiente = funcion.suficiente(y_best);
        if (suficiente) {
            recorrido.add(y_best);
            return recorrido;
        }
        for (iteraciones = 0; iteraciones < maxIteraciones; iteraciones++) {
            //numero de movimientos aleatorios para shaking
            int h = 1;
            IndividuoVNS y_p;
            IndividuoVNS y_p2;
            while (h <= hMax) {
                //Genera una solución aleatoria y_p de y, para h en el vecindario cambio (diversidad)
                //Se le puede aplicar una B. Tabu para que no repita soluciones (movimientos)
                y_p = estructuraVecindarioSacudida(y, h);
                //Va de un vecindario a otro buscando encontrar una mejora a s_inicial
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
        //CONDICION DE TERMINACION PARA AJUSTAR
        return recorrido;
    }

    //Va de un vecindario a otro si no encuentra una mejota a s_inicial
    public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
        //Variable para el tipo de estructura de vecindario
        int h = 1;
        IndividuoVNS s_inicial = individuoOriginal.clone();
        IndividuoVNS solEncontrada;
        while (h <= 2) {
            //Aplica una estructura de vecindario h a la s_inicial para mejorar
            solEncontrada = encontrarMejor(s_inicial, h);
            /*Si mejora s_inicial permanece en h1 (si h=1) o se devuelve a h1 encaso que este en h2 (h=2),
            valida que no sea un optimo local(osea que exista una mejor solucion)*/
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
            if (listaItemDentro.isEmpty()) {
                return individuo;
            }
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

    /*Aplica la estructura de vecindario h a la s_inicial, un numero de intentos 
    determinado (intentosEncontrarMejor) y va comparando si s_inicial mejora*/
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
                break;
            }
        } while (contador-- >= 0);
        return s_inicial;
    }

    /*Sacudida genera una solucion aleatoria y' realizando h(intentos) movimientos
    en el segundo vecindario (cambio) de la solucion y(s_inicial)*/
    protected IndividuoVNS sacudida(IndividuoVNS s_inicial, int vecindario, int intentos) {
        IndividuoVNS aux;
        boolean mejoro;
        s_inicial = s_inicial.clone();
        intentos = Math.min(intentos, s_inicial.elementosSeleccionados().size() - 1);
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

    protected double distancia(IndividuoVNS y, IndividuoVNS y2) {
        double suma = 0;
        for (int i = 0; i < y.getDimension(); i++) {
//            suma += y.get(i) - y2.get(i);
            suma += Math.abs(y.get(i) - y2.get(i));
        }
        return suma / y.getDimension();
    }

    public IndividuoVNS estructuraVecindarioSacudida(IndividuoVNS s_inicial, int intentos) {
        IndividuoVNS y = null;
        y = sacudida(s_inicial, 2, intentos);
        return y;
    }

    public void actualizarVarible(String nombre, int valor) {
        switch (nombre) {
            case "i_interc":
                intentosIntercambio = valor;
                break;
            case "i_encontrarM":
                intentosEncontrarMejor = valor;
                break;
        }
    }
}
