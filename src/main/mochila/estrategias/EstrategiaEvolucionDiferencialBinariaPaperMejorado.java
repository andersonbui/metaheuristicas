/*
 * Copyright (C) 2017 debian
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
package main.mochila.estrategias;

import metaheuristicas.IndividuoGen;
import metaheuristicas.poblacion.Poblacion;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialBinariaPaperMejorado extends EstrategiaEvolucionDiferencialBinariaPaper {

    public EstrategiaEvolucionDiferencialBinariaPaperMejorado(int tamPoblacion) {
        super(tamPoblacion);
        nombre = "EstrategiaEDB_PaperMM";
        cr = 0.2;
        alfa = 0.8;
        b = 20;
    }

    /**
     * genera hijos a partir de un padre y una madre y compiten en calidad entre
     * todos.
     *
     * @param numIndividuosElitismo
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo) {
        Poblacion siguienteGeneracion = poblacion.clone();
        siguienteGeneracion.aumentarGeneracion();
        siguienteGeneracion.clear();
        for (int k = 0; k < poblacion.size(); k++) {
            IndividuoGen objetivo = poblacion.remove(k);
            // MUTACION
            IndividuoGen mutado = mutar(poblacion);
            // CRUCE -> generacion del vector prueba
            IndividuoGen individuoPrueba = cruce(objetivo, mutado, k);
            // SELECCION
            seleccion(objetivo, individuoPrueba, siguienteGeneracion);
            poblacion.add(objetivo);
//            siguienteGeneracion.add(objetivo);
        }
        return siguienteGeneracion;
    }

//    @Override
//    protected IndividuoGen resta(IndividuoGen minuendo, IndividuoGen sustraendo) {
//        IndividuoGen diferencia = minuendo.clone();
//        double resta;
//        for (int i = 0; i < diferencia.getDimension(); i++) {
//            resta = minuendo.get(i) - sustraendo.get(i);
//            resta = Math.abs(resta % 2);
//            diferencia.set(i, resta);
//        }
//        return diferencia;
//    }

//    @Override
//    protected IndividuoGen suma(IndividuoGen sumando, IndividuoGen sumando2) {
//        IndividuoGen resultado = (IndividuoGen) sumando.clone();
//        double suma;
//        for (int i = 0; i < resultado.getDimension(); i++) {
//            suma = sumando.get(i) + sumando2.get(i);
//            suma = Math.abs(suma % 2);
//            resultado.set(i, suma);
//        }
//        return resultado;
//    }

}
