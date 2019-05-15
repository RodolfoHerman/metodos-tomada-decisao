package br.com.rodolfo.decisao.models;


/**
 * Carro
 */
public class Carro {

    private String descricao;
    private double compra;
    private String manutencao;
    private double taxa;
    private double consumo;
    private double potencia;
    private String versatil;
    private double passageiros;
    private double espaco;
    private double bagageiro;
    private String conforto;
    private String seguranca;
    private String estilo;
    
    public Carro() {}

    public Carro(String descricao, double compra, String manutencao, double taxa, double consumo, double potencia, String versatil, double passageiros, double espaco, double bagageiro, String conforto, String seguranca, String estilo) {
        this.descricao = descricao;
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

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCompra() {
        return this.compra;
    }

    public void setCompra(double compra) {
        this.compra = compra;
    }

    public String getManutencao() {
        return this.manutencao;
    }

    public void setManutencao(String manutencao) {
        this.manutencao = manutencao;
    }

    public double getTaxa() {
        return this.taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public double getConsumo() {
        return this.consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public double getPotencia() {
        return this.potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public String getVersatil() {
        return this.versatil;
    }

    public void setVersatil(String versatil) {
        this.versatil = versatil;
    }

    public double getPassageiros() {
        return this.passageiros;
    }

    public void setPassageiros(double passageiros) {
        this.passageiros = passageiros;
    }

    public double getEspaco() {
        return this.espaco;
    }

    public void setEspaco(double espaco) {
        this.espaco = espaco;
    }

    public double getBagageiro() {
        return this.bagageiro;
    }

    public void setBagageiro(double bagageiro) {
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
            " descricao='" + getDescricao() + "'" +
            " compra='" + getCompra() + "'" +
            ", manutencao='" + getManutencao() + "'" +
            ", taxa='" + getTaxa() + "'" +
            ", consumo='" + getConsumo() + "'" +
            ", potencia='" + getPotencia() + "'" +
            ", versatil='" + getVersatil() + "'" +
            ", passageiros='" + getPassageiros() + "'" +
            ", espaco='" + getEspaco() + "'" +
            ", bagageiro='" + getBagageiro() + "'" +
            ", conforto='" + getConforto() + "'" +
            ", seguranca='" + getSeguranca() + "'" +
            ", estilo='" + getEstilo() + "'" +
            "}";
    }

}