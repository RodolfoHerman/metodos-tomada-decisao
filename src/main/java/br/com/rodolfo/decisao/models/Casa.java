package br.com.rodolfo.decisao.models;

/**
 * Casa
 */
public class Casa {

    private String descricao;
    private double preco;
    private double distancia;
    private String estado;
    private double area;

    public Casa() {}

    public Casa(String descricao, double preco, double distancia, String estado, double area) {
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

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getDistancia() {
        return this.distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getArea() {
        return this.area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "{" +
            " descricao='" + getDescricao() + "'" +
            " preco='" + getPreco() + "'" +
            ", distancia='" + getDistancia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }

}