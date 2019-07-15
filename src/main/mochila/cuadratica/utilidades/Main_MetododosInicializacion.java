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

import main.mochila.cuadratica.ConjuntoInstancias.Instancia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.mochila.cuadratica.ConjuntoInstancias.ConjuntoInstanciasPruebas;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.FuncionMochilaIHEA;
import main.mochila.cuadratica.IHEA.hyperplane_exploration.IndividuoIHEA;

/**
 * prueba de metodos de inicializacion de individuo mochila cuadratica
 *
 * @author debian
 */
public class Main_MetododosInicializacion {

    private static FuncionMochilaIHEA funcion;

    public static void main(String[] args) {
        String nombreArchivo = "";

        List<Instancia> listaInstanc = null;
        ConjuntoInstanciasPruebas conjuntoInstan = new ConjuntoInstanciasPruebas();
        listaInstanc = conjuntoInstan.getConjuntoInstancias();

        List<ComparacionIdeal.Datos> comparaciones = new ArrayList();
        for (Instancia instancia : listaInstanc) {
            nombreArchivo = instancia.getNombreCompleto();

            LecturaParametrosCuadratica pc = new LecturaParametrosCuadratica();
            ParametrosInstancia parametros = pc.obtenerParametros(instancia);
            if (parametros == null) {
                System.err.println("no se pudo obtener el archivo: " + nombreArchivo);
                continue;
            }
            funcion = new FuncionMochilaIHEA(parametros.getMatrizBeneficios(), parametros.getCapacidad(), parametros.getVectorPesos(), parametros.getMaxGlobal());

            // lista de indices para ordenamiento
            List<Posicion> posiciones = new ArrayList();
            // crear estructura de comparacion
            for (int i = 0; i < funcion.getDimension(); i++) {
                double peso = funcion.peso(i);
                double relacion = funcion.relaciones(i);
                double beneficio = funcion.beneficio(i);
                posiciones.add(new Posicion(i, peso, relacion, beneficio));
            }
            Main_MetododosInicializacion obj = new Main_MetododosInicializacion();
            // ordenar de acuerdo a la estrctura anterior
            Collections.sort(posiciones, (Posicion o1, Posicion o2) -> {
                return obj.comparar4(o1, o2);
            });

            IndividuoIHEA indiAlcanzado = funcion.generarIndividuo();
            // crear individuo de los n primeros elementos de la lista ordenada
            int cont = 0;
            while (funcion.cabe(indiAlcanzado, posiciones.get(cont).posicion)) {
                indiAlcanzado.set(posiciones.get(cont).posicion, 1);
                cont++;
            }

            // lista de indices para ordenamiento
            ComparacionIdeal.Datos datosComparacion = ComparacionIdeal.comparacion(parametros, indiAlcanzado, posiciones);
            if (datosComparacion != null) {
                comparaciones.add(datosComparacion);
            }
        }
        ComparacionIdeal.estadisticas(comparaciones);
    }

    public List<Integer> ordenar(FuncionMochilaIHEA funcion) {
        List<Integer> indicesOrdenados = new ArrayList();
        //        // lista de indices para ordenamiento
        List<Main_MetododosInicializacion.Posicion> posiciones = new ArrayList();
        // crear estructura de comparacion
        for (int i = 0; i < funcion.getDimension(); i++) {
            double peso = funcion.peso(i);
            double relacion = funcion.relaciones(i);
            double beneficio = funcion.beneficio(i);
            posiciones.add(new Main_MetododosInicializacion.Posicion(i, peso, relacion, beneficio));
        }

        // ordenar de acuerdo a la estrctura anterior
        Collections.sort(posiciones, (Main_MetododosInicializacion.Posicion o1, Main_MetododosInicializacion.Posicion o2) -> {
            return comparar4(o1, o2);
        });

        for (Posicion posicion : posiciones) {
            indicesOrdenados.add(posicion.posicion);
        }
        return indicesOrdenados;
    }

    public static class Posicion {

        public int posicion;
        public double peso;
        public double relacion;
        public double beneficio;

        public Posicion(int posicion, double peso, double relacion, double beneficio) {
            this.posicion = posicion;
            this.peso = peso;
            this.relacion = relacion;
            this.beneficio = beneficio;
        }

    }

    public int comparar3(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio * o1.beneficio / (o1.relacion * o1.peso);
        Double resultado2 = o2.beneficio * o2.beneficio / (o2.relacion * o2.peso);
        return -resultado1.compareTo(resultado2);
    }

    public int comparar(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / (o1.relacion * o1.peso);
        Double resultado2 = o2.beneficio / (o2.relacion * o2.peso);
        return -resultado1.compareTo(resultado2);
    }

    public int comparar2(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / o1.peso;
        Double resultado2 = o2.beneficio / o2.peso;
        return -Double.compare(resultado1, resultado2);
    }

    public int comparar4(Posicion o1, Posicion o2) {
        Double resultado1 = o1.beneficio / o1.peso;
        Double resultado2 = o2.beneficio / o2.peso;
        int result = -Double.compare(resultado1, resultado2);
        if (result == 0) {
            return Double.compare(o1.peso, o2.peso);
        }
        return result;
    }
}
