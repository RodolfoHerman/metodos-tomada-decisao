package br.com.rodolfo.decisao.models.enums;

public enum Tipo {

    RUIM(3.0, "ruim"),
    BOM(5.0, "bom"),
    OTIMO(7.0, "otimo"),
    PEQUENO(3.0, "pequeno"),
    MEDIO(5.0, "medio"),
    GRANDE(7.0, "grande");

    private Double criterio;
    private String descricao;

    private Tipo(Double criterio, String descricao) {

        this.criterio = criterio;
        this.descricao = descricao;
    }

    public Double getCriterio() {

        return this.criterio;
    }

    public String getDescricao() {

        return this.descricao;
    }

    public static Tipo toEnum(String descricao) {

        if(descricao == null || descricao.equals("")) {

            return null;
        }

        for(Tipo tipo : Tipo.values()) {

            if(descricao.equals(tipo.getDescricao()))
                return tipo;
        }

        throw new IllegalArgumentException("Descrição inválida : ".concat(descricao));
    }
}