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
package metaheuristicas.poblacion;

import funciones.Funcion;
import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialBinariaPaperMejorado extends EstrategiaEvolucionDiferencial {

    private double b;

    public EstrategiaEvolucionDiferencialBinariaPaperMejorado(int tamPoblacion) {
        super(tamPoblacion);
        setNombreEstrategia("EstrategiaEDB_PaperMM");
        cr = 0.2;
        alfa = 0.8;
        b = 20;
    }

    @Override
    protected Punto mutar(Poblacion poblacion, Random rand) {
        Punto mutado = super.mutar(poblacion, rand);
        //variacion para problema de la mochila
        normalizar(mutado, rand);
        return mutado;
    }

    @Override
    protected Punto resta(Punto minuendo, Punto sustraendo) {
        Punto diferencia = minuendo.clone();
        double resta;
        for (int i = 0; i < diferencia.getDimension(); i++) {
            resta = minuendo.getValor(i) - sustraendo.getValor(i);
            resta = Math.abs(resta % 2);
//            minuendo.set(i, resta);
            diferencia.set(i, resta);
        }
        return diferencia;
    }

    @Override
    protected Punto suma(Punto sumando, Punto sumando2) {
        Punto resultado = (Punto) sumando.clone();
        double suma;
        for (int i = 0; i < resultado.getDimension(); i++) {
            suma = sumando.getValor(i) + sumando2.getValor(i);
//            sumando.set(i, suma);
            suma = Math.abs(suma % 2);
            resultado.set(i, suma);
        }
        return resultado;
    }

    public void normalizar(Punto punto, Random rand) {
        for (int i = 0; i < punto.getDimension(); i++) {
            double valor = punto.getValor(i);
            valor = rand.nextDouble() <= sig(valor) ? 1 : 0;
            punto.set(i, valor);
        }
        punto.evaluar();
    }

    public double sig(double x) {
        double result = 1 + Math.exp(-2 * b * (x - 0.5) / (1 + 2 * alfa));
        result = 1 / result;
        return result;

    }
    
}
