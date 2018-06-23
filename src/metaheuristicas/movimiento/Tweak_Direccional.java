package metaheuristicas.movimiento;

import metaheuristicas.funcion.FuncionGen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class Tweak_Direccional extends Tweak_1 {

    public Tweak_Direccional(double ancho) {
        super(ancho);
    }

    FuncionGen funcion;

    @Override
    public IndividuoGen tweak(IndividuoGen punto) {
        funcion = punto.getFuncion();
        IndividuoGen r = punto.clone();
        r.evaluar();
        List<IndividuoGen> listaSucesores = listaPosiblesSucesoresOrtogonales(punto, r.getValores());
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
    public List<IndividuoGen> listaPosiblesSucesoresOrtogonales(IndividuoGen punto, double[] vectorDireccionOriginal) {

        if (vectorDireccionOriginal.length != punto.getValores().length) {
            throw new IllegalArgumentException("error! tamanio diferente de punto y vector direccion!");
        }
        List<IndividuoGen> listaPuntos = new ArrayList();
        List<double[]> listaRest = new ArrayList();
        IndividuoGen nuevoPunto;
        combinaciones(new double[]{-1, 1}, new double[punto.getValores().length], 0, listaRest);

        for (double[] item : listaRest) {
            nuevoPunto = punto.clone();
            for (int i = 0; i < nuevoPunto.getDimension(); i++) {
                nuevoPunto.set(i, nuevoPunto.get(i) + item[i] * vectorDireccionOriginal[i]);
            }
            nuevoPunto.evaluar();
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
