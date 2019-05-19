package br.com.rodolfo.decisao.models;

/**
 * Subitem
 */
public class Subitem  {

    private String descricao;
    private String valor;
    private String peso;
    
    public Subitem() {}

    public Subitem(String descricao, String valor, String peso) {
        this.descricao = descricao;
        this.valor = valor;
        this.peso = peso;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPeso() {
        return this.peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }


    @Override
    public String toString() {
        return "{" +
            " descricao='" + getDescricao() + "'" +
            " valor='" + getValor() + "'" +
            ", peso='" + getPeso() + "'" +
            "}";
    }

}