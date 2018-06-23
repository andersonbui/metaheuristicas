package main.mochila.multidimensional.funciones;

import main.mochila.FuncionMochila;
import main.mochila.IndividuoMochila;

/**
 * @author debian
 */
public abstract class MochilaMultidimensional extends FuncionMochila<IndividuoMochila> {

    public MochilaMultidimensional(String nombre, int dimension, double prob_ceros) {
        super(nombre, dimension, prob_ceros);
    }

    public double obtenerPrecio(IndividuoMochila mochila, double[] beneficios) {
        double sumPX = 0;
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumPX += mochila.get(i) * beneficios[i];
        }
        return sumPX;
    }

    public IndividuoMochila limitarSuperiormente(IndividuoMochila mochila, double[] pesos, double capacidad) {
        int posicion;
        // para cada caracteristica(peso) del elemento
        while (mochila.pesar() > capacidad) {
            posicion = mayorPerjuicio(mochila, pesos);
            mochila.set(posicion, 0);
        }
        return mochila;
    }

    public IndividuoMochila limitarInferiormente(IndividuoMochila IndMochila, double[] pesos, double capacidad, int[] pos_articulos) {
        IndividuoMochila mochila = (IndividuoMochila) IndMochila;
        double espacios = sacarEspacios(mochila, pesos, capacidad);
        // en todos los articulos
        for (int pos : pos_articulos) {
            if (mochila.get(pos) == 0) {
                // dimension de la mochila
                if (espacios > pesos[pos]) {
                    mochila.set(pos, 1);
                    espacios = sacarEspacios(mochila, pesos, capacidad);
                }
            }
        }
        return IndMochila;
    }

    /**
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @param pesos
     * @param capacidad
     * @return
     */
    public double sacarEspacios(IndividuoMochila mochila, double[] pesos, double capacidad) {
        double espacios = capacidad - obtenerPeso(mochila, pesos);
        return espacios;
    }

    protected IndividuoMochila limitar(IndividuoMochila mochila, double[] pesos, double capacidad, int[] pos_articulos) {
        limitarInferiormente(mochila, pesos, capacidad, pos_articulos);
        limitarSuperiormente(mochila, pesos, capacidad);
        return mochila;
    }

    /**
     * optiene la posicion del elemento dentro de la mochila que causa mayor
     * perjuicio indicado por el vector pesos.
     *
     * @param mochila
     * @param pesos
     * @return
     */
    public int mayorPerjuicio(IndividuoMochila mochila, double[] pesos) {
        int posMayor = 0;
        double valorMayor = -Double.MAX_VALUE;
        double valor;
        // para cada elemento que podria ir en la mochila
        for (int k = 0; k < mochila.getDimension(); k++) {
            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
            valor = mochila.get(k) * pesos[k];
            if (valorMayor < valor) {
                posMayor = k;
                valorMayor = valor;
            }
        }
        return posMayor;
    }

}
