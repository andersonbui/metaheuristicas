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
import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;
import main.utilidades.LeerArchivo;
import static main.utilidades.Utilidades.eliminarEspaciosRepetidos;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class UtilCuadratica {

    static public List obtenerDatosMochilaCuadratica(String nombreArchivo) {
        LeerArchivo leer = new LeerArchivo();
        leer.abrir(nombreArchivo);
        List<String> listaCadenas = leer.leer();
        leer.terminar();
        List listaObj = new ArrayList();

        // nombre de la instancia
        String nombreInstancia = listaCadenas.remove(0);
        listaObj.add(nombreInstancia);
        // numVar: numero de elementos para la mochila
        int numVar = Integer.parseInt(listaCadenas.remove(0).trim());
        double[][] matrizBeneficios = new double[numVar][numVar];
        double[] vectorPesos = new double[numVar];
        double capacidad;
        String cadena;
        String[] vectSubdivisiones;

        for (int i = 0; i < numVar; i++) {
            cadena = listaCadenas.remove(0).replace(',', '.');
            cadena = eliminarEspaciosRepetidos(cadena);
            vectSubdivisiones = cadena.split("" + '\u0020');
            for (int k = i, j = 0; k < numVar; j++, k++) {
                if (i == 0) {
                    matrizBeneficios[k][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                } else {
                    matrizBeneficios[i - 1][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                }
            }
        }
        listaObj.add(matrizBeneficios);
        String optimo_cad = listaCadenas.remove(0); // espacio vacio o maximo ideal

        listaCadenas.remove(0); // 0 ó 1
        capacidad = Double.parseDouble(listaCadenas.remove(0).trim()); // capacidad de la mochila
        listaObj.add(capacidad);

        cadena = listaCadenas.remove(0).replace(',', '.');
        cadena = eliminarEspaciosRepetidos(cadena);
        vectSubdivisiones = cadena.split("" + '\u0020');
        for (int k = 0; k < numVar; k++) {
            vectorPesos[k] = Double.parseDouble(vectSubdivisiones[k].trim());
        }
        listaObj.add(vectorPesos);

        try {
            double optimo = Double.parseDouble(optimo_cad); // espacio vacio o maximo ideal
            listaObj.add(optimo);
        } catch (NumberFormatException e) {

        }
        return listaObj;
    }

    /**
     * realiza un intercambio, agrega un elemento y remueve otro que esta fuera
     * y dentro correspondientemente, de manera aleatoria dentro de individuo,
     * siempre y cuando se pueda, es decir, que se pueda agragar un elemento
     * aleatorio despues de haber removido uno, de lo cntrario no se hace nada.
     * se hace 10 intentos de busqueda de elementos aptos para realizar el
     * intercambio.
     *
     * @param individuo_original a quien se le realiza el intercambio
     * @param movimiento almacena la variables implicadas en el movimiento
     * @return
     */
    public static IndividuoCuadratico swap(IndividuoCuadratico individuo_original, Movimiento movimiento) {
        if (individuo_original.elementosSeleccionados().isEmpty()) {
            return null;
        }
        FuncionMochilaCuadratica funcion = (FuncionMochilaCuadratica) individuo_original.getFuncion();
        IndividuoCuadratico individuo = individuo_original.clone();
        int intentosIntercambio = 10;
        List<Integer> listaItemFuera;
        List<Integer> listaItemDentro;
        boolean estaVacia;
        int contador = intentosIntercambio;
        Integer posicionD; // debe ser objeto Integer
        do { // intentos de busqueda
            listaItemDentro = individuo.elementosSeleccionados();
            int tamLDentro = listaItemDentro.size();
            int aleatorioD = Aleatorio.nextInt(tamLDentro);
            posicionD = listaItemDentro.get(aleatorioD);
            // sacar elemento dentro de la mochila
            individuo.set(posicionD, 0);
            // obtener lista de elementos dentro de la mochila pero que caben dentro de esta
            listaItemFuera = individuo.elementosNoSeleccionados();
            listaItemFuera = funcion.filtrarPorFactibles(listaItemFuera, individuo);
//            listaItemFuera = obtenerItemsNoSelecionadosFiltrados(individuo);
            listaItemFuera.remove(posicionD);
            // verificar si hay elementos
            estaVacia = listaItemFuera.isEmpty();
            if (estaVacia) { // si no hay elementos fuera de la mochila que quepan
                individuo.set(posicionD, 1); // agregar elemento anteriormente removido
            }
        } while (estaVacia && contador-- >= 0);
        // salir si no hay elementos para intercambiar
        if (estaVacia) {
            return null;
        }
        int tamLDentro = listaItemFuera.size();
        int aleatorioF = Aleatorio.nextInt(tamLDentro);
        int posicionF = listaItemFuera.get(aleatorioF);
        // agregar elemento dentro de la mochila
        individuo.set(posicionF, 1);
        if (movimiento != null) {
            movimiento.i = posicionF;
            movimiento.j = posicionD;
        }
        return individuo;
    }

    /**
     * realiza un intercambio, agrega un elemento y remueve otro que esta fuera
     * y dentro correspondientemente, de manera aleatoria dentro de individuo,
     * siempre y cuando se pueda, es decir, que se pueda agragar un elemento
     * aleatorio despues de haber removido uno, de lo cntrario no se hace nada.
     * se hace 10 intentos de busqueda de elementos aptos para realizar el
     * intercambio.
     *
     * @param individuo_original a quien se le realiza el intercambio
     * @return
     */
    public static IndividuoCuadratico swap(IndividuoCuadratico individuo_original) {
        return swap(individuo_original, null);
    }

    public static class Movimiento {

        public int i;
        public int j;

        public Movimiento() {
            i = -1;
            j = -1;
        }

        public Movimiento(int indice_entro, int indice_salio) {
            this.i = indice_entro;
            this.j = indice_salio;
        }
    }

    /**
     * obtiene los valores de lower bound y upper bound
     *
     * @param funcion
     * @return double [lower_bound, upper_bound]
     */
    public static int[] optenerLowerUpper_Bound(FuncionMochilaCuadratica funcion) {
        int lowerB;
        int upperB;
        List<Integer> listaIndices = new ArrayList();
        for (int i = 0; i < funcion.getDimension(); i++) {
            listaIndices.add(i);
        }
        //ordenacionde elementos
        listaIndices.sort((Integer o1, Integer o2) -> {
            Double peso1 = funcion.peso(o1);
            Double peso2 = funcion.peso(o2);
            // orden decreciente
            return -peso1.compareTo(peso2);
        });
        lowerB = 0;
        int suma_lb = 0;
        upperB = 0;
        int suma_ub = 0;
        for (int i = 0, j = listaIndices.size() - 1; i < listaIndices.size(); j--, i++) {
            suma_lb += funcion.peso(listaIndices.get(i));
            if (suma_lb <= funcion.getCapacidad()) {
                lowerB++;
            }
            suma_ub += funcion.peso(listaIndices.get(j));
            if (suma_ub <= funcion.getCapacidad()) {
                upperB++;
            }
        }
        return new int[]{lowerB, upperB};
    }
    
    public static double[] vectorizarMatrizBeneficios(InstanciaAlgoritmo parametros) {
        double[][] mat = parametros.getMatrizBeneficios();
        int tamanio = (mat.length * (mat.length + 1)) / 2;
        int contador = 0;
        double[] vector = new double[tamanio];
        for (int k = 0; k < mat.length; k++) {
            for (int h = k; h < mat[0].length; h++) {
                vector[contador] = mat[k][h];
                contador++;
            }
        }
        return vector;
    }

    public static double desviacion(double[] valores) {
        double promedio = promedio(valores);
        double desviacion = 0;
        for (double val : valores) {
            desviacion += val * val;
        }
        desviacion = Math.sqrt((1.0 / valores.length) * (desviacion - valores.length * promedio * promedio));
        return desviacion;
    }

    public static double suma(double[] valores) {
        double promedio = 0;
        for (double val : valores) {
            promedio += val;
        }
        return promedio;
    }

    public static int suma(int[] valores) {
        int promedio = 0;
        for (int val : valores) {
            promedio += val;
        }
        return promedio;
    }
    
    public static double promedio(double[] valores) {
        double promedio = 0;
        for (double val : valores) {
            promedio += val;
        }
        return promedio / valores.length;
    }
    
    
    public static String formatear(double valor) {
        if (valor > 100000) {
            return String.format("%-2.2f", valor);
        }
        if (valor < 10) {
            return String.format("%-1.6f", valor);
        } else {
            return String.format("%.2f", valor);
        }
    }

    public static String formatear(int valor) {
        if (valor > 100000) {
            return String.format("%-2E", 1.0 * valor);
        } else {
            return String.format("%4d" + "", valor);
        }
    }
}
