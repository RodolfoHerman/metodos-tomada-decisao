package br.com.rodolfo.decisao.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * InstanciaDTO
 */
public class InstanciaDTO implements Comparable<InstanciaDTO>{

    public String descricao;
    public double valor;
    public Map<String,Double> valores;

    public InstanciaDTO() {}

    public InstanciaDTO(String descricao) {

        this.descricao = descricao;
    }
    
    public InstanciaDTO(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(String atributo, double atributoValor) {

        if(valores == null) {

            valores = new HashMap<>();
        }

        valores.put(atributo, atributoValor);
    }

    public void atualizarValores(Set<String> chaves, String atributo) {

        if(valores != null) {

            double soma = 0.0;

            for(String chave : chaves) {

                soma += valores.remove(chave); 
            }

            valores.put(atributo, soma/100.0);
        }
    }

    @Override
    public int compareTo(InstanciaDTO o) {
        return Double.compare(valor, o.getValor());
    }
    
}