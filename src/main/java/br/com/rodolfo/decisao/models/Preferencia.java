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


    private String compra;
    private String condominio;
    private String apartamento;
    private String garagem;
    private String cozinha;

    private String manutencao;
    private String seguro;
    private String consumo;
    private String potencia;
    private String versatil;
    private String passageiros;
    private String bagageiro;
    private String conforto;
    private String seguranca;
    private String estilo;

    public Preferencia() {}

    public Preferencia(String preco, String distancia, String estado, String area, String custo, String engenharia, String espaco, String extras, String compra, String condominio, String apartamento, String garagem, String cozinha, String manutencao, String seguro, String consumo, String potencia, String versatil, String passageiros, String bagageiro, String conforto, String seguranca, String estilo) {
        this.preco = preco;
        this.distancia = distancia;
        this.estado = estado;
        this.area = area;
        this.custo = custo;
        this.engenharia = engenharia;
        this.espaco = espaco;
        this.extras = extras;
        this.compra = compra;
        this.condominio = condominio;
        this.apartamento = apartamento;
        this.garagem = garagem;
        this.cozinha = cozinha;
        this.manutencao = manutencao;
        this.seguro = seguro;
        this.consumo = consumo;
        this.potencia = potencia;
        this.versatil = versatil;
        this.passageiros = passageiros;
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

    public String getCompra() {
        return this.compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getCondominio() {
        return this.condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

    public String getApartamento() {
        return this.apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getGaragem() {
        return this.garagem;
    }

    public void setGaragem(String garagem) {
        this.garagem = garagem;
    }

    public String getCozinha() {
        return this.cozinha;
    }

    public void setCozinha(String cozinha) {
        this.cozinha = cozinha;
    }

    public String getManutencao() {
        return this.manutencao;
    }

    public void setManutencao(String manutencao) {
        this.manutencao = manutencao;
    }

    public String getSeguro() {
        return this.seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
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
            ", custo='" + getCusto() + "'" +
            ", engenharia='" + getEngenharia() + "'" +
            ", espaco='" + getEspaco() + "'" +
            ", extras='" + getExtras() + "'" +
            ", compra='" + getCompra() + "'" +
            ", condominio='" + getCondominio() + "'" +
            ", apartamento='" + getApartamento() + "'" +
            ", garagem='" + getGaragem() + "'" +
            ", cozinha='" + getCozinha() + "'" +
            ", manutencao='" + getManutencao() + "'" +
            ", seguro='" + getSeguro() + "'" +
            ", consumo='" + getConsumo() + "'" +
            ", potencia='" + getPotencia() + "'" +
            ", versatil='" + getVersatil() + "'" +
            ", passageiros='" + getPassageiros() + "'" +
            ", bagageiro='" + getBagageiro() + "'" +
            ", conforto='" + getConforto() + "'" +
            ", seguranca='" + getSeguranca() + "'" +
            ", estilo='" + getEstilo() + "'" +
            "}";
    }
    
}