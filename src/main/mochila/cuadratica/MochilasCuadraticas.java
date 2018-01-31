package main.mochila.cuadratica;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;
import main.mochila.cuadratica.anson.FuncionMochilaCuadraticaGreedy;

/**
 *
 * @author debian
 */
public class MochilasCuadraticas extends Funcion {

    protected Funcion funcion;
    protected int contFuncion;
    protected Funcion[] funciones;

    /**
     *
     */
    public MochilasCuadraticas(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super("MOCHILA MultiD", vectorPesos.length, true);
        contFuncion = 0;
        funciones = new Funcion[]{
            //            new MochilaMultidimensionalOriginal(capacidades, w, maximizar),
            new FuncionMochilaCuadraticaGreedy(matrizBeneficios, capacidad, vectorPesos, maxGlobal), //            new MochilaMultidimensionalMejorada2(capacidades, w, maximizar),
        //            new MochilaMultidimensionalMejorada3(capacidades, w, maximizar)
        };
        funcion = funciones[contFuncion];
    }

    @Override
    public void siguiente() {
        funcion = funciones[++contFuncion];
    }

    @Override
    public boolean haySiguiente() {
        return contFuncion + 1 < funciones.length;
    }

    @Override
    public void reiniciar() {
        super.reiniciar();
        contFuncion = 0;
        funcion = funciones[contFuncion];
    }

    @Override
    public boolean suficiente(Individuo punto) {
        return funcion.suficiente(punto);
    }

    @Override
    public Individuo generarIndividuo() {
        return funcion.generarIndividuo();
    }

    @Override
    public double evaluar(Individuo mochila) {
        return funcion.evaluar(mochila);
    }

    @Override
    public int getContadorEvaluaciones() {
        return funcion.getContadorEvaluaciones();
    }

    @Override
    public void reiniciarContadorEvaluaciones() {
        funcion.reiniciarContadorEvaluaciones();
    }

    @Override
    public double getLimite() {
        return funcion.getLimite();
    }

    @Override
    public int getDimension() {
        return funcion.getDimension();
    }

    @Override
    public void setDimension(int dimension) {
        funcion.setDimension(dimension);
    }

    @Override
    public String getNombre() {
        return funcion.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        funcion.setNombre(nombre);
    }

    @Override
    public boolean isMaximizar() {
        return funcion.isMaximizar();
    }

    @Override
    public void setMaximizar(boolean maximizar) {
        funcion.setMaximizar(maximizar);
    }

    @Override
    public String toString() {
        return funcion.toString();
    }

}
