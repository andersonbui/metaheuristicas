package funciones;

import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalMejorada extends Funcion {

    private final double[] capacidades;
    private final List<double[]> w;
    private final double prob_ceros;
    private Punto mejor;

    /**
     *
     * @param capacidades
     * @param w
     * @param maximizar
     */
    public MochilaMultidimensionalMejorada(double[] capacidades, List<double[]> w, boolean maximizar) {
        super("MOCHILA MM", 2, w.size(), maximizar);
        this.capacidades = capacidades;
        this.w = w;
        prob_ceros = 0.6;
    }

    @Override
    public double evaluar(Punto punto) {
//        punto = limitar(punto);
        double result = obtenerPrecio(punto);
        punto.setCalidad(result);
        if (mejor == null || mejor.compareTo(punto) < 0) {
            mejor = punto;
            result = obtenerPrecio(punto);
            punto.setCalidad(result);
        }
        return result;
    }

    public double obtenerPrecio(Punto punto) {
        double sumPX = 0;
        int length = w.get(0).length;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumPX += punto.getValor(i) * w.get(i)[length - 1];
        }
        return sumPX;
    }

    public double obtenerPeso(Punto punto, int indicePeso) {
        double sumWX = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.getValor(i) * w.get(i)[indicePeso];
        }
        return sumWX;
    }

    @Override
    public Punto limitar(Punto punto) {
        int posicion;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            posicion = 0;
            while (obtenerPeso(punto, i) > capacidades[i]) {
                posicion = mayor(punto, i);
                punto.set(posicion, 0);
            }
        }

        return punto; //To change body of generated methods, choose Tools | Templates.
    }

    public int mayor(Punto punto, int indicePeso) {
        int mayor = -1;
        double valorMayor = 0;
        double valor;
        // para cada elemento que podria ir en la mochila
        for (int k = 1; k < punto.getDimension(); k++) {
            valor = 0;
            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
            valor += punto.getValor(k) * w.get(k)[indicePeso];
            if (mayor < 0 || valorMayor < valor) {
                mayor = k;
                valorMayor = valor;
            }
        }
        return mayor;
    }

    @Override
    public String toString() {
        return "x+y";
    }

    @Override
    public Punto generarPunto(Random rand) {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (rand.nextDouble() <= prob_ceros ? 0 : 1);
        }
        Punto nuevop = new Punto(this, valores, isMaximizar());
        nuevop.evaluar();
        return nuevop;
    }

    public Punto getMejor() {
        return mejor;
    }

    public void setMejor(Punto mejor) {
        this.mejor = mejor;
    }

}
