package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.Preferencia;

/**
 * PETR
 */
public class PETR<T> extends Algoritmos<T> {

    public PETR(List<T> objetos, List<Criterio> criterios, Preferencia preferencia) {
        
        super(objetos, criterios, preferencia);
    }

    @Override
    protected double[][] calcularMatriz() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        return null;
    }

    
}