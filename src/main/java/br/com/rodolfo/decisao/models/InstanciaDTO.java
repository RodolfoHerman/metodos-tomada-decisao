package br.com.rodolfo.decisao.models;

/**
 * InstanciaDTO
 */
public class InstanciaDTO implements Comparable<InstanciaDTO>{

    public String descricao;
    public double valor;

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

    @Override
    public int compareTo(InstanciaDTO o) {
        return Double.compare(valor, o.getValor());
    }
    
}