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
package main.mochila.cuadratica.sgvns.busqueda_vecidad_variable_mejorada;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;
import main.mochila.cuadratica.sgvns.SGVNS_M3.FuncionMochilaSGVNS_M3;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import main.mochila.cuadratica.utilidades.UtilCuadratica;
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
    List<Integer> variablesFijasLowerb;
    List<Integer> variablesFijasUpperb;
    boolean inicializado;

    public JSGVNS(FuncionMochilaSGVNS_M3 funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
        inicializado = false;
        this.maxIteraciones = maxIteraciones;
        this.funcion = funcion;
    }

    public void inicializar() {
        inicializado = true;
        lb = -1;
        ub = -1;
        lb = obtenerLowerBound();
        ub = obtenerUpperBound();
        nombre = "JSGVNS";
//        setMaxIteraciones((int) Math.sqrt(funcion.getDimension()) + 65);

        if (getCadenaParametros() != null) {
            String[] arrayParametros = getCadenaParametros().split(",");
            for (String arrayParametro : arrayParametros) {
                String[] unParametro = arrayParametro.split("=");
                actualizarVarible(unParametro[0], Integer.parseInt(unParametro[1]));
//                System.out.println("unParametro[0]: " + unParametro[0] + "; unParametro[1]: " + unParametro[1]);
            }
        }
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
        boolean solucionEncontrada = true;
        int L = 5;
        int k;
        if (suficiente) {
            recorrido.add(y_best);
            return recorrido;
        }
        for (iteraciones = 0; iteraciones < maxIteraciones; iteraciones++) {
            //numero de movimientos aleatorios para shaking
            int h = 1;
            IndividuoVNS y_p;
            IndividuoVNS y_p2;
//            funcion.
            while (h <= hMax) {
                //Genera una solución aleatoria y_p de y, para h en el vecindario cambio (diversidad)
                //TODO: Se le puede aplicar una B. Tabu para que no repita soluciones (movimientos)

                //MODIFICACION trabajar la sacudida con el vecindario 1
                y_p = sacudida(y, 1, h);

//                ################ Tabu Search ###############
                while (solucionEncontrada) {
                    // linea 14:
//                    variablesFijas = determinarVariablesFijas(k, x_prima, lb);
                    // linea 16: construct reduce constrain problem
//                    construirProblemaRestringidoReducido(variablesFijas, x_prima);
                    //contar variables fijas pertenecientes al ideal
//                int cuantosNoEstan = ComparacionIdeal.cuentaValorEnIdeal(instanciasAlgoritmo, variablesFijas,0);

                    // linea 17: run tabu serach engine (L,x',xb)
                    long tiempo_inicial = System.currentTimeMillis();
//                    contadortabu++;
                    y_p = tabuSearchEngine(L, y_p, y_best);
//                    long tiempo_final = System.currentTimeMillis();
//                    tiempototal += (tiempo_final - tiempo_inicial);
                    // linea 18:
                    if (y_p.compareTo(y_best) > 0) {
                        // linea 19:
                        if (y_p.pesar() <= funcion.getCapacidad()) {

                            y_best = y_p.clone();
                            // linea 21
                            y_p = addElemento(y_best);
                            // linea 20:
                            k = y_p.getDimension();
                            // linea 22: construct constrain problem CQKP[k]
                        }
                    } else {
                        // linea 24:
                        solucionEncontrada = false;
                    }
//                recorrido.addElemento(x_mejorGlobal);
//                    funcion.reiniciarVijarVariables();
                }
//                ############# Tabu Search ###############
//                variablesFijasUpperb = determinarVariablesFijasUpperBound(y_p.getDimension(), y_p, ub);
//                construirProblemaRestringidoReducido(variablesFijasUpperb);
//              getFuncion().reiniciarVijarVariablesUpperb();
//                getFuncion().reiniciarVijarVariables();

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
//                MODIFICACION
//                variablesFijasLowerb = determinarVariablesFijasLowerBound(y.getDimension(), y, lb);
//                construirProblemaRestringidoReducido(variablesFijasLowerb);

            }
            recorrido.add(y_best);
        }
        //CONDICION DE TERMINACION PARA AJUSTAR
//        cont_JSGVNS++;
//        System.out.println("cont_JSGVNS" + cont_JSGVNS);

        return recorrido;
    }

    public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
        //Variable para el tipo de estructura de vecindario
        int h = 1;
        IndividuoVNS s_inicial = individuoOriginal.clone();
        IndividuoVNS solEncontrada;
        while (h <= 2) {

            variablesFijasUpperb = determinarVariablesFijasUpperBound(s_inicial.getDimension(), s_inicial, ub);
            construirProblemaRestringidoReducido(variablesFijasUpperb);
            //Aplica una estructura de vecindario h a la s_inicial para mejorar
            solEncontrada = encontrarMejor(s_inicial, h);
            getFuncion().reiniciarFijarVariablesUpperb();
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
            //MODIFICACION
            mejoro = aux.compareTo(s_inicial) > 0;
            s_inicial = aux;
            if (mejoro) {
                break;
            }
        } while (intentos-- >= 0);

        return s_inicial;
    }

    @Override
    public FuncionMochilaSGVNS_M3 getFuncion() {
        return (FuncionMochilaSGVNS_M3) funcion;
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
    protected List<Integer> determinarVariablesFijasLowerBound(int dimension, IndividuoVNS individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimension;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
//MODIFICACION
//        int nf = (int) (lowerb);
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosDentro(individuo);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);

        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
        return listaIndices;
    }

    protected List<Integer> determinarVariablesFijasUpperBound(int dimension, IndividuoVNS individuo, int upperb) {
        // dimension de individuo
        int dimX = dimension;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (upperb + Math.max(0, (dimX - upperb) * (1 - 1 / (0.008 * n))));
//MODIFICACION
//        int nf = (int) (upperb);
        // items seleccionados
        List<Integer> itemsSeleccionados = elementosFueraYCaben(individuo);

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

    protected IndividuoVNS tabuSearchEngine(int L, IndividuoVNS x_inicial, IndividuoVNS x_referencia) {

        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = 0;
        //
        double fmax;
        tabu = new int[x_inicial.getDimension()][x_inicial.getDimension()];
        double frx = 0;
        double vcx;

        // residual cancellation sequence list
        List<Integer> list_RCS = new ArrayList();
        // inicializa el tamaño de la lista de ejecucion
        List<Integer> list_RL = new ArrayList();
        // almacena el valor de la funcion objetivo de la actual mejor solución factible global
        double fmin = x_referencia.evaluar();
        // x*: recuerda la mejor solucion encontrada hasta el momento
        IndividuoVNS x_aster = x_referencia;
        // linea 6; tamanio lista RL
//        int erl = 0;
        // linea 7:
        IndividuoVNS x = x_inicial.clone();
        int i_aster = 0;
        int j_aster = 0;

        int imax = -1;
        int jmax = -1;
        double vmax = -1;
        // linea 8:
        int iterTabu = 1;

        double viol_capacidad;
        double calidadi;
        while (vmin != Double.POSITIVE_INFINITY && list_RL.size() < L) {

            vmin = Double.POSITIVE_INFINITY;
            fmax = Double.NEGATIVE_INFINITY;
            List<Integer> I0;
            List<Integer> I1;
            I0 = elementosFueraYCaben(x);
            I1 = elementosDentro(x);
            vmax = -1;
            // linea 10:
            for (int j : I1) {
                viol_capacidad = funcion.getCapacidad() - x.pesar() + funcion.peso(j);
                calidadi = x.getCalidad() - funcion.contribucion(j, x);
                for (int i : I0) {
                    // linea 11:
                    // linea 12:
                    if (tabu[i][j] <= iterTabu - 1) {
                        //contribucion
                        frx = calidadi + funcion.contribucion(i, x, j);
                        // peso del articulo
                        vcx = viol_capacidad - funcion.peso(i);

                        if ((frx > fmin) && (((vcx < vmin)) || ((vcx == vmin) && (frx >= fmax)))) {
                            i_aster = i;
                            j_aster = j;
                            vmin = vcx;
                            fmax = frx;
                        }
                    }
                }
            }
            int i;
            Integer j;
            // linea 21:
            if (vmin != Double.POSITIVE_INFINITY) {
                // linea 22:
                x.set(i_aster, 1);
                x.set(j_aster, 0);
                if (vmin == 0) {
                    // linea 24:
                    list_RL.clear();
                    fmin = rawFuncion(x);
                    x_aster = x.clone();
                    // linea 25:
                } else {
                    //linea 26: actualizar estado tabu
                    iterTabu += 1;
                    list_RL.add(i_aster);
                    list_RL.add(j_aster);
                    // linea 27:
                    i = list_RL.size() - 1;
                    // linea 28:
                    list_RCS.clear();
                    while (i >= 0) {
                        // linea 29:
                        j = list_RL.get(i);
                        if (list_RCS.contains(j)) {
                            boolean ret = list_RCS.remove(j);
                        } else {
                            list_RCS.add(j);
                        }
                        if (list_RCS.size() == 2) {
                            tabu[list_RCS.get(0)][list_RCS.get(1)] = iterTabu;
                            tabu[list_RCS.get(1)][list_RCS.get(0)] = iterTabu;
                        }
                        i--;
                    }
                }
            }
        }
        return x_aster;

    }

    public double rawFuncion(IndividuoVNS individuo) {
        return individuo.evaluar();
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo ya sea que el
     * individuo resultante sea factible o no factible.
     *
     * @param individuo
     * @return indice del elemento adicionado
     */
    protected IndividuoVNS addElemento(IndividuoVNS individuo) {
        individuo = individuo.clone();
        List<Integer> listaI0 = funcion.obtener_I0(individuo);
        if (listaI0.isEmpty()) {
            return individuo;
        }
        int indice = Aleatorio.nextInt(listaI0.size());
        individuo.set(listaI0.get(indice), 1);
        return individuo;
    }
}
