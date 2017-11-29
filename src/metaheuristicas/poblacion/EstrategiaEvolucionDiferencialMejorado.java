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
import metaheuristicas.poblacion.seleccion.Ruleta;

/**
 *
 * @author debian
 */
public class EstrategiaEvolucionDiferencialMejorado extends Estrategia {

    protected double alfa; //mutation rate
    protected double cr; // tasa de cruce

    public EstrategiaEvolucionDiferencialMejorado(int tamPoblacion) {
        super(tamPoblacion, 2);
        setNombreEstrategia("EvolucionDiferencialMM");
        cr = 0.2;
        alfa = 0.8;
    }

    /**
     * genera hijos a partir de un padre y una madre y compiten en calidad entre
     * todos.
     *
     * @param numIndividuosElitismo
     * @param poblacion
     * @param rand
     * @return
     */
    @Override
    public Poblacion siguienteGeneracion(int numIndividuosElitismo, Poblacion poblacion, Random rand) {
        Poblacion siguienteGeneracion = poblacion.clone();
        siguienteGeneracion.aumentarGeneracion();
        siguienteGeneracion.clear();
        for (int k = 0; k < poblacion.size(); k++) {
            Punto objetivo = poblacion.remove(k);
            // MUTACION
            Punto mutado = mutar(poblacion, rand);
            // CRUCE -> generacion del vector prueba
            Punto individuoPrueba = cruce(objetivo, mutado, poblacion, k, rand);
            // SELECCION
            seleccion(objetivo, individuoPrueba, siguienteGeneracion);
            poblacion.add(objetivo);
        }
        return siguienteGeneracion;
    }

    protected void seleccion(Punto objetivo, Punto individuoPrueba, Poblacion siguienteGeneracion) {
//        individuoPrueba.evaluar();
//        objetivo.evaluar();
        Punto selleccionado = (individuoPrueba.compareTo(objetivo) > 0 ? individuoPrueba : objetivo);
        siguienteGeneracion.add(selleccionado);
    }

    protected Punto cruce(Punto objetivo, Punto mutado, Poblacion poblacion, int k, Random rand) {
        Punto individuoPrueba = objetivo.clone();
        double wi; // posicion i del punto mutado
        double vi; // posicion i del punto objetivo
        for (int i = 0; i < objetivo.getDimension(); i++) {
            wi = mutado.getValor(i);
            vi = objetivo.getValor(i);
            if (rand.nextDouble() <= cr || k == rand.nextInt(poblacion.size())) {
                individuoPrueba.set(i, wi);
            } else {
                individuoPrueba.set(i, vi);
            }
        }
        individuoPrueba.evaluar();
        return individuoPrueba;
    }

    protected Punto mutar(Poblacion poblacion, Random rand) {

        Punto x1 = Ruleta.seleccionar(poblacion, rand);
        Punto x2 = Ruleta.seleccionar(poblacion, rand);
        Punto x3 = Ruleta.seleccionar(poblacion, rand);
        poblacion.add(x1);
        poblacion.add(x2);
        poblacion.add(x3);
        
//        if (x1.compareTo(x2) < 0) {
//            Punto aux = x1;
//            x1 = x2;
//            x2 = aux;
//        }
        Punto diferenciaX1X2 = resta(x1, x2);
        Punto productoEscalar = multiplicacionPorEscalar(diferenciaX1X2, alfa);
        Punto mutado = suma(x3, productoEscalar);
//        mutado.evaluar();
        return mutado;
    }

    protected Punto resta(Punto minuendo, Punto sustraendo) {
        Punto diferencia = minuendo.clone();
        double resta;
        for (int i = 0; i < diferencia.getDimension(); i++) {
            resta = minuendo.getValor(i) - sustraendo.getValor(i);
//            minuendo.set(i, resta);
            diferencia.set(i, resta);
        }
        return diferencia;
    }

    protected Punto suma(Punto sumando, Punto sumando2) {
        Punto resultado = (Punto) sumando.clone();
        double suma;
        for (int i = 0; i < resultado.getDimension(); i++) {
            suma = sumando.getValor(i) + sumando2.getValor(i);
//            sumando.set(i, suma);
            resultado.set(i, suma);
        }
        return resultado;
    }

    protected Punto multiplicacionPorEscalar(Punto punto, double escalar) {
        Punto result = (Punto) punto.clone();
        double producto;
        for (int i = 0; i < result.getDimension(); i++) {
            producto = punto.getValor(i) * escalar;
            result.set(i, producto);
        }
        return result;
    }
}
