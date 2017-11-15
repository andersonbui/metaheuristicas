package funciones;

import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;

/**
 * @author debian
 */
public class Mochila extends Funcion {

    private final List<double[]> w;
    private final double L;
    private final double prob_ceros;
    private Punto mejor;

    public Mochila(double capacidad, List<double[]> w, boolean maximizar) {
        super("MOCHILA", capacidad, w.size(), maximizar);
        this.w = w;
        L = 1000; // grande y entero para garantizatr una buena penalizacion
        prob_ceros = 0.6;
    }

    @Override
    public double evaluar(Punto punto) {
        punto = limitar(punto);
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
        double sumWX = 0;
        double sumPX = 0;
        double result = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.getValor(i) * w.get(i)[0];
            sumPX += punto.getValor(i) * w.get(i)[1];
        }
        double resta = sumWX - limite;
        double max = resta > 0 ? resta : 0;
        double producto = L * max;
        result = sumPX - producto;
//        if (result > 1024) {
//            System.out.print("");
//        }
        return result;
    }

    public double obtenerPeso(Punto punto) {
        double sumWX = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.getValor(i) * w.get(i)[0];
        }
        return sumWX;
    }

    @Override
    public Punto limitar(Punto punto) {
        int posicion;
        while (obtenerPeso(punto) > limite) {
            posicion = mayor(punto);
            punto.set(posicion, 0);
        }
        return punto; //To change body of generated methods, choose Tools | Templates.
    }

    public int mayor(Punto punto) {
        int mayor = 0;
        for (int i = 1; i < punto.getDimension(); i++) {
            if (mayor < 0 || punto.getValor(mayor) * w.get(mayor)[0] < punto.getValor(i) * w.get(i)[0]) {
                mayor = i;
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
