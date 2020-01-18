/*
 * Copyright (C) 2020 debian
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
package main.mochila.cuadratica.IHEA;

import java.util.ArrayList;
import java.util.List;
import main.mochila.cuadratica.utilidades.ComparacionIdeal;
import main.mochila.cuadratica.utilidades.InstanciaAlgoritmo;

/**
 *
 * @author debian
 */
public class Depuracion {
    private double contadorFijosFalsosPositivos;
    private double contadorFijosFalsosNegativos;
    private int contadorIntercambios; // contador de intercambios dentro de la busqueda exaustiva de tabuSearch (estadistica)
    protected int tiempototal; // tiempo total que toma la busqueda tabu
    protected int contadortabu; // Contador de veces que se usa tabuSearch (estadistica)
    
    public Depuracion() {
        inicializar();
    }
    
    public final void inicializar () {
        contadorFijosFalsosPositivos = 0;
        contadorFijosFalsosNegativos = 0;
        tiempototal = 0;
        contadortabu = 0;
        contadorIntercambios = 0;
        
    }

    public double getContadorFijosFalsosPositivos() {
        return contadorFijosFalsosPositivos;
    }

    public void setContadorFijosFalsosPositivos(double contadorFijosFalsosPositivos) {
        this.contadorFijosFalsosPositivos = contadorFijosFalsosPositivos;
    }

    public double getContadorFijosFalsosNegativos() {
        return contadorFijosFalsosNegativos;
    }

    public void setContadorFijosFalsosNegativos(double contadorFijosFalsosNegativos) {
        this.contadorFijosFalsosNegativos = contadorFijosFalsosNegativos;
    }

    public int getContadorIntercambios() {
        return contadorIntercambios;
    }

    public void setContadorIntercambios(int contadorIntercambios) {
        this.contadorIntercambios = contadorIntercambios;
    }

    public int getTiempototal() {
        return tiempototal;
    }

    public void setTiempototal(int tiempototal) {
        this.tiempototal = tiempototal;
    }

    public int getContadortabu() {
        return contadortabu;
    }

    public void setContadortabu(int contadortabu) {
        this.contadortabu = contadortabu;
    }
    
       public void evaluarVariablesFijas(InstanciaAlgoritmo instancias, List<Integer> variablesFijas, int tamanio) {
        
        int amalos = ComparacionIdeal.cuentaValorEnIdeal(instancias, variablesFijas, 0, "cuanto son ceros");
        if (amalos > 0) {
            amalos += getContadorFijosFalsosPositivos();
            setContadorFijosFalsosPositivos(amalos);
        }

        List<Integer> variablesNoFijas = new ArrayList<>();
        for (int i = 0; i < tamanio; i++) {
            variablesNoFijas.add(i);
        }
        variablesNoFijas.removeAll(variablesFijas);

        int buenos = ComparacionIdeal.cuentaValorEnIdeal(instancias, variablesNoFijas, 1, "cuanto son unos");
        if (buenos > 0) {
            buenos += getContadorFijosFalsosNegativos();
            setContadorFijosFalsosNegativos(buenos);
        }
    }
}
