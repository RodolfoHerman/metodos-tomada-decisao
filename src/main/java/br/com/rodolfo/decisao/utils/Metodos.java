package br.com.rodolfo.decisao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.rodolfo.decisao.models.enums.Tipo;

/**
 * Metodos
 */
public class Metodos {

    private static final Pattern NUMERO = Pattern.compile("^(-?0|-?[1-9]\\d*)(\\.\\d+)?(E\\d+)?$");

    private Metodos() {}

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