package br.com.rodolfo.decisao.models;

/**
 * Carro
 */
public class Carro {

    private String descricao;
    private Item custo;
    private Item engenharia;
    private Item espaco;
    private Item extras;

    public Carro() {}

    public Carro(String descricao, Item custo, Item engenharia, Item espaco, Item extras) {
        this.descricao = descricao;
        this.custo = custo;
        this.engenharia = engenharia;
        this.espaco = espaco;
        this.extras = extras;
    }


    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Item getCusto() {
        return this.custo;
    }

    public void setCusto(Item custo) {
        this.custo = custo;
    }

    public Item getEngenharia() {
        return this.engenharia;
    }

    public void setEngenharia(Item engenharia) {
        this.engenharia = engenharia;
    }

    public Item getEspaco() {
        return this.espaco;
    }

    public void setEspaco(Item espaco) {
        this.espaco = espaco;
    }

    public Item getExtras() {
        return this.extras;
    }

    public void setExtras(Item extras) {
        this.extras = extras;
    }

    public String imprimirCampos() {
        return "{" +
            " descricao='" + getDescricao() + "'" +
            ", custo='" + getCusto() + "'" +
            ", engenharia='" + getEngenharia() + "'" +
            ", espaco='" + getEspaco() + "'" +
            ", extras='" + getExtras() + "'" +
            "}";
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}