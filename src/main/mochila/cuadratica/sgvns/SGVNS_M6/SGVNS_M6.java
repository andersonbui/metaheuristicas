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
package main.mochila.cuadratica.sgvns.SGVNS_M6;

import java.util.ArrayList;
import java.util.Collections;
import main.mochila.cuadratica.sgvns.SGVNS_M3.*;
import java.util.List;
import java.util.Random;
import main.mochila.cuadratica.sgvns.FuncionJSGVNS;
import main.mochila.cuadratica.sgvns.JSGVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.FuncionSGVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.IndividuoVNS;
import main.mochila.cuadratica.sgvns.busqueda_vecindad_variable.SGVNS;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;

/**
 *
 * @author Juan Diaz PC
 */
public class SGVNS_M6 extends SGVNS {

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

    public SGVNS_M6(FuncionSGVNS_M3 funcion, int maxIteraciones) {
        super(funcion, maxIteraciones);
        this.setNombre("SGVNS_M6");
        lb = funcion.obtenerLowerBound();
        ub = funcion.obtenerUpperBound();
    }

    List<Integer> variablesFijasMalas;

    @Override
    public FuncionSGVNS_M3 getFuncion() {
        return (FuncionSGVNS_M3) funcion;
    }

    /**
     *
     * @param varFijas
     */
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas) {
        getFuncion().fijarVariables(varFijas);

    }

    @Override
    public List<IndividuoVNS> ejecutar() {
        //Lista para graficar
        List<IndividuoVNS> recorrido = new ArrayList();
        //Encuentra una solucion inicial y
        IndividuoVNS y = solucionInicial();
        //Almacena la solucion inicial como best
        IndividuoVNS y_best = y;
        //termina cuando encuentra al optimo
        boolean suficiente = funcion.suficiente(y_best);
        if (suficiente) {
            recorrido.add(y_best);
            return recorrido;
        }
        boolean bandera=false;
        for (iteraciones = 0; iteraciones < maxIteraciones; iteraciones++) {
            //numero de movimientos aleatorios para shaking
            int h = 1;
            IndividuoVNS y_p;
            IndividuoVNS y_p2;
            while (h <= hMax) {
                //Genera una solución aleatoria y_p de y, para h en el vecindario cambio (diversidad)
                //Se le puede aplicar una B. Tabu para que no repita soluciones (movimientos)
//                y_p = estructuraVecindarioSacudida(y, h);

                y_p = sacudidaMejorInicial(y, 2, h);
//                y_p = sacudida(y, 2, h);
                //Va de un vecindario a otro buscando encontrar una mejora a s_inicial
                if (bandera == false) {
                    y_p = tabuSearchEngine(20, y_p, y_best);
                    bandera=true;
                }
                variablesFijasMalas = determinarMalasVariablesFijas(y_p, lb);
                construirProblemaRestringidoReducido(variablesFijasMalas);
                y_p2 = seq_VND(y_p);
                reiniciarVijarVariables();

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

    public List<Integer> fijarVariablesMaslas(FuncionSGVNS funcion, int upperb) {
        if (variablesFijasMalas == null) {
            // tamaño de la mochila
            int n = funcion.getDimension();
            // numero de variables fijas
            Random rand = new Random();
            double vari = 1.47 + (0.2 * rand.nextDouble() - 0.1);
            int nf = (int) ((upperb * vari));//1.3

            nf = Math.min(n, nf);
            // items seleccionados
            variablesFijasMalas = new ArrayList(n - nf);
            if (n - nf == 0) {
                return variablesFijasMalas;
            }
            List<Integer> listaInidicesResultado = ordenar(funcion);
            int tamanio = listaInidicesResultado.size();
//            nf = tamanio - nf;
            for (int i = nf; i < tamanio; i++) {
                variablesFijasMalas.add(listaInidicesResultado.get(i));
            }
        }
        System.out.print("");
        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido (listo)
        return variablesFijasMalas;
    }

    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @TODO realizar el calculo tambien con el LB solo, es decir, (nf=lowerb)
     * @param individuo
     * @param upperb
     * @return
     */
    protected List<Integer> determinarMalasVariablesFijas(IndividuoVNS individuo, int upperb) {
        variablesFijasMalas = fijarVariablesMaslas(funcion, upperb);
        return variablesFijasMalas;
    }

    public List<Integer> ordenar(FuncionSGVNS funcion) {
        List<Integer> indicesOrdenados = new ArrayList();
        //        // lista de indices para ordenamiento
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

        for (Posicion posicion : posiciones) {
            indicesOrdenados.add(posicion.posicion);
        }
        return indicesOrdenados;
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

    public int comparar4(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / o1.peso;
        Double resultado2 = o2.beneficio / o2.peso;
        int result = -Double.compare(resultado1, resultado2);
        if (result == 0) {
            return Double.compare(o1.peso, o2.peso);
        }
        return result;
    }

    /*Sacudida genera una solucion aleatoria y' realizando h(intentos) movimientos
    en el segundo vecindario (cambio) de la solucion y(s_inicial)*/
    protected IndividuoVNS sacudidaMejorInicial(IndividuoVNS s_inicial, int vecindario, int intentos) {
        IndividuoVNS aux;
        boolean mejoro;
        IndividuoVNS s_inicial_best;
        s_inicial_best = s_inicial.clone();
        intentos = Math.min(intentos, s_inicial_best.elementosSeleccionados().size() - 1);
        do {
            if (vecindario == 1) {
                aux = intercambio(s_inicial_best);
            } else {
                aux = cambio(s_inicial_best);
            }

            //MODIFICACION
            mejoro = aux.compareTo(s_inicial_best) > 0;
            s_inicial_best = aux;
            if (mejoro) {
                break;
            }
        } while (intentos-- >= 0);

        return s_inicial_best;
    }

    @Override
    public List<Integer> elementosDentro(IndividuoVNS mochila) {
        List listaI1 = getFuncion().obtener_I1(mochila);
        return listaI1;
    }

    /*Aplica la estructura de vecindario h a la s_inicial, un numero de intentos 
    determinado (intentosEncontrarMejor) y va comparando si s_inicial mejora*/
    @Override
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

    //Va de un vecindario a otro si no encuentra una mejota a s_inicial
    @Override
    public IndividuoVNS seq_VND(IndividuoVNS individuoOriginal) {
        //Variable para el tipo de estructura de vecindario
        int h = 1;
        IndividuoVNS s_inicial = individuoOriginal.clone();
        IndividuoVNS solEncontrada;
        while (h <= 2) {
//            variablesFijasMalas = determinarMalasVariablesFijas( s_inicial, lb);
//            construirProblemaRestringidoReducido(variablesFijasMalas);
            //Aplica una estructura de vecindario h a la s_inicial para mejorar
            solEncontrada = encontrarMejor(s_inicial, h);
//            reiniciarVijarVariables();
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

    public void reiniciarVijarVariables() {

        variablesFijasMalas = null;
        if (variablesFijasMalas != null) {
            variablesFijasMalas.clear();
        }
        getFuncion().getVariablesFijas().clear();
    }

    int contador_ignora_max = 10;

    protected IndividuoVNS tabuSearchEngine(int L, IndividuoVNS x_inicial, IndividuoVNS x_referencia) {

        double porcentaje_ignora = 0.0000000000000005; //0000000000000002
        int contador_ignora = 0;
        // almacenamiento de valores tabu
        int[][] tabu;
        double vmin = -1;
        // 
        double fmax;
        tabu = new int[x_inicial.getDimension()][x_inicial.getDimension()];
        double frx = 0;
        double vcx;
        boolean bueno = false;
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

        // linea 8:
        int iterTabu = 1;

        double viol_capacidad;
        double calidadi;
        int iterMax = 0;
        double default_min = Double.NEGATIVE_INFINITY;
//        double default_min = Double.POSITIVE_INFINITY;

        while (iterMax < L / 2 && vmin != default_min && list_RL.size() < L) {

            vmin = default_min;
            fmax = -default_min;
            List<Integer> I0;
            List<Integer> I1;
            I0 = elementosFuera(x);
            I1 = elementosDentro(x);
            // linea 10:
            for (int j : I1) {
                viol_capacidad = funcion.getCapacidad() - x.pesar() + funcion.peso(j);
                calidadi = x.getCalidad() - funcion.contribucion(j, x);
                for (int i : I0) {
                    // linea 11:
                    // linea 12:
                    if (tabu[i][j] != iterTabu) {
                        //contribucion
                        frx = calidadi + funcion.contribucion(i, x, j);
                        // peso del articulo
                        vcx = viol_capacidad - funcion.peso(i);
                        if (frx >= fmin) {
                            if ((vmin >= 0 && vcx > vmin && frx >= fmax) || (vmin < 0 && vcx > vmin) || (frx > fmax && vcx == vmin)) {
                                i_aster = i;
                                j_aster = j;
                                vmin = vcx;
                                fmax = frx;
                            }
                        }
                    }
                }
            }
            int i;
            Integer j;
            // linea 21:
            if (vmin != default_min) {

                // linea 22:
                x.set(j_aster, 0);
                x.set(i_aster, 1);
                frx = x.getCalidad();
                double vmax = funcion.getCapacidad() - x_aster.pesar();
//                if (vmin >= 0 && !(frx <= fmin && vmax >= vmin)) {
                if (vmin >= 0 && (frx > fmin || (frx == fmin && vmax < vmin))) {
                    double porcentaje = (1 - frx / fmin);
                    if (porcentaje < porcentaje_ignora) {
                        contador_ignora++;
//                        System.out.println("porcentaje: " + contador_ignora);
                    }
                    if ((contador_ignora > contador_ignora_max && porcentaje < porcentaje_ignora)) {
                        bueno = false;
                        iterMax += 2;
                    } else {
                        //if (vmin >= 0 && ((frx > fmin && vmax <= vmin) || (frx == fmin && vmax < vmin))) {
                        // linea 24:
                        iterMax = 0;
                        if (!bueno) {
                            list_RL.clear();
                        }
                        bueno = true;
                        //                    fmin = rawFuncion(x);indice
                        fmin = frx;
                        x_aster = x.clone();
                        // linea 25:
                    }
                } else {
                    bueno = false;
                    iterMax += 2;
                }
                list_RL.add(i_aster);
                list_RL.add(j_aster);
                //linea 26: actualizar estado tabu
                iterTabu += 1;
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
            } else {
                iterMax += 1;
            }
        }
        return x_aster;
    }

    /**
     * obtiene el subconjunto de elementos fuera de la mochila, pero que
     * individualmente quepan dentro de esta.
     *
     * @param mochila
     * @return lista de elementos fuera de la mochila
     */
    public List<Integer> elementosFuera(IndividuoVNS mochila) {
        List listaI0 = mochila.elementosNoSeleccionados();
//        listaI0 = funcion.filtrarPorFactibles(listaI0, mochila);
        return listaI0;
    }
}
