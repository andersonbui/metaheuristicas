package main.mochila.multidimensional.funciones;

import metaheuristicas.Individuo;
import metaheuristicas.Funcion;
import java.util.List;
import metaheuristicas.Aleatorio;

/**
 *
 * @author debian
 */
public class MochilaMultidimensional extends Funcion {

    protected Funcion funcion;
    protected int contFuncion;
    protected Funcion[] funciones;

    /**
     *
     * @param capacidades
     * @param w
     * @param maximizar
     */
    public MochilaMultidimensional(double[] capacidades, List<double[]> w, boolean maximizar) {
        super("MOCHILA MultiD", 2, w.size(), maximizar);
        contFuncion = 0;
        funciones = new Funcion[]{
//            new MochilaMultidimensionalOriginal(capacidades, w, maximizar),
            new MochilaMultidimensionalMejorada(capacidades, w, maximizar),
            new MochilaMultidimensionalMejorada2(capacidades, w, maximizar),
            new MochilaMultidimensionalMejorada3(capacidades, w, maximizar)
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
    public Individuo generarPunto() {
        return funcion.generarPunto();
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
    public void setLimite(double limite) {
        funcion.setLimite(limite);
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
    public double limitar(double valor) {
        return funcion.limitar(valor);
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
