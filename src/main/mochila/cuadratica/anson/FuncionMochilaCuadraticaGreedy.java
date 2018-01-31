package main.mochila.cuadratica.anson;

import java.util.ArrayList;
import java.util.List;
import main.mochila.multidimensional.funciones.MochilaMultidimensional;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class FuncionMochilaCuadraticaGreedy extends MochilaMultidimensional {

    private final double[][] matrizBeneficios;
    private final double capacidad;
    private final double[] vectorPesos;
    private int[] pos_articulos;
    List<Integer> posiciones;

    /**
     *
     * @param matrizBeneficios ganancia de cada elemento.
     * "matrizBeneficios[k][i]" es la ganancia que aporta el elemento k e i a la
     * vez
     * @param capacidad
     * @param vectorPesos lista de pesos
     * @param maxGlobal
     */
    public FuncionMochilaCuadraticaGreedy(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super("Cuadratica", vectorPesos.length, 0);
        
        prob_ceros = 0.650;
//        prob_ceros = 0.70;
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        pos_articulos = new int[vectorPesos.length];
        posiciones = new ArrayList();

        for (Integer i = 0; i < vectorPesos.length; i++) {
            posiciones.add(i);
        }
        posiciones.sort((Integer o1, Integer o2) -> {
            /**
             * comparar los indices, segun su beneficio //
             */
            Double aptotudO1 = beneficio(o1);
            Double aptotudO2 = beneficio(o2);
            return aptotudO2.compareTo(aptotudO1);
        });
        pos_articulos = listToArray(posiciones);

        this.maxGlobal = maxGlobal;
    }

    /**
     *
     * @param lista
     * @return
     */
    public final int[] listToArray(List<Integer> lista) {
        int[] array = new int[vectorPesos.length];
        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i);
        }
        return array;
    }

    /**
     * beneficio del elemento en la posicion: indice
     *
     * @param indice indice del elemento
     * @return
     */
    public double beneficio(int indice) {
        double suma = 0;
        for (int i = 0; i < indice; i++) {
            suma += matrizBeneficios[i][indice];
        }
        for (int k = indice + 1; k < vectorPesos.length; k++) {
            suma += matrizBeneficios[indice][k];
        }
        return (suma + matrizBeneficios[indice][indice] * 3) / vectorPesos[indice];
    }

    @Override
    public double evaluar(Individuo mochila) {
        super.evaluar(mochila);
        limitar(mochila, vectorPesos, capacidad, pos_articulos);
        double result = obtenerPrecio(mochila);
        mochila.setCalidad(result);
        return result;
    }

    public double obtenerPrecio(Individuo mochila) {
        double sumaPrecios = 0;
        for (int i = 0; i < mochila.getDimension(); i++) {
            for (int j = i; j < mochila.getDimension(); j++) {
                sumaPrecios += mochila.get(i) * mochila.get(j) * matrizBeneficios[i][j];
            }
        }
        return sumaPrecios;
    }

    @Override
    public String toString(Individuo individuo) {
        String cadena = "";
        cadena += obtenerPeso(individuo, vectorPesos) + ";";
        return "calidad:" + individuo.getCalidad() + "; pesos:" + cadena + "; maxGlobal:" + maxGlobal;
    }

    /**
     * optiene la posicion del elemento dentro de la mochila que causa mayor
     * perjuicio
     *
     * @param mochila
     * @param pesos
     * @return
     */
    @Override
    public int mayorPerjuicio(Individuo mochila, double[] pesos) {
        super.mayorPerjuicio(mochila, pesos);
        int posMayor = 0;
        double valorMayor = -Double.MAX_VALUE;
        double valor;
        // para cada elemento que podria ir en la mochila
        for (int k = 0; k < mochila.getDimension(); k++) {
            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
            valor = (mochila.get(k) * pesos[k]) / beneficio(mochila, k);
            if (valorMayor < valor) {
                posMayor = k;
                valorMayor = valor;
            }
        }
        return posMayor;
    }

    /**
     * beneficio del elemento en la posicion: indice
     *
     * @param mochila
     * @param indice indice del elemento
     * @return
     */
    public double beneficio(Individuo mochila, int indice) {
        int contador = 0;
        double suma = 0;
        for (int i = 0; i < indice; i++) {
            suma += matrizBeneficios[i][indice] * mochila.get(i) * mochila.get(indice);
            contador += (mochila.get(i) * mochila.get(indice) != 0 ? 1 : 0);
        }
        for (int k = indice + 1; k < vectorPesos.length; k++) {
            suma += matrizBeneficios[indice][k] * mochila.get(k) * mochila.get(indice);
            contador += (mochila.get(k) * mochila.get(indice) != 0 ? 1 : 0);
        }
        return (suma + matrizBeneficios[indice][indice]*(contador/vectorPesos.length)) ;
    }

    /**
     *
     * @param IndMochila
     * @param pesos
     * @param capacidad
     * @param pos_articulos
     * @return
     */
    @Override
    public Individuo limitarInferiormente(Individuo IndMochila, double[] pesos, double capacidad, int[] pos_articulos) {
        Individuo mochila = (Individuo) IndMochila;
        double espacios = sacarEspacios(mochila, pesos, capacidad);
//        //
//        posiciones.sort((Integer o1, Integer o2) -> {
//            /**
//             * comparar los indices, segun su beneficio //
//             */
//            Double aptotudO1 = beneficio(mochila, o1);
//            Double aptotudO2 = beneficio(mochila, o2);
//            return aptotudO2.compareTo(aptotudO1);
//        });
//        pos_articulos = listToArray(posiciones);
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
}