package br.com.rodolfo.decisao;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StartApp extends Application {

    public static Stage mainStage;
    private static Parent mainPane;

    
    @Override
    public void start(Stage stage) throws Exception {
        
        mainStage = stage;

        mainStage.setTitle("Tomada de Decisão");
        mainStage.setResizable(false);
        mainStage.centerOnScreen();

        mainStage.setOnCloseRequest(WindowEvent -> {

            Platform.exit();
            System.exit(0);
        });
        
        mostrarScenePrincipal();
    }

    public void mostrarScenePrincipal() throws IOException {

        mainPane = FXMLLoader.load(getClass().getResource("/views/Scene.fxml"));
        
        Scene scene = new Scene (mainPane);
        scene.getStylesheets().add("/styles/Styles.css");
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
