/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import funciones.Funcion;
import java.util.List;
import pruebas.Archivo;
import pruebas.GraficoGnuPlot;

/**
 *
 * @author debian
 */
public class Hill_Climbing extends AlgoritmoMetaheuristico {

    double paso;

    public Hill_Climbing(Funcion funcion, double paso) {
        super(funcion, "SUBIENDO LA COLINA");
        this.paso = paso;
    }

    @Override
    public Punto ejecutar(long semilla, int iteraciones, List listaPuntosS) {
        Punto s = this.generarPunto(semilla);
        s.setCalidad(funcion.evaluar(s));
        if (listaPuntosS != null) {
            listaPuntosS.add(s);
        }
        Punto r;
        String nombreFile = nombre + "#" + funcion.getNombre();
//        Archivo.abrir(nombreFile + ".dat");
        for (int i = 0; i < iteraciones; i++) {

            r = tweak(s, i, paso);
            r.setCalidad(funcion.evaluar(r));
            if (r.compareTo(s) > 0) {
                s = r;
            }
//            System.out.println("s:" + s);
            if (listaPuntosS != null) {
                listaPuntosS.add(s);
            }
//            Archivo.escribir(s.toString());
        }
//        Archivo.terminar();
//        GraficoGnuPlot.plot(3, nombreFile, funcion);
        return s;
    }

}
