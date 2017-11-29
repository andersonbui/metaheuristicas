package funciones;

import metaheuristicas.Punto;

/**
 *
 * @author debian
 */
public class GoldsteinAndPrice extends Funcion {

    public GoldsteinAndPrice(double limite, int dimension, boolean maximizar) {
        super("GOLDSTEINANDPRICE", limite, dimension, maximizar);
    }

    @Override
     public double evaluar(Punto punto) {
        double suma = 0;
        double[] valores = punto.getValores();
        for (int i = 0; i < valores.length-1; i++) {
            //suma += Math.pow((Math.pow(valores[i],2) - valores[i+1] + 1),2) + Math.pow((1-valores[i]),2);
            double x1=valores[i],x2=valores[i+1];
            suma = 1 + (Math.pow((x1 + x2 + 1),2) * (19-14*x1+3*Math.pow(x1,2)-14*x2+6*x1*x2+3*Math.pow(x2,2))) * (30*Math.pow((2*x1-3*x2),2)*(18-32*x1+12*Math.pow(x1,2)+48*x2-36*x1*x2+27*x2*x2));
        }
        return suma;
    }

    @Override
    public String toString() {
        return "(1+(x+y+1)^2·(19-14·x+3·x^2-14·y+6·x·y+3·y^2))·(30+(2·x-3·y)^2·(18-32·x+12·x^2+48·y-36·x·y+27·y^2))";
    }

}
