package main.regresion_cuadratica;

import metaheuristicas.IndividuoGen;
import metaheuristicas.funcion.FuncionGen;
import java.util.List;
import main.Algebraicas.FuncionAlgebraica;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class ErrorCuadratico extends FuncionAlgebraica {

    private final List<IndividuoGen> puntosReferencia;
    private final double prob_ceros = 0.8;

    public ErrorCuadratico(double limite, int dimension, List<IndividuoGen> puntosReferencia, boolean maximizar) {
        super("ERROR CUADRATICO", limite, dimension, maximizar);
        this.puntosReferencia = puntosReferencia;
    }

    /**
     * Evalua en la funcion de Error cuadratico
     *
     * @param punto
     * @return
     */
    @Override
    public double evaluar(IndividuoGen punto) {
        super.evaluar(punto);
        punto = getPuntoFitnessErrorCuadratico(puntosReferencia, punto);
        double resultado = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length; i++) {
            resultado += (valores[i] * valores[i]);
        }
        return resultado / valores.length;
    }

    @Override
    public String toString() {
        return "(x*x+y*y)/2";
    }

    /**
     * Genera punto que va a ser evaluado por la funcion Error cuadratico, a
     * partir de los puntos de prueba de regresion
     *
     * @param listPuntos
     * @param coeficientes
     * @return
     */
    public IndividuoGen getPuntoFitnessErrorCuadratico(List<IndividuoGen> listPuntos, IndividuoGen coeficientes) {
        double[] valoresFitness = new double[listPuntos.size()];
        for (int i = 0; i < listPuntos.size(); i++) {

            IndividuoGen punto = listPuntos.get(i);
            valoresFitness[i] = punto.getCalidad() - funcionPolinomio(punto, coeficientes);
        }
        return new IndividuoGen(this, valoresFitness);
    }

    /**
     * Evlua el punto en el polinomio generado a partir de los coeficientes mas
     * las correspondientes variables de un pilinomio grado 2
     *
     * @param punto punto
     * @param coeficientes coeficientes delpolinomio de grado 2
     * @return
     */
    public double funcionPolinomio(IndividuoGen punto, IndividuoGen coeficientes) {
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

    public IndividuoGen generarPunto(FuncionGen funcion) {
        IndividuoGen nuevop = funcion.generarIndividuo();
        double[] valores = nuevop.getValores();
        for (int i = 0; i < valores.length; i++) {
            if (Aleatorio.nextDouble() < prob_ceros) {
                valores[i] = 0.;
            }
        }
        return nuevop;
    }
}
