package tweaks;

import java.util.ArrayList;
import java.util.List;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Tweak_Explotacion extends AlgoritmoMetaheuristico {

    double ancho;

    public Tweak_Explotacion(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public List<Punto> ejecutar(Punto punto) {

        Punto nuevop = (Punto) punto.clone();
        double[] valores = nuevop.getValores();
        // improve
        int index = rand.nextInt(valores.length);
        valores[index] = tweaki(valores[index], ancho);
        // end improve
//        for (int i = 0; i < valores.length; i++) {
//            if (rand.nextDouble() < 0.1) {
//                valores[i] = tweaki(0, funcion.getLimite());
//            }
//        }
        List lista = new ArrayList();
        lista.add(nuevop);
        return lista;
    }

}
