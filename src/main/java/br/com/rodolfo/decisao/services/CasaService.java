package br.com.rodolfo.decisao.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rodolfo.decisao.models.Casa;

/**
 * CasaService
 */
public class CasaService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CasaService() {}

    public List<Casa> buscarInstancias(String caminho) throws JsonParseException, JsonMappingException, IOException {
        
        return Arrays.asList(objectMapper.readValue(new File(caminho), Casa[].class));
    }
}