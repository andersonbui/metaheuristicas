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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debian
 */
public class Prueba {

    public static void main(String[] args) throws IOException {
        String nombreArchivoDatos = "temp/EstrategiaGenetica-(ESFERA)";
        String titulo = "titulo";
        String funcion = "x*x+y*y";
        FileWriter outF = null;
        try {
            File file = new File(nombreArchivoDatos + ".gp");
            outF = new FileWriter(file);
//            outF = new FileWriter("auxfile.gp");
            PrintWriter out = new PrintWriter(outF);
            //        out.println("set terminal gif");
//        out.println("set output \"" + filename + ".gif\"");
//        out.print("set title " + "\"" + filename + "\"" + "\n");
//        out.print("set xlabel " + "\"Time\"" + "\n");
//        out.print("set ylabel " + "\"UA\"" + "\n");
            double limite = 50;

            out.println("set isosample 50");
//            out.println("set hidden3d");
//            out.println("set contour base");
//            out.println("set pm3d");
            out.println("set style line 100 lc rgb \"gray20\" lw 0.5");
//            out.println("set pm3d hidden3d 100");
            out.println("set view 58,228");
//            out.println("set zrange [-"+20+":"+20+"]");
//            out.println("set palette rgbformulae 22,13,-31");
            out.println("set title \"" + titulo + "\"");
            out.println("f(x,y) = " + funcion);
            out.println("set xrange [-" + limite + ":" + limite + "]");
            out.println("set yrange [-" + limite + ":" + limite + "]");
            out.println("splot f(x,y) w l lw 0.5, \"" + nombreArchivoDatos + ".dat\" title \"" + 2 + "\" w lp linewidth 2 linetype 6");
            out.println("pause 1");
            out.println("set isosample 10");
            out.println("replot");
            for (int i = 1; i < 50; i++) {

                out.println("set xrange [-" + (limite - i) + ":" + (limite - i) + "]");
                out.println("set yrange [-" + (limite - i) + ":" + (limite - i) + "]");
                out.println("pause 0.0005");
                out.println("replot");
            }

//            out.println("splot [-" + limite + ":" + limite + "] [-" + limite + ":" + limite
//                    + "] f(x,y) w l , \"" + nombreArchivoDatos + ".dat\" title \""+2+"\" w l linewidth 2 linetype 6");
//        out.println("plot \""+filename+"\" with linespoints,\"new\" with linespoints");
            out.close();// It's done, closing document.
            Runtime commandPrompt = Runtime.getRuntime();
            commandPrompt.exec("gnuplot -persist " + nombreArchivoDatos + ".gp");
            Runtime.getRuntime().exec("gnuplot -persist " + nombreArchivoDatos + ".gp");
//            file.delete();
        } catch (IOException ex) {
            Logger.getLogger(GraficoGnuPlot.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outF.close();
            } catch (IOException ex) {
                Logger.getLogger(GraficoGnuPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void listaOrdenada() {
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
