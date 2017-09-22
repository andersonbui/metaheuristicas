package tweaks;

import java.util.ArrayList;
import java.util.List;
import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class Tweak_B_HC extends Tweak {

    double paso;
    double beta = 0.5;
    double bw = 0.6;

    @Override
    public List<Punto> ejecutar(Punto punto, double radio) {

        Punto nuevop = (Punto) punto.clone();
        double[] valores = nuevop.getValores();
        // improve
        int index = rand.nextInt(valores.length);
        valores[index] = tweaki(valores[index], bw);
        // end improve
        for (int i = 0; i < valores.length; i++) {
            if (rand.nextDouble() < beta) {
                valores[i] = tweaki(0, funcion.getLimite());
            }
        }
        List lista = new ArrayList();
        lista.add(nuevop);
        return lista;
    }

}
