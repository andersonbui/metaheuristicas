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
//        super(paso, 1, false); //Hill_Climbing_
        super(paso); //B_Hill_Climbing
        super.nombre = nombre;
        this.paso = paso;
    }


    @Override
    public Punto tweak(Punto punto, double paso) {

        Punto nuevop = (Punto) punto.clone();
        double[] valores = nuevop.getValores();
        // improve
        int index = rand.nextInt(valores.length);
        valores[index] = tweaki(valores[index], getPaso());
        
        return nuevop;
    }
    
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
        return paso;
    }

}
