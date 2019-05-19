package br.com.rodolfo.decisao.models;

import java.util.List;

/**
 * Item
 */
public class Item {

    private String valor;
    private List<Subitem> subitens;

    public Item(String valor) {

        this.valor = valor;
    }

    public Item(String valor, List<Subitem> subitens) {
        this.valor = valor;
        this.subitens = subitens;
    }


    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Subitem> getSubitens() {
        return this.subitens;
    }

    public void setSubitens(List<Subitem> subitens) {
        this.subitens = subitens;
    }


    @Override
    public String toString() {
        return "{" +
            " valor='" + getValor() + "'" +
            ", subitens='" + getSubitens() + "'" +
            "}";
    }


}