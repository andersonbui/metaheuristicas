package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class MochilaMultidOrdenXDensidadBeneficio extends MochilaMultidimensional_LimitRelleno {

    /**
     *
     * @param VectorRestricciones
     * @param beneficios
     * @param capacidades
     *
     */
    public MochilaMultidOrdenXDensidadBeneficio(double[][] VectorRestricciones, double[] beneficios, double[] capacidades) {
        super(VectorRestricciones, beneficios, capacidades);
        this.nombre = "MOCHILA MD M2";
        prob_ceros=0.8;
    }

    /**
     *
     * @param indice
     * @return
     */
    @Override
    public double beneficio(int indice) {
        return densidadGanancia(indice);
    }

    /**
     * obtiene la densidad de ganancia por unidad de peso
     *
     * @param indice indice del elemento
     * @return densidad de suma_pesos/beneficio del elemento en la posicion
     * indice
     */
    public double densidadGanancia(int indice) {
        double densidadGanancia = 0;
        for (double[] VectorRestriccion : VectorRestricciones) {
            densidadGanancia += VectorRestriccion[indice];
        }
        densidadGanancia = (beneficios[indice] / densidadGanancia);
        return densidadGanancia;
    }
    
    //    /**
//     * obtiene la densidad de ganancia por unidad de peso
//     *
//     * @param indice indice del elemento
//     * @return densidad de suma_pesos/beneficio del elemento en la posicion
//     * indice
//     */
//    @Override
//    public double densidadGanancia(int indice) {
//        double densidadGanancia = 0;
//        /**
//         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
//         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
//         */
//        double promedioPesos;
//        for (double[] VectorRestriccione : VectorRestricciones) {
//            densidadGanancia += VectorRestriccione[indice];
//        }
//        //calculo desviacion estandar de pesos
//        promedioPesos = densidadGanancia / VectorRestricciones.length;
//        double desviacionEst = 0;
//        for (double[] VectorRestriccione : VectorRestricciones) {
//            desviacionEst += Math.pow(promedioPesos - VectorRestriccione[indice], 2);
//        }
//        desviacionEst = Math.sqrt(desviacionEst / (beneficios.length - 1));
//        densidadGanancia = ((beneficios[indice]) / (densidadGanancia + desviacionEst * 1));
//        return densidadGanancia;
//    }
}
