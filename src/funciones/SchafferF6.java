package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class SchafferF6 extends Funcion {

    public SchafferF6(double limite, int dimension, boolean maximizar) {
        super("SCHAFFERF6", limite, dimension, maximizar);
    }

    @Override
     public double evaluar(Punto punto) {
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length-1; i++) {
            double x1=valores[i],x2=valores[i+1];
            suma = -0.5-(Math.pow(Math.sin(Math.sqrt((x1*x1)+(x2*x2))),2)-0.5)/(Math.pow((1+0.001*(x1*x1+x2*x2)),2));
        }
        return suma;
    }

    @Override
    public String toString() {
        return "-0.5-((sin(sqrt(x**2 + y**2)))**2-0.5)/(((1+0.001*(x**2 + y**2)))**2)";
    }

}
