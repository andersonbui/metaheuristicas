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
package main.mochila.cuadratica.hyperplane_exploration;

import java.util.ArrayList;
import java.util.List;
import main.mochila.IndividuoMochila;
import metaheuristicas.Individuo;
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
public class FuncionMochilaHyperplaneExplorationTest {

    FuncionMochilaHyperplaneExploration instanceFuncion;

    public FuncionMochilaHyperplaneExplorationTest() {
        double[][] matrizbeneficio = new double[][]{
            {1, 2, 3, 4},
            {0, 1, 2, 4},
            {0, 0, 1, 2},
            {0, 0, 0, 1}
        };
        double capacidad = 5;
        double[] vectorPesos = new double[]{1, 2, 3, 4};
        instanceFuncion = new FuncionMochilaHyperplaneExploration(matrizbeneficio, capacidad, vectorPesos, null);
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
     * Test of contribucion method, of class
     * FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testContribucion() {
        System.out.println("contribucion");
        int indice = 1;
        IndividuoMochila mochila = new IndividuoMochila(instanceFuncion, new double[]{1, 0, 0, 1});

        double expResult = 7;
        double result = instanceFuncion.contribucion(indice, mochila);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of densidad method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testDensidad() {
        System.out.println("densidad");
        int indice = 1;
        IndividuoMochila mochila = new IndividuoMochila(instanceFuncion, new double[]{1, 0, 0, 1});
        double expResult = 7 / 2.0;
        double result = instanceFuncion.densidad(indice, mochila);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of optener_lb_ub method, of class
     * FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testOptener_lb_ub() {
        System.out.println("optener_lb_ub");
        int[] expResult = new int[]{1, 2};
        int[] result = instanceFuncion.optener_lb_ub();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of obtener_lb method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testObtener_lb() {
        System.out.println("obtener_lb");
        int expResult = 1;
        int result = instanceFuncion.obtener_lb();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtener_ub method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testObtener_ub() {
        System.out.println("obtener_ub");
        int expResult = 2;
        int result = instanceFuncion.obtener_ub();
        assertEquals(expResult, result);
    }

    /**
     * Test of violacionDeCapacidad method, of class
     * FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testViolacionDeCapacidad() {
        System.out.println("violacionDeCapacidad");
        IndividuoMochila individuo = new IndividuoMochila(instanceFuncion, new double[]{1, 0, 0, 1});
        double expResult = 0.0;
        double result = instanceFuncion.violacionDeCapacidad(individuo);
        assertEquals(expResult, result, 0.0);

        individuo.set(1, 1);
        expResult = -2;
        result = instanceFuncion.violacionDeCapacidad(individuo);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of filtrarPorFactibles method, of class
     * FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testFiltrarPorFactibles() {
        System.out.println("filtrarPorFactibles");
        List<Integer> listaIndices = new ArrayList();
        listaIndices.add(0);
        listaIndices.add(2);
        listaIndices.add(3);
        IndividuoMochila individuo = new IndividuoMochila(instanceFuncion, new double[]{0, 1, 0, 0});
        List<Integer> expResult = new ArrayList();
        expResult.add(0);
        expResult.add(2);
        List<Integer> result = instanceFuncion.filtrarPorFactibles(listaIndices, individuo);
        assertEquals(expResult, result);
        assertNotSame(expResult, result);
    }

    /**
     * Test of swapFactible method, of class
     * FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testSwapFactible() {
        System.out.println("swapFactible");
        IndividuoMochila individuo = new IndividuoMochila(instanceFuncion, new double[]{0, 1, 1, 0});

        boolean result = instanceFuncion.swapFactible(0, 2, individuo);
        assertTrue(result);

        result = instanceFuncion.swapFactible(3, 2, individuo);
        assertFalse(result);

        result = instanceFuncion.swapFactible(3, 1, individuo);
        assertFalse(result);

        result = instanceFuncion.swapFactible(0, 1, individuo);
        assertTrue(result);
        try {
            instanceFuncion.swapFactible(0, 0, individuo);
            fail("fallo por no exception");
        } catch (java.security.InvalidParameterException e) {

        }

    }
    
    /**
     * Test of beneficio method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testBeneficio() {
        System.out.println("beneficio");
//        int indice = 0;
//        double expResult = 1;
//        double result = instanceFuncion.beneficio(indice);
//        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of toString method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
//        String expResult = "";
//        String result = instanceFuncion.toString();
//        assertEquals(expResult, result);
    }
}
