package br.com.rodolfo.decisao.utils;

import java.util.List;

/**
 * Metodos
 */
public class Metodos {

    private Metodos() {}

    public static double[] calcularMediaMatriz(double[][] matriz) {

        int tamanho = matriz[0].length;
        double[] media = new double[tamanho];

        for(int x = 0; x < tamanho; x++) {

            double temp = 0.0;

            for(int y = 0; y < tamanho; y++) {

                temp += matriz[y][x];
            }

            for(int y = 0; y < tamanho; y++) {

                matriz[y][x] = matriz[y][x]/temp;
            }
            
        }

        for(int x = 0; x < tamanho; x++) {
            
            double temp = 0.0;

            for(int y = 0; y < tamanho; y++) {

                temp += matriz[x][y];
            }

            media[x] = temp/tamanho;
        }

        return media;
    }
    
    
    public static double[][] matrizPreferencia(List<Double> valores, boolean preferencia) {

        double[][] matriz = criarMatrizNeutra(valores.size());
        double media      = calcularMedia(valores);

        for(int x = 0; x < valores.size(); x++) {

            double temp1 = valores.get(x);

            for(int y = 0; y < valores.size(); y++) {

                double temp2 = valores.get(y);

                if(x != y) {
                    
                    double criterio = valorPadronizado(temp1, temp2, media);

                    if(preferencia) {

                        matriz[x][y] = (temp1 < temp2) ? criterio : 1.0/criterio;
                        matriz[y][x] = (temp1 < temp2) ? 1.0/criterio : criterio;

                    } else {

                        matriz[x][y] = (temp1 < temp2) ? 1.0/criterio : criterio;
                        matriz[y][x] = (temp1 < temp2) ? criterio : 1.0/criterio;
                    }
                }
            }
        }

        return matriz;
    }

    public static double valorPadronizado(double valor1, double valor2, double media) {

        double padrao = Math.abs((valor1 - valor2)/media);

        if(padrao <= 0.4)
            return 3.0;
        
        if(padrao <= 0.8)
            return 5.0;

        if(padrao <= 1.1)
            return 7.0;

        return 9.0;
    }

    public static double calcularMedia(List<Double> valores) {
        
        return (valores.stream().reduce(0.0, Double::sum))/valores.size();
    }

    public static double[][] criarMatrizNeutra(int tamanho) {
        
        double[][] matriz = new double[tamanho][tamanho];

        for(int x = 0; x < tamanho; x++) {
            for(int y = 0; y < tamanho; y++) {

                matriz[x][y] = 1.0;
            }
        }

        return matriz;
    }
    
    public static boolean preferenciaParaBoolean(String preferencia) {
        
        return preferencia.equals(">");
    }
}