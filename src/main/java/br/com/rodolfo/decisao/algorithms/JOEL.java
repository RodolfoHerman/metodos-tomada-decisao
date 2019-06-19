package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.rodolfo.decisao.algorithms.sequence.Sobol;
import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.InstanciaDTO;
import br.com.rodolfo.decisao.models.Preferencia;
import br.com.rodolfo.decisao.utils.Metodos;

/**
 * Artigo implementado :
 * PEREIRA JR, J. G. et al. On multicriteria decision making under conditions of uncertainty. Information Sciences, v. 324, p. 44-59, 2015.
 */
public class JOEL<T> extends SMART<T> {

    private final double incerteza;
    private final int qtdAmostras;

    public JOEL(List<T> objetos, List<Criterio> criterios, Preferencia preferencia, int qtdAmostras, double incerteza) {
        
        super(objetos, criterios, preferencia);
        this.incerteza   = incerteza;
        this.qtdAmostras = qtdAmostras;
    }

    @Override
    public String executar() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        StringBuilder imprimir = new StringBuilder();
        StringBuilder temp     = new StringBuilder();
        double[][] agregada    = calcularMatriz();
        double[][] transposta  = Metodos.matrizTransposta(agregada);
        List<InstanciaDTO> dto = new ArrayList<>();
        Double[] resposta      = criarResposta(agregada);

        imprimir.append("*** Matriz Payoff Agregada ***").append(System.lineSeparator()).append(System.lineSeparator());
        imprimir.append("-------").append("\t\t");

        for(int x = 0; x < transposta[0].length; x++) {

            imprimir.append("Cenário ").append(Metodos.formatarNumero(x+1)).append("\t\t");
        }

        imprimir.append(System.lineSeparator());

        for(int x = 0; x < transposta.length; x++) {

            imprimir.append(Metodos.formatarPalavra(objetos.get(x).toString())).append("\t\t");
            temp.append(Metodos.formatarPalavra(objetos.get(x).toString())).append("\t\t");
            dto.add(new InstanciaDTO(objetos.get(x).toString()));

            for(int y = 0; y < transposta[0].length; y++) {

                imprimir.append(String.format("%.8f", transposta[x][y])).append("\t\t");
            }

            imprimir.append(System.lineSeparator());
        }

        imprimir.append(System.lineSeparator())
                .append("*** Resultado Mu-D ***").append(System.lineSeparator()).append(System.lineSeparator());
        
        imprimir.append(temp.toString()).append(System.lineSeparator());

        int posicao = 0;

        for (Double r : resposta) {
            
            dto.get(posicao++).valor = r;
            imprimir.append(String.format("%.4f", r)).append("\t\t");
        }

        imprimir.append(System.lineSeparator()).append(System.lineSeparator());

        dto.stream()
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

