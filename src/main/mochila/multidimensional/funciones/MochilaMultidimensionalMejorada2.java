package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalMejorada2 extends MochilaMultidimensionalOriginal {

    protected List<Integer> articulos = new ArrayList();

    /**
     *
     * @param capacidades
     * @param w lista de pesos y ganancia de cada elemento. "w.get(k)[length -
     * 1]" es la ganancia que aporta el elemento k
     *
     * @param maximizar
     */
    public MochilaMultidimensionalMejorada2(double[] capacidades, List<double[]> w, boolean maximizar) {
        super(capacidades, w, maximizar);
        this.nombre = "MOCHILA MD M2";
        // ordenamiento de los articulos, por aptitud

        for (int i = 0; i < w.size(); i++) {
            articulos.add(i);//i:indices de los articulos 
        }
        articulos.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                /**
                 * comparar los indices, segun aptitud(w.get(o1).length - 1),
                 * aptitud se encuentra en la ultima posicion de cada articulo
                 * W.get(i),i=0,1,...,w.size();
                 */
                // densidad de ganacia por unidad de peso
                Double aptotudO1 = densidadGanancia(o1);
                Double aptotudO2 = densidadGanancia(o2);
                // ganancia
//                int posicionAptitud = w.get(o1).length - 1;
//                Double aptotudO1 = w.get(o1)[posicionAptitud];
//                Double aptotudO2 = w.get(o2)[posicionAptitud];
                return aptotudO2.compareTo(aptotudO1);
            }
        });
    }

    public int menorDensidadGananciaEnMochila(Individuo mochila) {
        int posicionMayorOptimo = 0;
        double densidadGMenor = Double.MAX_VALUE;
        double densidadGanancia;
        // para cada elemento que podria ir en la mochila
        for (int k = 1; k < mochila.getDimension(); k++) {
            if (mochila.getValor(k) != 0) {
                densidadGanancia = densidadGanancia(k);
                if (densidadGMenor > densidadGanancia) {
                    posicionMayorOptimo = k;
                    densidadGMenor = densidadGanancia;
                }
            }
        }
        return posicionMayorOptimo;
    }

    /**
     * obtiene la densidad de ganancia por unidad de peso
     *
     * @param k
     * @return
     */
    public double densidadGanancia(int k) {
        double densidadGanancia = 0;
        /**
         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
         */
        double[] elemento = w.get(k);
        int pos_UltimoElemento = elemento.length - 1;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            densidadGanancia += elemento[i];
        }
        densidadGanancia = (elemento[pos_UltimoElemento] / densidadGanancia);
        return densidadGanancia;
    }

    public Individuo limitarSuperiormente(Individuo mochila) {
        int posicion;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            while (obtenerPeso(mochila, i) > capacidades[i]) {
                posicion = menorDensidadGananciaEnMochila(mochila);
                mochila.set(posicion, 0);
            }
        }
        return mochila;
    }

    public Individuo limitarInferiormente(Individuo mochila) {
        double[] espacios = sacarEspacios(mochila);
        boolean cabeArticulo;
        // en todos los articulos
        for (int pos : articulos) {
            cabeArticulo = true;
            if (mochila.getValor(pos) == 0) {
                // dimension de la mochila
                for (int i = 0; i < espacios.length; i++) {
                    if (espacios[i] < w.get(pos)[i]) {
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
        return mochila;
    }

    @Override
    public Individuo limitar(Individuo mochila) {
        limitarSuperiormente(mochila);
        limitarInferiormente(mochila);
        return mochila; 
    }

    public double[] sacarEspacios(Individuo mochila) {
        double[] espacios = new double[capacidades.length];
        for (int i = 0; i < capacidades.length; i++) {
            espacios[i] = capacidades[i] - obtenerPeso(mochila, i);
        }
        return espacios;
    }

}
