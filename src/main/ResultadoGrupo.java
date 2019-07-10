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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.mochila.cuadratica.utilidades.ParametrosInstancia;
import metaheuristicas.IndividuoGen;

/**
 * resultados de ejecucion de un algoritmo en N numero de pruebas
 *
 * @author debian
 */
public class ResultadoGrupo implements Iterable<ResultadoAlgoritmo>{

    private List<ResultadoAlgoritmo> resultadosAlgoritmo;
    private String instancia;
    private IndividuoGen mejorIndividuo;
    private ParametrosInstancia parametros;

    public ResultadoGrupo(String instancia, IndividuoGen mejorIndividuo) {
        this.instancia = instancia;
        this.mejorIndividuo = mejorIndividuo;
    }

    public ResultadoGrupo() {
        resultadosAlgoritmo = new ArrayList();
    }

    public int size() {
        return resultadosAlgoritmo.size();
    }

    public boolean isEmpty() {
        return resultadosAlgoritmo.isEmpty();
    }

    public boolean add(ResultadoAlgoritmo e) {
        return resultadosAlgoritmo.add(e);
    }

    public ResultadoAlgoritmo get(int index) {
        return resultadosAlgoritmo.get(index);
    }

//    public List<ResultadoAlgoritmo> getResultadosAlgoritmo() {
//        return resultadosAlgoritmo;
//    }
//
//    public void setResultadosAlgoritmo(List<ResultadoAlgoritmo> resultadosAlgoritmo) {
//        this.resultadosAlgoritmo = resultadosAlgoritmo;
//    }
    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public IndividuoGen getMejorIndividuo() {
        return mejorIndividuo;
    }

    public void setMejorIndividuo(IndividuoGen mejorIndividuo) {
        this.mejorIndividuo = mejorIndividuo;
    }

    public ParametrosInstancia getParametros() {
        return parametros;
    }

    public void setParametros(ParametrosInstancia parametros) {
        this.parametros = parametros;
    }

    
    @Override
    public Iterator<ResultadoAlgoritmo> iterator() {
        return resultadosAlgoritmo.iterator();
    }

}
