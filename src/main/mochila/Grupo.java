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
package main.mochila;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.mochila.cuadratica.ParametrosCuadratica;
import metaheuristicas.AlgoritmoMetaheuristico;

/**
 *
 * @author debian
 */
public abstract class Grupo {

    List<AlgoritmoMetaheuristico> algoritmos;
    private final ParametrosCuadratica parametros;
    String nombreFuncion;
    protected int maxIteraciones;

    /**
     *
     * @param parametros
     * @param nombreFuncion
     * @param maxIteraciones
     */
    public Grupo(ParametrosCuadratica parametros, String nombreFuncion, int maxIteraciones) {
        algoritmos = new ArrayList<>();
        this.parametros = parametros;
        this.nombreFuncion = nombreFuncion;
        this.maxIteraciones = maxIteraciones;
    }

    public abstract void inicializar();

    public Iterator<AlgoritmoMetaheuristico> iterator() {
        return algoritmos.iterator();
    }

    public List<AlgoritmoMetaheuristico> getAlgoritmos() {
        return algoritmos;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public boolean isEmpty() {
        return algoritmos.isEmpty();
    }

    public boolean add(AlgoritmoMetaheuristico e) {
        return algoritmos.add(e);
    }

    public AlgoritmoMetaheuristico get(int index) {
        return algoritmos.get(index);
    }

    public int getMaxIteraciones() {
        return maxIteraciones;
    }

    public void setMaxIteraciones(int maxIteraciones) {
        this.maxIteraciones = maxIteraciones;
    }
    
    
}
