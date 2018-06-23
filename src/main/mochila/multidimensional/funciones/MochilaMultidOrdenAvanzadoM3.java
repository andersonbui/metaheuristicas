package main.mochila.multidimensional.funciones;

import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class MochilaMultidOrdenAvanzadoM3 extends MochilaMultidOrdenXDensidadBeneficioM2 {

    /**
     *
     * @param VectorRestricciones
     * @param beneficios
     * @param capacidades
     *
     */
    public MochilaMultidOrdenAvanzadoM3(double[][] VectorRestricciones, double[] beneficios, double[] capacidades) {
        super(VectorRestricciones, beneficios, capacidades);
        this.nombre = "MOCHILA MD M3";
//        maxGlobal = 117930.0;
        prob_ceros = 0.9401; //pocos elementos y mas de dos dimensiones
//        prob_ceros = 0.999; // muchos datos y de dos dimensiones
    }

    /**
     * obtiene la densidad de ganancia por unidad de peso
     *
     * @param indice indice del elemento
     * @return densidad de suma_pesos/beneficio del elemento en la posicion
     * indice
     */
    @Override
    public double densidadGanancia(int indice) {
        // ((B5*B$2 + C5*C$2)/(D$2*K5))*L5
        double sumaElementos = 0;
        double sumaPesos = 0;
        double sumaCapacidades = 0;
        /**
         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
         */
        for (int i = 0; i < capacidades.length; i++) {
            sumaElementos += VectorRestricciones[i][indice] * capacidades[i];
            sumaCapacidades += capacidades[i];
            sumaPesos += VectorRestricciones[i][indice];
        }
        double densidad = ((beneficios[indice]) / (sumaPesos));
        double resultado = (sumaElementos / (sumaCapacidades * sumaPesos)) * densidad;
        return resultado;
    }
}
