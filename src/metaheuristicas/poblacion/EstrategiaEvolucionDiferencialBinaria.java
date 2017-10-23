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

import java.util.Random;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialBinaria extends EstrategiaEvolucionDiferencial {

    public EstrategiaEvolucionDiferencialBinaria(int tamPoblacion) {
        super(tamPoblacion);
        setNombreEstrategia("EstrategiaEvolucionDB");
        cr = 0.5;
        alfa = 1.4;
    }

    @Override
    protected Punto mutar(Poblacion poblacion, Random rand) {
        Punto mutado = super.mutar(poblacion, rand);
        //variacion para problema de la mochila
        normalizar(mutado, rand);
        return mutado;
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
        double result = 1 + Math.exp(-x);
        result = 1 / result;
        return result;
    }
//
//    @Override
//    protected Punto resta(Punto minuendo, Punto sustraendo) {
//        Punto result = super.resta(minuendo, sustraendo);
////        for (int i = 0; i < minuendo.getDimension(); i++) {
////            double valor = sustraendo.getValor(i);
////            valor = valor >= 1 ? 1 : 0;
////            minuendo.set(i, valor);
////        }
//        return result;
//
//    }
//
//    @Override
//    protected Punto suma(Punto sumando, Punto sumando2) {
//        Punto result = super.suma(sumando, sumando2); 
////        for (int i = 0; i < sumando.getDimension(); i++) {
////            double valor = sumando.getValor(i);
////            valor = valor >= 1 ? 1 : 0;
////            sumando.set(i, valor);
////        }
//        return result;
//    }
//    
    
}
