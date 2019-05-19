package br.com.rodolfo.decisao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.enums.Tipo;

/**
 * Metodos
 */
public class Metodos {

    private static final Pattern NUMERO = Pattern.compile("^(-?0|-?[1-9]\\d*)(\\.\\d+)?(E\\d+)?$");

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

    public static double[] criarVetorCriterios(List<Criterio> criterios, Map<String,Integer> mapaCriterio) {

        double[][] matrizCriterios = Metodos.criarMatrizNeutra(mapaCriterio.size());

        for(Criterio criterio : criterios) {

            int indice1 = mapaCriterio.get(criterio.getPreferencia1());
            int indice2 = mapaCriterio.get(criterio.getPreferencia2());

            if(criterio.getCriterio().equals(">")) {

                matrizCriterios[indice1][indice2] = criterio.getNivel();
                matrizCriterios[indice2][indice1] = 1.0/criterio.getNivel();

            } else {

                matrizCriterios[indice1][indice2] = 1.0/criterio.getNivel();
                matrizCriterios[indice2][indice1] = criterio.getNivel();
            }
        }

        return Metodos.calcularMediaMatriz(matrizCriterios);
    }

    public static double[] multiplicarVetorComMatriz(double[] vetor, double[][] matriz) {

        List<Double> resultado = new ArrayList<>();

        for(int x = 0; x < matriz.length; x++) {
            
            double temp = 0.0;

            for(int y = 0; y < matriz[0].length; y++) {

                temp += matriz[x][y] * vetor[y];
            }

            resultado.add(temp);
        }

        return resultado.stream().mapToDouble(Double::doubleValue).toArray();
    }
    
    public static boolean preferenciaParaBoolean(String preferencia) {
        
        return preferencia == null || preferencia.equals("") ? true : preferencia.equals(">") ? true : false;
    }

    public static boolean verificarArquivos(List<String> arquivos, String[] lista) {
        
        int qtd = arquivos.size();

        for(String arq : lista) {

            if(arquivos.contains(arq)) {

                qtd--;
            }
        }

        return qtd == 0 ? true : false;
    }

    public static String retornarNomeMetodo(String criterio) {
        
        return "get".concat("" + criterio.toUpperCase().charAt(0)).concat(criterio.substring(1)); 
    }

    public static boolean isNumero(String numero) {
        return numero != null && NUMERO.matcher(numero).matches();
    }

    public static void imprimirVetor(double[] vetor) {
        
        for(double valor : vetor) {

            System.out.print(valor + "\t");
        }

        System.out.println();
    }

    public static void imprimirMatriz(double[][] matriz) {
        
        for(int x = 0; x < matriz.length; x++) {
            for(int y = 0; y < matriz[0].length; y++) {
                System.out.print(matriz[x][y] + "\t\t\t");
            }
            System.out.println();
        }
    }

    public static String formatarPalavra(String palavra) {
        
        int tamanho  = palavra.length();
        int desejado = 6;
        
        if(tamanho >= desejado) {

            palavra = palavra.substring(0, desejado);

        } else {

            int dif = desejado - tamanho;

            for(int x = 0; x < dif; x++) {

                palavra = palavra.concat(" ");
            }
        }

        return palavra;
    }

    public static double recuperarValor(String valor) {

        try {
            
            return Metodos.isNumero(valor) ? Double.valueOf(valor) : Tipo.toEnum(valor).getCriterio();

        } catch (Exception e) {
            
            return 0.0;
        }

    }
}