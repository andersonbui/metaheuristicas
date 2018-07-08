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
package main;

import gnuplot.Punto;
import java.util.List;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class Recorrido implements Comparable<Recorrido> {

    private IndividuoGen mejorIndividuo;
    private List<Punto> recorridoCalidad;
    private List<Punto> recorrido3D;
    private double promedioCalidad;
    private String nombreRecorrido;

    /**
     *
     * @param recorridoCalidad
     * @param recorrido3D
     * @param promedioCalidad
     * @param nombreRecorrido
     * @param mejorIndividuo
     */
    public Recorrido(List<Punto> recorridoCalidad, List<Punto> recorrido3D, double promedioCalidad, String nombreRecorrido, IndividuoGen mejorIndividuo) {
        this.recorridoCalidad = recorridoCalidad;
        this.recorrido3D = recorrido3D;
        this.promedioCalidad = promedioCalidad;
        this.nombreRecorrido = nombreRecorrido;
        this.mejorIndividuo = mejorIndividuo;
    }

    public List<Punto> getRecorridoCalidad() {
        return recorridoCalidad;
    }

    public List<Punto> getRecorrido3D() {
        return recorrido3D;
    }

    public double getPromedioCalidad() {
        return promedioCalidad;
    }

    public void setPromedioCalidad(double promedioCalidad) {
        this.promedioCalidad = promedioCalidad;
    }

    public String getNombreRecorrido() {
        return nombreRecorrido;
    }

    public void setNombreRecorrido(String nombreAlgoritmo) {
        this.nombreRecorrido = nombreAlgoritmo;
    }

    @Override
    public String toString() {
        return "Recorrido{" + mejorIndividuo.toStringBinario() + '}';
    }

    @Override
    public int compareTo(Recorrido otro) {
        Double calidad = this.mejorIndividuo.getCalidad();
        return calidad.compareTo(otro.mejorIndividuo.getCalidad());
    }

    public IndividuoGen getMejorIndividuo() {
        return mejorIndividuo;
    }

}
