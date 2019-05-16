package br.com.rodolfo.decisao.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

/**
 * TelaController
 */
public class TelaController implements Initializable {

    @FXML
    private ToggleGroup metodos;

    @FXML
    private ToggleGroup problemas;

    @FXML
    private TextArea textArea;

    @FXML
    private Button btnAnalisar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        metodos.getToggles().forEach(node -> {

            RadioButton radio = (RadioButton) node;
            radio.setDisable(true);
        });

        problemas.getToggles().forEach(node -> {

            RadioButton radio = (RadioButton) node;
            radio.setDisable(true);
        });

        btnAnalisar.setDisable(true);
    }

    @FXML
    public void btnAnalisarAction() {
        
        System.out.println("TESTE");
    }

    
    @FXML
    public void abrirArquivos() {

    }

    @FXML
    public void fecharPrograma() {
        
        Platform.exit();
        System.exit(0);
    }

}