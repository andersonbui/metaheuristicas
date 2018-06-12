/*
 * Copyright (C) 2018 JUAN
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
package main.mochila.cuadratica.graspBasadoMemoria;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.graspBasadoMemoria.GraspReinicio.ItemCalidad;
import metaheuristicas.Aleatorio;
import metaheuristicas.funcion.Funcion;
import metaheuristicas.Individuo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JUAN
 */
public class GraspTest {

    FuncionGraspTabuR funcion;
    GraspReinicio instanceGrasp;
    List<int[]> lista_Q;
    List<GraspReinicio.ItemCalidad> LRC;

    public GraspTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        LRC = new ArrayList();
        LRC.add(new GraspReinicio.ItemCalidad(2, 10.2));
        LRC.add(new GraspReinicio.ItemCalidad(3, 8.2));
        LRC.add(new GraspReinicio.ItemCalidad(1, 6.2));

        lista_Q = new ArrayList();
        lista_Q.add(new int[]{0, 0, 0, 0});//k=0
        lista_Q.add(new int[]{0, 0, 0, 0});//k=1-1
        lista_Q.add(new int[]{1, 1, 1, 1});//k=2-1
        lista_Q.add(new int[]{2, 2, 1, 2});//k=3-1
        lista_Q.add(new int[]{3, 2, 1, 3});//k=4-1
        //                   [0, 0.33, 0.66, 0]
        lista_Q.add(new int[]{4, 2, 1, 4});//k=5-1
        //                   [0, 0, 0, 0]
        double[][] matrizbeneficio = new double[][]{
            {1, 2, 3, 4},
            {0, 1, 2, 4},
            {0, 0, 1, 2},
            {0, 0, 0, 1}
        };
        double capacidad = 5;
        double[] vectorPesos = new double[]{1, 2, 3, 4};
        funcion = new FuncionGraspTabuR(matrizbeneficio, capacidad, vectorPesos, null);
        instanceGrasp = new GraspReinicio(funcion, 2, 2, 2);
    }

    @After
    public void tearDown() {
    }
//
//    /**
//     * Test of ejecutar method, of class Grasp.
//     */
//    @Test
//    public void testEjecutar() {
//        System.out.println("ejecutar");
//        Funcion funcion = null;
//        Grasp instance = null;
//        List<Individuo> expResult = null;
//        List<Individuo> result = instance.ejecutar(funcion);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//

    /**
     * Test of definicionSolParcialS method, of class Grasp.
     */
    @Test
    public void testDefinicionSolParcialS() {
        System.out.println("definicionSolParcialS");
        int m = 1;
        int sigma = 2;
        Individuo expResult = new Individuo(funcion, new double[]{1, 1, 0, 1});
        Individuo result = instanceGrasp.definicionSolParcialS(funcion, m, sigma, lista_Q);
        assertEquals(expResult, result);
    }
