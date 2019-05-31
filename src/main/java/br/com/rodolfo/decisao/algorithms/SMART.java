package br.com.rodolfo.decisao.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SMART<T> extends Algoritmos<T> {

    private final Map<String, Set<String>> subAtributos = new HashMap<>();
    private final Map<String, Double> pesosItems = new HashMap<>();

    public SMART(List<T> objetos, List<Criterio> criterios, Preferencia preferencia) {

        super(objetos, criterios, preferencia);
    }

    protected double[][] calcularMatriz() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Map<String, List<Double>> valoresAtributos = recuperarValoresAtributos();
        Map<String, List<Double>> listaMinMax = criarListasMinMax();

        double[][] matriz = new double[objetos.size()][posicaoAtributos.size()];
        
        for(Map.Entry<String,Integer> entry : posicaoAtributos.entrySet()) {

            List<Double> valores = valoresAtributos.get(entry.getKey());

            if(valores == null) {
                
                for(String chave : subAtributos.get(entry.getKey())) {

                    Method metodo = this.preferencia.getClass().getMethod(Metodos.retornarNomeMetodo(chave), new Class[] {});
                    boolean pref  = Metodos.preferenciaParaBoolean((String) metodo.invoke(preferencia, new Object[] {}));

                    valores = valoresAtributos.get(chave);

                    valoresAtributos.put(chave, calcularInterpolacao(valores, listaMinMax.get(chave), pref));
                }
                
            } else {
                
                Method metodo = this.preferencia.getClass().getMethod(Metodos.retornarNomeMetodo(entry.getKey()), new Class[] {});
                boolean pref  = Metodos.preferenciaParaBoolean((String) metodo.invoke(preferencia, new Object[] {}));

                valoresAtributos.put(entry.getKey(), calcularInterpolacao(valores, listaMinMax.get(entry.getKey()), pref));
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

    private List<Double> calcularInterpolacao(List<Double> valores, List<Double> minMax, boolean pref) {

        return valores.stream().map(d -> calcularInterpolacao(minMax.get(0), minMax.get(1), d, pref)).collect(Collectors.toList());
    }

    private double calcularInterpolacao(double menor, double maior, double valor, boolean pref) {

        double calc = 0.0;

        if(pref) {

            double divisor = (maior - menor) == 0 ? 1.0 : (maior - menor);
            calc = (valor - menor)/divisor;
            
        } else {
            
            double divisor = (menor - maior) == 0 ? 1.0 : (menor - maior);
            calc = (valor - maior)/divisor; 
        }

        return 100.0 * (calc == 0 ? 0.01 : calc);
    }

    private Map<String,List<Double>> recuperarValoresAtributos() throws NoSuchMethodException, SecurityException, IllegalAccessException,
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

    private Map<String,List<Double>> criarListasMinMax() throws NoSuchMethodException, SecurityException, IllegalAccessException,
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

    private void inserirValorMap(Map<String,List<Double>> mapa, String atributo, String valor) {

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
}