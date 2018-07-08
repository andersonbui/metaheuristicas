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

    public IndividuoMochila limitarSuperiormente(IndividuoMochila mochila, double capacidad) {
        int posicion;
        // para cada caracteristica(peso) del elemento
        while (mochila.pesar() > capacidad) {
            posicion = mayorPerjuicio(mochila);
            mochila.set(posicion, 0);
        }
        return mochila;
    }

    public IndividuoMochila limitarInferiormente(IndividuoMochila IndMochila, double capacidad, int[] pos_articulos) {
        IndividuoMochila mochila = (IndividuoMochila) IndMochila;
        double espacios = sacarEspacios(mochila, capacidad);
        // en todos los articulos
        for (int pos : pos_articulos) {
            if (mochila.get(pos) == 0) {
                // dimension de la mochila
                if (espacios > peso(pos)) {
                    mochila.set(pos, 1);
                    espacios = sacarEspacios(mochila, capacidad);
                }
            }
        }
        return IndMochila;
    }

    /**
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @param capacidad
     * @return
     */
    public double sacarEspacios(IndividuoMochila mochila, double capacidad) {
        double espacios = capacidad - obtenerPeso(mochila);
        return espacios;
    }

    protected IndividuoMochila limitar(IndividuoMochila mochila, double capacidad, int[] pos_articulos) {
        limitarInferiormente(mochila, capacidad, pos_articulos);
        limitarSuperiormente(mochila, capacidad);
        return mochila;
    }

    /**
     * optiene la posicion del elemento dentro de la mochila que causa mayor
     * perjuicio indicado por el vector pesos.
     *
     * @param mochila
     * @return
     */
    public int mayorPerjuicio(IndividuoMochila mochila) {
        int posMayor = 0;
        double valorMayor = -Double.MAX_VALUE;
        double valor;
        // para cada elemento que podria ir en la mochila
        for (int k = 0; k < mochila.getDimension(); k++) {
            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
            valor = mochila.get(k) * peso(k);
            if (valorMayor < valor) {
                posMayor = k;
                valorMayor = valor;
            }
        }
        return posMayor;
    }

}
