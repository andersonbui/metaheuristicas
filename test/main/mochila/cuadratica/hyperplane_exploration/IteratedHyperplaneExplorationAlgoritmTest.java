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
public class IteratedHyperplaneExplorationAlgoritmTest {

    FuncionMochilaHyperplaneExploration funcion;
    IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo;

    public IteratedHyperplaneExplorationAlgoritmTest() {
        double[][] matrizbeneficio = new double[][]{
            {1, 2, 3, 4},
            {0, 1, 2, 4},
            {0, 0, 1, 2},
            {0, 0, 0, 1}
        };
        double capacidad = 5;
        double[] vectorPesos = new double[]{1, 2, 3, 4};
        funcion = new FuncionMochilaHyperplaneExploration(matrizbeneficio, capacidad, vectorPesos, null);
        instanceAlgoritmo = new IteratedHyperplaneExplorationAlgoritm(funcion);
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
//     * Test of ejecutar method, of class IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testEjecutar() {
//        System.out.println("ejecutar");
//        List<Individuo> expResult = null;
//        List<Individuo> result = instanceAlgoritmo.ejecutar(funcion);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of iterateHiperplaneExploration method, of class
//     * IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testIterateHiperplaneExploration() {
//        System.out.println("iterateHiperplaneExploration");
//        int L = 0;
//        int rcl = 0;
//        int maxIter = 0;
//        List<Individuo> expResult = null;
//        List<Individuo> result = instanceAlgoritmo.iterateHiperplaneExploration(L, rcl, maxIter);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of determinarVariablesFijas method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testDeterminarVariablesFijas() {
        System.out.println("determinarVariablesFijas");
        int dimension = 2;
        int lb = 2;
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 1});
        int[] expResult = {0, 1};
        int[] result = instanceAlgoritmo.determinarVariablesFijas(dimension, individuo, lb);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of listaIndicesOrdenadosPorDensidad method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testListaIndicesOrdenadosPorDensidad() {
        System.out.println("listaIndicesOrdenadosPorDensidad");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 1});
        boolean ascendente = false;
        List<Integer> expResult = new ArrayList();
        expResult.add(0);
        expResult.add(1);
        expResult.add(3);
        expResult.add(2);

        List<Integer> result = instanceAlgoritmo.listaIndicesOrdenadosPorDensidad(individuo, ascendente);
        assertEquals(expResult, result);
    }

    /**
     * Test of construirProblemaRestringidoReducido method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testConstruirProblemaRestringidoReducido() {
//        System.out.println("construirProblemaRestringidoReducido");
//        int[] varFijas = null;
//        IteratedHyperplaneExplorationAlgoritm.CQKP cqkp_k = null;
//        Individuo x_actual = null;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        instanceAlgoritmo.construirProblemaRestringidoReducido(varFijas, cqkp_k, x_actual);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    /**
//     * Test of tabuSearchEngine method, of class
//     * IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testTabuSearchEngine() {
//        System.out.println("tabuSearchEngine");
//        int L = 0;
//        Individuo x_inicial = null;
//        Individuo x_referencia = null;
//        int iter = 0;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        Individuo expResult = null;
//        Individuo result = instanceAlgoritmo.tabuSearchEngine(L, x_inicial, x_referencia, iter);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of rawFuncion method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testRawFuncion() {
        System.out.println("rawFuncion");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 1});
        double expResult = 13;
        double result = instanceAlgoritmo.rawFuncion(individuo);
        assertEquals(expResult, result, 0.0);
    }

//    /**
//     * Test of perturbacion method, of class
//     * IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testPerturbacion() {
//        System.out.println("perturbacion");
//        Individuo individuo = null;
//        int iteraciones = 0;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        Individuo expResult = null;
//        Individuo result = instanceAlgoritmo.perturbacion(individuo, iteraciones);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of construirCQKP method, of class
//     * IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testConstruirCQKP() {
//        System.out.println("construirCQKP");
//        int numVarFijas = 0;
//        IteratedHyperplaneExplorationAlgoritm.CQKP cqkp = null;
//        Individuo x_individuo = null;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        IteratedHyperplaneExplorationAlgoritm.CQKP expResult = null;
//        IteratedHyperplaneExplorationAlgoritm.CQKP result = instanceAlgoritmo.construirCQKP(numVarFijas, cqkp, x_individuo);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of GreedyRandomizedConstruction method, of class
//     * IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testGreedyRandomizedConstruction_int() {
//        System.out.println("GreedyRandomizedConstruction");
//        int rcl = 0;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        Individuo expResult = null;
//        Individuo result = instanceAlgoritmo.GreedyRandomizedConstruction(rcl);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of GreedyRandomizedConstruction method, of class
//     * IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testGreedyRandomizedConstruction_Individuo_int() {
//        System.out.println("GreedyRandomizedConstruction");
//        Individuo individuo = null;
//        int rcl = 0;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        Individuo expResult = null;
//        Individuo result = instanceAlgoritmo.GreedyRandomizedConstruction(individuo, rcl);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of obtener_I1_I0 method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testObtener_I1_I0() {
        System.out.println("obtener_I1_I0");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 1});
        List<Integer> I0 = new ArrayList();
        I0.add(2);
        List I1 = new ArrayList();
        I1.add(0);
        I1.add(1);
        I1.add(3);

        List resultI0 = instanceAlgoritmo.obtener_I0(individuo);
        List resultI1 = instanceAlgoritmo.obtener_I1(individuo);
        assertEquals(I0, resultI0);
        assertEquals(I1, resultI1);
    }

    /**
     * Test of obtener_I1 method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testObtener_I1() {
        System.out.println("obtener_I1");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 1});
        List<Integer> expResult = new ArrayList();
        expResult.add(0);
        expResult.add(1);
        expResult.add(3);
        List<Integer> result = instanceAlgoritmo.obtener_I1(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtener_I0 method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testObtener_I0() {
        System.out.println("obtener_I0");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 1});
        List<Integer> expResult = new ArrayList();
        expResult.add(2);
        List<Integer> result = instanceAlgoritmo.obtener_I0(individuo);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of descent method, of class IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testDescent() {
//        System.out.println("descent");
//        Individuo original = null;
//        IteratedHyperplaneExplorationAlgoritm instanceAlgoritmo = null;
//        Individuo expResult = null;
//        Individuo result = instanceAlgoritmo.descent(original);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of add method, of class IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 0}); // 
        int expResult = -1;
        int result = instanceAlgoritmo.add(individuo);
        assertEquals(expResult, result);

        individuo = new Individuo(funcion, new double[]{0, 0, 0, 1});
        expResult = 0;
        result = instanceAlgoritmo.add(individuo);
        assertEquals(expResult, result);

    }

//    /**
//     * Test of swap method, of class IteratedHyperplaneExplorationAlgoritm.
//     */
//    @Test
//    public void testSwap() {
//        System.out.println("swap");
//        Individuo individuo = new Individuo(funcion, new double[]{0, 0, 1, 1});
//        int[] expResult = new int[]{2, 0};
//        int[] result = instanceAlgoritmo.swap(individuo);
//        assertArrayEquals(expResult, result);
//    }
    /**
     * Test of dimensionHiperplano method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testDimensionHiperplano() {
        System.out.println("dimensionHiperplano");
        Individuo individuo = new Individuo(funcion, new double[]{1, 1, 0, 0});
        int expResult = 2;
        int result = instanceAlgoritmo.dimensionHiperplano(individuo);
        assertEquals(expResult, result);
    }

}
