package funciones;

import java.util.List;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class ErrorCuadratico extends Funcion {

    private final List<Punto> puntosReferencia;

    public ErrorCuadratico(double limite, int dimension, List<Punto> puntosReferencia) {
        super("ERROR CUADRATICO", limite, dimension);
        this.puntosReferencia = puntosReferencia;
    }

    @Override
    public double evaluar(Punto punto) {
        punto = getPuntoAEvaluarError(puntosReferencia, punto);
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

    public Punto getPuntoAEvaluarError(List<Punto> listPuntos, Punto coeficientes) {
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

}
