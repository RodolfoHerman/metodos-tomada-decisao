package br.com.rodolfo.decisao.models.enums;

public enum Tipo {

    RUIM(1, "ruim"),
    BOM(3, "bom"),
    OTIMO(7, "otimo");

    private Integer criterio;
    private String descricao;

    private Tipo(Integer criterio, String descricao) {

        this.criterio = criterio;
        this.descricao = descricao;
    }

    public Integer getCriterio() {

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