        return imprimir.toString();
    }

    @Override
    protected double[][] calcularMatriz() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        
        Map<String,List<Double>> valoresAtributos = recuperarValoresAtributos();
        Map<String,Double[][]> cenarios   = new HashMap<>();

        double[][] sequencia = getSequencia(valoresAtributos.size(), objetos.size());
        
        for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            List<Double> valores = valoresAtributos.get(entry.getKey());

            if(valores == null) {
                
                for(String chave : subAtributos.get(entry.getKey())) {

                    Method metodo = this.preferencia.getClass().getMethod(Metodos.retornarNomeMetodo(chave), new Class[] {});
                    boolean pref  = Metodos.preferenciaParaBoolean((String) metodo.invoke(preferencia, new Object[] {}));

                    valores = valoresAtributos.get(chave);
                    
                    // cenarios.put(chave, calcular_mu_ap(calcularInterpolacao(valores, sequencia, chave), pref));
                    cenarios.put(chave, calcular_mu_ap(calcularInterpolacao(valores, sequencia), pref));
                }
                
            } else {

                Method metodo = this.preferencia.getClass().getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});
                boolean pref  = Metodos.preferenciaParaBoolean((String) metodo.invoke(preferencia, new Object[] {}));

                // cenarios.put(entry.getKey(), calcular_mu_ap(calcularInterpolacao(valores, sequencia, entry.getKey()), pref));
                cenarios.put(entry.getKey(), calcular_mu_ap(calcularInterpolacao(valores, sequencia), pref));
            }
        }

        int linhas  = sequencia.length;
        int colunas = sequencia[0].length;
        
        return criarPayoffAgregada(cenarios, linhas, colunas);
    }

    private Double[] criarResposta(double[][] agregada) {

        int colunas = agregada[0].length;
        List<Double> resposta = new ArrayList<>();

        for(int y = 0; y < colunas; y++) {

            resposta.add(calcular_mu_D(y, agregada));
        }

        return resposta.toArray(new Double[colunas]);
    }

    private double[][] criarPayoffAgregada(Map<String,Double[][]> cenarios, int linhas, int colunas) {

        double[][] agregada = criarMatrizNeutra(linhas, colunas);

        for(Map.Entry<String,Double[][]> entry : cenarios.entrySet()) {

            Double[][] valores = entry.getValue();

            for(int x = 0; x < linhas; x++) {
                for(int y = 0; y < colunas; y++) {

                    agregada[x][y] = agregada[x][y] < valores[x][y] ? agregada[x][y] : valores[x][y];
                }
            }
        }

        return agregada;
    }

    private Double[][] calcular_mu_ap(Double[][] matrizPayoff, boolean maximizar) {
    
        Double[][] modificadaPayoff = new Double[matrizPayoff.length][matrizPayoff[0].length];
        DoubleSummaryStatistics sumario = Stream.of(matrizPayoff).flatMap(elementos -> Stream.of(elementos))
            .collect(Collectors.summarizingDouble(Double::doubleValue));

        if(maximizar) {

            for(int x = 0; x < modificadaPayoff.length; x++) {
                for(int y = 0; y < modificadaPayoff[0].length; y++) {

                    modificadaPayoff[x][y] = (matrizPayoff[x][y] - sumario.getMin())/(sumario.getMax() - sumario.getMin());
                }
            }

        } else {

            for(int x = 0; x < modificadaPayoff.length; x++) {
                for(int y = 0; y < modificadaPayoff[0].length; y++) {

                    modificadaPayoff[x][y] = (sumario.getMax() - matrizPayoff[x][y])/(sumario.getMax() - sumario.getMin());
                }
            }
        }

        return modificadaPayoff;
    }

    private double calcular_mu_D(final int coluna, double[][] matriz) {
        
        DoubleSummaryStatistics sumario = Stream.of(matriz).map(elementos -> elementos[coluna])
            .collect(Collectors.summarizingDouble(Double::doubleValue));

        return sumario.getAverage();
    }


    // protected Double[][] calcularInterpolacao(List<Double> valores, double[][] sequencia, String chave) {
    protected Double[][] calcularInterpolacao(List<Double> valores, double[][] sequencia) {

        Double[][] resposta = new Double[sequencia.length][sequencia[0].length];
        // double peso  = pesosItems.get(chave) == 1.0 ? 1.0 : (pesosItems.get(chave)/100.0);

        for(int x = 0; x < sequencia.length; x++) {
            for(int y = 0; y < sequencia[0].length; y++) {

                double menor = valores.get(y) - (valores.get(y) * incerteza);
                double maior = valores.get(y) + (valores.get(y) * incerteza);

                resposta[x][y] = menor + ((maior - menor) * sequencia[x][y]); // * peso;
            }
        }

        return resposta;
    }

    private double[][] getSequencia(int atributos, int instancias) {

        Sobol sobol  = new Sobol();
        int amostras = instancias * 2;
        
        if(qtdAmostras > amostras) {

            amostras = qtdAmostras;
        }
        

        // if(atributos <= (2 * instancias + 2)) {

        //     amostras = 4 * instancias + 2;

        // } else {

        //     amostras = 4 * (Double.valueOf(Math.ceil(atributos/(2 * instancias + 2))).intValue() * (2 * instancias + 2));
        // }

        return sobol.generate(amostras, instancias);
    }

    
}