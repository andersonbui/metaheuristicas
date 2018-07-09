package main.mochila.cuadratica;

import java.util.ArrayList;
import java.util.List;
import main.mochila.FuncionMochila;

/**
 *
 * @author debian
 * @param <Individuo>
 */
public abstract class FuncionMochilaCuadratica<Individuo extends IndividuoCuadratico> extends FuncionMochila<Individuo> {

    protected final double[][] matrizBeneficios;
    protected final double capacidad;

    /**
     *
     * @param matrizBeneficios ganancia de cada elemento.
     * "matrizBeneficios[k][i]" es la ganancia que aporta el elemento k e i a la
     * vez
     * @param capacidad sumatoria de todos los pesos de los elementos dentro de
     * la mochila deben ser menores a la capacidad
     * @param vectorPesos lista de n pesos, donde n = |mochila|
     * @param maxGlobal
     * @param prob_ceros
     */
    public FuncionMochilaCuadratica(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal, double prob_ceros) {
        super("Cuadratica", vectorPesos.length, prob_ceros);
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        this.maxGlobal = maxGlobal == null ? Double.POSITIVE_INFINITY : maxGlobal;
    }

    /**
     *
     * @param matrizBeneficios ganancia de cada elemento.
     * "matrizBeneficios[k][i]" es la ganancia que aporta el elemento k e i a la
     * vez
     * @param capacidad
     * @param vectorPesos lista de pesos
     * @param maxGlobal
     */
    public FuncionMochilaCuadratica(double[][] matrizBeneficios, double capacidad, double[] vectorPesos, Double maxGlobal) {
        super("Cuadratica", vectorPesos.length, 1);
        this.matrizBeneficios = matrizBeneficios;
        this.capacidad = capacidad;
        this.vectorPesos = vectorPesos;
        this.maxGlobal = maxGlobal == null ? Double.POSITIVE_INFINITY : maxGlobal;
    }

    @Override
    final protected double evaluar(Individuo p) {
        return beneficio(p);
    }

    /**
     * calcula el total de beneficio que tiene que generan actualmente los
     * elemetos introducidos.
     *
     * @param mochila
     * @return
     */
    public double beneficio(Individuo mochila) {
        double totalBeneficio = 0;
        int dim = mochila.getDimension();
        for (int i = 0; i < dim; i++) {
            for (int j = i; j < dim; j++) {
                totalBeneficio += mochila.get(i) * mochila.get(j) * matrizBeneficios[i][j];
            }
        }
        return totalBeneficio;
    }

    /**
     * calcula el total de beneficio que tiene el elemento con indice (a veces
     * referenciado como "benefico crudo").
     *
     * @param indice indice de la posicion del elemento a calcular beneficio
     * @return
     */
    public double beneficio(int indice) {
        double suma = 0;

        for (int i = 0; i < vectorPesos.length; i++) {
            if (i < indice) {
                suma += matrizBeneficios[i][indice];
            } else if (i > indice) {
                suma += matrizBeneficios[indice][i];
            }
        }
        return suma + matrizBeneficios[indice][indice];
    }

    /**
     * cuantas relaciones tiene el elemento correspodiente a indice con los
     * demas elementos, es decir, cuantos beneficios en pareja son iguales a
     * cero.
     *
     * @param indice
     * @return
     */
    public double relaciones(int indice) {
        int suma = 0;
        for (int i = 0; i < vectorPesos.length; i++) {
            if (matrizBeneficios[i][indice] != 0 && i < indice) {
                suma += 1;
            } else if (matrizBeneficios[indice][i] != 0 && i > indice) {
                suma += 1;
            }
        }
        suma += matrizBeneficios[indice][indice] != 0 ? 1 : 0;
        return suma;
    }

    /**
     * Beneficio total que trae el elemento ubicado en indice dentro de mochila
     *
     * @param indice
     * @param mochila
     * @return
     */
    public double contribucion(int indice, Individuo mochila) {
        double suma = 0;
//        List<Integer> listaElemSelec = mochila.elementosSeleccionadosSinFiltro();
//        for (int i : listaElemSelec) {
//            if (i < indice) {
//                suma += matrizBeneficios[i][indice];
//            } else if (i > indice) {
//                suma += matrizBeneficios[indice][i];
//            }
//        }
        for (int i = 0; i < vectorPesos.length; i++) {
            if (mochila.get(i) != 0) {
                if (i < indice) {
                    suma += matrizBeneficios[i][indice];
                } else if (i > indice) {
                    suma += matrizBeneficios[indice][i];
                }
            }
        }

        return suma + matrizBeneficios[indice][indice];
    }

    public double contribucion(int indice, Individuo mochila, Integer sin_k) {
        double suma = 0;
//        List<Integer> listaElemSelec = mochila.elementosSeleccionadosSinFiltro();
//        listaElemSelec.remove(sin_k);
//        for (int i : listaElemSelec) {
//            if (i < indice) {
//                suma += matrizBeneficios[i][indice];
//            } else if (i > indice) {
//                suma += matrizBeneficios[indice][i];
//            }
//        }
        for (int i = 0; i < vectorPesos.length; i++) {
            if (mochila.get(i) != 0 && i != sin_k) {
                if (i < indice) {
                    suma += matrizBeneficios[i][indice];
                } else if (i > indice) {
                    suma += matrizBeneficios[indice][i];
                }
            }
        }

        return suma + matrizBeneficios[indice][indice];
    }

    @Override
    public String toString(Individuo individuo) {
        String cadena = "";
        cadena += individuo.pesar() + ";";
        return "calidad:" + individuo.getCalidad() + "; pesos:" + cadena + "; maxGlobal:" + maxGlobal;
    }

    /**
     * Calcula la densidad de beneficio de adicionar el elemento de la posicion
     * indice en la mochila, sobre el peso del elemento
     *
     * @param indice indice del objeto a adicionar
     * @param mochila
     * @return beneficio-elemento/peso-elemento
     */
    public double densidad(int indice, Individuo mochila) {
        return contribucion(indice, mochila) / vectorPesos[indice];
    }

    /**
     * Verifica si el elemento en la posicion indice cabe en la mochila. true si
     * cabe, false si no.
     *
     * @param mochila
     * @param indice
     * @return
     */
    public boolean cabe(Individuo mochila, int indice) {
        return (capacidad - mochila.pesar() - vectorPesos[indice]) >= 0;
    }

    /**
     * obtiene un subconjunto de listaIndices, tal que cada indice cabe en
     * mochila sin cobrepasar su capacidad.
     *
     * @param listaIndices
     * @param mochila
     * @return
     */
    public List<Integer> filtrarPorFactibles(List<Integer> listaIndices, Individuo mochila) {
        List<Integer> nuevaLista = new ArrayList(listaIndices);
        nuevaLista.removeIf((Integer t) -> {
            return !cabe(mochila, t);
        });
        return nuevaLista;
    }

    public double getCapacidad() {
        return capacidad;
    }

    /**
     * obtiene el beneficio que en pareja realizan los elementos con indices i y
     * j.
     *
     *
     * @param i idice del primer elemento
     * @param j idice del segundo elemento
     * @return beneficio en pareja de los dos elementos i y j
     */
    public double beneficio(int i, int j) {
        return matrizBeneficios[i][j];
    }

    @Override
    public Individuo generarIndividuo() {
        Individuo nuevop = (Individuo) new IndividuoCuadratico(this);
        return nuevop;
    }

}
