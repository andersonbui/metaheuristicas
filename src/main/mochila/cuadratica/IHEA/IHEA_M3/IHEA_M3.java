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
package main.mochila.cuadratica.IHEA.IHEA_M3;

import java.util.List;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.IHEA_AjusteVariables.IHEA_AV;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;

/**
 *
 * @author debian
 */
public class IHEA_M3 extends IHEA_AV {

    public IHEA_M3(FuncionMochilaIHEA funcion) {
        super(funcion);
    }

    @Override
    public void inicializar() {
        super.inicializar(); //To change body of generated methods, choose Tools | Templates.
    }
 
//        /**
//     * obtine los indices de todas las varibles seleccionadas que seran fijas
//     *
//     * @TODO realizar el calculo tambien con el LB solo, es decir, (nf=lowerb)
//     * @param dimensionHyp
//     * @param individuo
//     * @param lowerb
//     * @return
//     */
//    public List<Integer> determinarVariablesFijas2(int dimensionHyp, IndividuoIHEA individuo, int lowerb) {
//        // dimension de individuo
//        int dimX = dimensionHyp;  
//        // tamaño de la mochila
//        int n = individuo.getDimension();
//        // numero de variables fijas
//        int nf;
//        nf = (int) (lowerb + Math.max(0, (dimX - lowerb) * (1 - 1 / (0.008 * n))));
////        System.out.print(nf+" - ");
////        nf = (int) (0.860 * (lowerb + (getUb() - lowerb) / 2.0)); //general
////        nf = (int)(1.08 * (lowerb + (getUb() - lowerb) / 2.0)); //300
////        nf = (int) (0.99 * ((getUb() + lowerb) / 2.0) * (dimX *1.0/ getUb())); //300
////        nf = (int)(0.96*(lowerb + (getUb() - lowerb) / 2.0)); //100
////        System.out.println("- "+nf);
////        nf = (int)(1.20*(lowerb + (getUb() - lowerb) / 2.0)); //1000
//        // items seleccionados
//        List<Integer> itemsSeleccionados = elementosDentro(individuo);
//
//        List<Integer> listaIndices = (new PrimerosPorDensidad()).primerosPorDensidad2(itemsSeleccionados, individuo, nf, false);
//
//        // vector de indices de variables fijas
//        // obtener los primeros nf indices de los elementos más densos
//        // TODO: comprobar si todas estas variables fijas hacen parte del optimo global conocido
//        return listaIndices;
//    }

    @Override
    public int getNumeroNF(IndividuoIHEA individuo) {
        // dimension de individuo
        int k = dimensionHiperplano(individuo);
        // tamaño de la mochila
        int n = individuo.getFuncion().getDimension();
        if(n<=0){
            System.out.println("sdsd");
        }
        int c = (int)individuo.getFuncion().getCapacidad();
        // calcular numero de variables fijas
        // int nf = (int) (getLb() + Math.max(0, (k - getLb()) * (1 - 1 / (0.008 * n))));
        // (kub-c/(n*3))*(1-1/(k*0.05)) // mio7
//        int nf = (int)((getUb() - c/(n*3))*(1 - 1/(k*0.05))); //mio7
        // (kub-20)*(1-1/(k*0.099)) // mio1
//        int nf = (int)((getUb() - 20)*(1 - 1/(k*0.099))); //mio1 YA
        // klb+(k-klb)*(1-1/(n*0.010)) +(kub-k)/2
        //int nf = getLb() + (int)(Math.max(0, (k - getLb()))*(1-1.0/(n*0.010))) + (getUb() - k)/2; //YA
        // klb+(k-klb)*(1-1/(n*0.010)) +(kub-k)/2 -(kub-(c*0.02))*0.02
         int nf = getLb() + (int)(Math.max(0, (k - getLb()))*(1-1.0/(n*0.010))) + (getUb() - k)/2 - (int)Math.max(0,(getUb() - (c*0.02))*.02); // mejor
        // klb+(k-klb)*max(c/PT*.6,abs(1-1/(n*0.012))) +(kub-k)/2 -(kub-(c*0.02))*0.02
        //int nf = getLb() + (int)((k - getLb())*Math.max((30/n)*c/getPesoT(), Math.abs(1-1.0/(n*0.0090)))) + (getUb() - k)/2 - (int)((getUb() - (c*0.02))*.02); // mucho mejor
        //(kub-20)*(1-1/(k*0.099))-(kub-(c*0.028))*0.015
        //int nf = (int)Math.max(0,(int)((getUb() - 20)*(1 - 1/(k*0.099)))- (getUb() - (c*0.028))*.015); // YA
        //(-9*kub-10*1+10*k) - c/600
        //int nf = (int)Math.max(0,(10*k-9*getUb()-10) - c/540); // YA malo
        // klb +(k-klb)*max(c/PT*50/n,abs(1-1000/(n*8)))
        // int nf = getLb() + (int)((k - getLb())*Math.max((50/n)*c/getPesoT(), Math.abs(1-1.0/(n*0.008)))); // muhco mejor
        return nf;
    }


}
