/*
 * https://stackoverflow.com/questions/19419128/using-gnuplot-with-java
 */
package main;

import funciones.Funcion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debian
 */
public class GraficoGnuPlot {

    public static void plot3D(String nombreArchivoDatos, String titulo, Funcion funcion) {

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
            double limite = funcion.getLimite();

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
//            out.println("splot [-" + limite + ":" + limite + "] [-" + limite + ":" + limite
//                    + "] f(x,y) w l , \"" + nombreArchivoDatos + ".dat\" title \""+2+"\" w l linewidth 2 linetype 6");
//        out.println("plot \""+filename+"\" with linespoints,\"new\" with linespoints");
            out.close();// It's done, closing document.
            Runtime.getRuntime().exec("gnuplot -persist " + nombreArchivoDatos + ".gp");

//            Runtime commandPrompt = Runtime.getRuntime();
//            commandPrompt.exec("gnuplot auxfile.gp");
            //commandPrompt.waitFor();
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

    public static void plot2D(String nombreArchivoDatos, String titulo, Funcion funcion) {

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
            double limite = funcion.getLimite();

            out.println("set isosample 50");
//            out.println("set hidden3d");
//            out.println("set contour base");
//            out.println("set pm3d");
            out.println("set style line 100 lc rgb \"gray20\" lw 0.5");
//            out.println("set pm3d hidden3d 100");
//            out.println("set zrange [-"+20+":"+20+"]");
//            out.println("set palette rgbformulae 22,13,-31");
            out.println("set title \"" + titulo + "\"");
//            out.println("set xrange [-" + limite + ":" + limite + "]");
//            out.println("set yrange [-" + limite + ":" + limite + "]");
            out.println("plot \"" + nombreArchivoDatos + ".dat\" title \"" + 2 + "\" w l linewidth 2 linetype 6");
//            out.println("splot [-" + limite + ":" + limite + "] [-" + limite + ":" + limite
//                    + "] f(x,y) w l , \"" + nombreArchivoDatos + ".dat\" title \""+2+"\" w l linewidth 2 linetype 6");
//        out.println("plot \""+filename+"\" with linespoints,\"new\" with linespoints");
            out.close();// It's done, closing document.
            Runtime.getRuntime().exec("gnuplot -persist " + nombreArchivoDatos + ".gp");
//            file.delete();
        } catch (IOException ex) {
//            Logger.getLogger(GraficoGnuPlot.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outF.close();
            } catch (IOException ex) {
                Logger.getLogger(GraficoGnuPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
