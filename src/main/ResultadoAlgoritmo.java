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

import metaheuristicas.AlgoritmoMetaheuristico;

/**
 * resultados de ejecucion de un algoritmo en N numero de pruebas
 *
 * @author debian
 */
public class ResultadoAlgoritmo {

    public AlgoritmoMetaheuristico algoritmo;
    public double promedioIteraciones;
    public double numPruebas;
    public double exitos;
    public double promedioCalidadOptimos;
    public double desviacionCalidadOptimos;
    public double tiempoTotal;
    public double promedionumEvaluaciones;
    public Recorrido mejorRecorrido;

    /**
     * 
     * @param algoritmo
     * @param promedioIteraciones
     * @param numPruebas
     * @param exitos
     * @param promedioCalidadOptimos
     * @param desviacionCalidadOptimos
     * @param tiempoTotal
     * @param promedionumEvaluaciones
     * @param mejorRecorrido 
     */
    public ResultadoAlgoritmo(AlgoritmoMetaheuristico algoritmo, double promedioIteraciones, double numPruebas, double exitos, double promedioCalidadOptimos, double desviacionCalidadOptimos, double tiempoTotal, double promedionumEvaluaciones, Recorrido mejorRecorrido) {
        this.algoritmo = algoritmo;
        this.promedioIteraciones = promedioIteraciones;
        this.numPruebas = numPruebas;
        this.exitos = exitos;
        this.promedioCalidadOptimos = promedioCalidadOptimos;
        this.desviacionCalidadOptimos = desviacionCalidadOptimos;
        this.tiempoTotal = tiempoTotal;
        this.promedionumEvaluaciones = promedionumEvaluaciones;
        this.mejorRecorrido = mejorRecorrido;
    }

    
}
