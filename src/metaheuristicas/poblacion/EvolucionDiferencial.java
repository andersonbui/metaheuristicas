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

import metaheuristicas.Aleatorio;
import metaheuristicas.Individuo;
import metaheuristicas.poblacion.seleccion.Ruleta;

/**
 *
 * @author debian
 */
public class EvolucionDiferencial extends AlgoritmoEvolutivo {

    protected double alfa; //mutation rate
    protected double cr; // tasa de cruce

    public EvolucionDiferencial(int tamPoblacion) {
        super(tamPoblacion, 2);
        nombre = "EstrategiaEvolucionDiferencial";
        cr = 0.6;
        alfa = 1.1;
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
            Individuo objetivo = poblacion.remove(k);
            // MUTACION
            Individuo mutado = mutar(poblacion);
            // CRUCE -> generacion del vector prueba
            Individuo individuoPrueba = cruce(objetivo, mutado, k);
            // SELECCION
            seleccion(objetivo, individuoPrueba, siguienteGeneracion);
            poblacion.add(objetivo);
        }
        return siguienteGeneracion;
    }

    protected void seleccion(Individuo objetivo, Individuo individuoPrueba, Poblacion siguienteGeneracion) {
        individuoPrueba.evaluar();
        Individuo selleccionado = (individuoPrueba.compareTo(objetivo) > 0 ? individuoPrueba : objetivo);
        siguienteGeneracion.add(selleccionado);
    }

    protected Individuo cruce(Individuo objetivo, Individuo mutado, int k) {
        Individuo individuoPrueba = objetivo.clone();
        double wi; // posicion i del punto mutado
        double vi; // posicion i del punto objetivo
        for (int i = 0; i < objetivo.getDimension(); i++) {
            wi = mutado.get(i);
            vi = objetivo.get(i);
            if (Aleatorio.nextDouble() <= cr || k == Aleatorio.nextInt(poblacion.size())) {
                individuoPrueba.set(i, wi);
            } else {
                individuoPrueba.set(i, vi);
            }
        }
        return individuoPrueba;
    }

    protected Individuo mutar(Poblacion poblacion) {
        Individuo x1 = Ruleta.seleccionar(poblacion);
        Individuo x2 = Ruleta.seleccionar(poblacion);
        Individuo x3 = Ruleta.seleccionar(poblacion);
        poblacion.add(x1);
        poblacion.add(x2);
        poblacion.add(x3);
        Individuo diferenciaX1X2 = resta(x1, x2);
        Individuo productoEscalar = multiplicacionPorEscalar(diferenciaX1X2, alfa);
        Individuo mutado = suma(x3, productoEscalar);
        return mutado;
    }

    protected Individuo resta(Individuo minuendo, Individuo sustraendo) {
        Individuo diferencia = minuendo.clone();
        double resta;
        for (int i = 0; i < diferencia.getDimension(); i++) {
            resta = minuendo.get(i) - sustraendo.get(i);
            diferencia.set(i, resta);
        }
        return diferencia;
    }

    protected Individuo suma(Individuo sumando, Individuo sumando2) {
        Individuo resultado = (Individuo) sumando.clone();
        double suma;
        for (int i = 0; i < resultado.getDimension(); i++) {
            suma = sumando.get(i) + sumando2.get(i);
            resultado.set(i, suma);
        }
        return resultado;
    }

    protected Individuo multiplicacionPorEscalar(Individuo punto, double escalar) {
        Individuo result = (Individuo) punto.clone();
        double producto;
        for (int i = 0; i < result.getDimension(); i++) {
            producto = punto.get(i) * escalar;
            result.set(i, producto);
        }
        return result;
    }
}
