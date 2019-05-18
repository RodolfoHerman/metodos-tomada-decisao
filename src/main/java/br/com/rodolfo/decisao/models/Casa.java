package br.com.rodolfo.decisao.models;

/**
 * Casa
 */
public class Casa {

    private String descricao;
    private String preco;
    private String distancia;
    private String estado;
    private String area;

    public Casa() {}

    public Casa(String descricao, String preco, String distancia, String estado, String area) {
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

    public String getPreco() {
        return this.preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDistancia() {
        return this.distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    
    public String imprimirCampos() {
        return "{" +
            " descricao='" + getDescricao() + "'" +
            " preco='" + getPreco() + "'" +
            ", distancia='" + getDistancia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}