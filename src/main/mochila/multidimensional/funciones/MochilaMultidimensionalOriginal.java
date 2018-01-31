package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class MochilaMultidimensionalOriginal extends Funcion {

    protected final double[] capacidades;
    protected double prob_ceros;
    protected final double[][] VectorRestricciones;
    protected final double[] beneficios;

    /**
     *
     * @param VectorRestricciones vector de pesos de cada elemento de cada tipo
     * de restriccion. VectorRestricciones[i][k]; i=0,1,...,n; k=0,1,...,m; n:
     * numero de restricciones por elemento (tipo restricciones), m: numeros de
     * elementos
     * @param beneficios vector de veneficios por cada elemento
     * @param capacidades vector de capacidades por cada tipo de restriccion, es
     * decir, numero de mochilas.
     */
    public MochilaMultidimensionalOriginal(double[][] VectorRestricciones, double[] beneficios, double[] capacidades) {
        super("MOCHILA MD", beneficios.length, true);
        this.beneficios = beneficios;
        this.capacidades = capacidades;
        prob_ceros = 0.6;
        this.VectorRestricciones = VectorRestricciones;
    }

    @Override
    public double evaluar(Individuo mochila) {
        super.evaluar(mochila);
        limitar(mochila);
        double result = obtenerPrecio(mochila);
        return result;
    }

    public double obtenerPrecio(Individuo mochila) {
        double sumPX = 0;
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumPX += mochila.get(i) * beneficios[i];
        }
        return sumPX;
    }

    public double obtenerPeso(Individuo mochila, int indicePeso) {
        double sumWX = 0;
        // para cada elemento que podria ir en la mochila
        for (int i = 0; i < mochila.getDimension(); i++) {
            sumWX += mochila.get(i) * VectorRestricciones[indicePeso][i];
        }
        return sumWX;
    }

    @Override
    public void limitar(Individuo mochila) {
        int posicion;
        // para cada caracteristica(peso) del elemento
        for (int i = 0; i < capacidades.length; i++) {
            while (obtenerPeso(mochila, i) > capacidades[i]) {
                posicion = mayorPerjuicio(mochila, i);
                mochila.set(posicion, 0);
            }
        }

//        return mochila; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * optiene la posicion del elemento dentro de la mochila que causa mayor
     * perjuicio para el tipo de restriccion indicado por: indicePeso
     *
     * @param mochila
     * @param indiceRestriccion tipo de restriccion (tipo de peso)
     * @return
     */
    public int mayorPerjuicio(Individuo mochila, int indiceRestriccion) {
        int posMayor = 0;
        double valorMayor = -Double.MAX_VALUE;
        double valor;
        // para cada elemento que podria ir en la mochila
        for (int k = 0; k < mochila.getDimension(); k++) {
            // para indicePeso-esimo caracteristica(peso) del elemento k-esimo
            valor = mochila.get(k) * VectorRestricciones[indiceRestriccion][k];
            if (valorMayor < valor) {
                posMayor = k;
                valorMayor = valor;
            }
        }
        return posMayor;
    }

    @Override
    public String toString() {
        return "x+y";
    }

    @Override
    public String toString(Individuo individuo) {
        String cadena = "";
        for (int i = 0; i < capacidades.length; i++) {
            cadena += "[" + i + "]" + obtenerPeso(individuo, i) + ";";
        }
        return "calidad:" + individuo.getCalidad() + "; pesos:" + cadena;
    }

    @Override
    public Individuo generarIndividuo() {
        Individuo nuevop = new Individuo(this);
        for (int i = 0; i < nuevop.getDimension(); i++) {
            if (Aleatorio.nextDouble() > prob_ceros) {
                nuevop.set(i, 1);
            }
        }
        return nuevop;
    }

    public double[] getCapacidades() {
        return capacidades;
    }
}
