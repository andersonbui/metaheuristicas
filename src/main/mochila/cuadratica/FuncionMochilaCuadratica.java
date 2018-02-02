package main.mochila.cuadratica;

import main.mochila.FuncionMochila;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaHyperplaneExploration;
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
        this.maxGlobal = maxGlobal;
    }

    @Override
    public double evaluar(Individuo p) {
        super.evaluar(p);
        p.setPeso(obtenerPeso(p, vectorPesos));
        return obtenerPrecio(p);
    }

    public double obtenerPrecio(Individuo mochila) {
        double sumaPrecios = 0;
        int dimension = mochila.getDimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = i; j < dimension; j++) {
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
