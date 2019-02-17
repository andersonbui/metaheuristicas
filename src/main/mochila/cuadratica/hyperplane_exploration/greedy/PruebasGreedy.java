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
package main.mochila.cuadratica.hyperplane_exploration.greedy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.GrupoAlgoritmosMochilaCuadratica;
import main.GrupoInstancias;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.hyperplane_exploration.UtilidadesIHEA;
import main.mochila.cuadratica.utilidades.ComparacionIdeal;
import main.mochila.cuadratica.utilidades.LecturaParametrosCuadratica;
import main.mochila.cuadratica.utilidades.ParametrosCuadratica;

/**
 *
 * @author debian
 */
public class PruebasGreedy {

    public static void main(String[] args) throws FileNotFoundException, Exception {

        String nombreArchivo;
        List<GrupoInstancias> instancias = new ArrayList();
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 1, 10));
        double porcentaje = 0;
        int cantidadPorcentajes = 0;
        //calculo tiempo
        int intentos = 10000;
        List<ComparacionIdeal.Datos> comparaciones = new ArrayList();
        for (GrupoInstancias instancia : instancias) {
            System.out.println("########################################################################");
            for (int indice_instancia = instancia.inicio; indice_instancia <= instancia.ultimo; indice_instancia++) {
                nombreArchivo = instancia.getNombreArchivoCompleto(indice_instancia);

                System.out.println("---------------------------------------------------------------");
                System.out.println("Nombre archivo: " + nombreArchivo);
                // dimension de los puntos;
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
                ParametrosCuadratica parametros = lpc.obtenerParametros(nombreArchivo);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }
                double[][] matrizBeneficios = parametros.getMatrizBeneficios();
                double capacidad = parametros.getCapacidad();
                double[] vectorPesos = parametros.getVectorPesos();
                Double maxGlobal = parametros.getMaxGlobal();

                FuncionMochilaIHEA funcionHyperplanos = new FuncionMochilaIHEA(matrizBeneficios, capacidad, vectorPesos, maxGlobal);
                IteratedHyperplaneExplorationAlgoritm aIHEA = new IteratedHyperplaneExplorationAlgoritm(funcionHyperplanos);

                // generar individuo
                IndividuoIHEA mochila = new IndividuoIHEA(funcionHyperplanos);
                int rcl = 5;
                // lista indices
                long tiempo_inicial = System.currentTimeMillis();
                IndividuoIHEA individuo = (new Greedy(rcl)).ejecutar(mochila);
                long tiempo_final = System.currentTimeMillis();
                double promedio1 = ((tiempo_final - tiempo_inicial) / (double) intentos);
                System.out.println("tiempo promedio: " + promedio1 + "seg");

                porcentaje += promedio1;
                // lista de indices para ordenamiento
                ComparacionIdeal.Datos datos = ComparacionIdeal.comparacion(parametros, individuo);
                if (datos != null) {
                    comparaciones.add(datos);
                }

            }
        }
        ComparacionIdeal.estadisticas(comparaciones);
        System.out.println("----\nTiempo promedio segundos: " + ((porcentaje / cantidadPorcentajes) * 100) + " %");
    }

}
