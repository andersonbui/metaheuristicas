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
public class FuncionMochilaHyperplaneExplorationTest {
    
    public FuncionMochilaHyperplaneExplorationTest() {
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
     * Test of contribucion method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testContribucion() {
        System.out.println("contribucion");
        int indice = 0;
        Individuo mochila = null;
        FuncionMochilaHyperplaneExploration instance = null;
        double expResult = 0.0;
        double result = instance.contribucion(indice, mochila);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of densidad method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testDensidad() {
        System.out.println("densidad");
        int indice = 0;
        Individuo mochila = null;
        FuncionMochilaHyperplaneExploration instance = null;
        double expResult = 0.0;
        double result = instance.densidad(indice, mochila);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        FuncionMochilaHyperplaneExploration instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of optener_lb_ub method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testOptener_lb_ub() {
        System.out.println("optener_lb_ub");
        FuncionMochilaHyperplaneExploration instance = null;
        instance.optener_lb_ub();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_lb method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testObtener_lb() {
        System.out.println("obtener_lb");
        FuncionMochilaHyperplaneExploration instance = null;
        int expResult = 0;
        int result = instance.obtener_lb();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_ub method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testObtener_ub() {
        System.out.println("obtener_ub");
        FuncionMochilaHyperplaneExploration instance = null;
        int expResult = 0;
        int result = instance.obtener_ub();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of violacionDeCapacidad method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testViolacionDeCapacidad() {
        System.out.println("violacionDeCapacidad");
        Individuo individuo = null;
        FuncionMochilaHyperplaneExploration instance = null;
        double expResult = 0.0;
        double result = instance.violacionDeCapacidad(individuo);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of filtrarPorFactibles method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testFiltrarPorFactibles() {
        System.out.println("filtrarPorFactibles");
        List<Integer> listaIndices = null;
        Individuo individuo = null;
        FuncionMochilaHyperplaneExploration instance = null;
        List<Integer> expResult = null;
        List<Integer> result = instance.filtrarPorFactibles(listaIndices, individuo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sacarEspacios method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testSacarEspacios() {
        System.out.println("sacarEspacios");
        Individuo mochila = null;
        FuncionMochilaHyperplaneExploration instance = null;
        double expResult = 0.0;
        double result = instance.sacarEspacios(mochila);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of swapFactible method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testSwapFactible() {
        System.out.println("swapFactible");
        int pos_add = 0;
        int pos_sacar = 0;
        Individuo individuo = null;
        FuncionMochilaHyperplaneExploration instance = null;
        boolean expResult = false;
        boolean result = instance.swapFactible(pos_add, pos_sacar, individuo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limitarInferiormente method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testLimitarInferiormente() {
        System.out.println("limitarInferiormente");
        Individuo mochila = null;
        List<Integer> listaOrdenada = null;
        FuncionMochilaHyperplaneExploration instance = null;
        Individuo expResult = null;
        Individuo result = instance.limitarInferiormente(mochila, listaOrdenada);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beneficio method, of class FuncionMochilaHyperplaneExploration.
     */
    @Test
    public void testBeneficio() {
        System.out.println("beneficio");
        int indice = 0;
        FuncionMochilaHyperplaneExploration instance = null;
        double expResult = 0.0;
        double result = instance.beneficio(indice);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
