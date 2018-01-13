package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalMejorada extends MochilaMultidimensionalOriginal {

    List<Integer> articulos = new ArrayList();

    /**
     *
     * @param capacidades
     * @param w
     * @param maximizar
     */
    public MochilaMultidimensionalMejorada(double[] capacidades, List<double[]> w, boolean maximizar) {
        super(capacidades, w, maximizar);
        this.nombre = "MOCHILA MD M";
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
                int posicionAptitud = w.get(o1).length - 1;
                Double aptotudO1 = w.get(o1)[posicionAptitud];
                Double aptotudO2 = w.get(o2)[posicionAptitud];
                return aptotudO2.compareTo(aptotudO1);
            }
        });
    }

    @Override
    public Individuo limitar(Individuo punto) {
        int posicion;
        boolean mas = true;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            posicion = 0;
            while (obtenerPeso(punto, i) > capacidades[i]) {
                posicion = mayor(punto, i);
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
                        break;
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
