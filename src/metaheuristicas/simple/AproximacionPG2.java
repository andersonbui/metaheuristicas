package metaheuristicas.simple;

import tweaks.Tweak;

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
    public AproximacionPG2(Tweak tweak) {
//        super(paso, 1, false); //Hill_Climbing_
        super(); //B_Hill_Climbing
        super.nombre = "Aproximacion PG2";
        this.tweak = tweak;
    }
}
