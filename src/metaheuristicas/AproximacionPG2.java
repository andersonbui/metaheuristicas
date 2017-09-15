package metaheuristicas;

/**
 *
 * @author debian
 */
public class AproximacionPG2 extends Hill_Climbing {

    private final double prob_ceros = 0.8;

    /**
     * suiendo la colina (simple)
     *
     * @param nombre
     * @param paso
     */
    public AproximacionPG2(String nombre, double paso) {
        super(paso, 3, false); //Hill_Climbing
//        super(paso); //B_Hill_Climbing
        super.nombre = nombre;
        this.paso = paso;
    }


//    @Override
//    public Punto tweak(Punto punto, double paso) {
//
//        Punto nuevop = (Punto) punto.clone();
//        double[] valores = nuevop.getValores();
//        // improve
//        int index = rand.nextInt(valores.length);
//        valores[index] = tweaki(valores[index], getPaso());
//        // end improve
//        for (int i = 0; i < valores.length; i++) {
//            if (rand.nextDouble() < 0.4) {
//                valores[i] = tweaki(0, funcion.getLimite());
//            }
//        }
//        return nuevop;
//    }
    
    @Override
    public Punto generarPunto() {
        Punto nuevop = super.generarPunto();
        double[] valores = nuevop.getValores();
        for (int i = 0; i < valores.length; i++) {
            if (rand.nextDouble() < prob_ceros) {
                valores[i] = 0;
            }
        }
        return nuevop;
    }

    public double getPaso() {
        paso /= 1.00002;
        return paso;
    }

}
