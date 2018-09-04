package gnuplot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.utilidades.EscribirArchivo;

/**
 *
 * @author debian
 */
public class GraficoGnuPlot {

    private double xrange;
    private double yrange;
    private String titulo;
    private String carpetaTemporal;
    List<CD> listaDeCD;
    List<String> listaFuncionesAritmeticas;
    List<String> parametros;
    String parametrosFunciones = " w l lw 0.1 ";
    String parametrosCD;
    String comandoGraficacion;
    String tipoGrafica;

    public GraficoGnuPlot() {
        carpetaTemporal = "temp/";
        listaDeCD = new ArrayList();
        parametros = new ArrayList();
        listaFuncionesAritmeticas = new ArrayList();
        titulo = "";
        parametrosCD = "";
        xrange = 0;
        yrange = 0;
    }

    public class CD {

        private List<Punto> cd;
        private String nombre;

        public CD(List<Punto> cd, String nombre) {
            this.cd = cd;
            this.nombre = nombre;
        }

        public List<Punto> getCd() {
            return cd;
        }

        public void setCd(List<Punto> cd) {
            this.cd = cd;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        
    }

    public void addConjuntoDatos(List<Punto> cd, String nombre) {
        listaDeCD.add(new CD(cd, nombre));
    }

    public void addFuncionAritmetica(String funcionAritmetica) {
        listaFuncionesAritmeticas.add(funcionAritmetica);
    }

    public void limpiar() {
        listaDeCD.clear();
        listaFuncionesAritmeticas.clear();
        parametros.clear();
        xrange = 0;
        yrange = 0;
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">  
    public String getTitulo() {
        return titulo;
    }

    public double getXrange() {
        return xrange;
    }

    public void setXrange(double xrange) {
        this.xrange = xrange;
        parametros.add("set xrange [-" + xrange + ":" + xrange + "]");
    }

    public double getYrange() {
        return yrange;
    }

    public void setYrange(double yrange) {
        this.yrange = yrange;
        parametros.add("set yrange [-" + yrange + ":" + yrange + "]");
    }

    public String getCarpetaTemporal() {
        return carpetaTemporal;
    }

    public void setCarpetaTemporal(String carpetaTemporal) {
        this.carpetaTemporal = carpetaTemporal;
    }
    //</editor-fold> 

    public void parametrosGenerales() {

        parametros.add("set title \"" + titulo + "\"");

        parametros.add("set isosample 50");
//        parametros.add("set style line 100 lc rgb \"gray20\" lw 0.5");
//        out.println("set terminal gif");
//        out.println("set output \"" + filename + ".gif\"");
        parametros.add("set xlabel " + "\"x\"" + "\n");
        parametros.add("set ylabel " + "\"y\"" + "\n");
        parametros.add("set zlabel " + "\"aptitud\"" + "\n");
//            parametros.add("set hidden3d");
//            parametros.add("set contour base");
//            parametros.add("set pm3d");
//            parametros.add("set pm3d hidden3d 100");
        parametros.add("set view 58,228");
//            parametros.add("set zrange [-"+20+":"+20+"]");
//            parametros.add("set palette rgbformulae 22,13,-31");
    }

    public void plot3D(String titulo) {
        this.titulo = titulo;
        comandoGraficacion = "splot";
        parametrosCD = " title \"Recorrido\" w lp linewidth 2 linetype 6 ";
        tipoGrafica = "3D";
        plot(titulo);
    }

    private String agregarFunciones() {
        //funciones
        String funciones = "";
        String nomFuncion;
        int numfunciones = listaFuncionesAritmeticas.size();

        for (int i = 0; i < numfunciones; i++) {
            // nombre de la función aritmética
            nomFuncion = (char) (97 + i) + "(x,y)";
            // nombres de todas las funciones, para referenciar las que serán graficadas
            funciones += nomFuncion + parametrosFunciones;
            // declaracion de cada función aritmética
            String funcion = nomFuncion + " = " + listaFuncionesAritmeticas.get(i);
            // declaracion de la función aritmetica como parametro a gnuplot
            parametros.add(funcion);
            // separar los nombres de funciones por comas, sólo si hay más de una función
            if (i < numfunciones - 1) {
                funciones += ", ";
            }
        }
        return funciones;
    }

    private String agregarConjuntoDatos(String titulo) {

        String nombreArchivo;
        String cds = listaFuncionesAritmeticas.isEmpty() ? "" : ",";
        int numCds = listaDeCD.size();
        EscribirArchivo archivo = new EscribirArchivo();
        // creacion archivos recorrido
        for (int i = 0; i < numCds; i++) {
            List<Punto> cd = listaDeCD.get(i).getCd();
            nombreArchivo = carpetaTemporal + i + "-" + tipoGrafica + "-" + titulo + ".dat";
            cds += " \"" + nombreArchivo + "\" " +  " title \""+listaDeCD.get(i).getNombre()+"\" w l linewidth 2 ";
            archivo.abrir(nombreArchivo);
            archivo.escribir(cd);
            archivo.terminar();
            // separar los nombres de funciones por comas, sólo si hay más de una función
            if (i < numCds - 1) {
                cds += ", ";
            }
        }
        return cds;
    }

    private void plot(String titulo) {
        titulo = titulo.replace('\u0020', '_');
        parametrosGenerales();
        String funciones = agregarFunciones();
        String cds = agregarConjuntoDatos(titulo);
        String nombreScript = carpetaTemporal + tipoGrafica + "-" + titulo + ".gp";

        File fileDir = new File(carpetaTemporal);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        parametros.add(comandoGraficacion + " " + funciones + cds);

        EscribirArchivo archivo = new EscribirArchivo();
        archivo.abrir(nombreScript);
        archivo.escribir(parametros);
        archivo.terminar();

        try {
//            out.println("splot f(x,y) w l , \"" + nombreArchivoDatos + ".dat\" title \""+2+"\" w l linewidth 2 linetype 6");
//        out.println("plot \""+filename+"\" with linespoints,\"new\" with linespoints");
            Runtime.getRuntime().exec("gnuplot -persist " + nombreScript);

//            Runtime commandPrompt = Runtime.getRuntime();
//            commandPrompt.exec("gnuplot auxfile.gp");
            //commandPrompt.waitFor();
            //            file.delete();
        } catch (IOException ex) {
            Logger.getLogger(GraficoGnuPlot.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    public void plot2D(String titulo) {
        this.titulo = titulo;
        comandoGraficacion = "plot";
//        parametrosCD = " title \"Recorrido\" w l linewidth 2 linetype 6 ";
        tipoGrafica = "2D";
        plot(titulo);
    }

}
