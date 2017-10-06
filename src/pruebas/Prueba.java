/*
 * Copyright (C) 2017 debian
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
package pruebas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author debian
 */
public class Prueba {

    public static void main(String[] args) {
        List numeros = new ArrayList();
        Random rand = new Random(10);
        int cantidad = 100000;
        int num;
        boolean ascendente = true;
        for (int i = 0; i < cantidad; i++) {
            num = rand.nextInt(cantidad);
            int pos = Utilidades.indiceOrdenadamente(numeros, num, ascendente);
            numeros.add(pos, num);
        }
        System.out.println("lista: " + numeros);
        Integer aux = null;
        for (Object numero : numeros) {
            int actual = (int) numero;
            if (aux == null) {
                aux = actual;
            }
            if (ascendente && aux.compareTo(actual) > 0) {
                System.out.println("MAL ascendente-------");
                break;
            }
            if (!ascendente && aux.compareTo(actual) < 0) {
                System.out.println("MAL descendente-------");
                break;
            }
        }
    }
}
