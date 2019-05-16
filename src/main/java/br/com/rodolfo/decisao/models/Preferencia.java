package br.com.rodolfo.decisao.models;

/**
 * Preferencia
 */
public class Preferencia {

    private String preco;
    private String distancia;
    private String area;
    private String compra;
    private String taxa;
    private String consumo;
    private String potencia;
    private String passageiros;
    private String espaco;
    private String bagageiro;

    public Preferencia() {}


    public Preferencia(String preco, String distancia, String area, String compra, String taxa, String consumo, String potencia, String passageiros, String espaco, String bagageiro) {
        this.preco = preco;
        this.distancia = distancia;
        this.area = area;
        this.compra = compra;
        this.taxa = taxa;
        this.consumo = consumo;
        this.potencia = potencia;
        this.passageiros = passageiros;
        this.espaco = espaco;
        this.bagageiro = bagageiro;
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

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompra() {
        return this.compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getTaxa() {
        return this.taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public String getConsumo() {
        return this.consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public String getPotencia() {
        return this.potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getPassageiros() {
        return this.passageiros;
    }

    public void setPassageiros(String passageiros) {
        this.passageiros = passageiros;
    }

    public String getEspaco() {
        return this.espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getBagageiro() {
        return this.bagageiro;
    }

    public void setBagageiro(String bagageiro) {
        this.bagageiro = bagageiro;
    }


    @Override
    public String toString() {
        return "{" +
            " preco='" + getPreco() + "'" +
            ", distancia='" + getDistancia() + "'" +
            ", area='" + getArea() + "'" +
            ", compra='" + getCompra() + "'" +
            ", taxa='" + getTaxa() + "'" +
            ", consumo='" + getConsumo() + "'" +
            ", potencia='" + getPotencia() + "'" +
            ", passageiros='" + getPassageiros() + "'" +
            ", espaco='" + getEspaco() + "'" +
            ", bagageiro='" + getBagageiro() + "'" +
            "}";
    }

}