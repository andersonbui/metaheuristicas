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
package main.mochila.cuadratica.IHEA.hyperplane_exploration;

import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_ajustado.FuncionMochilaIHEA_A;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
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

    FuncionMochilaIHEA_A funcion;
    IteratedHyperplaneExplorationAlgoritm instance;

    public IteratedHyperplaneExplorationAlgoritmTest() {
        double[][] matrizbeneficio = new double[][]{
            {1, 2, 3, 4},
            {0, 1, 2, 4},
            {0, 0, 1, 2},
            {0, 0, 0, 1}
        };
        double capacidad = 6;
        double[] vectorPesos = new double[]{1, 2, 3, 4};
        funcion = new FuncionMochilaIHEA_A(matrizbeneficio, capacidad, vectorPesos, null);
        instance = new IteratedHyperplaneExplorationAlgoritm(funcion);
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
     * Test of ejecutar method, of class IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testEjecutar() {
        System.out.println("ejecutar");
//        IteratedHyperplaneExplorationAlgoritm instance = null;
//        List<IndividuoIHEA> expResult = null;
//        List<IndividuoIHEA> result = instance.ejecutar();
//        assertEquals(expResult, result);
    }

    /**
     * Test of iterateHiperplaneExploration method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testIterateHiperplaneExploration() {
        System.out.println("iterateHiperplaneExploration");
//        int L = 0;
//        int rcl = 0;
//        int maxIter = 0;
//        IteratedHyperplaneExplorationAlgoritm instance = null;
//        List<IndividuoIHEA> expResult = null;
//        List<IndividuoIHEA> result = instance.iterateHiperplaneExploration(L, rcl, maxIter);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of determinarVariablesFijas method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testDeterminarVariablesFijas() {
        System.out.println("determinarVariablesFijas");
        System.out.println("determinarVariablesFijas");
        int dimension = 2;
        int lb = 2;
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 0, 1});
        List<Integer> expResult = new ArrayList<>();
        expResult.add(0);
        expResult.add(1);
        List<Integer> result = instance.determinarVariablesFijas(dimension, individuo, lb);
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of primerosPorDensidad method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testNPrimerosOrdenadosPorDensidad() {
        System.out.println("nPrimerosOrdenadosPorDensidad");
        List<Integer> listaIndices = new ArrayList();
        listaIndices.add(2);
        listaIndices.add(3);
        IndividuoIHEA mochila = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 0});
        int n = 1;
        boolean minimo = false;
        List<Integer> expResult = new ArrayList();
        expResult.add(2);
        List<Integer> result = (new PrimerosPorDensidad()).primerosPorDensidad(listaIndices, mochila, n, minimo);
        assertEquals(expResult, result);

    }

    /**
     * Test of construirProblemaRestringidoReducido method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testConstruirProblemaRestringidoReducido() {
        System.out.println("construirProblemaRestringidoReducido");
        List<Integer> varFijas = new ArrayList<>();
        varFijas.add(1);
        IndividuoIHEA x_actual = new IndividuoIHEA(funcion, new double[]{1, 1, 0, 0});
        instance.construirProblemaRestringidoReducido(varFijas, x_actual);
        assertEquals(instance.getFuncion().getVariablesFijas().size(), 1);
    }

    /**
     * Test of tabuSearchEngine method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testTabuSearchEngine() {
        System.out.println("tabuSearchEngine");
        int L = 0;
        IndividuoIHEA x_referencia = new IndividuoIHEA(funcion, new double[]{0, 1, 1, 0});
        double calidad_original = x_referencia.getCalidad();
        IndividuoIHEA result = instance.tabuSearchEngine(L, x_referencia, x_referencia);
        assertEquals(true, result.getCalidad() >= calidad_original);
    }

    /**
     * Test of rawFuncion method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testRawFuncion() {
        System.out.println("rawFuncion");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 0, 1});
        double expResult = 13;
        double result = instance.rawFuncion(individuo);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of perturbacion method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testPerturbacion() {
        System.out.println("perturbacion");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});
        IndividuoIHEA result = instance.perturbacion(individuo, 3);
        assertEquals(true, result != null);
    }

    /**
     * Test of construirCQKP method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testConstruirCQKP() {
        System.out.println("construirCQKP");
//        int numVarFijas = 0;
//        IteratedHyperplaneExplorationAlgoritm.CQKP cqkp = null;
//        IndividuoIHEA x_individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
//        IteratedHyperplaneExplorationAlgoritm instance = null;
//        IteratedHyperplaneExplorationAlgoritm.CQKP expResult = null;
//        IteratedHyperplaneExplorationAlgoritm.CQKP result = instance.construirCQKP(numVarFijas, cqkp, x_individuo);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of GreedyRandomizedConstruction method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testGreedyRandomizedConstruction() {
        System.out.println("GreedyRandomizedConstruction");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 0, 0});
        int rcl = 2;
        double calidad_original = individuo.getCalidad();
//        IndividuoIHEA expResult = null;
        IndividuoIHEA result = instance.GreedyRandomizedConstruction(individuo, rcl);
        assertEquals(true, result.getCalidad() >= calidad_original);
    }

    /**
     * Test of elementosDentro method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testElementosDentro() {
        System.out.println("elementosDentro");
        System.out.println("elementosFuera");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
        List<Integer> expResult = new ArrayList();
        expResult.add(0);
        expResult.add(1);
        expResult.add(2);
        expResult.add(3);
        //ningun elemento fuera
        List<Integer> result = instance.elementosDentro(individuo);
        assertEquals(expResult, result);

        // un elemento fuera
        individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 1, 0});
        expResult.clear();
        expResult.add(0);
        expResult.add(2);
        result = instance.elementosDentro(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of elementosFuera method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testElementosFuera() {
        System.out.println("elementosFuera");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
        List<Integer> expResult = new ArrayList();
        //ningun elemento fuera
        List<Integer> result = instance.elementosFuera(individuo);
        assertEquals(expResult, result);

        // un elemento fuera
        individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 1, 0});
        expResult.add(1);
        expResult.add(3);
        result = instance.elementosFuera(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of descent method, of class IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testDescent() {
        System.out.println("descent");
//        IndividuoIHEA original = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
//        IteratedHyperplaneExplorationAlgoritm instance = null;
//        IndividuoIHEA expResult = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
//        IndividuoIHEA result = instance.descent(original);
//        assertEquals(expResult, result);
    }

    /**
     * Test of addElemento method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testAddElemento() {
        System.out.println("addElemento");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{0, 1, 1, 1}); // 
        IndividuoIHEA expResult = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
        IndividuoIHEA result = instance.addElemento(individuo);
        assertEquals(expResult, result);

        individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 1, 1});
        result = instance.addElemento(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of add_factible method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testAdd_factible() {
        System.out.println("add_factible");

        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 0, 0});
        int expResult = 3;
        IndividuoIHEA result = instance.add_factible(individuo);
        assertEquals(expResult, result.cantidadI1());

        individuo = new IndividuoIHEA(funcion, new double[]{0, 0, 0, 1});
        expResult = 2;
        result = instance.add_factible(individuo);
        assertEquals(expResult, result.cantidadI1());
        // primer elemento
        Integer resultadoInt = 3;
        assertEquals(resultadoInt, result.elementosSeleccionados().get(0));
        // ultimo elemento
        resultadoInt = 0;
        assertEquals(resultadoInt, result.elementosSeleccionados().get(1));

    }

    /**
     * Test of dimensionHiperplano method, of class
     * IteratedHyperplaneExplorationAlgoritm.
     */
    @Test
    public void testDimensionHiperplano() {
        System.out.println("dimensionHiperplano");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 1, 0, 0});
        int expResult = 2;
        int result = instance.dimensionHiperplano(individuo);
        assertEquals(expResult, result);
    }

}
