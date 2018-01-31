package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class FuncionMochilaGreedy extends MochilaMultidOrdenXDensidadBeneficioM2 {

    /**
     *
     * @param VectorRestricciones
     * @param beneficios
     * @param capacidades
     */
    public FuncionMochilaGreedy(double[][] VectorRestricciones, double[] beneficios, double[] capacidades) {
        super(VectorRestricciones, beneficios, capacidades);
        this.nombre = "Greedy";
//        maxGlobal = 117930.0;

    }

    /**
     * obtiene la densidad de ganancia por unidad de peso
     *
     * @param k
     * @return
     */
    public double funcionSeleccion(int k) {
        double densidadGanancia = 0;
        /**
         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
         * "VectorRestricciones[k][length - 1]" es la ganancia que aporta el
         * elemento k
         */
//        double promedioPesos;
        double[] caract = VectorRestricciones[k];
        int pos_UltimoElemento = caract.length - 1;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            densidadGanancia += caract[i];
        }
        //calculo desviacion estandar de pesos
//        promedioPesos = densidadGanancia / pos_UltimoElemento;
//        double desviacionEst = 0;
//        for (int i = 0; i < pos_UltimoElemento; i++) {
//            desviacionEst += Math.pow(promedioPesos - caract[i], 2);
//        }
//        desviacionEst = Math.sqrt(desviacionEst / (pos_UltimoElemento - 1));
//        densidadGanancia = ((caract[pos_UltimoElemento]) / (densidadGanancia + desviacionEst * 0.5));
        densidadGanancia = ((caract[pos_UltimoElemento]) / (densidadGanancia));
        return densidadGanancia;
    }

    @Override
    public double evaluar(Individuo mochila) {
        super.evaluar(mochila);
        double result = obtenerPrecio(mochila);
        return result;
    }

    public Individuo generarPunto(double valor) {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (Aleatorio.nextDouble() <= prob_ceros ? 0. : valor);
        }
        Individuo nuevop = new Individuo(this, valores);
        return nuevop;
    }
//    public int menorDensidadGananciaEnMochila(Individuo mochila) {
//        int posicionMayorOptimo = 0;
//        double densidadGMenor = Double.MAX_VALUE;
//        double densidadGanancia;
//        // para cada elemento que podria ir en la mochila
//        for (int k = 1; k < mochila.getDimension(); k++) {
//            if (mochila.getValor(k) != 0) {
//                densidadGanancia = funcionSeleccion(k);
//                if (densidadGMenor > densidadGanancia) {
//                    posicionMayorOptimo = k;
//                    densidadGMenor = densidadGanancia;
//                }
//            }
//        }
//        return posicionMayorOptimo;
//    }

    /**
     * ver si el peso de los objetos de la ochila excede alguna capacidad
     *
     * @param mochila
     * @return
     */
    public boolean factible(Individuo mochila) {

        for (int i = 0; i < capacidades.length; i++) {
            if (obtenerPeso(mochila, i) > capacidades[i]) {
                return false;
            }
        }
        return true;
    }

//    public Individuo limitarInferiormente(Individuo mochila) {
//        double[] espacios = sacarEspacios(mochila);
//        boolean cabeArticulo;
//        // en todos los articulos
//        for (int pos : articulos) {
//            cabeArticulo = true;
//            if (mochila.get(pos) == 0) {
//                // dimension de la mochila
//                for (int i = 0; i < espacios.length; i++) {
//                    if (espacios[i] < VectorRestricciones[i][pos]) {
//                        cabeArticulo = false;
//                        break;
//                    }
//                }
//                if (cabeArticulo) {
//                    mochila.set(pos, 1);
//                    espacios = sacarEspacios(mochila);
//                }
//            }
//        }
//        return mochila;
//    }
}
