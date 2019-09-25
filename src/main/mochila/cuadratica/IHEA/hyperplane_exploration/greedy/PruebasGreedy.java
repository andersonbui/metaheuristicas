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
package main.mochila.cuadratica.IHEA.hyperplane_exploration.greedy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.GrupoAlgoritmosMochilaCuadratica;
import main.mochila.cuadratica.ConjuntoInstancias.GrupoInstancias;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IteratedHyperplaneExplorationAlgoritm;
import main.mochila.cuadratica.utilidades.PrimerosPorDensidad;
import main.mochila.cuadratica.utilidades.ComparacionIdeal;
import main.mochila.cuadratica.ConjuntoInstancias.Instancia;
import main.mochila.cuadratica.utilidades.LecturaParametrosCuadratica;
import main.mochila.cuadratica.utilidades.InstanciaAlgoritmo;

/**
 *
 * @author debian
 */
public class PruebasGreedy {

    public static void main(String[] args) throws FileNotFoundException, Exception {

        String nombreArchivo;
        List<GrupoInstancias> gruposInstancias = new ArrayList();
//        InstanciaAlgoritmo.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_75_%d.txt", 1, 10));
//        InstanciaAlgoritmo.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_50_%d.txt", 1, 10));
//        InstanciaAlgoritmo.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_25_%d.txt", 1, 10));
        gruposInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10,"100"));
        gruposInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10,"100"));
        gruposInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_25_%d.txt", 1, 10,"100"));
        gruposInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 1, 10,"100"));
        gruposInstancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10,"100"));
//        InstanciaAlgoritmo.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_25_%d.txt", 1, 20));
//        InstanciaAlgoritmo.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_300_50_%d.txt", 1, 10));
        double porcentaje = 0;
        int cantidadPorcentajes = 0;
        //calculo tiempo
        int intentos = 10000;
        List<ComparacionIdeal.Datos> comparaciones = new ArrayList();
        for (GrupoInstancias ginstancia : gruposInstancias) {
            System.out.println("########################################################################");
            List<Instancia>instancias = ginstancia.getInstancias();

            for (Instancia instancia : instancias) {
                nombreArchivo = instancia.getNombreCompleto();

                System.out.println("---------------------------------------------------------------");
                System.out.println("Nombre archivo: " + nombreArchivo);
                // dimension de los puntos;
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
                InstanciaAlgoritmo parametros = lpc.obtenerParametrosInstancias(instancia);
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
                ComparacionIdeal.Datos datos = ComparacionIdeal.comparacion(parametros, individuo,null);
                if (datos != null) {
                    comparaciones.add(datos);
                }

            }
        }
        ComparacionIdeal.estadisticas(comparaciones);
        System.out.println("----\nTiempo promedio segundos: " + ((porcentaje / cantidadPorcentajes) * 100) + " %");
    }

}
