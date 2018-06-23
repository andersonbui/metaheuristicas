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

import main.mochila.cuadratica.IndividuoCuadratico;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class VNSIT {

    FuncionSGVNS funcion;
    IndividuoCuadratico mochila;
    Random rand = new Random();
    int tamanio = 1000;

    public VNSIT() {
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

        funcion = new FuncionSGVNS(matrizBeneficios, capacidad, vectorPesos, 100.);

        double[] valores = new double[tamanio];
        mochila = new IndividuoCuadratico(funcion, valores);
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

//    /**
//     * Test of ejecutar method, of class VNS.
//     */
//    @Test
//    public void testEjecutar() {
//        System.out.println("ejecutar");
//        VNS instance = null;
//        List<Individuo> expResult = null;
//        List<Individuo> result = instance.ejecutar();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of seq_VND method, of class VNS.
//     */
//    @Test
//    public void testSeq_VND() {
//        System.out.println("seq_VND");
//        VNS instance = null;
//        FuncionSGVNS.IndividuoCuadratico expResult = null;
//        FuncionSGVNS.IndividuoCuadratico result = instance.seq_VND();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of elementosFuera method, of class VNS.
     */
    @Test
    public void testElementosFuera() {
        System.out.println("elementosFuera");
        VNS instance = new VNS(funcion, 1);
        List<Integer> expResult = new ArrayList();
        for (int i = 0; i < funcion.getDimension(); i++) {
            expResult.add(i);
        }
        for (int i = 0; i < 1000; i++) {
            Integer posicion = rand.nextInt(mochila.getDimension());
            int valor = (int) mochila.get(posicion);
            valor = 1 - valor;
            if (valor == 0) {
                boolean ret = expResult.add(posicion);
            } else {
                boolean ret = expResult.remove(posicion);
            }
            mochila.set(posicion, valor);

            List<Integer> result = instance.elementosFuera(mochila);
            assertTrue(expResult.containsAll(result));
            List list2 = funcion.filtrarPorFactibles(expResult, mochila);
            assertTrue(list2.containsAll(result) && result.containsAll(list2));
        }

    }

    /**
     * Test of elementosDentro method, of class VNS.
     */
    @Test
    public void testElementosDentro() {
        System.out.println("elementosDentro");
        VNS instance = new VNS(funcion, 1);
        List<Integer> expResult = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            Integer posicion = rand.nextInt(mochila.getDimension());
            int valor = (int) mochila.get(posicion);
            valor = 1 - valor;
            if (valor == 1) {
                boolean ret = expResult.add(posicion);
            } else {
                boolean ret = expResult.remove(posicion);
            }
            mochila.set(posicion, valor);

            List<Integer> result = instance.elementosDentro(mochila);
            assertTrue(expResult.containsAll(result) && result.containsAll(expResult));
        }
    }

    /**
     * Test of cambio method, of class VNS.
     */
    @Test
    public void testCambio() {
        System.out.println("cambio");
        VNS instance = new VNS(funcion, 1);
        for (int i = 0; i < 50; i++) {
            Integer posicion = rand.nextInt(mochila.getDimension());
            int valor = (int) mochila.get(posicion);
            valor = 1 - valor;
            mochila.set(posicion, valor);
        }
        List lista = mochila.elementosNoSeleccionados();
        mochila = instance.cambio(mochila);
        List listares = mochila.elementosNoSeleccionados();
        assertNotEquals(lista.size(), listares.size());
        assertTrue(lista.containsAll(listares) || listares.containsAll(lista));
        assertFalse(lista.containsAll(listares) && listares.containsAll(lista));
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of intercambio method, of class VNS.
     */
    @Test
    public void testIntercambio() {
        System.out.println("intercambio");
        VNS instance = new VNS(funcion, 1);
        for (int i = 0; i < 50; i++) {
            Integer posicion = rand.nextInt(mochila.getDimension());
            int valor = (int) mochila.get(posicion);
            valor = 1 - valor;
            mochila.set(posicion, valor);
        }
        List lista = mochila.elementosNoSeleccionados();
        mochila = instance.intercambio(mochila);
        List listares = mochila.elementosNoSeleccionados();
        assertEquals(lista.size(), listares.size());
        assertFalse(lista.containsAll(listares) || listares.containsAll(lista));
        assertFalse(lista.containsAll(listares) && listares.containsAll(lista));
    }

    /**
     * Test of solucionInicial method, of class VNS.
     */
    @Test
    public void testSolucionInicial() {
        System.out.println("solucionInicial");
        VNS instance = new VNS(funcion, 1);

        IndividuoCuadratico result = instance.solucionInicial();

        assertTrue(instance.elementosFuera(result).isEmpty());
    }

//    /**
//     * Test of buscarMayor method, of class VNS.
//     */
//    @Test
//    public void testBuscarMayor() {
//        System.out.println("buscarMayor");
//        FuncionSGVNS.IndividuoCuadratico individuo = null;
//        VNS instance = null;
//        int expResult = 0;
//        int result = instance.buscarMayor(individuo);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
