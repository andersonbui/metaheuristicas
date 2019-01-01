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
import main.GrupoInstancias;
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;
import main.mochila.cuadratica.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.hyperplane_exploration.IndividuoIHEA;

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
        List<GrupoInstancias> instancias = new ArrayList<>();
        double sumaParecidoUpperTotal = 0;
        double sumaParecidoLowerTotal = 0;
        double desv_sumaParecidoLowerTotal = 0;
        double sumaUltimoUpperTotal = 0;  // suma de todos los cocientes: ultimo/upperbound
        int contador = 0;
        int ultimoSeleccionado;

//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_50_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/jeu_100_25_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_100_100_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_100_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_25_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_50_%d.txt", 1, 10));
        instancias.add(new GrupoInstancias("mochilaCuadratica/grupo1/", "jeu_200_75_%d.txt", 1, 10));
//        instancias.add(new GrupoInstancias("mochilaCuadratica/", "r_10_100_%d.txt", 13, 13));

        for (GrupoInstancias instancia : instancias) {
            System.out.println("########################################################################");
            for (int indice_instancia = 1; indice_instancia <= instancia.cantidad; indice_instancia++) {
                ultimoSeleccionado = 0;
                nombreArchivo = instancia.getNombreArchivoCompleto(indice_instancia);
                
                LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
                ParametrosCuadratica parametros = pc.obtenerParametros(nombreArchivo);
                if (parametros == null) {
                    System.out.println("no se pudo obtener el archivo: " + nombreArchivo);
                    continue;
                }
                funcion = new FuncionMochilaIHEA(parametros.getMatrizBeneficios(), parametros.getCapacidad(), parametros.getVectorPesos(), parametros.getMaxGlobal());

                int[] valsIdeal = parametros.getVectorIdeal();
                if (valsIdeal == null) {
                    System.out.println("No hay ideal.");
                    continue;
                }
                IndividuoCuadratico indiIdeal = new IndividuoCuadratico(funcion);
                // crear individuo ideal
                for (int i = 0; i < valsIdeal.length; i++) {
                    if (valsIdeal[i] == 1) {
                        indiIdeal.set(i, 1);
//                        ultimoSeleccionado = i;
                    }
                }

                // lista de indices para ordenamiento
                List<Posicion> posiciones = new ArrayList();
                // crear estructura de comparacion
                for (int i = 0; i < funcion.getDimension(); i++) {
                    double peso = funcion.peso(i);
                    double relacion = funcion.relaciones(i);
                    double beneficio = funcion.beneficio(i);
                    posiciones.add(new Posicion(i, peso, relacion, beneficio));
                }
              
                // obtener cantidad de unos consecutivos en el individuo encontrado con respecto al ideal
                int cantidadSeleccionadosConsecutivos = 0;
                for (int i = 0; i < posiciones.size(); i++) {
                    int pos = posiciones.get(i).posicion;
                    if (valsIdeal[pos] == 1) {
                        cantidadSeleccionadosConsecutivos = i;
                    } else {
                        break;
                    }
                }
                // obtener la cantidad de unos en el individuo encontrado con respecto al ideal
                for (int i = 0; i < posiciones.size(); i++) {
                    int pos = posiciones.get(i).posicion;
                    if (valsIdeal[pos] == 1) {
                        ultimoSeleccionado = i;
                    }
                }

                IndividuoCuadratico indiAlcanzado = funcion.generarIndividuo();
                // crear individuo de los n primeros elementos de la lista ordenada
                while (funcion.cabe(indiAlcanzado, posiciones.get(0).posicion)) {
                    indiAlcanzado.set(posiciones.remove(0).posicion, 1);
                }

                contador++;
            }
        }
        
    }
}
