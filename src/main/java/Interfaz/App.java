package Interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 *
 * @author Grupo 6
 */
public class App extends Application {

    public static Scene scene;

    

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Configuraciones"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Que animal soy?");
        Image imagen = new Image("img\\icono.png");
        stage.getIcons().add(imagen);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


}
