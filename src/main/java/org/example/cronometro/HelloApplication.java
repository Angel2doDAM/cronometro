package org.example.cronometro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Establecer el icono de la aplicación
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

            // Cargar la vista inicial, por ejemplo, hello-view.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("intro.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Cronómetro CBVdC");  // Título de la ventana
            stage.setScene(scene);            // Establece la escena
            stage.show();                     // Muestra la ventana
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}