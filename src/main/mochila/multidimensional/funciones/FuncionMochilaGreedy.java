package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import java.util.List;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class FuncionMochilaGreedy extends MochilaMultidimensionalMejorada2 {

    /**
     *
     * @param capacidades
     * @param w lista de pesos y ganancia de cada elemento. "w.get(k)[length -
     * 1]" es la ganancia que aporta el elemento k
     *
     * @param maximizar
     */
    public FuncionMochilaGreedy(double[] capacidades, List<double[]> w, boolean maximizar) {
        super(capacidades, w, maximizar);
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
         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
         */
//        double promedioPesos;
        double[] caract = w.get(k);
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
        mochila.setCalidad(result);
        return result;
    }

    @Override
    public double obtenerPrecio(Individuo punto) {
        double sumPX = 0;
        int length = w.get(0).length;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumPX += punto.getValor(i) * w.get(i)[length - 1];
        }
        return sumPX;
    }

    @Override
    public double obtenerPeso(Individuo mochila, int indicePeso) {
        double sumWX = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumWX += mochila.getValor(i) * w.get(i)[indicePeso];
        }
        return sumWX;
    }

    @Override
    public String toString() {
        return "x+y";
    }

    public Individuo generarPunto(double valor) {
        Double[] valores = new Double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (Aleatorio.nextDouble() <= prob_ceros ? 0. : valor);
        }
        Individuo nuevop = new Individuo(this, valores, isMaximizar());
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

    public Individuo limitarInferiormente(Individuo mochila, List<Integer> articulos) {
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

    public double desviacion(Individuo individuo) {
        double densidadGanancia = 0;
        /**
         * para indicePeso-esimo caracteristica(peso) del elemento k-esimo
         * "w.get(k)[length - 1]" es la ganancia que aporta el elemento k
         */
        Double[] valores = individuo.getValores();
        double promedioPesos;
        double[] caract = w.get(0);
        int pos_UltimoElemento = caract.length - 1;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            for (int k = 0; k < w.size(); k++) {
                densidadGanancia += w.get(k)[i] * valores[k];
            }

        }
        //calculo desviacion estandar de pesos
        promedioPesos = densidadGanancia / pos_UltimoElemento;
        double desviacionEst = 0;
        double suma;
        for (int i = 0; i < pos_UltimoElemento; i++) {
            suma = 0;
            for (int k = 0; k < w.size(); k++) {
                suma += w.get(k)[i] * valores[k];

            }
            desviacionEst += Math.pow(promedioPesos - suma, 2);
        }
        desviacionEst = Math.sqrt(desviacionEst / (pos_UltimoElemento - 1));
        densidadGanancia = ((caract[pos_UltimoElemento]) / (densidadGanancia + desviacionEst * 1));

        return densidadGanancia;
    }

}
