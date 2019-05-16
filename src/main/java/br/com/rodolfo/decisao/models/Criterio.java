package br.com.rodolfo.decisao.models;

/**
 * Criterio
 */
public class Criterio {

    private String preferencia1;
    private String criterio;
    private String preferencia2;
    private double nivel;

    public Criterio() {}

    public Criterio(String preferencia1, String criterio, String preferencia2, double nivel) {
        this.preferencia1 = preferencia1;
        this.criterio = criterio;
        this.preferencia2 = preferencia2;
        this.nivel = nivel;
    }


    public String getPreferencia1() {
        return this.preferencia1;
    }

    public void setPreferencia1(String preferencia1) {
        this.preferencia1 = preferencia1;
    }

    public String getCriterio() {
        return this.criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getPreferencia2() {
        return this.preferencia2;
    }

    public void setPreferencia2(String preferencia2) {
        this.preferencia2 = preferencia2;
    }

    public double getNivel() {
        return this.nivel;
    }

    public void setNivel(double nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "{" +
            " preferencia1='" + getPreferencia1() + "'" +
            ", criterio='" + getCriterio() + "'" +
            ", preferencia2='" + getPreferencia2() + "'" +
            ", nivel='" + getNivel() + "'" +
            "}";
    }

}