package br.com.rodolfo.decisao.models;

/**
 * Casa2
 */
public class Casa2 {

    private String descricao;
    private Item preco;
    private Item distancia;
    private Item estado;
    private Item area;

    public Casa2(String descricao, Item preco, Item distancia, Item estado, Item area) {
        this.descricao = descricao;
        this.preco = preco;
        this.distancia = distancia;
        this.estado = estado;
        this.area = area;
    }


    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Item getPreco() {
        return this.preco;
    }

    public void setPreco(Item preco) {
        this.preco = preco;
    }

    public Item getDistancia() {
        return this.distancia;
    }

    public void setDistancia(Item distancia) {
        this.distancia = distancia;
    }

    public Item getEstado() {
        return this.estado;
    }

    public void setEstado(Item estado) {
        this.estado = estado;
    }

    public Item getArea() {
        return this.area;
    }

    public void setArea(Item area) {
        this.area = area;
    }


    @Override
    public String toString() {
        return "{" +
            " descricao='" + getDescricao() + "'" +
            ", preco='" + getPreco() + "'" +
            ", distancia='" + getDistancia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }

}