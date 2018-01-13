package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalMejorada3 extends MochilaMultidimensionalMejorada2 {

    /**
     *
     * @param capacidades
     * @param w lista de pesos y ganancia de cada elemento. "w.get(k)[length -
     * 1]" es la ganancia que aporta el elemento k
     *
     * @param maximizar
     */
    public MochilaMultidimensionalMejorada3(double[] capacidades, List<double[]> w, boolean maximizar) {
        super(capacidades, w, maximizar);
        this.nombre = "MOCHILA MD M3";
//        maxGlobal = 117930.0;
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
//

    /**
     * obtiene la densidad de ganancia por unidad de peso
     *
     * @param k
     * @return
     */
    @Override
    public double densidadGanancia(int k) {
        double densidadGanancia = 0;
        /**
         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
         */
        double promedioPesos;
        double[] caract = w.get(k);
        int pos_UltimoElemento = caract.length - 1;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            densidadGanancia += caract[i];
        }
        //calculo desviacion estandar de pesos
        promedioPesos = densidadGanancia / pos_UltimoElemento;
        double desviacionEst = 0;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            desviacionEst += Math.pow(promedioPesos - caract[i], 2);
        }
        desviacionEst = Math.sqrt(desviacionEst / (pos_UltimoElemento - 1));
        densidadGanancia = ((caract[pos_UltimoElemento]) / (densidadGanancia + desviacionEst * 1));
        return desviacionEst;
    }

//    @Override
//    public Individuo limitar(Individuo mochila) {
////        mochila.set(articulos.get(0), 1);
////        mochila.set(articulos.get(1),1);
////        mochila.set(articulos.get(2),1);
////        mochila.set(articulos.get(3),1);
//        limitarSuperiormente(mochila);
//        limitarInferiormente(mochila);
//        return mochila; //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public Individuo generarPunto() {
        Double[] valores = new Double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = 0.0;
        }
        Individuo nuevop = new Individuo(this, valores, isMaximizar());
        limitarInferiormente(nuevop);
        return nuevop;
    }
}
