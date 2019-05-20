package br.com.rodolfo.decisao.models.enums;

public enum Tipo {

    PESSIMO(3.0, "pessimo"),
    RUIM(5.0, "ruim"),
    BOM(7.0, "bom"),
    OTIMO(9.0, "otimo"),
    EXCELENTE(11.0, "excelente"),
    MPEQUENO(3.0, "muito pequeno"),
    PEQUENO(5.0, "pequeno"),
    MEDIO(7.0, "medio"),
    GRANDE(9.0, "grande"),
    MGRANDE(11.0, "muito grande");

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