/*
 * Copyright (C) 2019 Juan Diaz PC
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
import main.Item;
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import main.mochila.cuadratica.utilidades.UtilCuadratica;
import main.utilidades.Utilidades;
import metaheuristicas.Aleatorio;

/**
 *
 * @author Juan Diaz PC
 */
public class JSGVNS extends SGVNS {

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

    public JSGVNS(FuncionJSGVNS funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
        lb = -1;
        ub = -1;
        lb = obtenerLowerBound();
        ub = obtenerUpperBound();
        nombre = "JSGVNS";
    }

    /**
     * obtiene un subconjunto de elementos dentro de la mochila.
     *
     * @param mochila
     * @return lista de elementos fuera de la mochila
     */
    @Override
    public List<Integer> elementosDentro(IndividuoVNS mochila) {
        return getFuncion().obtener_I1(mochila);
    }
    /**
     * obtiene el subconjunto de elementos fuera de la mochila, pero que
     * individualmente quepan dentro de esta.
     *
     * @param mochila
     * @return lista de elementos fuera de la mochila
     */
    @Override
    public List<Integer> elementosFueraYCaben(IndividuoVNS mochila) {
        List listaI0 = mochila.obtener_I0();
        listaI0 = funcion.filtrarPorFactibles(listaI0, mochila);
        return listaI0;
    }

    @Override
    public List<IndividuoVNS> ejecutar() {
        //Lista para graficar
        List<IndividuoVNS> recorrido = new ArrayList();
        //Encuentra una solucion inicial y
        IndividuoVNS y = solucionInicial();
        //Almacena la solucion inicial como best
        IndividuoVNS y_best = y;
        //termina cuando encuentra el optimo
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
            List<Integer> variablesFijas;
//            funcion.
            while (h <= hMax) {
                //Genera una solución aleatoria y_p de y, para h en el vecindario cambio (diversidad)
                //Se le puede aplicar una B. Tabu para que no repita soluciones (movimientos)

//                variablesFijas = determinarVariablesFijas(y.getDimension(), y, lb);
//                construirProblemaRestringidoReducido(variablesFijas);
                //Modificacion trabajar la sacudida con el vecindario 1
                y_p = sacudida(y, 1, h);
                //Va de un vecindario a otro buscando encontrar una mejora a s_inicial
                y_p2 = seq_VND(y_p);
//                getFuncion().reiniciarVijarVariables();
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

    /*Sacudida genera una solucion aleatoria y' realizando h(intentos) movimientos
    en el segundo vecindario (cambio) de la solucion y(s_inicial)*/
    private IndividuoVNS sacudida(IndividuoVNS s_inicial, int vecindario, int intentos) {
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
            //MODIFICACION
            mejoro = aux.compareTo(s_inicial) > 0;
            if (mejoro) {
                s_inicial = aux;
                break;
                //TODO: puede no salir con el break y continuar con la ejecucion pero al final retornar la mejor
            }
            //
            s_inicial = aux;

        } while (intentos-- >= 0);
        return s_inicial;
    }

    @Override
    public FuncionJSGVNS getFuncion() {
        return (FuncionJSGVNS) funcion;
    }

    /**
     * obtiene el lower bound
     *
     * @return
     */
    public int obtenerLowerBound() {
        if (lb == -1) {
            int[] lu_b = UtilCuadratica.optenerLowerUpper_Bound(getFuncion());
            lb = lu_b[0];
            ub = lu_b[1];
        }
        return lb;
    }

    /**
     * obtiene el upper bound
     *
     * @return
     */
    public int obtenerUpperBound() {
        if (ub == -1) {
            obtenerLowerBound();
        }
        return ub;
    }

    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @param dimension
     * @param individuo
     * @param lowerb
     * @return
     */
    protected List<Integer> determinarVariablesFijas(int dimension, IndividuoVNS individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimension;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }

    /**
     *
     * @param varFijas
     */
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas) {
        getFuncion().fijarVariables(varFijas);

    }
}
