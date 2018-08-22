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
package main.mochila.cuadratica;

import java.util.List;
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
public class FuncionMochilaCuadraticaTest {
    
    public FuncionMochilaCuadraticaTest() {
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
     * Test of evaluar method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testEvaluar() {
        System.out.println("evaluar");
        IndividuoCuadratico p = null;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.evaluar(p);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beneficio method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testBeneficio_GenericType() {
        System.out.println("beneficio");
        IndividuoCuadratico mochila = null;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.beneficio(mochila);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beneficio method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testBeneficio_int() {
        System.out.println("beneficio");
        int indice = 0;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.beneficio(indice);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of relaciones method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testRelaciones() {
        System.out.println("relaciones");
        int indice = 0;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.relaciones(indice);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contribucion method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testContribucion_int_GenericType() {
        System.out.println("contribucion");
        int indice = 0;
        IndividuoCuadratico mochila = null;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.contribucion(indice, mochila);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
//         System.out.println("contribucion");
//        int indice = 1;
//        IndividuoIHEA mochila = new IndividuoIHEA(instanceFuncion, new double[]{1, 0, 0, 1});
//
//        double expResult = 7;
//        double result = instanceFuncion.contribucion(indice, mochila);
//        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of contribucion method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testContribucion_3args() {
        System.out.println("contribucion");
        int indice = 0;
        IndividuoCuadratico mochila = null;
        Integer sin_k = null;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.contribucion(indice, mochila, sin_k);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        IndividuoCuadratico individuo = null;
        FuncionMochilaCuadratica instance = null;
        String expResult = "";
        String result = instance.toString(individuo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of densidad method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testDensidad() {
        System.out.println("densidad");
        int indice = 0;
        IndividuoCuadratico mochila = null;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.densidad(indice, mochila);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
//         System.out.println("densidad");
//        int indice = 1;
//        IndividuoIHEA mochila = new IndividuoIHEA(instanceFuncion, new double[]{1, 0, 0, 1});
//        double expResult = 7 / 2.0;
//        double result = instanceFuncion.densidad(indice, mochila);
//        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of cabe method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testCabe() {
        System.out.println("cabe");
        IndividuoCuadratico mochila = null;
        int indice = 0;
        FuncionMochilaCuadratica instance = null;
        boolean expResult = false;
        boolean result = instance.cabe(mochila, indice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of filtrarPorFactibles method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testFiltrarPorFactibles() {
        System.out.println("filtrarPorFactibles");
        List<Integer> listaIndices = null;
        IndividuoCuadratico mochila = null;
        FuncionMochilaCuadratica instance = null;
        List<Integer> expResult = null;
        List<Integer> result = instance.filtrarPorFactibles(listaIndices, mochila);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
//        System.out.println("filtrarPorFactibles");
//        List<Integer> listaIndices = new ArrayList();
//        listaIndices.add(0);
//        listaIndices.add(2);
//        listaIndices.add(3);
//        IndividuoIHEA individuo = new IndividuoIHEA(instanceFuncion, new double[]{0, 1, 0, 0});
//        List<Integer> expResult = new ArrayList();
//        expResult.add(0);
//        expResult.add(2);
//        List<Integer> result = instanceFuncion.filtrarPorFactibles(listaIndices, individuo);
//        assertEquals(expResult, result);
//        assertNotSame(expResult, result);
    }

    /**
     * Test of getCapacidad method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testGetCapacidad() {
        System.out.println("getCapacidad");
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.getCapacidad();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beneficio method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testBeneficio_int_int() {
        System.out.println("beneficio");
        int i = 0;
        int j = 0;
        FuncionMochilaCuadratica instance = null;
        double expResult = 0.0;
        double result = instance.beneficio(i, j);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generarIndividuo method, of class FuncionMochilaCuadratica.
     */
    @Test
    public void testGenerarIndividuo() {
        System.out.println("generarIndividuo");
        FuncionMochilaCuadratica instance = null;
        IndividuoCuadratico expResult = null;
        IndividuoCuadratico result = instance.generarIndividuo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class FuncionMochilaCuadraticaImpl extends FuncionMochilaCuadratica {

        public FuncionMochilaCuadraticaImpl() {
            super(null, 0.0, null, null);
        }
    }
    
}
