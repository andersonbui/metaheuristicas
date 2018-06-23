package main.mochila.multidimensional.funciones;

import metaheuristicas.IndividuoGen;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */
public class MochilasMultidimensionales extends FuncionGen {

    protected FuncionGen funcion;
    protected int contFuncion;
    protected FuncionGen[] funciones;

    /**
     *
     * @param VectorRestricciones
     * @param beneficios
     * @param capacidades
     */
    public MochilasMultidimensionales(double[][] VectorRestricciones, double[] beneficios, double[] capacidades) {
        super("MOCHILA MultiD", beneficios.length, true);
        contFuncion = 0;
        funciones = new FuncionGen[]{
            //            new MochilaMultidimensionalOriginal(VectorRestricciones, beneficios, capacidades),
            new MochilaMultidimensional_LimitRellenoM(VectorRestricciones, beneficios, capacidades),
            new MochilaMultidOrdenXDensidadBeneficioM2(VectorRestricciones, beneficios, capacidades),
            new MochilaMultidOrdenAvanzadoM3(VectorRestricciones, beneficios, capacidades)
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
    public boolean suficiente(IndividuoGen punto) {
        return funcion.suficiente(punto);
    }

    @Override
    public IndividuoGen generarIndividuo() {
        return funcion.generarIndividuo();
    }

    @Override
    protected double evaluar(IndividuoGen mochila) {
        if (funcion.getClass() == MochilaMultidimensional_LimitRellenoM.class) {
            System.out.print("");
        }
        return funcion.evaluacion(mochila);
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

    @Override
    public String toString(IndividuoGen individuo) {
        return funcion.toString(individuo);
    }

}
