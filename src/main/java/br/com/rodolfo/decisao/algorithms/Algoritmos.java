package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.InstanciaDTO;
import br.com.rodolfo.decisao.models.Preferencia;
import br.com.rodolfo.decisao.utils.Metodos;

/**
 * Algoritmos
 */
public abstract class Algoritmos<T> {

    protected List<T> objetos;
    protected List<Criterio> criterios;
    protected Preferencia preferencia;
    protected Class<?> classe;
    protected final Map<String, Integer> posicaoAtributos = new HashMap<>();

    public Algoritmos(List<T> objetos, List<Criterio> criterios, Preferencia preferencia) {

        this.objetos = objetos;
        this.criterios = criterios;
        this.preferencia = preferencia;
        this.classe = objetos.get(0).getClass();

        criarMapasAtributos();
    }

    protected abstract double[][] calcularMatriz() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    private void criarMapasAtributos() {

        int posicao = 0;

        List<String> atributos = Arrays.asList(this.classe.getDeclaredFields()).stream()
                .filter(field -> !field.getName().equals("descricao")).sorted(Comparator.comparing(Field::getName))
                .map(Field::getName).collect(Collectors.toList());

        for (String atributo : atributos) {

            this.posicaoAtributos.put(atributo, posicao++);
        }
    }

    public String executar() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        StringBuilder imprimir = new StringBuilder();
        StringBuilder temp     = new StringBuilder();
        List<InstanciaDTO> dto = new ArrayList<>();
        int posicao = 0;

        double[] vetorCriterios = criarVetorCriterios();
        double[][] preferencias = calcularMatriz();
        double[] resposta       = Metodos.multiplicarVetorComMatriz(vetorCriterios, preferencias);

        imprimir.append("*** Critérios do Usuário ***").append(System.lineSeparator()).append(System.lineSeparator());

        for (Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            temp.append(Metodos.formatarPalavra(entry.getKey())).append("\t\t");
        }

        imprimir.append(temp.toString());
        imprimir.append(System.lineSeparator());

        for (Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            imprimir.append(String.format("%.4f", vetorCriterios[entry.getValue()])).append("\t\t");
        }

        imprimir.append(System.lineSeparator()).append(System.lineSeparator())
                .append("*** Critérios Calculados ***").append(System.lineSeparator()).append(System.lineSeparator())
                .append("-------").append("\t\t")
                .append(temp.toString()).append(System.lineSeparator());

        temp.setLength(0);

        for(int x = 0; x < preferencias.length; x++) {

            temp.append(Metodos.formatarPalavra(objetos.get(posicao).toString())).append("\t\t");
            imprimir.append(Metodos.formatarPalavra(objetos.get(posicao).toString())).append("\t\t");
            dto.add(new InstanciaDTO(objetos.get(posicao).toString()));

            for (Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

                imprimir.append(String.format("%.4f", preferencias[x][entry.getValue()])).append("\t\t");
            }

            imprimir.append(System.lineSeparator());
            posicao++;
        }

        imprimir.append(System.lineSeparator())
                .append("*** Resultado ***").append(System.lineSeparator()).append(System.lineSeparator());

        imprimir.append(temp.toString()).append(System.lineSeparator());

        posicao = 0;

        for(double r : resposta) {

            dto.get(posicao++).valor = r;
            imprimir.append(String.format("%.4f", r)).append("\t\t");
        }

        imprimir.append(System.lineSeparator()).append(System.lineSeparator());

        dto.stream()
            // .sorted(Comparator.comparing(InstanciaDTO::getValor))
            .sorted(Comparator.reverseOrder())
            .findFirst()
            .ifPresent(obj -> imprimir.append("#####################################################")
                                       .append(System.lineSeparator())
                                      .append("#\tA melhor escolha é : '")
                                      .append(Metodos.formatarPalavra(obj.descricao))
                                      .append("' com valor de : '")
                                      .append(String.format("%.4f", obj.valor))
                                      .append("'")
                                      .append("\t\t#")
                                      .append(System.lineSeparator())
                                      .append("#####################################################"));
                                
        imprimir.append(System.lineSeparator()).append(System.lineSeparator()).append(System.lineSeparator())
                .append("*** Legendas ***").append(System.lineSeparator()).append(System.lineSeparator());

        dto.stream()
           .sorted(Comparator.comparing(InstanciaDTO::getDescricao))
           .forEach(obj -> imprimir.append(Metodos.formatarPalavra(obj.descricao))
                        .append("\t\t")
                        .append(" -> ")
                        .append("\t")
                        .append(obj.descricao)
                        .append(System.lineSeparator()));

        imprimir.append(System.lineSeparator()).append(System.lineSeparator());

        for (Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            imprimir.append(Metodos.formatarPalavra(entry.getKey()))
                    .append("\t\t")
                    .append(" -> ")
                    .append("\t")
                    .append(entry.getKey())
                    .append(System.lineSeparator());
        }

        return imprimir.toString();
    }

    private double[] criarVetorCriterios() {

        double[][] matrizCriterios = criarMatrizNeutra(posicaoAtributos.size(), posicaoAtributos.size());

        for(Criterio criterio : criterios) {

            int indice1 = posicaoAtributos.get(criterio.getPreferencia1());
            int indice2 = posicaoAtributos.get(criterio.getPreferencia2());

            if(criterio.getCriterio().equals(">")) {

                matrizCriterios[indice1][indice2] = criterio.getNivel();
                matrizCriterios[indice2][indice1] = 1.0/criterio.getNivel();

            } else {

                matrizCriterios[indice1][indice2] = 1.0/criterio.getNivel();
                matrizCriterios[indice2][indice1] = criterio.getNivel();
            }
        }

        return calcularMediaMatriz(matrizCriterios);
    }

    protected double[][] criarMatrizNeutra(int linhas, int colunas) {
        
        double[][] matriz = new double[linhas][colunas];

        for(int x = 0; x < linhas; x++) {
            for(int y = 0; y < colunas; y++) {

                matriz[x][y] = 1.0;
            }
        }

        return matriz;
    }

    protected double[] calcularMediaMatriz(double[][] matriz) {

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
    
}