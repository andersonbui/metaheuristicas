/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author debian
 */
public class AproximacionPG2 extends AlgoritmoMetaheuristico {

    private final List<Punto> puntosReferencia;
    private final double prob_ceros = 0.5;
    private final double paso;

    /**
     * suiendo la colina (simple)
     *
     * @param nombre
     * @param puntosReferencia
     * @param paso
     */
    public AproximacionPG2(String nombre, List<Punto> puntosReferencia, double paso) {
        super(nombre);
        this.paso = paso;
        this.puntosReferencia = puntosReferencia;
    }

    @Override
    public Punto ejecutar(Punto inicial, int iteraciones, Collection listaPuntosS) {
        Punto s = inicial;
        s.setCalidad(funcion.evaluar(getFitness(puntosReferencia, s)));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak(s, paso);
            r.setCalidad(funcion.evaluar(getFitness(puntosReferencia, r)));

            if (r.compareTo(s) > 0) {
                s = r;
            }
            if (listaPuntosS != null) {
                listaPuntosS.add(s);
            }
        }
        return s;
    }

//    @Override
//    public Punto tweak(Punto punto, double paso) {
//        return null;
//    }
    public Punto getFitness(List<Punto> listPuntos, Punto coeficientes) {
        double[] valoresFitness = new double[listPuntos.size()];
        for (int i = 0; i < listPuntos.size(); i++) {

            Punto punto = listPuntos.get(i);
            valoresFitness[i] = punto.getCalidad() - funcionPolinomio(punto, coeficientes);
        }
        return new Punto(valoresFitness);
    }

    /**
     *
     * @param punto tamanio 10
     * @param coeficientes tamanio 54
     * @return
     */
    public double funcionPolinomio(Punto punto, Punto coeficientes) {
        // 
        double[] valoresX = punto.getValores();
        double[] valoresCoeficientes = coeficientes.getValores();
        int posCoeficientes = 0;
        double sumaTotal = 0;

        for (int i = 0; i < valoresX.length; i++) {
            for (int j = i; j < valoresX.length; j++) {
                sumaTotal += valoresCoeficientes[posCoeficientes] * valoresX[j] * valoresX[i];
                posCoeficientes++;
            }
        }
        return sumaTotal;
    }

    @Override
    public Punto generarPunto() {
        Punto nuevop = super.generarPunto();
        double[] valores = nuevop.getValores();
        // end improve
        for (int i = 0; i < valores.length; i++) {
            if (rand.nextDouble() < prob_ceros) {
                valores[i] = 0;
            }
        }
        return nuevop;
    }

}
