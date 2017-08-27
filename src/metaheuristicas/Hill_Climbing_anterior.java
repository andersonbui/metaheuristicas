/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import funciones.Funcion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debian
 */
public class Hill_Climbing_anterior extends AlgoritmoMetaheuristico {

    double paso;

    public Hill_Climbing_anterior(Funcion funcion, double paso) {
        super(funcion, "SUBIENDO LA COLINA MAXIMA ORTOGONAL");
        this.paso = paso;
    }

    @Override
    public Punto ejecutar(long semilla, int iteraciones, Collection listaPuntosS) {
        Punto s = this.generarPunto(semilla);
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r;
        for (int i = 0; i < iteraciones; i++) {
            r = tweak(new Punto(new double[funcion.getDimension()]), (i + semilla), paso);
            List<Punto> listaSucesores = null;
            try {
                listaSucesores = listaPosiblesSucesoresOrtogonales(s, r.getValores());
//                listaSucesores = listaPosiblesSucesoresOrtogonales(s, r.getValores());
            } catch (Exception ex) {
                Logger.getLogger(Hill_Climbing_anterior.class.getName()).log(Level.SEVERE, null, ex);
            }
            Collections.sort(listaSucesores);
//            System.out.println("array:"+listaSucesores);
            r = listaSucesores.get(listaSucesores.size() - 1);
//            System.out.println("r:"+r);
            r.setCalidad(funcion.evaluar(r));
            if (r.compareTo(s) > 0) {
                s = r;
            }
            if (listaPuntosS != null) {
                listaPuntosS.add(s);
            }
        }
        return s;
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
        Hill_Climbing_anterior.combinaciones(new double[]{-1, 1}, new double[punto.getValores().length], 0, listaRest);

        for (double[] item : listaRest) {

            vectorAux = punto.getValores().clone();
            for (int i = 0; i < vectorAux.length; i++) {
                vectorAux[i] = limitar(vectorAux[i] + item[i] * vectorDireccionOriginal[i]);
            }
            nuevoPunto = new Punto(vectorAux);
            nuevoPunto.setCalidad(funcion.evaluar(nuevoPunto));
            listaPuntos.add(nuevoPunto);
        }
        return listaPuntos;
    }

    public static void combinaciones(double[] valores, double[] resultado, int index, List listaResultado) {
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
