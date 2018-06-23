package main.mochila.cuadratica.anson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import metaheuristicas.Aleatorio;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class FuncionMochilaCuadraticaGreedy_MM extends FuncionMochilaCuadraticaGreedy {

    public FuncionMochilaCuadraticaGreedy_MM(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
        nombre = "FMCG-MM";
    }
//
//    /**
//     * beneficio del elemento en la posicion: indice
//     *
//     * @param mochila
//     * @param indice indice del elemento
//     * @return
//     */
//    @Override
//    public double beneficio(IndividuoGen mochila, int indice) {
//        int contador = 0;
//        double suma = 0;
//        for (int i = 0; i < indice; i++) {
//            suma += matrizBeneficios[i][indice] * mochila.get(i);
//            contador += (mochila.get(i) * matrizBeneficios[i][indice] != 0 ? 1 : 0);
//        }
//        for (int k = indice + 1; k < vectorPesos.length; k++) {
//            suma += matrizBeneficios[indice][k] * mochila.get(k);
//            contador += (mochila.get(k) * matrizBeneficios[indice][k] != 0 ? 1 : 0);
//        }
//        return (suma + matrizBeneficios[indice][indice] * (contador / vectorPesos.length));
//    }
//
//    public int[] mayorBeneficio(IndividuoGen mochila) {
//
//        int posMayor = 0;
//        int posAnterior = 0;
//        double valorMayor = Double.POSITIVE_INFINITY;
//        double valor;
//        // para cada elemento que podria ir en la mochila
//        for (int k = 0; k < mochila.getDimension(); k++) {
//            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
////            valor = (mochila.get(k) * pesos[k]);
//            if (mochila.get(k) == 0) {
//                valor = (vectorPesos[k]) / beneficio(mochila, k);
////            valor = (mochila.get(k) * pesos[k]) / beneficio(mochila, k);
//                if (valorMayor > valor) {
//                    posAnterior = posMayor;
//
//                    posMayor = k;
//                    valorMayor = valor;
//                }
//            }
//        }
//        return new int[]{posMayor, posAnterior};
//    }
//
//    /**
//     * Si hay espacio en la mochila se seleccionan elementos, que generen mayor
//     * beneficio, hasta que no le quepa ningun elemento mas.
//     *
//     * @param IndMochila
//     * @param pesos
//     * @param capacidad
//     * @param pos_articulos
//     * @return
//     */
//    @Override
//    public IndividuoGen limitarInferiormente(IndividuoGen IndMochila, double[] pesos, double capacidad, int[] pos_articulos) {
//        IndividuoGen mochila = (IndividuoGen) IndMochila;
//        double espacios;
//
//        List<Integer> individuos = new ArrayList();
//        // en todos los articulos
//        for (int pos : pos_articulos) {
//            individuos.add(pos);
//        }
//
//        espacios = sacarEspacios(mochila, pesos, capacidad);
//        int pos;
//        int aleatorio;
//        while (true) {
////            rango = Math.min(rango, individuos.size());
////            aleatorio = Aleatorio.nextInt(rango);
////            pos = individuos.remove(aleatorio);
//            int[] result = mayorBeneficio(mochila);
//            pos = result[Aleatorio.nextInt(result.length)];
////            pos = 
//            espacios -= pesos[pos];
//            // dimension de la mochila
//            if (espacios < 0) {
//                break;
//            }
//            mochila.set(pos, 1);
//        }
//        return IndMochila;
//    }
}
