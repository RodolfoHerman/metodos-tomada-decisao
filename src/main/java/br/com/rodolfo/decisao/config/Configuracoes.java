package br.com.rodolfo.decisao.config;

import java.util.Arrays;
import java.util.List;

/**
 * Configuracoes
 */
public class Configuracoes {
    
    public String carroCriterios;
    public String carroInstancias;
    public String casaCriterios;
    public String casaInstancias;
    public String arquivoPreferencias;

    public List<String> arquivosParaLista() {
        
        return Arrays.asList(carroCriterios, carroInstancias, casaCriterios, casaInstancias, arquivoPreferencias);
    }
}