package funciones;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalMejorada extends MochilaMultidimensional {

    List<Integer> articulos = new ArrayList();

    /**
     *
     * @param capacidades
     * @param w
     * @param maximizar
     */
    public MochilaMultidimensionalMejorada(double[] capacidades, List<double[]> w, boolean maximizar) {
        super(capacidades, w, maximizar);
        this.nombre = "MOCHILA MultiD MM";
        // ordenamiento de los articulos, por aptitud

        for (int i = 0; i < w.size(); i++) {
            articulos.add(i);
        }
        articulos.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                Double primer = w.get(o1)[w.get(o1).length - 1];
                return -primer.compareTo(w.get(o2)[w.get(o2).length - 1]);
            }

        });
    }

    @Override
    public Punto limitar(Punto punto) {
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

    public double[] sacarEspacios(Punto punto) {
        double[] espacios = new double[capacidades.length];
        for (int i = 0; i < capacidades.length; i++) {
            espacios[i] = capacidades[i] - obtenerPeso(punto, i);
        }
        return espacios;
    }

}
