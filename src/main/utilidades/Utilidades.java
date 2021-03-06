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
package main.utilidades;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class Utilidades {

    static public List<IndividuoGen> obtenerDatosRegresion(int numDatos, String nombreArchivo, boolean maximizar) {
        LeerArchivo leer = new LeerArchivo();
        leer.abrir(nombreArchivo);
        List<String> listaCadenas = leer.leer(numDatos);
        List<IndividuoGen> listaPuntos = new ArrayList();
        int j;
        leer.terminar();
        for (int i = 1; i < listaCadenas.size(); i++) {
            int posicionSubdivisiones = 0;
            String cadena = listaCadenas.get(i).replace(',', '.');

            cadena = eliminarEspaciosRepetidos(cadena);

            String[] vectSubdivisiones = cadena.split("" + '\u0020');
            double[] valoresPuntoActual = new double[vectSubdivisiones.length];
            valoresPuntoActual[0] = 1.;
            for (j = 1; j < valoresPuntoActual.length; j++) {
//                System.out.print("[" + valoresPuntoActual.length + "]<" + vectSubdivisiones[posicionSubdivisiones] + ">");
                valoresPuntoActual[j] = Double.parseDouble(vectSubdivisiones[posicionSubdivisiones++].trim());
            }
            IndividuoGen puntoActual = new IndividuoGen(null, valoresPuntoActual);

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
        texto = texto.trim();
        return texto;
    }

    /**
     * retorna la posicion del nuevo elemento a ser adicionado en la lista
     * ordenada, ordenado segun la variable ascendente.
     *
     * @param lista
     * @param punto
     * @param ascendente
     * @return
     */
    public static int indiceOrdenadamente(List lista, Comparable punto, boolean ascendente) {
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
                inf = pos + 1;
            } else if (comparacion < 0) {

                sup = pos - 1;
            }
            if (sup < inf || comparacion == 0) {
                return inf;
            }
        }
    }

    /**
     * retorna la posicion del nuevo elemento a ser adicionado en la lista
     * ordenada, ordenado segun la variable ascendente. La lista tendra un
     * limite maximo hasta el cual se llenara de manera ordenada, los elementos
     * en posiiones que sobrepasen el limite seran ignorados.
     *
     * @param lista
     * @param elemento
     * @param ascendente
     * @param limite
     * @return
     */
    public static int indiceOrdenadamente(List<Object> lista, Comparable elemento, boolean ascendente, int limite) {
        if (lista.isEmpty()) {
            return 0;
        }
        // comprobar que el elemento este dentro de limite
        if (lista.size() >= limite) {
            if (ascendente) {
                if (elemento.compareTo(lista.get(limite - 1)) > 0) {
                    return -1;
                }
            } else if (elemento.compareTo(lista.get(limite - 1)) < 0) {
                return -1;
            }
        }
        return indiceOrdenadamente(lista, elemento, ascendente);
    }

    /**
     * retorna la posicion del nuevo elemento a ser adicionado en la lista
     * ordenada, ordenado segun la variable ascendente. La lista tendra un
     * limite maximo hasta el cual se llenara de manera ordenada, los elementos
     * en posiiones que sobrepasen el limite seran ignorados.
     *
     * @param lista
     * @param elemento
     * @param comparator
     * @return
     */
    public static int indiceOrdenadamente(List lista, Object elemento, Comparator comparator) {
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
            comparacion = comparator.compare(elemento, puntoPos);
            if (comparacion >= 0) {
                inf = pos + 1;
            } else if (comparacion < 0) {
                sup = pos - 1;
            }
            if (sup < inf || comparacion == 0) {
                return inf;
            }
        }
    }

    /**
     * retorna la posicion del nuevo elemento a ser adicionado en la lista
     * ordenada, ordenado segun la variable ascendente. La lista tendra un
     * limite maximo hasta el cual se llenara de manera ordenada, los elementos
     * en posiiones que sobrepasen el limite seran ignorados.
     *
     * @param lista
     * @param elemento
     * @param comparator
     * @param limite
     * @return
     */
    public static int indiceOrdenadamente(List lista, Object elemento, Comparator comparator, int limite) {
        if (lista.isEmpty()) {
            return 0;
        }
        // comprobar que el elemento este dentro de limite
        if (lista.size() >= limite) {
            if (comparator.compare(elemento, lista.get(limite - 1)) > 0) {
                return -1;
            }
        }
        return indiceOrdenadamente(lista, elemento, comparator);
    }

    public static void resta() {
        double[] mayor = {1., 1., 1.};
        double[] menor = {1., 1., 1.};
        double[] temp;
        if (comparaToBinario(mayor, menor) < 0) {
            temp = mayor;
            mayor = menor;
            menor = temp;
        }
        double[] resultado = resta(mayor, menor, 2);
        System.out.println("resta:" + Arrays.toString(resultado));
        resultado = suma(mayor, menor, 2);
        System.out.println("suma:" + Arrays.toString(resultado));

    }

    public static double[] invertirArray(double[] array) {
        double[] ar_i = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ar_i[array.length - i - 1] = array[i];
        }
        return ar_i;
    }

    public static int comparaToBinario(double[] a, double[] b) {
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

    public static double[] resta(double[] a, double[] b, int radix) {
        double[] ar = invertirArray(a);
        double[] br = invertirArray(b);

        double[] resultado = new double[ar.length];
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
    public static double[] suma(double[] a, double[] b, int radix) {
        double[] ar = invertirArray(a);
        double[] br = invertirArray(b);

        double[] resultado = new double[ar.length];
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

    public static double calcularDesviacion(List<IndividuoGen> lista, double promedio) {
        double suma = 0;
        double diferencia;
        for (IndividuoGen item : lista) {
            diferencia = item.getCalidad() - promedio;
            suma += diferencia * diferencia;
        }
        return Math.sqrt(suma / (lista.size() - 1));
    }

    public static double promedio(List<Double> lista) {
        double suma = 0;
        for (double item : lista) {
            suma += item;
        }
        return suma / lista.size();

    }

    public static double suma(List<Double> lista) {
        double suma = 0;
        for (double item : lista) {
            suma += item;
        }
        return suma;

    }
    
    public static double desviacion(List<Double> lista, double promedio) {
        double suma = 0;
        double diferencia;
        for (double item : lista) {
            diferencia = item - promedio;
            suma += diferencia * diferencia;
        }
        return Math.sqrt(suma / (lista.size() - 1));
    }

//    //pruebas
//    public static void main(String[] args) {
//        List listaNum = new ArrayList();
//        int tamanioLista = 10;
//        for (int i = 0; i < tamanioLista; i++) {
//            listaNum.add(i);
//        }
//        int intentos = 1000;
//        int rangoAleatorios = tamanioLista * 3;
//        int indice;
//        int aleatorio;
//        long tiempo_inicial = System.currentTimeMillis();
//
//        for (int i = 0; i < intentos; i++) {
//            aleatorio = Aleatorio.nextInt(rangoAleatorios);
////            indice = indiceOrdenadamente(listaNum, aleatorio, true, tamanioLista);
//            indice = indiceOrdenadamente(listaNum, aleatorio, true);
//            if (indice > 0) {
//                listaNum.add(indice, aleatorio);
//            }
//        }
//        long tiempo_final = System.currentTimeMillis();
//        System.out.println("promedio: " + ((tiempo_final - tiempo_inicial) / (double) 1));
//
//    }
    /**
     *
     * @param dir
     * @param extensiones extensiones por ejemplo "*.{c,h,cpp,hpp,java}"
     * @return
     * @throws IOException
     */
    public static List<Path> obtenerArchivosDeDirectorio(Path dir, String extensiones) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, extensiones)) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            // I/O error encounted during the iteration, the cause is an IOException
            throw ex.getCause();
        }
        return result;
    }
    
    public static boolean isDouble(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
    
    public static boolean isInteger(String str) { 
        try {  
          Integer.parseInt(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
}
