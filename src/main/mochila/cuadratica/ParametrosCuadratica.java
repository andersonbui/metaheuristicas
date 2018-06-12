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
package main.mochila.cuadratica;

/**
 *
 * @author debian
 */
public class ParametrosCuadratica {

    String nombreInstancia;
    double[][] matrizBeneficios;
    double capacidad;
    double[] vectorPesos;
    Double maxGlobal;

    /**
     * 
     * @param nombreInstancia
     * @param matrizBeneficios
     * @param capacidad
     * @param vectorPesos
     * @param maxGlobal 
     */
    public ParametrosCuadratica(String nombreInstancia, double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        this.nombreInstancia = nombreInstancia;
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        this.maxGlobal = maxGlobal;
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

    @Override
    public String toString() {
        return "ParametrosCuadratica{" + "\nnombreInstancia=" + nombreInstancia + ", \nmatrizBeneficios=" + matrizBeneficios + ", \ncapacidad=" + capacidad + ", \nvectorPesos=" + vectorPesos + ", \nmaxGlobal=" + maxGlobal + "\n}";
    }
    
}