//
//    /**
//     * Test of faseConstruccion method, of class Grasp.
//     */
//    @Test
//    public void testFaseConstruccion() {
//        System.out.println("faseConstruccion");
//        int minLen = 0;
//        int maxLen = 0;
//        Individuo solParcialS = null;
//        Grasp instance = null;
//        Individuo expResult = null;
//        Individuo result = instance.faseConstruccion(minLen, maxLen, solParcialS);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of faseBusquedaLocal method, of class Grasp.
//     */
//    @Test
//    public void testFaseBusquedaLocal() {
//        System.out.println("faseBusquedaLocal");
//        Individuo s = new Individuo(funcion, new double[]{1, 0, 1, 0});
//        Individuo expResult = null;
//        Individuo result = instanceGrasp.faseBusquedaLocal(s);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of obtenerLRC method, of class Grasp.
     */
    @Test
    public void testObtenerLRC() {
        System.out.println("obtenerLRC");
        List<GraspReinicio.ItemCalidad> listItemNoSeleccionados = new ArrayList();
        listItemNoSeleccionados.add(new GraspReinicio.ItemCalidad(2, 10.2));
        listItemNoSeleccionados.add(new GraspReinicio.ItemCalidad(3, 8.2));
        listItemNoSeleccionados.add(new GraspReinicio.ItemCalidad(5, 3.2));
        listItemNoSeleccionados.add(new GraspReinicio.ItemCalidad(6, 1.2));
        int len = 2;
        List<GraspReinicio.ItemCalidad> expResult = new ArrayList();
        expResult.add(listItemNoSeleccionados.get(0));
        expResult.add(listItemNoSeleccionados.get(1));
        List<GraspReinicio.ItemCalidad> result = instanceGrasp.obtenerLRC(listItemNoSeleccionados, len);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerItemNoSelecionado method, of class Grasp.
     */
    @Test
    public void testObtenerItemNoSelecionado() {
        System.out.println("obtenerItemNoSelecionado");
        Individuo individuo = new Individuo(funcion, new double[]{0, 1, 0, 0});
        List<Integer> expResult = new ArrayList();
        expResult.add(0);
        expResult.add(2);
        List<Integer> result = instanceGrasp.obtenerItemsNoSelecionados(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerItemSelecionado method, of class Grasp.
     */
    @Test
    public void testObtenerItemSelecionado() {
        System.out.println("obtenerItemSelecionado");
        Individuo individuo = new Individuo(funcion, new double[]{0, 1, 0, 1});
        List<Integer> expResult = new ArrayList();
        expResult.add(1);
        expResult.add(3);
        List<Integer> result = instanceGrasp.obtenerItemsSelecionados(individuo);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculoQk method, of class Grasp.
     */
    @Test
    public void testCalculoQk() {
        System.out.println("calculoQk");
        instanceGrasp.calculoQk();
    }

    /**
     *
     * Test of calcularQkl method, of class Grasp.
     */
    @Test
    public void testCalcularQkl() {
        System.out.println("calcularQkl");
        List<GraspReinicio.ItemCalidad> listaLRC = LRC;
        int expResult = 6;
        instanceGrasp.Q = lista_Q;
        instanceGrasp.k = 4;
        int result = instanceGrasp.calcularQkl(listaLRC);
        assertEquals(expResult, result);
    }

    /**
     * Test of probabilidadSeleccionLRC method, of class Grasp.
     */
    @Test
    public void testProbabilidadSeleccionLRC() {
        System.out.println("probabilidadSeleccionLRC");
        List<GraspReinicio.ItemCalidad> listaLRC = LRC;
        int Qkl = 3;
        double[] expResult = new double[]{2 / 3.0, 0, 1 / 3.0};
        instanceGrasp.Q = lista_Q;
        instanceGrasp.k = 4;
        double[] result = instanceGrasp.probabilidadSeleccionLRC(listaLRC, Qkl);
        assertArrayEquals(expResult, result, 0);
    }

    /**
     * Test of seleccionItemDeLRC method, of class Grasp.
     */
//    @Test
//    public void testSeleccionItemDeLRC() {
//        System.out.println("seleccionItemDeLRC");
//        int Qkl = 3;
//        double[] qkl = new double[]{2/3.0,0,1/3.0};
//        List<Grasp.ItemCalidad> listaLRC = LRC;
//        Grasp.ItemCalidad expResult =listaLRC.get(0);
//        Grasp.ItemCalidad result = instanceGrasp.seleccionItemDeLRC(Qkl, qkl, listaLRC);
//        assertEquals(expResult, result);
//    }
//    /**
//     * Test of shift method, of class Grasp.
//     */    @Test
//    public void testShift() {
//        System.out.println("shift");
//        Individuo individuo = new Individuo(funcion, new double[]{0, 0, 1, 1});
//        Individuo expResult = new Individuo(funcion, new double[]{0, 1, 1, 1});
//        Individuo result = instanceGrasp.shift(individuo);
//        assertEquals(expResult, result);
//    }
    
    /**
     * Test of swap method, of class Grasp.
     */
    @Test
    public void testSwap() {
        System.out.println("swap");
        
        Individuo individuo = new Individuo(funcion, new double[]{1, 0, 1, 0});
        List<GraspReinicio.ItemCalidad> listItemNoSeleccionados = new ArrayList();
        listItemNoSeleccionados.add(new GraspReinicio.ItemCalidad(1, 10.2));
        listItemNoSeleccionados.add(new GraspReinicio.ItemCalidad(3, 8.2));
        
        Individuo expResult = new Individuo(funcion, new double[]{1, 0, 0, 0});
        Individuo result = instanceGrasp.swap(individuo);
        assertEquals(expResult, result);   
    }

//    /**
//     * Test of ejecutar method, of class Grasp.
//     */
//    @Test
//    public void testEjecutar() {
//        System.out.println("ejecutar");
//        Funcion funcion = null;
//        Grasp instance = null;
//        List<Individuo> expResult = null;
//        List<Individuo> result = instance.ejecutar(funcion);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of faseConstruccion method, of class Grasp.
//     */
//    @Test
//    public void testFaseConstruccion() {
//        System.out.println("faseConstruccion");
//        int minLen = 0;
//        int maxLen = 0;
//        Individuo solParcialS = null;
//        Grasp instance = null;
//        Individuo expResult = null;
//        Individuo result = instance.faseConstruccion(minLen, maxLen, solParcialS);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of faseBusquedaLocal method, of class Grasp.
//     */
//    @Test
//    public void testFaseBusquedaLocal() {
//        System.out.println("faseBusquedaLocal");
//        Individuo s = null;
//        Grasp instance = null;
//        Individuo expResult = null;
//        Individuo result = instance.faseBusquedaLocal(s);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
