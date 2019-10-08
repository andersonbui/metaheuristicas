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

import main.mochila.cuadratica.FuncionMochilaCuadratica;
import main.mochila.cuadratica.IndividuoCuadratico;

/**
 *
 * @author debian
 */
public class InstanciaAlgoritmo {

    private String nombreArchivo;

    private String nombreInstancia;
    private double[][] matrizBeneficios;
    private double capacidad;
    private double[] vectorPesos;
    // mejorables
    private int[] vectorIdeal;
    private Double maxGlobal;
    // parametros para edicion de mejorables
    private int posicionIdeal;
    private int posicionMaxGlobal;

    /**
     *
     * @param nombreArchivo
     * @param nombreInstancia
     * @param matrizBeneficios
     * @param capacidad
     * @param vectorPesos
     * @param vectorIdeal
     * @param maxGlobal
     */
    public InstanciaAlgoritmo(String nombreArchivo, String nombreInstancia, double[][] matrizBeneficios, double capacidad, double[] vectorPesos, int[] vectorIdeal, Double maxGlobal) {
        this.nombreArchivo = nombreArchivo;
        this.nombreInstancia = nombreInstancia;
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        this.maxGlobal = maxGlobal;
        this.vectorIdeal = vectorIdeal;
    }

    public InstanciaAlgoritmo() {
        
    }

    public IndividuoCuadratico obtenerIndividuoIdeal(FuncionMochilaCuadratica funcion) {
        IndividuoCuadratico indiIdeal = new IndividuoCuadratico(funcion);
        // crear individuo ideal
        int[] ideal = getVectorIdeal();
        if (ideal == null) {
//            System.out.println("No hay ideal.");
            return null;
        }
        for (int i = 0; i < ideal.length; i++) {
            if (ideal[i] == 1) {
                indiIdeal.set(i, 1);
            }
        }
        return indiIdeal;
    }

    public String getNombreInstancia() {
        return nombreInstancia;
    }

    public void setNombreInstancia(String nombreInstancia) {
        this.nombreInstancia = nombreInstancia;
    }

    public double[][] getMatrizBeneficios() {
        return matrizBeneficios;
    }

    public void setMatrizBeneficios(double[][] matrizBeneficios) {
        this.matrizBeneficios = matrizBeneficios;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public double[] getVectorPesos() {
        return vectorPesos;
    }

    public void setVectorPesos(double[] vectorPesos) {
        this.vectorPesos = vectorPesos;
    }

    public Double getMaxGlobal() {
        return maxGlobal;
    }

    public void setMaxGlobal(Double maxGlobal) {
        this.maxGlobal = maxGlobal;
    }

    public int[] getVectorIdeal() {
        return vectorIdeal;
    }

    public void setVectorIdeal(int[] vectorIdeal) {
        this.vectorIdeal = vectorIdeal;
    }

    public int getPosicionIdeal() {
        return posicionIdeal;
    }

    public void setPosicionIdeal(int posicionIdeal) {
        this.posicionIdeal = posicionIdeal;
    }

    public int getPosicionMaxGlobal() {
        return posicionMaxGlobal;
    }

    public void setPosicionMaxGlobal(int posicionMaxGlobal) {
        this.posicionMaxGlobal = posicionMaxGlobal;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public String toString() {
        return "ParametrosCuadratica{" + "\nnombreInstancia=" + nombreInstancia + ", \nmatrizBeneficios=" + matrizBeneficios + ", \ncapacidad=" + capacidad + ", \nvectorPesos=" + vectorPesos + ", \nmaxGlobal=" + maxGlobal + "\n}";
    }

}
