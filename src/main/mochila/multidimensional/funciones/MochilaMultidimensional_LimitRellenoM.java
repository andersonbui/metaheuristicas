package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import java.util.ArrayList;
import java.util.List;
import metaheuristicas.Aleatorio;
import metaheuristicas.funcion.Funcion;

/**
 *
 * @author debian
 */
public class MochilaMultidimensional_LimitRellenoM extends MochilaMultidimensionalOriginal {

    protected List<Integer> articulos = new ArrayList();

    /**
     *
     * @param VectorRestricciones
     * @param beneficios
     * @param capacidades
     */
    public MochilaMultidimensional_LimitRellenoM(double[][] VectorRestricciones, double[] beneficios, double[] capacidades) {
        super(VectorRestricciones, beneficios, capacidades);
        this.nombre = "MOCHILA MD M";
        // ordenamiento de los articulos, por aptitud
        prob_ceros = 0.8;
        for (int i = 0; i < beneficios.length; i++) {
            articulos.add(i);//i:indices de los articulos 
        }
        articulos.sort((Integer o1, Integer o2) -> {
            /**
             * comparar los indices, segun su beneficio
             */
            Double aptotudO1 = beneficio(o1);
            Double aptotudO2 = beneficio(o2);
            return aptotudO2.compareTo(aptotudO1);
        });
    }

    public Individuo limitarSuperiormente(Individuo IndMochila) {
        int posicion;
        IndividuoMochila mochila = (IndividuoMochila) IndMochila;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            while (obtenerPeso(mochila, i) > capacidades[i]) {
                posicion = mayorPerjuicio(mochila, i);
                mochila.set(posicion, 0);
            }
        }
        return IndMochila;
    }

    /**
     * beneficio del elemento en la posicion: indice
     *
     * @param indice indice del elemento
     * @return
     */
    public double beneficio(int indice) {
        return beneficios[indice];
    }

    public Individuo limitarInferiormente(Individuo IndMochila) {
        IndividuoMochila mochila = (IndividuoMochila) IndMochila;
        double[] espacios = sacarEspacios(mochila);
        boolean cabeArticulo;
        // en todos los articulos
        for (int pos : articulos) {
            cabeArticulo = true;
            if (mochila.get(pos) == 0) {
                // dimension de la mochila
                for (int i = 0; i < espacios.length; i++) {
                    if (espacios[i] < VectorRestricciones[i][pos]) {
                        cabeArticulo = false;
                        break;
                    }
                }
                if (cabeArticulo) {
                    mochila.set(pos, 1);
                    espacios = sacarEspacios(mochila);
                }
            }
        }
        return IndMochila;
    }

    /**
     * quita los elementos mas perjudiciales, si hay demasiados como para
     * sobrepasar la capacidad de la mochila, o rellena con los las
     * beneficiosos, si hay muy pocos como para sobrar espacio en la mochila.
     *
     * @param mochila
     */
    @Override
    public void limitar(Individuo mochila) {
        limitarSuperiormente(mochila);
        limitarInferiormente(mochila);
    }

    /**
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @return
     */
    public double[] sacarEspacios(Individuo mochila) {
        double[] espacios = new double[capacidades.length];
        for (int i = 0; i < capacidades.length; i++) {
            espacios[i] = capacidades[i] - obtenerPeso(mochila, i);
        }
        return espacios;
    }

    @Override
    public double obtenerPeso(Individuo mochila, int indicePeso) {
        return ((IndividuoMochila) mochila).getPeso(indicePeso);
    }

    @Override
    public double obtenerPrecio(Individuo mochila) {
        return ((IndividuoMochila) mochila).getCalidad();
    }

    @Override
    public Individuo generarIndividuo() {
        Individuo nuevop = new IndividuoMochila(this);
        for (int i = 0; i < nuevop.getDimension(); i++) {
            if (Aleatorio.nextDouble() > prob_ceros) {
                nuevop.set(i, 1);
            }
        }
        return nuevop;
    }

    public class IndividuoMochila extends Individuo {

        private double[] pesos;
        private double[] espacios;

        public IndividuoMochila(Funcion funcion) {
            super(funcion);
            pesos = new double[capacidades.length];
            espacios = new double[capacidades.length];
        }

        public double getPeso(int pos) {
            return pesos[pos];
        }

        @Override
        public void set(int posicion, double valor) {

            double valAnterior = get(posicion);

            if (valAnterior == 0 && valor != 0) {
                for (int i = 0; i < capacidades.length; i++) {
                    pesos[i] = pesos[i] + VectorRestricciones[i][posicion];
                }
                calidad = calidad + beneficios[posicion];
            } else if (valAnterior != 0 && valor == 0) {
                for (int i = 0; i < capacidades.length; i++) {
                    pesos[i] = pesos[i] - VectorRestricciones[i][posicion];
                }
                calidad = calidad - beneficios[posicion];

            }
            super.set(posicion, valor);
        }

        @Override
        public Individuo clone() {
            IndividuoMochila ind = (IndividuoMochila) super.clone(); //To change body of generated methods, choose Tools | Templates.
            ind.pesos = pesos.clone();
            ind.espacios = espacios.clone();
            return ind;
        }

    }
}
