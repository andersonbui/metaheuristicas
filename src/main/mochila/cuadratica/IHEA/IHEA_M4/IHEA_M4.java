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

/**
 *
 * @author debian
 */
public class IHEA_M4 extends IHEA_M1 {

    //    protected int ub; // upper bown
    public List<Integer> listaIndicesMalos;
    
    public IHEA_M4(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }
 
    public List<Integer> fijarVariablesMaslas(FuncionMochilaIHEA funcion, int upperb) {
        if (listaIndicesMalos == null) {
            // tamaño de la mochila
            int n = funcion.getDimension();
            // numero de variables fijas
            Random rand = new Random();
            double vari = 2.2;
//            double vari = 1.47 + (0.2 * rand.nextDouble() - 0.1);
            int nf = (int) (((upperb) * vari));//1.3
            
            nf = Math.min(n, nf);
            int inicio = n - nf;
            
            if( inicio <= 0 ){
                return new ArrayList(0);
            }
            // items seleccionados
            listaIndicesMalos = new ArrayList(nf);
            List<Integer> listaInidicesResultado = (new Main_MetododosInicializacion()).ordenar(funcion);
            for (int i = inicio; i < n; i++) {
                listaIndicesMalos.add(listaInidicesResultado.get(i));
            }
        }
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
        listaIndicesMalos = fijarVariablesMaslas(funcion, upperb);
        return listaIndicesMalos;
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
