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
package main;

import metaheuristicas.Funcion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Individuo;

/**
 *
 * @author debian
 */
public class Utilidades {

    static public List<Individuo> obtenerDatosRegresion(int numDatos, String nombreArchivo, boolean maximizar) {
        LeerArchivo.abrir(nombreArchivo);
        List<String> listaCadenas = LeerArchivo.leer(numDatos);
        List<Individuo> listaPuntos = new ArrayList();
        int j;
        LeerArchivo.terminar();
        for (int i = 1; i < listaCadenas.size(); i++) {
            int posicionSubdivisiones = 0;
            String cadena = listaCadenas.get(i).replace(',', '.');

            cadena = eliminarEspaciosRepetidos(cadena);

            String[] vectSubdivisiones = cadena.split("" + '\u0020');
            Double[] valoresPuntoActual = new Double[vectSubdivisiones.length];
            valoresPuntoActual[0] = 1.;
            for (j = 1; j < valoresPuntoActual.length; j++) {
//                System.out.print("[" + valoresPuntoActual.length + "]<" + vectSubdivisiones[posicionSubdivisiones] + ">");
                valoresPuntoActual[j] = Double.parseDouble(vectSubdivisiones[posicionSubdivisiones++].trim());
            }
            Individuo puntoActual = new Individuo(null, valoresPuntoActual, maximizar);
            puntoActual.setCalidad(Double.parseDouble(vectSubdivisiones[posicionSubdivisiones++]));
            listaPuntos.add(puntoActual);
//            System.out.println("<" + puntoActual.toString() + ">");
        }
        return listaPuntos;
    }

    static public String eliminarEspaciosRepetidos(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        texto = "";
        while (tokens.hasMoreTokens()) {
            texto += " " + tokens.nextToken();
        }
        texto = texto.toString();
        texto = texto.trim();
        return texto;
    }

    public static int indiceOrdenadamente(List<Object> lista, Comparable punto, boolean ascendente) {
        if (lista.isEmpty()) {
            return 0;
        }
        int pos;
        int sup = lista.size() - 1;
        int inf = 0;
        Object puntoPos;
        int comparacion;
        while (true) {
            pos = inf + (sup - inf) / 2;
            puntoPos = lista.get(pos);
            comparacion = punto.compareTo(puntoPos) * (ascendente ? 1 : -1);
            if (comparacion >= 0) {
                inf = pos + (ascendente ? 1 : 1);
            } else if (comparacion < 0) {

                sup = pos + (ascendente ? -1 : -1);
            }
            if (sup < inf || comparacion == 0) {
                return inf;
            }
        }
    }

    public static void resta() {
        Double[] mayor = {1., 1., 1.};
        Double[] menor = {1., 1., 1.};
        Double[] temp;
        if (comparaToBinario(mayor, menor) < 0) {
            temp = mayor;
            mayor = menor;
            menor = temp;
        }
        Double[] resultado = resta(mayor, menor, 2);
        System.out.println("resta:" + Arrays.toString(resultado));
        resultado = suma(mayor, menor, 2);
        System.out.println("suma:" + Arrays.toString(resultado));

    }

    public static Double[] invertirArray(Double[] array) {
        Double[] ar_i = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            ar_i[array.length - i - 1] = array[i];
        }
        return ar_i;
    }

    public static int comparaToBinario(Double[] a, Double[] b) {
        Double v_a;
        Double v_b;
        for (int i = 0;; i++) {
            v_a = a.length > i ? a[i] : 0;
            v_b = b.length > i ? b[i] : 0;
            if (v_b == 0 && v_a == 0) {
                if (!(a.length > i && b.length > i)) {
                    return 1;
                } else {
                    continue;
                }
            }
            if (v_b == 0) {
                return 1;
            }
            if (v_a == 0) {
                return -1;
            }

        }
    }

    public static Double[] resta(Double[] a, Double[] b, int radix) {
        Double[] ar = invertirArray(a);
        Double[] br = invertirArray(b);

        Double[] resultado = new Double[ar.length];
        int acarreo = 0;
        Double v_a;
        Double v_b;
        for (int i = 0;; i++) {
            v_a = ar.length > i ? ar[i] : 0;
            v_b = br.length > i ? br[i] : 0;
            if (!(br.length > i || ar.length > i)) {
                break;
            }
            resultado[i] = v_a + acarreo - v_b;
            acarreo = resultado[i] < 0 ? -1 : 0;
            resultado[i] = Math.abs((radix + resultado[i]) % radix);

        }
        resultado = invertirArray(resultado);
        return resultado;
    }

    /**
     *
     * @param a es mayor que b
     * @param b
     * @param radix base de la numeración
     * @return
     */
    public static Double[] suma(Double[] a, Double[] b, int radix) {
        Double[] ar = invertirArray(a);
        Double[] br = invertirArray(b);

        Double[] resultado = new Double[ar.length];
        int acarreo = 0;
        Double v_a;
        Double v_b;
        for (int i = 0;; i++) {
            v_a = ar.length > i ? ar[i] : 0;
            v_b = br.length > i ? br[i] : 0;
            if (!(br.length > i || ar.length > i)) {
                if (acarreo > 0) {
                    resultado = Arrays.copyOf(resultado, resultado.length + 1);
                } else {
                    break;
                }
            }
            resultado[i] = v_a + acarreo + v_b;
            acarreo = resultado[i] >= radix ? 1 : 0;
            resultado[i] = resultado[i] % radix;

        }
        resultado = invertirArray(resultado);
        return resultado;
    }

}
