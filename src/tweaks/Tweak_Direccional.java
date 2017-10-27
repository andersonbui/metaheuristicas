package tweaks;

import funciones.Funcion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Tweak_Direccional extends Tweak_1 {

    public Tweak_Direccional(double ancho) {
        super(ancho);
    }

    Funcion funcion;

    @Override
    public Punto tweak(Punto punto, Random rand) {
        funcion = punto.getFuncion();
        Punto r = punto.clone();
        r.evaluar();
        List<Punto> listaSucesores = listaPosiblesSucesoresOrtogonales(punto, r.getValores());
        Collections.sort(listaSucesores);
        // reemplazo del punto original
        punto = listaSucesores.get(listaSucesores.size() - 1);
        punto.evaluar();
        return punto;
    }

    /**
     * crea una lista que contiene los puntos que son ortogonales al vecor
     * direccion(incluido) dimension de punto dede ser igual al tamanio del
     * vector direccion.
     *
     * @param punto
     * @param vectorDireccionOriginal
     * @return
     */
    public List<Punto> listaPosiblesSucesoresOrtogonales(Punto punto, double[] vectorDireccionOriginal) {

        if (vectorDireccionOriginal.length != punto.getValores().length) {
            throw new IllegalArgumentException("error! tamanio diferente de punto y vector direccion!");
        }
        List<Punto> listaPuntos = new ArrayList();
        double[] vectorAux;
        List<double[]> listaRest = new ArrayList();
        Punto nuevoPunto;
        combinaciones(new double[]{-1, 1}, new double[punto.getValores().length], 0, listaRest);

        for (double[] item : listaRest) {
            nuevoPunto = punto.clone();
            vectorAux = nuevoPunto.getValores();
            for (int i = 0; i < vectorAux.length; i++) {
                vectorAux[i] = funcion.limitar(vectorAux[i] + item[i] * vectorDireccionOriginal[i]);
            }
            nuevoPunto.setCalidad(funcion.evaluar(nuevoPunto));
            listaPuntos.add(nuevoPunto);
        }
        return listaPuntos;
    }

    public void combinaciones(double[] valores, double[] resultado, int index, List listaResultado) {
        if (index >= resultado.length) {
            listaResultado.add(resultado.clone());
        } else {
            for (int i = 0; i < valores.length; i++) {
                resultado[index] = valores[i];
                combinaciones(valores, resultado, index + 1, listaResultado);
            }
        }
    }
}
