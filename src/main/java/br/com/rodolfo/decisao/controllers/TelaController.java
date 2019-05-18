package br.com.rodolfo.decisao.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import br.com.rodolfo.decisao.StartApp;
import br.com.rodolfo.decisao.algorithms.AHP;
import br.com.rodolfo.decisao.config.Configuracoes;
import br.com.rodolfo.decisao.models.Carro;
import br.com.rodolfo.decisao.models.Casa;
import br.com.rodolfo.decisao.models.Criterio;
import br.com.rodolfo.decisao.models.Preferencia;
import br.com.rodolfo.decisao.services.CarroService;
import br.com.rodolfo.decisao.services.CasaService;
import br.com.rodolfo.decisao.services.CriterioService;
import br.com.rodolfo.decisao.services.PreferenciaService;
import br.com.rodolfo.decisao.utils.Metodos;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

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

    // Variáveis locais
    private DirectoryChooser directoryChooser;
    private final CarroService carroService = new CarroService();
    private final CasaService casaService = new CasaService();
    private final CriterioService criterioService = new CriterioService();
    private final PreferenciaService preferenciaService = new PreferenciaService();
    private final Configuracoes configuracoes = new Configuracoes();
    private final String PROPERTIES = "config.properties";
    private List<Carro> carros = new ArrayList<>();
    private List<Criterio> carrosCriterios = new ArrayList<>();
    private List<Casa> casas = new ArrayList<>();
    private List<Criterio> casasCriteriios = new ArrayList<>();
    private Preferencia preferencia;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Properties properties = new Properties();

        try (InputStream is = TelaController.class.getClassLoader().getResourceAsStream(PROPERTIES)) {

            properties.load(is);

            configuracoes.carroCriterios = properties.getProperty("carro.criterios");
            configuracoes.carroInstancias = properties.getProperty("carro.instancias");
            configuracoes.casaCriterios = properties.getProperty("casa.criterios");
            configuracoes.casaInstancias = properties.getProperty("casa.instancias");
            configuracoes.arquivoPreferencias = properties.getProperty("arquivo.preferencias");

        } catch (Exception e) {
            // TODO: handle exception
        }

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

        // AHP2<Carro> ahp2 = new AHP2<>(carros, carrosCriterios, preferencia);
        // AHP<Casa> ahp = new AHP<>(casas, casasCriteriios, preferencia);
        AHP<Carro> ahp = new AHP<>(carros, carrosCriterios, preferencia);
        ahp.executar();
    }

    @FXML
    public void abrirArquivos() {

        DirectoryChooser chooser = getDirectoryChooser();
        File file = chooser.showDialog(StartApp.mainStage);

        if (file != null) {

            if (Metodos.verificarArquivos(configuracoes.arquivosParaLista(), file.list())) {

                abrirArquivosJson(file);
                habilitarCampos();
            }

            chooser.setInitialDirectory(file);
        }

    }

    @FXML
    public void fecharPrograma() {

        Platform.exit();
        System.exit(0);
    }

    // Métodos locais
    private DirectoryChooser getDirectoryChooser() {

        if (directoryChooser == null) {

            directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Abrir diretório");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }

        return directoryChooser;
    }

    private void habilitarCampos() {

        metodos.getToggles().forEach(node -> {

            RadioButton radio = (RadioButton) node;
            radio.setDisable(false);
        });

        problemas.getToggles().forEach(node -> {

            RadioButton radio = (RadioButton) node;
            radio.setDisable(false);
        });

        btnAnalisar.setDisable(false);
    }

    private void abrirArquivosJson(File file) {

        String caminho = file.getAbsolutePath();

        try {
            
            carros.addAll(carroService.buscarInstancias(caminho.concat("//").concat(configuracoes.carroInstancias)));
            carrosCriterios.addAll(criterioService.buscarCriterios(caminho.concat("//").concat(configuracoes.carroCriterios)));
            casas.addAll(casaService.buscarInstancias(caminho.concat("//").concat(configuracoes.casaInstancias)));
            casasCriteriios.addAll(criterioService.buscarCriterios(caminho.concat("//").concat(configuracoes.casaCriterios)));
            preferencia = preferenciaService.buscarPreferencia(caminho.concat("//").concat(configuracoes.arquivoPreferencias)).get(0);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}