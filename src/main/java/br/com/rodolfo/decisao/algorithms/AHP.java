package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.Item;
import br.com.rodolfo.decisao.models.Preferencia;
import br.com.rodolfo.decisao.utils.Metodos;

/**
 * AHP2
 */
public class AHP<T> extends Algoritmos<T> {


    public AHP(List<T> objetos, List<Criterio> criterios, Preferencia preferencia) {

        super(objetos, criterios, preferencia);
    }

    protected double[][] calcularMatriz() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        double[][] matriz = new double[objetos.size()][posicaoAtributos.size()];
        Map<String,double[]> valores = new HashMap<>();

        for (Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {
            
            List<Double> temp   = new ArrayList<>();
            Method criarMetodo1 = this.classe.getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});

            for(T objeto : objetos) {

                Item item = (Item) criarMetodo1.invoke(objeto, new Object[] {});
                temp.add(Metodos.recuperarValor(item.getValor()));
            }

            Method criarMetodo2 = this.preferencia.getClass().getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});

            valores.put(entry.getKey(), calcularMediaMatriz(
                matrizPreferencia(temp, Metodos.preferenciaParaBoolean((String) criarMetodo2.invoke(preferencia, new Object[] {})))
            ));
        }

        for (Map.Entry<String,double[]> entry : valores.entrySet()) {

            double[] valor = entry.getValue();
            int posicao =  posicaoAtributos.get(entry.getKey());

            for(int x = 0; x < valor.length; x++) {

                matriz[x][posicao] = valor[x];
            }
        }

        return matriz;
    }

    private double[][] matrizPreferencia(List<Double> valores, boolean preferencia) {

        double[][] matriz = criarMatrizNeutra(valores.size());
        double media      = calcularMedia(valores);

        for(int x = 0; x < valores.size(); x++) {

            double temp1 = valores.get(x);

            for(int y = 0; y < valores.size(); y++) {

                double temp2 = valores.get(y);

                if(x != y) {
                    
                    double criterio = valorPadronizado(temp1, temp2, media);

                    if(preferencia) {

                        matriz[x][y] = (temp1 > temp2) ? criterio : 1.0/criterio;
                        matriz[y][x] = (temp1 > temp2) ? 1.0/criterio : criterio;

                    } else {

                        matriz[x][y] = (temp1 < temp2) ? criterio : 1.0/criterio;
                        matriz[y][x] = (temp1 < temp2) ? 1.0/criterio : criterio;
                    }
                }
            }
        }

        return matriz;
    }

    private double valorPadronizado(double valor1, double valor2, double media) {

        double padrao = Math.abs((valor1 - valor2)/media);

        if(padrao <= 0.4)
            return 3.0;
        
        if(padrao <= 0.8)
            return 5.0;

        if(padrao <= 1.1)
            return 7.0;

        return 9.0;
    }

    private double calcularMedia(List<Double> valores) {
        
        return (valores.stream().reduce(0.0, Double::sum))/valores.size();
    }
    
}