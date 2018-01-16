package main.mochila.cuadratica.funciones;

import main.mochila.multidimensional.funciones.*;
import metaheuristicas.Individuo;
import metaheuristicas.Funcion;
import java.util.List;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class MochilaCuadraticaOriginal extends Funcion {

    protected final double[] capacidades;
    protected final List<double[]> w;
    protected final double prob_ceros;

    /**
     *
     * @param capacidades
     * @param w
     * @param maximizar
     */
    public MochilaCuadraticaOriginal(double[] capacidades, List<double[]> w, boolean maximizar) {
        super("MOCHILA MD", 2, w.size(), maximizar);
        this.capacidades = capacidades;
        this.w = w;
        prob_ceros = 0.6;
    }

    @Override
    public double evaluar(Individuo mochila) {
        super.evaluar(mochila);
        mochila = limitar(mochila);
        double result = obtenerPrecio(mochila);
        mochila.setCalidad(result);
        return result;
    }

    public double obtenerPrecio(Individuo punto) {
        double sumPX = 0;
        int length = w.get(0).length;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumPX += punto.get(i) * w.get(i)[length - 1];
        }
        return sumPX;
    }

    public double obtenerPeso(Individuo mochila, int indicePeso) {
        double sumWX = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumWX += mochila.get(i) * w.get(i)[indicePeso];
        }
        return sumWX;
    }

    @Override
    public Individuo limitar(Individuo mochila) {
        int posicion;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            posicion = 0;
            while (obtenerPeso(mochila, i) > capacidades[i]) {
                posicion = mayor(mochila, i);
                mochila.set(posicion, 0);
            }
        }

        return mochila; //To change body of generated methods, choose Tools | Templates.
    }

    public int mayor(Individuo punto, int indicePeso) {
        int mayor = -1;
        double valorMayor = 0;
        double valor;
        // para cada elemento que podria ir en la mochila
        for (int k = 1; k < punto.getDimension(); k++) {
            valor = 0;
            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
            valor += punto.get(k) * w.get(k)[indicePeso];
            if (mayor < 0 || valorMayor < valor) {
                mayor = k;
                valorMayor = valor;
            }
        }
        return mayor;
    }

    @Override
    public String toString() {
        return "x+y";
    }

    @Override
    public Individuo generarIndividuo() {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (Aleatorio.nextDouble() <= prob_ceros ? 0. : 1);
        }
        Individuo nuevop = new Individuo(this, valores, isMaximizar());
        return nuevop;
    }

    public double[] getCapacidades() {
        return capacidades;
    }
}
