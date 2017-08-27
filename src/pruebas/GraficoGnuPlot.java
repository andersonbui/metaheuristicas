/*
 * https://stackoverflow.com/questions/19419128/using-gnuplot-with-java
 */
package pruebas;

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

    public static void plot(int dimension, String titulo, Funcion funcion) {

        FileWriter outF = null;
        try {
            File file = new File(titulo + ".gp");
            outF = new FileWriter(file);
//            outF = new FileWriter("auxfile.gp");
            PrintWriter out = new PrintWriter(outF);
            //        out.println("set terminal gif");
//        out.println("set output \"" + filename + ".gif\"");
//        out.print("set title " + "\"" + filename + "\"" + "\n");
//        out.print("set xlabel " + "\"Time\"" + "\n");
//        out.print("set ylabel " + "\"UA\"" + "\n");
            out.println("set isosample 40");
//            out.println("set pm3d");
            out.println("set title \"" + titulo + "\"");
            out.println("f(x,y) = " + funcion);
            double limite = funcion.getLimite();
//            out.println("splot [-10:10] [-10:10] f(x,y) w dots , \"" + titulo + ".dat\" title \"A\" w lp linewidth 1 linetype 6");
            out.println("splot [-"+limite+":"+limite+"] [-"+limite+":"+limite+"] f(x,y) w l , \"" + titulo + ".dat\" title \"A\" w lp linewidth 1 linetype 6");
//        out.println("plot \""+filename+"\" with linespoints,\"new\" with linespoints");
            out.close();// It's done, closing document.
            Runtime.getRuntime().exec("gnuplot -persist " + titulo + ".gp");
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
}
