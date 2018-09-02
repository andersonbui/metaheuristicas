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
package main.mochila.cuadratica;

import main.Ejecutor;
import static main.Ejecutor.imprimirConFormato;
import main.Recorrido;
import main.mochila.Grupo;

/**
 *
 * @author debian
 */
public class EjecutorCuadratica extends Ejecutor{
    
    public Recorrido ejecutarAlgoritmosMasFunciones(Grupo grupo,
            boolean graficaRecorrido, boolean graficaConvergencia,
            int numeroPruebas) {

        System.out.println("----------------------------------------------------------.");
        System.out.println("NPI: Numero iteraciones promedio. -- ");
        System.out.print("TP: Tiempo Promedio.");
        System.out.println("TE: Tasa de exito. -- ");
        System.out.println("DPR: Desviación porcentual relativa.");
        System.out.println("MAXIMO: " + grupo.getParametros().getMaxGlobal());
        System.out.println("CAPACIDAD: " + grupo.getParametros().getCapacidad());
        System.out.println("----------------------------------------------------------.");

        imprimirConFormato("FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
                "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
        String titulo = "(" + grupo.getNombreFuncion() + ")";
        
        Recorrido mejorRecorrido = super.ejecutarGrupo(grupo, graficaRecorrido, graficaConvergencia, numeroPruebas, titulo);
        
        System.out.println("");
        return mejorRecorrido;
    }


}
