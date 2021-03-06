package br.com.rodolfo.decisao.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rodolfo.decisao.models.Preferencia;

/**
 * PreferenciaService
 */
public class PreferenciaService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PreferenciaService() {}

    public List<Preferencia> buscarPreferencia(String caminho) throws JsonParseException, JsonMappingException, IOException {
        
        return Arrays.asList(objectMapper.readValue(new File(caminho), Preferencia[].class));
    }
}