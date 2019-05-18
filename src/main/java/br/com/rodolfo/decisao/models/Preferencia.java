package br.com.rodolfo.decisao.models;

/**
 * Preferencia
 */
public class Preferencia {

    private String preco;
    private String distancia;
    private String estado;
    private String area;
    private String compra;
    private String manutencao;
    private String taxa;
    private String consumo;
    private String potencia;
    private String versatil;
    private String passageiros;
    private String espaco;
    private String bagageiro;
    private String conforto;
    private String seguranca;
    private String estilo;

    public Preferencia() {}


    public Preferencia(String preco, String distancia, String estado, String area, String compra, String manutencao, String taxa, String consumo, String potencia, String versatil, String passageiros, String espaco, String bagageiro, String conforto, String seguranca, String estilo) {
        this.preco = preco;
        this.distancia = distancia;
        this.estado = estado;
        this.area = area;
        this.compra = compra;
        this.manutencao = manutencao;
        this.taxa = taxa;
        this.consumo = consumo;
        this.potencia = potencia;
        this.versatil = versatil;
        this.passageiros = passageiros;
        this.espaco = espaco;
        this.bagageiro = bagageiro;
        this.conforto = conforto;
        this.seguranca = seguranca;
        this.estilo = estilo;
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

    public String getCompra() {
        return this.compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getManutencao() {
        return this.manutencao;
    }

    public void setManutencao(String manutencao) {
        this.manutencao = manutencao;
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

    public String getVersatil() {
        return this.versatil;
    }

    public void setVersatil(String versatil) {
        this.versatil = versatil;
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

    public String getConforto() {
        return this.conforto;
    }

    public void setConforto(String conforto) {
        this.conforto = conforto;
    }

    public String getSeguranca() {
        return this.seguranca;
    }

    public void setSeguranca(String seguranca) {
        this.seguranca = seguranca;
    }

    public String getEstilo() {
        return this.estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "{" +
            " preco='" + getPreco() + "'" +
            ", distancia='" + getDistancia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", area='" + getArea() + "'" +
            ", compra='" + getCompra() + "'" +
            ", manutencao='" + getManutencao() + "'" +
            ", taxa='" + getTaxa() + "'" +
            ", consumo='" + getConsumo() + "'" +
            ", potencia='" + getPotencia() + "'" +
            ", potencia='" + getVersatil() + "'" +
            ", passageiros='" + getPassageiros() + "'" +
            ", espaco='" + getEspaco() + "'" +
            ", bagageiro='" + getBagageiro() + "'" +
            ", conforto='" + getConforto() + "'" +
            ", seguranca='" + getSeguranca() + "'" +
            ", estilo='" + getEstilo() + "'" +
            "}";
    }

}