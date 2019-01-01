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

import java.util.List;
import java.util.ListIterator;
import main.utilidades.Utilidades;
import static main.utilidades.Utilidades.eliminarEspaciosRepetidos;

/**
 *
 * @author debian
 */
public class ArchivoInstancia {

    List<String> lineas;
    int INDICE_NOMBRE = 0;
    int INDICE_TAMANIO = 1;
    int INICIO_MATRIZ = 2;
    int INDICE_MAXG = 0; //despues de matriz de beneficio
    int INDICE_CAPACIDAD = 2; //despues de matriz de beneficio
    int INDICE_PESOS = 3; //despues de matriz de beneficio
    int INDICE_INDIVIDUO_MAXG = 4; //despues de matriz de beneficio

    public ArchivoInstancia(List<String> lineas) {
        this.lineas = lineas;
    }

    public List<String> getLineas() {
        return lineas;
    }

    public void setLineas(List<String> lineas) {
        this.lineas = lineas;
    }

    /**
     * obtiene el tamaño de la matriz de beneficio
     *
     * @return
     */
    public int getTamanio() {
        return Integer.parseInt(lineas.get(INDICE_TAMANIO));
    }

    /**
     * obtiene el INDICE_NOMBRE de la instancia
     *
     * @return
     */
    public String getNombre() {
        return lineas.get(INDICE_NOMBRE);
    }

    /**
     * obtiene el maximo global conocido de la instancia
     *
     * @return
     */
    public double getMaximoGlobal() {
        int indice = INICIO_MATRIZ + getTamanio() + INDICE_MAXG;
        double maxGlobal = Double.NaN;
        try {
            maxGlobal = Double.parseDouble(lineas.get(indice)); // espacio vacio o maximo ideal
        } catch (NumberFormatException e) {
        }
        return maxGlobal;
    }

    /**
     * Establece el maximo global conocido de la instancia
     *
     * @param maxG
     */
    public void setMaximoGlobal(double maxG) {
        int indice = INICIO_MATRIZ + getTamanio() + INDICE_MAXG;
        lineas.set(indice, String.valueOf(maxG));
    }

    /**
     * obtiene la capacidad de la instancia
     *
     * @return
     */
    public Double getCapacidad() {
        int indice = INICIO_MATRIZ + getTamanio() + INDICE_CAPACIDAD;
        return Double.parseDouble(lineas.get(indice));
    }

    /**
     * obtiene el vector de pesos de la instancia
     *
     * @return
     */
    public double[] getPesos() {
        int indice = INICIO_MATRIZ + getTamanio() + INDICE_PESOS;
        String cadena;
        String[] vectSubdivisiones;
        int numElementos = getTamanio();
        double[] vectorPesos = new double[numElementos];
        // lectura de vector pesos
        cadena = lineas.get(indice);
        cadena = eliminarEspaciosRepetidos(cadena);
        vectSubdivisiones = cadena.split("" + '\u0020');
        for (int k = 0; k < numElementos; k++) {
            vectorPesos[k] = Double.parseDouble(vectSubdivisiones[k].trim());
        }
        return vectorPesos;
    }

    /**
     * obtiene el vector de valores del individuo maximo global de la instancia
     *
     * @return
     */
    public int[] getInstanciaMaxG() {
        int indice = INICIO_MATRIZ + getTamanio() + INDICE_INDIVIDUO_MAXG;
        String cadena;
        String[] vectSubdivisiones;
        int numElementos = getTamanio();
        int[] vectorideal = null;

        if (lineas.size() - 1 < indice) {
            return null;
        }

        cadena = lineas.get(indice).trim();
        cadena = eliminarEspaciosRepetidos(cadena);
        vectSubdivisiones = cadena.split("" + '\u0020');
        if (vectSubdivisiones.length == numElementos) {
            vectorideal = new int[numElementos];
            for (int k = 0; k < numElementos; k++) {
                vectorideal[k] = Integer.parseInt(vectSubdivisiones[k].trim());
            }
        }
        return vectorideal;
    }

    /**
     * Establece el vector de valores del individuo maximo global de la
     * instancia
     *
     * @param vectorideal
     */
    public void setInstanciaMaxG(int[] vectorideal) throws IllegalArgumentException {
        int indice = INICIO_MATRIZ + getTamanio() + INDICE_INDIVIDUO_MAXG;
        int numElementos = getTamanio();
        if (vectorideal.length != numElementos) {
            throw new IllegalArgumentException("tamanio diferente al de la instancia");
        }
        StringBuilder sbuild = new StringBuilder();
        for (int i : vectorideal) {
            sbuild.append(i).append(" ");
        }
        if (lineas.size() <= indice) {
            lineas.add(indice, sbuild.toString());
        }else{
            lineas.set(indice, sbuild.toString());
        }
    }

    /**
     * obtiene la matriz de beneficio bien formada
     *
     * @return
     */
    public double[][] getMatriz() {
        double[][] matrizBeneficios;
        int numElementos = getTamanio();
        matrizBeneficios = new double[numElementos][numElementos];
        int comienzoMatriz = INICIO_MATRIZ;
        int finalMatriz = INICIO_MATRIZ + numElementos;
        String cadena;
        String[] vectSubdivisiones;

        ListIterator<String> iterador = lineas.subList(comienzoMatriz, finalMatriz).listIterator();
        // obtener matriz de beneficios
        for (int i = 0; i < numElementos; i++) {
            cadena = iterador.next().replace(',', '.');
            cadena = Utilidades.eliminarEspaciosRepetidos(cadena);
            vectSubdivisiones = cadena.split("" + '\u0020');
            for (int k = i, j = 0; k < numElementos; j++, k++) {
                if (i == 0) {
                    matrizBeneficios[k][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                } else {
                    matrizBeneficios[i - 1][k] = Double.parseDouble(vectSubdivisiones[j].trim());
                }
            }
        }
        return matrizBeneficios;
    }

}
