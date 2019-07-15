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
import java.util.ArrayList;
import java.util.List;
import main.mochila.IndividuoMochila;
import main.mochila.cuadratica.IHEA.hyperplane_exploration_ajustado.FuncionMochilaIHEA_A;
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
public class FuncionMochilaIHEATest {
    
    FuncionMochilaIHEA_A funcion;

    public FuncionMochilaIHEATest() {
        double[][] matrizbeneficio = new double[][]{
            {1, 2, 3, 4},
            {0, 1, 2, 4},
            {0, 0, 1, 2},
            {0, 0, 0, 1}
        };
        double capacidad = 5;
        double[] vectorPesos = new double[]{1, 2, 3, 4};
        funcion = new FuncionMochilaIHEA_A(matrizbeneficio, capacidad, vectorPesos, null);
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
     * Test of toString method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
//        
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerLowerBound method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testObtenerLowerBound() {
        System.out.println("obtenerLowerBound");
        int expResult = 1;
        int result = funcion.obtenerLowerBound();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerUpperBound method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testObtenerUpperBound() {
        System.out.println("obtenerUpperBound");
        int expResult = 2;
        int result = funcion.obtenerUpperBound();
        assertEquals(expResult, result);
    }

    /**
     * Test of violacionDeCapacidad method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testViolacionDeCapacidad() {
         System.out.println("violacionDeCapacidad");
        IndividuoIHEA individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});
        double expResult = 0;
        double result = funcion.violacionDeCapacidad(individuo);
        assertEquals(expResult, result, 0.0);

        individuo.set(1, 1);
        expResult = -2;
        result = funcion.violacionDeCapacidad(individuo);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of sacarEspacios method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testSacarEspacios() {
        System.out.println("sacarEspacios");
        IndividuoIHEA mochila = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});
        double expResult = 0.0;
        double result = funcion.sacarEspacios(mochila);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of obtener_I1 method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testObtener_I1() {
        System.out.println("obtener_I1");
        IndividuoMochila individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});;
        
       List<Integer> expResult = new ArrayList();
        expResult.add(0);
        expResult.add(3);
        List<Integer> result = funcion.obtener_I1(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtener_I0 method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testObtener_I0() {
        System.out.println("obtener_I0");
        IndividuoMochila individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});;
        
        List<Integer> expResult = new ArrayList();
        expResult.add(1);
        expResult.add(2);
        List<Integer> result = funcion.obtener_I0(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerPeso method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testObtenerPeso() {
        System.out.println("obtenerPeso");
        IndividuoIHEA mochila = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});
        double expResult = 5.0;
        double result = funcion.obtenerPeso(mochila);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of generarIndividuo method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testGenerarIndividuo() {
        System.out.println("generarIndividuo");
        IndividuoIHEA expResult = new IndividuoIHEA(funcion);
        IndividuoIHEA result = funcion.generarIndividuo();
        assertEquals(expResult, result);
    }

    /**
     * Test of fijarVariables method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testFijarVariables() {
        System.out.println("fijarVariables");
        IndividuoMochila individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});
        
        List<Integer> varFijas = new ArrayList();
        varFijas.add(0);
        varFijas.add(3);
        
        funcion.fijarVariables(individuo, varFijas);
        List listaI1 =  funcion.obtener_I1(individuo);
        
        assertTrue(listaI1.isEmpty());
    }

    /**
     * Test of reiniciarVijarVariables method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testReiniciarVijarVariables() {
        System.out.println("reiniciarVijarVariables");
        IndividuoMochila individuo = new IndividuoIHEA(funcion, new double[]{1, 0, 0, 1});
        List<Integer> varFijas = new ArrayList();
        varFijas.add(0);
        varFijas.add(3);
        List<Integer> listI1 = new ArrayList();
        listI1.add(0);
        listI1.add(3);
        
        funcion.fijarVariables(individuo, varFijas);
        List<Integer> result2 = funcion.obtener_I1(individuo);
        assertTrue(result2.isEmpty());
        
        funcion.reiniciarVijarVariables();
        List<Integer> listVarFijas = funcion.getVariablesFijas();
        assertTrue(listVarFijas.isEmpty());
        
        result2 = funcion.obtener_I1(individuo);
        assertEquals(listI1, result2);
    }

   

    /**
     * Test of getVariablesFijas method, of class FuncionMochilaIHEA_A.
     */
    @Test
    public void testGetVariablesFijas() {
        System.out.println("getVariablesFijas");
        List<Integer> expResult = new ArrayList();
        List<Integer> result = funcion.getVariablesFijas();
        assertEquals(expResult, result);
        
    }
    
}
