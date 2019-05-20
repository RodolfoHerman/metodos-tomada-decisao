package br.com.rodolfo.decisao.models;

/**
 * Preferencia
 */
public class Preferencia {

    private String preco;
    private String distancia;
    private String estado;
    private String area;
    private String custo;
    private String engenharia;
    private String espaco;
    private String extras;

    public Preferencia() {}

    public Preferencia(String preco, String distancia, String estado, String area, String custo, String engenharia, String espaco, String extras) {
        this.preco = preco;
        this.distancia = distancia;
        this.estado = estado;
        this.area = area;
        this.custo = custo;
        this.engenharia = engenharia;
        this.espaco = espaco;
        this.extras = extras;
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

    public String getCusto() {
        return this.custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public String getEngenharia() {
        return this.engenharia;
    }

    public void setEngenharia(String engenharia) {
        this.engenharia = engenharia;
    }

    public String getEspaco() {
        return this.espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getExtras() {
        return this.extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }


    @Override
    public String toString() {
        return "{" +
            " preco='" + getPreco() + "'" +
            ", distancia='" + getDistancia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", area='" + getArea() + "'" +
            ", custo='" + getCusto() + "'" +
            ", engenharia='" + getEngenharia() + "'" +
            ", espaco='" + getEspaco() + "'" +
            ", extras='" + getExtras() + "'" +
            "}";
    }


}