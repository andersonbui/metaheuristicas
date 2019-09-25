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
package main.mochila.cuadratica.utilidades;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.ConjuntoInstancias.GrupoInstancias;
import main.mochila.cuadratica.ConjuntoInstancias.Instancia;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;

/**
 * Realiza estadisticas de cada instancia y las guarda en el mismo archivo o las
 * borra de los archivos, si asi se configura
 *
 * @author debian
 */
public class EstadisticasInstancias {

    public static void main(String[] args) {
        FuncionMochilaCuadratica funcion;
        String nombreArchivo = "";
        List<GrupoInstancias> listGrupoInstancias = new ArrayList<>();

        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_25_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_25_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_50_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_75_%d.txt", 1, 10,"100"));
        listGrupoInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1000/", "1000_100_%d.txt", 1, 10,"100"));
//        InstanciaAlgoritmo.add(new GrupoInstancias("mochilaCuadratica/", "r_10_100_%d.txt", 1, 10));

        StringBuilder sbuild = new StringBuilder();
        sbuild.append(String.format("%20s","pm_pesos")).append("|").//promedio pesos
                append(formatear("pm_pesos |")).//promedio pesos
                append(formatear("dv_pesos |")).//desviacion pesos
                append(formatear("pm_Mcal |")).//promedio M_calidad
                append(formatear("dv_Mcal |")).//promedio M_calidad
                append(formatear("Sum_Pes |")).//suma pesos
                append(formatear("Sum_Mcal |")).//suma Mcal
                append(formatear("Capacid|")).//capacidad
                append(formatear("SPes/cap |")).// (suma pesos) / capacidad
                append(formatear("SMat/Cal |")).//(suma M_calidad) / calidad
                append(formatear("lowerB |")). // lowerB
                append(formatear("upperB")). // upperB
                append(formatear("lowerB/upperB")) // lowerB/upperB
                ;

        for (GrupoInstancias grupoInstancia : listGrupoInstancias) {
            System.out.println("########################################################################");
            
            List<Instancia> instancias = grupoInstancia.getInstancias();

            for (Instancia instancia : instancias) {
                
                nombreArchivo = instancia.getNombreCompleto();

                LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
                InstanciaAlgoritmo parametros = pc.obtenerParametrosInstancias(instancia);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }

                int[] valsIdeal = parametros.getVectorIdeal();
                if (valsIdeal == null) {
                    System.out.println("No hay ideal.");
                    continue;
                }
                funcion = new FuncionMochilaCuadratica(
                        parametros.getMatrizBeneficios(),
                        parametros.getCapacidad(),
                        parametros.getVectorPesos(),
                        parametros.getMaxGlobal()) {
                };

                sbuild.append("\n");
                sbuild.append(String.format("%20s", instancia.getNombre()));
                double[] vecValPesos = parametros.getVectorPesos();
                sbuild.append(formatear(UtilCuadratica.promedio(vecValPesos)));//promedio pesos
                sbuild.append(formatear(UtilCuadratica.desviacion(vecValPesos)));//desviacion pesos
                double[] vecValMCal = UtilCuadratica.vectorizarMatrizBeneficios(parametros);//
                sbuild.append(formatear(UtilCuadratica.promedio(vecValMCal)));//promedio M_calidad
                sbuild.append(formatear(UtilCuadratica.desviacion(vecValMCal)));//desviacion M_calidad
                double sumaPesos = UtilCuadratica.suma(vecValPesos);
                double sumaMCal = UtilCuadratica.suma(vecValMCal);
                sbuild.append(formatear(sumaPesos));//suma pesos
                sbuild.append(formatear(sumaMCal));//suma Mcal
                sbuild.append(formatear(parametros.getCapacidad()));//capacidad
                double div_sp_c = sumaPesos / parametros.getCapacidad();// (suma pesos) / capacidad
                sbuild.append(formatear(div_sp_c));// (suma pesos) / capacidad
                sbuild.append(formatear(sumaPesos / parametros.getMaxGlobal()));//(suma M_calidad) / calidad
                int[] lowB_upB = UtilCuadratica.optenerLowerUpper_Bound(funcion);
                sbuild.append(formatear(lowB_upB[0])); // lowerB
                sbuild.append(formatear(lowB_upB[1])); // upperB
                sbuild.append(formatear(lowB_upB[0]/(double)lowB_upB[1])); // upperB
//                contador++;
            }
        }
        System.out.println(sbuild.toString());

    }

    private static String formatear(double valor) {
        return formatear(String.format("%-2.3f", valor));
    }

    private static String formatear(int valor) {
        return formatear(String.format("%-2d", valor));
    }

    private static String formatear(String valor) {
        return String.format("%11s", valor);
    }

}
