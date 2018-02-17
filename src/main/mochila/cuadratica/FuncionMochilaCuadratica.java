package main.mochila.cuadratica;

import java.util.ArrayList;
import java.util.List;
import main.mochila.FuncionMochila;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class FuncionMochilaCuadratica extends FuncionMochila {

    protected final double[][] matrizBeneficios;
    protected final double capacidad;
    protected final double[] vectorPesos;

    /**
     *
     * @param matrizBeneficios ganancia de cada elemento.
     * "matrizBeneficios[k][i]" es la ganancia que aporta el elemento k e i a la
     * vez
     * @param capacidad
     * @param vectorPesos lista de pesos
     * @param maxGlobal
     * @param prob_ceros
     */
    public FuncionMochilaCuadratica(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal, double prob_ceros) {
        super("Cuadratica", vectorPesos.length, prob_ceros);
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        this.maxGlobal = maxGlobal == null ? Double.POSITIVE_INFINITY : maxGlobal;
    }

    /**
     *
     * @param matrizBeneficios ganancia de cada elemento.
     * "matrizBeneficios[k][i]" es la ganancia que aporta el elemento k e i a la
     * vez
     * @param capacidad
     * @param vectorPesos lista de pesos
     * @param maxGlobal
     */
    public FuncionMochilaCuadratica(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super("Cuadratica", vectorPesos.length, 1);
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        this.maxGlobal = maxGlobal == null ? Double.POSITIVE_INFINITY : maxGlobal;
    }

    @Override
    public double evaluar(Individuo p) {
        super.evaluar(p);
        return obtenerPrecio(p);
    }

    public int upperBound(){
        int v;
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < vectorPesos.length; i++) {
            listaIndices.add(i);
        }
        //ordenacionde elementos
        listaIndices.sort((Integer o1, Integer o2) -> {
            Double peso1 = vectorPesos[o1];
            Double peso2 = vectorPesos[o2];
            // orden creciente
            return peso1.compareTo(peso2);
        });
        v = 0;
        int suma_V = 0;
        for (int i = 0; i < listaIndices.size(); i++) {
            suma_V += vectorPesos[listaIndices.get(i)];
            if (suma_V <= capacidad) {
                v++;
            } else {
                break;
            }
        }
        return v;
    }
    public double obtenerPrecio(Individuo mochila) {
        double sumaPrecios = 0;
        int dim = mochila.getDimension();
        for (int i = 0; i < dim; i++) {
            for (int j = i; j < dim; j++) {
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
     * obtiene el espacio total de cada tipo de restriccion dentro de la mochila
     *
     * @param mochila
     * @return
     */
    public double sacarEspacios(Individuo mochila) {
        return capacidad - obtenerPeso(mochila, vectorPesos);
    }
}
