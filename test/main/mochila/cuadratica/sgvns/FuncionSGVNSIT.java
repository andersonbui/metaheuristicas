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

import java.util.Random;
import main.mochila.IndividuoMochila;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author debian
 */
public class FuncionSGVNSIT {

    FuncionSGVNS instance;
    int tamanio = 1000;

    Random rand = new Random();

    public FuncionSGVNSIT() {
        tamanio = 1000;
        double[][] matrizBeneficios = new double[tamanio][tamanio];
        for (int i = 0; i < matrizBeneficios.length; i++) {
            for (int k = 0; k < matrizBeneficios[0].length; k++) {
                matrizBeneficios[i][k] = 1;
            }
        }
        double capacidad = 100;
        double[] vectorPesos = new double[tamanio];
        for (int k = 0; k < vectorPesos.length; k++) {
            vectorPesos[k] = 2;
        }

        instance = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, 100.);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of generarIndividuo method, of class FuncionSGVNS.
     */
    @Test
    public void testGenerarIndividuo() {

        double[] valores = new double[tamanio];
        IndividuoMochila indEsperado = new IndividuoMochila(instance, valores);

        System.out.println("generarIndividuo");
        IndividuoMochila result = instance.generarIndividuo();
        assertArrayEquals(indEsperado.getValores(), result.getValores(), 0);
        int posicion;
        int posicion2;
        int mitad = valores.length / 2;
        double resultado;
        long tiempo_inicial = System.currentTimeMillis();
        for (int k = 0; k < 100000; k++) {
            posicion = rand.nextInt(mitad);
            posicion2 = mitad + rand.nextInt(mitad);
            // insertar un elemento
            result.set(posicion, 1);
            // probar el beneficio
            resultado = result.evaluar();
            assertEquals(1, resultado, 0);
            // probar el peso
            assertEquals(2, result.pesar(), 0);
//            assertEquals(2, instance.calcularPeso(result), 0);
            // insertar un nuevo elemento
            result.set(posicion2, 1);
            // probar nuevamente el beneficio
            resultado = result.evaluar();
            assertEquals(3, resultado, 0);
            // probar nuevamente el peso
            assertEquals(4, result.pesar(), 0);
//            assertEquals(4, instance.calcularPeso(result), 0);
            // sacar los elementos insertados
            result.set(posicion, 0);
            result.set(posicion2, 0);
        }
        long tiempo_final = System.currentTimeMillis();
        System.out.println("tiempo: " + (tiempo_final - tiempo_inicial));
        assertArrayEquals(indEsperado.getValores(), result.getValores(), 0);

    }

}
