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
package main.mochila.cuadratica.IHEA.hyperplane_exploration;

import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.greedy.Greedy;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.utilidades.ComparacionIdeal;
import main.mochila.cuadratica.utilidades.instanciasAlgoritmo;
import static main.mochila.cuadratica.utilidades.UtilCuadratica.swap;
import metaheuristicas.Aleatorio;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public class IteratedHyperplaneExplorationAlgoritm extends AlgoritmoMetaheuristico<FuncionMochilaIHEA, IndividuoIHEA> {

    boolean inicializado;
    int rcl; // lista restringida de candidatos - construccion greedy
    private int lb; // lower bown
    private int ub; // lower bown
    private int mt;
    int ms;
    private int L; // tamanio maximo de la lista de ejecucion - busqueda tabu
    int tiempototal; // tiempo total que toma la busqueda tabu
    private int intentosDescent; // intento de busqueda obtimo - procedimiento descendente.
    private int contadorIntercambios; // contador de intercambios dentro de la busqueda exaustiva de tabuSearch (estadistica)
    int contadortabu; // Contador de veces que se usa tabuSearch (estadistica)
    int contadorFijosFalsosPositivos;
    private instanciasAlgoritmo instancias;

    public IteratedHyperplaneExplorationAlgoritm(FuncionMochilaIHEA funcion) {
        super(funcion);
        inicializado = false;
    }

    public void inicializar() {
        contadorFijosFalsosPositivos = 0;
        inicializado = true;
        nombre = "IHEA";
        lb = funcion.obtenerLowerBound();
        ub = funcion.obtenerUpperBound();
        tiempototal = 0;
        rcl = 20;
        L = 300;
        ms = 3;
        mt = 10;
        intentosDescent = 5;
        setMaxIteraciones((int) Math.sqrt(funcion.getDimension()) + 65);

        if (getCadenaParametros() != null) {
            String[] arrayParametros = getCadenaParametros().split(",");
            for (String arrayParametro : arrayParametros) {
                String[] unParametro = arrayParametro.split("=");
                actualizarVarible(unParametro[0], Integer.parseInt(unParametro[1]));
//                System.out.println("unParametro[0]: " + unParametro[0] + "; unParametro[1]: " + unParametro[1]);
            }
        }
    }

    public void actualizarVarible(String nombre, int valor) {
        switch (nombre) {
            case "L":
                L = valor;
                break;
            case "mt":
                mt = valor;
                break;
            case "ms":
                ms = valor;
                break;
            case "rcl":
                rcl = valor;
                break;
            case "idesc":
                intentosDescent = valor;
                break;
        }
    }

    @Override
    public List<IndividuoIHEA> ejecutar() {
        contadortabu = 0;
        contadorIntercambios = 0;
        if (!inicializado) {
            System.out.println("peligro" + getNombre() + " no inicializado");
            return null;
        }
        List listaResult = iterateHiperplaneExploration(L, rcl, maxIteraciones);
//        System.out.println("===> tiempo: " + tiempototal / contadortabu);
//        System.out.println("contador intercambios tabu: " + contadorIntercambios);
        return listaResult;
    }

    protected List<IndividuoIHEA> iterateHiperplaneExploration(int L, int rcl, int maxIter) {

        List<IndividuoIHEA> recorrido = new ArrayList();
        /**
         * maximo tamanio de la lista de ejecución(lista que guarda todos los
         * atributos de los movimientos implementados), que sirve como condicion
         * determinacion.
         */
        // indice de las variables fijas para el problema restringido
        List<Integer> variablesFijas;
        //variable bandera.
        boolean solucionEncontrada;
        //dimension k.
        int k;
        /**
         * linea 3: x0 = GreedyRandomizedConstruction(rcl). solucion inicial
         */
        IndividuoIHEA indi = funcion.generarIndividuo();
        IndividuoIHEA x_inicial = GreedyRandomizedConstruction(indi, rcl);
        /**
         * linea 4: x0 = descent(x0). mejoramiento de la solución inicial
         */
        x_inicial = descent(x_inicial);
        /**
         * linea 5: x' = x0. x' represents the current solution.
         */
        IndividuoIHEA x_prima = x_inicial.clone();
        // linea 6: iter = 0
        iteraciones = 0;
        /**
         * linea 7: xb= x'. Xb records the best solution found in current round
         * of hyperplane exploration.
         */
        IndividuoIHEA x_mejorRondaHyper = x_prima.clone();
        /**
         * linea 8: x* = xb. x* records the global best solution.
         */
        IndividuoIHEA x_mejorGlobal = x_mejorRondaHyper.clone();
        //linea 9:

        for (; iteraciones < maxIter; iteraciones++) {
//            boolean suficiente = funcion.suficiente(x_mejorGlobal);
//            if (suficiente) {
//                recorrido.add(x_mejorGlobal);
//                return recorrido;
//            }
            // linea 10:
            solucionEncontrada = true;
            // linea 11:
            k = dimensionHiperplano(x_prima);
            // linea 12: construct constrain problem CQKP[k]
            // linea 13: fase de exploracion
            // linea 14:
            while (solucionEncontrada) {
                // linea 14:
                variablesFijas = determinarVariablesFijas(k, x_prima, lb);
                // linea 16: construct reduce constrain problem
                construirProblemaRestringidoReducido(variablesFijas, x_prima);
                //contar variables fijas pertenecientes al ideal
                int amalos = ComparacionIdeal.cuentaValorEnIdeal(instancias, variablesFijas, 0, "cuanto son ceros");
                contadorFijosFalsosPositivos += amalos;
                // linea 17: run tabu serach engine (L,x',xb)
                long tiempo_inicial = System.currentTimeMillis();
                contadortabu++;
                x_prima = tabuSearchEngine(L, x_prima, x_mejorRondaHyper);
                long tiempo_final = System.currentTimeMillis();
                tiempototal += (tiempo_final - tiempo_inicial);
                // linea 18:
                if (x_prima.compareTo(x_mejorRondaHyper) > 0) {
                    // linea 19:
                    if (x_prima.pesar() <= funcion.getCapacidad()) {

                        x_mejorRondaHyper = x_prima.clone();
                        // linea 21
                        x_prima = addElemento(x_mejorRondaHyper);
                        // linea 20:
                        k = dimensionHiperplano(x_prima);
                        // linea 22: construct constrain problem CQKP[k]
                    }
                } else {
                    // linea 24:
                    solucionEncontrada = false;
                }
//                recorrido.addElemento(x_mejorGlobal);
                funcion.reiniciarVijarVariables();
            }
            //linea 27:
            if (x_mejorRondaHyper.compareTo(x_mejorGlobal) > 0) {
                // linea 28:
                x_mejorGlobal = x_mejorRondaHyper.clone();
            }
            // linea 30: fase de perturbacion
            // linea 31:
            x_prima = perturbacion(x_mejorRondaHyper, iteraciones);
            // linea 32:
            x_prima = descent(x_prima);
            // linea 33:
            x_mejorRondaHyper = x_prima.clone();
            recorrido.add(x_mejorGlobal);
        }
//        System.out.println(""+instancias.getNombreInstancia()+ ":" + contadorFijosFalsosPositivos);

        return recorrido;
    }

    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @TODO realizar el calculo tambien con el LB solo, es decir, (nf=lowerb)
     * @param dimensionHyp
     * @param individuo
     * @param lowerb
     * @return
     */
    public List<Integer> determinarVariablesFijas(int dimensionHyp, IndividuoIHEA individuo, int lowerb) {
        // dimension de individuo
        int dimX = dimensionHyp;
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
//        System.out.print(nf+" - ");
//        nf = (int) (0.860 * (lowerb + (ub - lowerb) / 2.0)); //general
//        nf = (int)(1.08*(lowerb + (ub - lowerb) / 2.0)); //300
//        nf = (int)(0.96*(lowerb + (ub - lowerb) / 2.0)); //100
//        System.out.println("- "+nf);
//        nf = (int)(1.20*(lowerb + (ub - lowerb) / 2.0)); //1000
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
     * @param individuoActual
     */
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas, IndividuoIHEA individuoActual) {
        funcion.fijarVariables(individuoActual, varFijas);
    }

    protected IndividuoIHEA tabuSearchEngine(int L, IndividuoIHEA x_inicial, IndividuoIHEA x_referencia) {

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
        IndividuoIHEA x_aster = x_referencia;
        // linea 6; tamanio lista RL
//        int erl = 0;
        // linea 7:
        IndividuoIHEA x = x_inicial.clone();
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
            I0 = elementosFuera(x);
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

    public double rawFuncion(IndividuoIHEA individuo) {
        return individuo.evaluar();
    }

    /**
     *
     * @param individuo
     * @param iteraciones
     * @return
     */
    protected IndividuoIHEA perturbacion(IndividuoIHEA individuo, int iteraciones) {
        individuo = individuo.clone();
        List<Integer> I1 = elementosDentro(individuo);

        // dimension de individuo
        int dimX = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getDimension();
        // numero de variables fijas
        int nf = (int) (getLb() + Math.max(0, (dimX - getLb()) * (1 - 1 / (0.008 * n))));

        int t = Math.min(getMt(), I1.size() - nf);
        int s = Math.min(getMs(), t);

        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad(I1, individuo, t, true);
        int posaleatoria;
        for (int i = 0; i < s; i++) {
            posaleatoria = Aleatorio.nextInt(listaIndices.size());
            individuo.set(listaIndices.remove(posaleatoria), 0);
        }
        individuo = GreedyRandomizedConstruction(individuo, getRcl());
        return individuo;
    }

    protected class CQKP {

    }

    protected CQKP construirCQKP(int numVarFijas, CQKP cqkp, IndividuoIHEA x_individuo) {
        return cqkp;
    }

    /**
     * Ejecuta un algoritmo gredy aleatorizado a partir de un individuo y el
     * tamaño de la lista restringida de candidatos (rcl)
     *
     * @param individuo
     * @param rcl tamaño de la lista restringida de candidatos
     * @return el individuo mejorado por elalgoritmo gredy
     */
    protected IndividuoIHEA GreedyRandomizedConstruction(IndividuoIHEA individuo, int rcl) {

        return (new Greedy(rcl)).ejecutar(individuo);
    }

    /**
     * procedimeinto que obtiene la lista de los elementos seleccionados (I1) en
     * individuo.
     *
     * @param individuo s * @return List de indices de elementos seleccionados
     * @return
     */
    protected List<Integer> elementosDentro(IndividuoIHEA individuo) {
        return funcion.obtener_I1(individuo);
    }

    /**
     * procedimeinto que obtiene la lista de los elementos no seleccionados (I1)
     * en individuo.
     *
     * @param individuo
     * @return List de indices de elementos no seleccionados
     */
    protected List<Integer> elementosFuera(IndividuoIHEA individuo) {
        return funcion.obtener_I0(individuo);
    }

    /**
     * realiza un mejoramiento al individuo utilizando addElemento y swap.
     *
     * @param original
     * @return
     */
    protected IndividuoIHEA descent(IndividuoIHEA original) { ////////////OPCION DE MEJORAR EN TIEMPO (for)
        IndividuoIHEA mejor = (IndividuoIHEA) original.clone();
        IndividuoIHEA individuo1;
        IndividuoIHEA individuo2;
        boolean vecindariouno = Aleatorio.nextBoolean();
        while (true) {
            if (vecindariouno) {
                //ADD
                individuo1 = add_factible(mejor);
                if (individuo1 != null && individuo1.compareTo(mejor) > 0) {
                    mejor = individuo1;
                    vecindariouno = !vecindariouno;
                } else {
                    break;
                }
            }
            if (!vecindariouno) {
                //SWAP
                individuo2 = (IndividuoIHEA) swap(mejor);
                if (individuo2 != null && individuo2.compareTo(mejor) > 0) {
                    mejor = individuo2;
                    vecindariouno = !vecindariouno;
                } else {
                    break;
                }
            }
        }
        return mejor;
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo ya sea que el
     * individuo resultante sea factible o no factible.
     *
     * @param individuo
     * @return indice del elemento adicionado
     */
    protected IndividuoIHEA addElemento(IndividuoIHEA individuo) {
        individuo = individuo.clone();
        List<Integer> listaI0 = funcion.obtener_I0(individuo);
        if (listaI0.isEmpty()) {
            return individuo;
        }
        int indice = Aleatorio.nextInt(listaI0.size());
        individuo.set(listaI0.get(indice), 1);
        return individuo;
    }

    /**
     * adiciona un item, escogido aleatoriamente, a individuo de manera tal que
     * el individuo resultante sea factible
     *
     * @param individuo
     * @return el individuo con el elemento adicionado o null si no se encontro
     * factible
     */
    protected IndividuoIHEA add_factible(IndividuoIHEA individuo) {
        individuo = individuo.clone();
        List<Integer> listaI0 = funcion.obtener_I0(individuo);
        listaI0 = funcion.filtrarPorFactibles(listaI0, individuo);
        if (listaI0.isEmpty()) {
            return individuo;
        }
        // TODO puede mejorarse ordenando los elementos por mayor densidad de beneficio
        int indice = Aleatorio.nextInt(listaI0.size());
        individuo.set(listaI0.get(indice), 1);
        return individuo;
    }

    /**
     * optinene la dimension o número de elementos(unos) que hay en el
     * individuo.
     *
     * @param individuo
     * @return
     */
    protected int dimensionHiperplano(IndividuoIHEA individuo) {
        return individuo.cantidadI1();
    }

    public int getRcl() {
        return rcl;
    }

    public void setRcl(int rcl) {
        this.rcl = rcl;
    }

    public int getLb() {
        return lb;
    }

    public void setLb(int lb) {
        this.lb = lb;
    }

    public int getUb() {
        return ub;
    }

    public void setUb(int ub) {
        this.ub = ub;
    }

    public int getMt() {
        return mt;
    }

    public void setMt(int mt) {
        this.mt = mt;
    }

    public int getMs() {
        return ms;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }

    public int getL() {
        return L;
    }

    public void setL(int L) {
        this.L = L;
    }

    public int getTiempototal() {
        return tiempototal;
    }

    public void setTiempototal(int tiempototal) {
        this.tiempototal = tiempototal;
    }

    public int getIntentosDescent() {
        return intentosDescent;
    }

    public void setIntentosDescent(int intentosDescent) {
        this.intentosDescent = intentosDescent;
    }

    public int getContadorIntercambios() {
        return contadorIntercambios;
    }

    public void setContadorIntercambios(int contadorIntercambios) {
        this.contadorIntercambios = contadorIntercambios;
    }

    public int getContadortabu() {
        return contadortabu;
    }

    public void setContadortabu(int contadortabu) {
        this.contadortabu = contadortabu;
    }

    public void setInstancias(instanciasAlgoritmo instancias) {
        this.instancias = instancias;
    }

    public instanciasAlgoritmo getInstancias() {
        return instancias;
    }
}
