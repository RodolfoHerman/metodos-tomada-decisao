package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.InstanciaDTO;
import br.com.rodolfo.decisao.models.Item;
import br.com.rodolfo.decisao.models.Preferencia;
import br.com.rodolfo.decisao.models.Subitem;
import br.com.rodolfo.decisao.utils.Metodos;

/**
 * SMART
 */
public class SMART<T> {

    private List<T> objetos;
    private List<Criterio> criterios;
    private Preferencia preferencia;
    private Class<?> classe;
    private final Map<String, Integer> posicaoAtributos = new HashMap<>();
    private final Map<String, Set<String>> subAtributos = new HashMap<>();
    private final Map<String, Double> pesosItems = new HashMap<>();

    public SMART(List<T> objetos, List<Criterio> criterios, Preferencia preferencia) {

        this.objetos = objetos;
        this.criterios = criterios;
        this.preferencia = preferencia;
        this.classe = objetos.get(0).getClass();

        criarMapasAtributos();
    }

    public String executar() {

        return null;
    }

    private void criarMapasAtributos() {

        int posicao = 0;

        List<String> atributos = Arrays.asList(this.classe.getDeclaredFields()).stream()
                .filter(field -> !field.getName().equals("descricao")).sorted(Comparator.comparing(Field::getName))
                .map(Field::getName).collect(Collectors.toList());

        for (String atributo : atributos) {

            this.posicaoAtributos.put(atributo, posicao++);
        }
    }

    public double[][] calcularMatrizInterpolacao() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Map<String,List<Double>> valoresAtributos = recuperarValoresAtributos();
        Map<String,List<Double>> listaMinMax      = criarListasMinMax();

        double[][] matriz = new double[objetos.size()][posicaoAtributos.size()];

        for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            List<Double> valores = valoresAtributos.get(entry.getKey());

            if(valores == null) {
                
                for(String chave : subAtributos.get(entry.getKey())) {

                    valores = valoresAtributos.get(chave);
                    List<Double> minMax = listaMinMax.get(chave);

                    valoresAtributos.put(chave, calcularInterpolacao(valores, minMax));
                }
                
            } else {
                
                List<Double> minMax = listaMinMax.get(entry.getKey());
                valoresAtributos.put(entry.getKey(), calcularInterpolacao(valores, minMax));
            }
        }

        recuperarPesosItems();

        for(Map.Entry<String,List<Double>> entry : valoresAtributos.entrySet()) {

            List<Double> valores = entry.getValue();
            List<Double> temp    = new ArrayList<>();

            for(Double valor : valores) {

                temp.add(valor * pesosItems.get(entry.getKey()));
            }

            valoresAtributos.put(entry.getKey(), temp);
        }

        List<InstanciaDTO> instancias = new ArrayList<>();

        for(int x = 0; x < objetos.size(); x++) {

            InstanciaDTO iDTO = new InstanciaDTO();

            for(Map.Entry<String,List<Double>> entry : valoresAtributos.entrySet()) {

                double atributoValor = entry.getValue().get(x);

                iDTO.setValor(entry.getKey(), atributoValor);
            }

            instancias.add(iDTO);
        }

        for(Map.Entry<String,Set<String>> entry : subAtributos.entrySet()) {

            for(InstanciaDTO instancia : instancias) {

                instancia.atualizarValores(entry.getValue(), entry.getKey());
            }
        }

        for(int x = 0; x < instancias.size(); x++) {

            InstanciaDTO instancia     = instancias.get(x);
            Map<String,Double> valores = instancia.valores;

            for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

                matriz[x][entry.getValue()] = valores.get(entry.getKey());
            }
        }
        
        return matriz;
    }

    private List<Double> calcularInterpolacao(List<Double> valores, List<Double> minMax) {

        return valores.stream().map(d -> calcularInterpolacao(minMax.get(0), minMax.get(1), d)).collect(Collectors.toList());
    }

    private double calcularInterpolacao(double menor, double maior, double valor) {

        double calc = 100.0 * ((valor - menor) / ((maior - menor) == 0 ? 1 : (maior - menor)));

        return calc == 0 ? 1.0 : calc;
    }

    public Map<String,List<Double>> recuperarValoresAtributos() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        
        Map<String,List<Double>> retorno = new HashMap<>();

        for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            Method metodo = this.classe.getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});

            for(T objeto : objetos) {

                Item item = (Item) metodo.invoke(objeto, new Object[] {});

                if(item.getSubitens() != null) {

                    for(Subitem subitem : item.getSubitens()) {

                        Set<String> temp = subAtributos.get(entry.getKey());

                        if(temp == null) {

                            temp = new HashSet<>();
                        }
                        
                        temp.add(subitem.getDescricao());
                        subAtributos.put(entry.getKey(), temp);

                        inserirValorMap(retorno, subitem.getDescricao(), subitem.getValor());
                    }

                } else {

                    inserirValorMap(retorno, entry.getKey(), item.getValor());
                }

            }
        }

        return retorno;
    }

    public Map<String,List<Double>> criarListasMinMax() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        // List<List<Double>> valores = new ArrayList<>();
        Map<String,List<Double>> valores = new HashMap<>();
        Map<String,List<Double>> temp    = recuperarValoresAtributos();

        for(Map.Entry<String,List<Double>> entry : temp.entrySet()) {

            double minimo = entry.getValue().stream()
                .mapToDouble(d -> d)
                .min().getAsDouble();
            
            double maximo = entry.getValue().stream()
                .mapToDouble(d -> d)
                .max().getAsDouble();

            double media = entry.getValue().stream()
                .mapToDouble(d -> d)
                .average().getAsDouble();

            minimo = (minimo/2) < 1 ? entry.getKey().equals("distancia") ? (minimo/2) : 1.0 : Math.rint(minimo/2);
            
            maximo = maximo + media;

            valores.put(entry.getKey(), new ArrayList<>(Arrays.asList(minimo, maximo)));
        }

        return valores;
    }

    public void inserirValorMap(Map<String,List<Double>> mapa, String atributo, String valor) {

        List<Double> lista = mapa.get(atributo);
        
        if(lista == null) {

            lista = new ArrayList<>();
        }

        lista.add(Metodos.recuperarValor(valor));
        
        mapa.put(atributo, lista);
    }

    private void recuperarPesosItems() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        
        Object objeto = objetos.get(0);
        
        for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            Method metodo = this.classe.getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});

            Set<String> subs = subAtributos.get(entry.getKey());

            if(subs == null) {

                pesosItems.put(entry.getKey(), 1.0);

            } else {

                Item item = (Item) metodo.invoke(objeto, new Object[] {});

                for(Subitem s : item.getSubitens()) {

                    double valor = Metodos.recuperarValor(s.getPeso()) == 0 ? 1.0 : Metodos.recuperarValor(s.getPeso());
                    pesosItems.put(s.getDescricao(), valor);
                    
                }
            }
        }
    }

    public int recuperarNumeroColunas() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Object objeto = objetos.get(0);
        int colunas   = 0;

        for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            Method metodo = this.classe.getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});
            Item item     = (Item) metodo.invoke(objeto, new Object[] {});

            colunas += (item.getSubitens() == null ? 1 : item.getSubitens().size());
        }

        return colunas;
    }

}