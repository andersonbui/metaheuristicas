package main.mochila.funciones;

import metaheuristicas.Individuo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalMejorada2 extends MochilaMultidimensionalOriginal {

    List<Integer> articulos = new ArrayList();

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
//                Double aptotudO1 = densidadGanancia(o1);
//                Double aptotudO2 = densidadGanancia(o2);
                // ganancia
                int posicionAptitud = w.get(o1).length - 1;
                Double aptotudO1 = w.get(o1)[posicionAptitud];
                Double aptotudO2 = w.get(o2)[posicionAptitud];
                return aptotudO2.compareTo(aptotudO1);
            }
        });
    }

    public int menorDensidadGanancia(Individuo mochile, int indicePeso) {
        int posicionMayorOptimo = 0;
        double densidadGMenor = Double.MAX_VALUE;
        double densidadGanancia;
        // para cada elemento que podria ir en la mochila
        for (int k = 1; k < mochile.getDimension(); k++) {
            if (mochile.getValor(k) != 0) {
                densidadGanancia = 0;
                /**
                 * para indicePeso-esimo caracteristica(peso) del elemento
                 * k-esimo "w.get(k)[length - 1]" es la ganancia que aporta el
                 * elemento k
                 */
                for (int i = 0; i < w.get(k).length - 1; i++) {
                    densidadGanancia += mochile.getValor(k) * w.get(k)[i];
                }
                densidadGanancia = w.get(k)[(w.get(k).length - 1)] / densidadGanancia;
                if (densidadGMenor > densidadGanancia) {
                    posicionMayorOptimo = k;
                    densidadGMenor = densidadGanancia;
                }
            }
        }
        return posicionMayorOptimo;
    }

    public int mayorDensidadGanancia(Individuo mochila) {
        int posicionMayorOptimo = 0;
        double densidadGMayor = Double.MIN_VALUE;
        double densidadGanancia;
        // para cada elemento que podria ir en la mochila
        for (int k = 1; k < mochila.getDimension(); k++) {
            if (mochila.getValor(k) == 0) {
                densidadGanancia = 0;
                /**
                 * para indicePeso-esimo caracteristica(peso) del elemento
                 * k-esimo "w.get(k)[length - 1]" es la ganancia que aporta el
                 * elemento k
                 */
                double[] caract = w.get(k);
                int pos_UltimoElemento = caract.length - 1;
                for (int i = 0; i < pos_UltimoElemento; i++) {
                    densidadGanancia += caract[i];
                }
                densidadGanancia = caract[pos_UltimoElemento] / densidadGanancia;
                if (densidadGMayor < densidadGanancia) {
                    posicionMayorOptimo = k;
                    densidadGMayor = densidadGanancia;
                }
            }
        }
        return posicionMayorOptimo;
    }

    public double densidadGanancia(int k) {
        double densidadGanancia = 0;
        /**
         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
         */
        double[] caract = w.get(k);
        int pos_UltimoElemento = caract.length - 1;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            densidadGanancia += caract[i];
        }
        densidadGanancia = caract[pos_UltimoElemento] / densidadGanancia;

        return densidadGanancia;
    }

    @Override
    public Individuo limitar(Individuo punto) {
        int posicion;
        boolean mas = true;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            posicion = 0;
            while (obtenerPeso(punto, i) > capacidades[i]) {
                posicion = menorDensidadGanancia(punto, i);
                punto.set(posicion, 0);
            }
        }
        double[] espacios = sacarEspacios(punto);
        boolean cabeArticulo = true;
        do {
            // en todos los articulos
            for (int pos : articulos) {
                cabeArticulo = true;
                if (punto.getValor(pos) == 0) {
                    // dimension de la mochila
                    for (int i = 0; i < espacios.length; i++) {
                        if (espacios[i] < w.get(pos)[i]) {
                            cabeArticulo = false;
                            break;
                        }
                    }
                    if (cabeArticulo) {
                        punto.set(pos, 1);
                        espacios = sacarEspacios(punto);
//                        break;
                    }
                }
            }
//        } while (cabeArticulo);
        } while (false);
        return punto; //To change body of generated methods, choose Tools | Templates.
    }

    public double[] sacarEspacios(Individuo mochila) {
        double[] espacios = new double[capacidades.length];
        for (int i = 0; i < capacidades.length; i++) {
            espacios[i] = capacidades[i] - obtenerPeso(mochila, i);
        }
        return espacios;
    }

}
