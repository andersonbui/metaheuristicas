package metaheuristicas.simple;

import metaheuristicas.AlgoritmoMetaheuristico;


/**
 *
 * @author debian
 */
public class AproximacionPG2 extends Hill_Climbing {

    /**
     * suiendo la colina (simple)
     *
     * @param tweak
     */
    public AproximacionPG2(AlgoritmoMetaheuristico tweak) {
//        super(paso, 1, false); //Hill_Climbing_
        super(tweak); //B_Hill_Climbing
        super.nombre = "Aproximacion PG2";
    }
}
