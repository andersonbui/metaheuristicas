package metaheuristicas.movimiento;

import metaheuristicas.funcion.FuncionGen;
import metaheuristicas.Aleatorio;
import metaheuristicas.IndividuoGen;

/**
 *
 * @author debian
 */
public class Tweak_Explotacion extends Tweak {

    double ancho;

    public Tweak_Explotacion(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public IndividuoGen tweak(IndividuoGen punto) {
        IndividuoGen nuevo = (IndividuoGen) punto.clone();
        // improve
        int posicion = Aleatorio.nextInt(nuevo.getDimension());
        nuevo.set(posicion, genValor(nuevo.get(posicion), ancho));
        nuevo.evaluar();
        return nuevo;
    }
}
