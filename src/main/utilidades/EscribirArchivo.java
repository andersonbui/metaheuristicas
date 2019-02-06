package main.utilidades;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debian
 */
public class EscribirArchivo {

    private FileWriter fw;
    private PrintWriter pw;
    boolean pudoAbrir;

    public EscribirArchivo() {
        pudoAbrir = false;
    }

    public boolean abrir(String nombreArchivo, boolean agregar) {
        try {
            fw = new FileWriter(nombreArchivo, agregar);
            pw = new PrintWriter(fw);
            pudoAbrir = true;
        } catch (IOException ex) {
//            Logger.getLogger(EscribirArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pudoAbrir;
    }

    public boolean abrir(String nombreArchivo) {
        try {
            fw = new FileWriter(nombreArchivo, false);
            pw = new PrintWriter(fw);
            pudoAbrir = true;
        } catch (IOException ex) {
            System.out.println("No existe directorio");
            //Logger.getLogger(EscribirArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pudoAbrir;
    }

    public void escribir(String cadena) {
        pw.println(cadena);
    }

    public void escribir(List lista) {
        lista.forEach((object) -> {
            escribir(object.toString());
        });
    }

    public void terminar() {
        if (pw != null) {
            pw.close();
        }
    }
}
