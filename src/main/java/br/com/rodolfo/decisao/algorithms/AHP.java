package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.InstanciaDTO;
import br.com.rodolfo.decisao.models.Item;
import br.com.rodolfo.decisao.models.Preferencia;
import br.com.rodolfo.decisao.models.enums.Tipo;
import br.com.rodolfo.decisao.utils.Metodos;

/**
 * AHP2
 */
public class AHP<T> {

    private List<T> objetos;
    private List<Criterio> criterios;
    private Preferencia preferencia;
    private Map<String, Integer> mapaCriterio;
    // private Map<String, Integer> mapaInstancia;
    private Class<?> classe;

    public AHP(List<T> objetos, List<Criterio> criterios, Preferencia preferencia) {

        this.objetos = objetos;
        this.criterios = criterios;
        this.preferencia = preferencia;
        this.classe = objetos.get(0).getClass();

        criarMapas();
    }

    public String executar() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        StringBuilder imprimir = new StringBuilder();
        StringBuilder temp     = new StringBuilder();
        List<InstanciaDTO> dto = new ArrayList<>();
        int posicao = 0;

        double[] vetorCriterios = Metodos.criarVetorCriterios(criterios, mapaCriterio);
        double[][] preferencias = calcularMatriz();
        double[] resposta       = Metodos.multiplicarVetorComMatriz(vetorCriterios, preferencias);

        imprimir.append("*** Critérios do Usuário ***").append(System.lineSeparator()).append(System.lineSeparator());

        for (Map.Entry<String,Integer> entry : mapaCriterio.entrySet()) {

            temp.append(Metodos.formatarPalavra(entry.getKey())).append("\t\t");
        }

        imprimir.append(temp.toString());
        imprimir.append(System.lineSeparator());

        for (Map.Entry<String,Integer> entry : mapaCriterio.entrySet()) {

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

            for (Map.Entry<String,Integer> entry : mapaCriterio.entrySet()) {

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
            .ifPresent(obj -> imprimir.append("A melhor escolha é : '")
                                      .append(Metodos.formatarPalavra(obj.descricao))
                                      .append("' com valor de : '")
                                      .append(String.format("%.4f", obj.valor))
                                      .append("'"));

        return imprimir.toString();
    }

    private void criarMapas() {

        this.mapaCriterio = new HashMap<>();
        int posicao = 0;

        List<String> criterios = Arrays.asList(this.classe.getDeclaredFields()).stream()
                .filter(f -> !f.getName().equals("descricao"))
                .sorted(Comparator.comparing(Field::getName))
                .map(f -> f.getName())
                .collect(Collectors.toList());

        // List<String> instancias = objetos.stream().sorted(Comparator.comparing(Object::toString)).map(o -> o.toString())
        //         .collect(Collectors.toList());

        for (String criterio : criterios) {

            mapaCriterio.put(criterio, posicao++);
        }

    }

    private double[][] calcularMatriz() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        double[][] matriz = new double[objetos.size()][mapaCriterio.size()];
        Map<String,double[]> valores = new HashMap<>();

        for (Map.Entry<String,Integer> entry : mapaCriterio.entrySet()) {
            
            List<Double> temp   = new ArrayList<>();
            Method criarMetodo1 = this.classe.getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});

            for(T objeto : objetos) {

                Item item = (Item) criarMetodo1.invoke(objeto, new Object[] {});
                temp.add(Metodos.recuperarValor(item.getValor()));
            }

            Method criarMetodo2 = this.preferencia.getClass().getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});

            valores.put(entry.getKey(), Metodos.calcularMediaMatriz(
                Metodos.matrizPreferencia(temp, Metodos.preferenciaParaBoolean((String) criarMetodo2.invoke(preferencia, new Object[] {})))
            ));
        }

        for (Map.Entry<String,double[]> entry : valores.entrySet()) {

            double[] valor = entry.getValue();
            int posicao =  mapaCriterio.get(entry.getKey());

            for(int x = 0; x < valor.length; x++) {

                matriz[x][posicao] = valor[x];
            }
        }

        return matriz;
    }

    
}