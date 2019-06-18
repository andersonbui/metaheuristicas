/*
 * Copyright (C) 2019 debian
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import main.utilidades.LeerArchivo;
import main.utilidades.Utilidades;

/**
 *
 * @author debian
 */
public class ActualizarMejoresResultados {

    public static void main(String[] args) throws IOException {
//        String directorioInstancias = "mochilaCuadratica/grupo1/";
//        String directorioResultados = "mochilaCuadratica/QKPResults/group1/";
        String directorioInstancias = "mochilaCuadratica/grupo1000/";
        String directorioResultados = "mochilaCuadratica/QKPResults/group2/";
//        String directorioInstancias = "mochilaCuadratica/grupo2000/";
//        String directorioResultados = "mochilaCuadratica/QKPResults/group2/";
        String extensionRes = ".sol";
        String nombreInstancia;
        String nombreSinExtension;

        File directorio = new File(directorioInstancias);
        List<Path> listaArchivos = Utilidades.obtenerArchivosDeDirectorio(directorio.toPath(), "*.txt");
        for (Path archivo : listaArchivos) {
            nombreInstancia = archivo.toString();
            //quitar directorio raiz
            nombreInstancia = nombreInstancia.replaceAll(directorioInstancias, "");
            //quitar extension
            nombreSinExtension = nombreInstancia.split("\\.")[0];
            MejorIntancia mi = obtenerMejor(directorioResultados + nombreSinExtension + extensionRes);
            if (mi == null) {
                System.out.println("no hay resultado ideal para " + nombreSinExtension);
                continue;
            }
            LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
            ParametrosCuadratica parametros = lpc.obtenerParametros(directorioInstancias + nombreInstancia);

            System.out.println("en" + nombreSinExtension + ".sol " + mi.calidad + " => instancia: " + parametros.getMaxGlobal());
            if (parametros.getMaxGlobal().isNaN() || parametros.getMaxGlobal().compareTo(mi.calidad) < 0) {
                parametros.setMaxGlobal(mi.calidad);
                parametros.setVectorIdeal(mi.valores);

                try {
                    lpc.actualizar(parametros.getNombreArchivo(), parametros);
                } catch (Exception e) {
                    System.out.println("---fallo la actualizacion del maximo global:\n " + e.getLocalizedMessage());
                    System.out.println("---mensaje de error: \n " + e.getMessage());
                }
            } else if (parametros.getMaxGlobal().compareTo(mi.calidad) > 0) {
                System.out.println("se encontro mejor que mejor resultado");
            }

        }
    }

    /**
     *
     * @param nombre nombre del archivo resultados sin extension
     * @return
     */
    public static MejorIntancia obtenerMejor(String nombre) {
        LeerArchivo leer = new LeerArchivo();
        boolean sepudo = leer.abrir(nombre);
        if (!sepudo) {
            System.out.println("No se pudoleeer: " + nombre);
            return null;
        }
        List<String> lineas = leer.leer();
        leer.terminar();
        double calidad = Double.parseDouble(lineas.get(0).split(":")[1]);
        String[] individuo = lineas.get(1).split(" ");
        int[] valores = new int[individuo.length];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = Integer.parseInt(individuo[i]);
        }
        return new MejorIntancia(valores, calidad, nombre);
    }

    public static class MejorIntancia {

        int[] valores;
        double calidad;
        String nombre;

        public MejorIntancia(int[] valores, double calidad, String nombre) {
            this.valores = valores;
            this.calidad = calidad;
            this.nombre = nombre;
        }

    }
}
