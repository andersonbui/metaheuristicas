/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicas;

import java.security.InvalidAlgorithmParameterException;

/**
 *
 * @author debian
 */
public abstract class Funcion2D extends Funcion {

    /**
     *
     * @param nombre
     * @param limite
     * @param dimension
     * @param maximizar
     * @throws java.security.InvalidAlgorithmParameterException
     */
    public Funcion2D(String nombre, double limite, int dimension, boolean maximizar) throws InvalidAlgorithmParameterException {
        super(nombre, limite, dimension, maximizar);
        if (dimension != 2) {
            throw new InvalidAlgorithmParameterException("Solo para dos dimensiones");
        }
    }

}
