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
package main.mochila.cuadratica.IHEA.IHEA_M4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.IHEA_AjusteVariables.IHEA_AV;
import main.mochila.cuadratica.IHEA.IHEA_M1.IHEA_M1;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.utilidades.Main_MetododosInicializacion;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;

/**
 *
 * @author debian
 */
public class IHEA_M4 extends IHEA_AV {

    //    protected int ub; // upper bown
//    public List<Integer> listaIndicesMalos;
    
    public IHEA_M4(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }
 
    public List<Integer> fijarVariablesMaslas(IndividuoIHEA individuo,FuncionMochilaIHEA funcion, int upperb) {
        List<Integer> listaIndicesMalos = null;
//        if (listaIndicesMalos == null) {
            // tamaño de la mochila
            int n = funcion.getDimension();
            // numero de variables fijas
            // n - upperb - 40
            int nf = (int) Math.max(0,(n - upperb - 40));//1.3
            int inicio = n - nf;
            nf = Math.min(n, nf);
            
            if( inicio <= 0 ){
                return new ArrayList(0);
            }
            
            List<Integer> itemsNoSeleccionados = elementosFuera(individuo);
            listaIndicesMalos = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsNoSeleccionados, individuo, nf, true);
            
//        }
//        System.out.print("");
        // vector de indices de variables fijas
        // obtener los primeros nf indices de los elementos más densos
        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido (listo)
        return listaIndicesMalos;
    }
  
    
    /**
     * obtine los indices de todas las varibles seleccionadas que seran fijas
     *
     * @TODO realizar el calculo tambien con el LB solo, es decir, (nf=lowerb)
     * @param individuo
     * @param upperb
     * @return
     */
    protected List<Integer> determinarMalasVariablesFijas(IndividuoIHEA individuo, int upperb) {
        return fijarVariablesMaslas(individuo, funcion, upperb);
    }

    @Override
    public FuncionMochilaIHEA_M4 getFuncion() {
        return (FuncionMochilaIHEA_M4) funcion;
    }

    /**
     *
     * @param varFijas
     * @param individuoActual
     */
    @Override
    protected void construirProblemaRestringidoReducido(List<Integer> varFijas, IndividuoIHEA individuoActual) {
        getFuncion().fijarVariables(individuoActual, varFijas);
        List<Integer> malas = determinarMalasVariablesFijas(individuoActual, getUb());
        getFuncion().fijarVariablesMalas(malas);
    }
    

}
