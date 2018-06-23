package main.mochila.simple;

import metaheuristicas.IndividuoGen;
import metaheuristicas.funcion.FuncionGen;
import java.util.List;
import metaheuristicas.Aleatorio;

/**
 * @author debian
 */
public class MochilaSimple extends FuncionGen {

    private final List<double[]> w;
    private final double L;
    private final double capacidad;
    private final double prob_ceros;
    private IndividuoGen mejor;

    public MochilaSimple(double capacidad, List<double[]> w, boolean maximizar) {
        super("MOCHILA", w.size(), maximizar);
        this.capacidad = capacidad;
        this.w = w;
        L = 1000; // grande y entero para garantizatr una buena penalizacion
        prob_ceros = 0.6;
    }

    @Override
    public double evaluar(IndividuoGen punto) {
        limitar(punto);
        double result = obtenerPrecio(punto);
        return result;
    }

    public double obtenerPrecio(IndividuoGen punto) {
        double sumWX = 0;
        double sumPX = 0;
        double result = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.get(i) * w.get(i)[0];
            sumPX += punto.get(i) * w.get(i)[1];
        }
        double resta = sumWX - capacidad;
        double max = resta > 0 ? resta : 0;
        double producto = L * max;
        result = sumPX - producto;
//        if (result > 1024) {
//            System.out.print("");
//        }
        return result;
    }

    public double obtenerPeso(IndividuoGen punto) {
        double sumWX = 0;
        for (int i = 0; i < punto.getDimension(); i++) {
            sumWX += punto.get(i) * w.get(i)[0];
        }
        return sumWX;
    }

    @Override
    public void limitar(IndividuoGen punto) {
        int posicion;
        while (obtenerPeso(punto) > capacidad) {
            posicion = mayor(punto);
            punto.set(posicion, 0);
        }
    }

    public int mayor(IndividuoGen punto) {
        int mayor = 0;
        for (int i = 1; i < punto.getDimension(); i++) {
            if (mayor < 0 || punto.get(mayor) * w.get(mayor)[0] < punto.get(i) * w.get(i)[0]) {
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
    public IndividuoGen generarIndividuo() {
        double[] valores = new double[getDimension()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = (Aleatorio.nextDouble() <= prob_ceros ? 0. : 1);
        }
        IndividuoGen nuevop = new IndividuoGen(this, valores);
        nuevop.evaluar();
        return nuevop;
    }

    public IndividuoGen getMejor() {
        return mejor;
    }

    public void setMejor(IndividuoGen mejor) {
        this.mejor = mejor;
    }

}
