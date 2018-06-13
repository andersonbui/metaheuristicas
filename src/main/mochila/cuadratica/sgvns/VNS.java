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
package main.mochila.cuadratica.sgvns;

import java.util.List;
import java.util.Random;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 * @param <funcionVNS>
 */
public class VNS<funcionVNS extends FuncionSGVNS> extends AlgoritmoMetaheuristico<funcionVNS> {

    /**
     *
     * @param funcion
     */
    public VNS(funcionVNS funcion) {
        this.funcion = funcion;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Individuo> ejecutar() {
        solucionInicial();
        return null;
    }

    public Individuo solucionInicial() {
        Individuo individuo = funcion.generarIndividuo();
        individuo.getDimension();
        Random rand = new Random();
        
        long tiempo_inicial = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            individuo.set(rand.nextInt(funcion.getDimension()), i);
            individuo.evaluar();
            funcion.calcularPeso(individuo);
        }
        long tiempo_final = System.currentTimeMillis();
        System.out.println("tiempo: " + (tiempo_final - tiempo_inicial));
        return null;
    }

    public int buscarMayor(Individuo individuo) {
        double[] valores = individuo.getValores();
        int posicion = 0;
        for (int i = 0; i < valores.length; i++) {
            individuo.evaluar();
        }
        return posicion;
    }

}
