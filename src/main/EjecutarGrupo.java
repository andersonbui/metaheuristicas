/*
 * Copyright (C) 2018 debian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import gnuplot.GraficoGnuPlot;
import gnuplot.Punto;
import gnuplot.Punto2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metaheuristicas.FabricaAlgoritmoMetaheuristico;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */
public class EjecutarGrupo {
        
    private Grupo grupo;
    private boolean graficaRecorrido;
    private boolean graficaConvergencia;
    private int numeroPruebas;
    private String instancia;

    
    GraficoGnuPlot gnuplot;
    public EjecutarGrupo() {
        gnuplot = new GraficoGnuPlot();
    }

    public final ResultadoGrupo ejecutarGrupo() {
        EjecutorAlgoritmo ejAlgoritmo = null;
        List<FabricaAlgoritmoMetaheuristico> l_amgoritmos = grupo.getAlgoritmos();
        Recorrido mejorRecorrido = null;
        ResultadoGrupo resultadoGrupo = new ResultadoGrupo();
        resultadoGrupo.setInstancia(instancia);
        List<HiloEjecucionAlgoritmo> listaHilos = new ArrayList<>();
        List<Recorrido> listaRecorridos = new ArrayList(); // para grafica de convergencia
        int maxIteraciones = grupo.getMaxIteraciones();
        for (FabricaAlgoritmoMetaheuristico algoritmo : l_amgoritmos) {
            ejAlgoritmo = new EjecutorAlgoritmo();
            ejAlgoritmo.setAlgoritmo(algoritmo);
            ejAlgoritmo.setNumeroPruebas(numeroPruebas);
            ejAlgoritmo.setMaxIteraciones(maxIteraciones);
            
            HiloEjecucionAlgoritmo hilaEA = new HiloEjecucionAlgoritmo(ejAlgoritmo);
            listaHilos.add(hilaEA);
//            FuncionGen funcion = algoritmo.getFuncion();
//            ResultadoAlgoritmo resultado = ejAlgoritmo.ejecutar();
//        resultadoGrupo.add(resultado);
//            Recorrido recorrido = resultado.mejorRecorrido;
//            // guardar mejor recorrido
//            if (mejorRecorrido == null || mejorRecorrido.compareTo(recorrido) < 0) {
//                mejorRecorrido = recorrido;
//            }
//            listaRecorridos.add(recorrido);
//            if (graficaRecorrido) {
//                grafico3D(recorrido.getRecorrido3D(), instancia, funcion);
//            }    
        }
        //ejecutar todos los algorirmos
        for (HiloEjecucionAlgoritmo hilo : listaHilos) {
            hilo.start();
        }
        // esperar a que terminen todos
        for (HiloEjecucionAlgoritmo hilo : listaHilos) {
            try {
                hilo.join();
                ResultadoAlgoritmo resultado = hilo.resultadoGrupo;
                FuncionGen funcion = resultado.algoritmo.getFuncion();
                resultadoGrupo.add(resultado);
                Recorrido recorrido = resultado.mejorRecorrido;
                // guardar mejor recorrido
                if (mejorRecorrido == null || mejorRecorrido.compareTo(recorrido) < 0) {
                    mejorRecorrido = recorrido;
                }
                listaRecorridos.add(recorrido);
                if (graficaRecorrido) {
                    grafico3D(recorrido.getRecorrido3D(), instancia, funcion);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(EjecutarGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        resultadoGrupo.setMejorIndividuo(mejorRecorrido.getMejorIndividuo());
        if (graficaConvergencia) {
            grafico2D(listaRecorridos, instancia);
        }
        return resultadoGrupo;
    }
        
    static class HiloEjecucionAlgoritmo extends Thread {

        private final EjecutorAlgoritmo ejecutorAlgoritmo;
        ResultadoAlgoritmo resultadoGrupo;

        public HiloEjecucionAlgoritmo(EjecutorAlgoritmo ejAlgoritmo) {
            this.ejecutorAlgoritmo = ejAlgoritmo;
        }

        @Override
        public void run() {
            resultadoGrupo = ejecutorAlgoritmo.ejecutar();
        }
    }
    
    public void grafico3D(List<Punto> cd, String titulo, FuncionGen funcion) {
        if (cd == null) {
            throw new IllegalArgumentException(titulo);
        }
        if (!(cd.isEmpty())) {
//                gnuplot.addFuncionAritmetica(funcion.toString());
            gnuplot.addFuncionAritmetica(funcion.toString());
//            gnuplot.setXrange(funcion.getLimite());
//            gnuplot.setYrange(funcion.getLimite());
            gnuplot.addConjuntoDatos(cd, funcion.getNombre());
            gnuplot.plot3D(titulo);

            gnuplot.limpiar();
        }
    }

    /**
     * crea Grafica de convergencia de la calidad de todos los puntos en cada
     * recorrido de listaRecorridos, cada uno con color y descripcion
     * correspondiente para poder ser identificados independientemente.
     *
     * @param listaRecorridos lista de recorridos graficados
     * @param titulo nombre de la grafica
     */
    public void grafico2D(List<Recorrido> listaRecorridos, String titulo) {
        int mayorTamanio = Integer.MIN_VALUE;
        for (Recorrido item : listaRecorridos) {
            mayorTamanio = Math.max(item.getRecorridoCalidad().size(), mayorTamanio);
        }

        for (Recorrido itemRecorrido : listaRecorridos) {
            List<Punto> cd = itemRecorrido.getRecorridoCalidad();
            int amanioCD = cd.size();
            Punto2D ultimo = (Punto2D) cd.get(amanioCD - 1);
            for (int i = 0; i < (mayorTamanio - amanioCD); i++) {
                ultimo = (Punto2D) ultimo.clone();
                ultimo.setX(ultimo.getX() + 1);
                cd.add(ultimo);
            }
            gnuplot.addConjuntoDatos(cd, itemRecorrido.getNombreRecorrido() + "(" + itemRecorrido.getPromedioCalidad() + ")");
        }

        gnuplot.plot2D(titulo);
        gnuplot.limpiar();
    }
    
    public  void setParametros(Grupo grupo,
            boolean graficaRecorrido, boolean graficaConvergencia,
            int numeroPruebas, String instancia) {
        
        this.grupo = grupo;
        this.graficaRecorrido = graficaRecorrido;
        this.graficaConvergencia = graficaConvergencia;
        this.numeroPruebas = numeroPruebas;
        this.instancia = instancia;
    }
}
