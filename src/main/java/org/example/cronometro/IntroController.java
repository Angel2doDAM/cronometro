package org.example.cronometro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

    @FXML
    private ComboBox Minutos;

    @FXML
    private TextField ResLocal;

    @FXML
    private TextField ResVisit;

    @FXML
    private TextField SumLocal;

    @FXML
    private TextField SumVisit;

    @FXML
    void OnAceptarClic(ActionEvent event) {
        if (Minutos.getValue() == null) {
            Minutos.setValue("30");
        }
        if (Objects.equals(SumLocal.getText(), "")) {
            SumLocal.setText("W");
        }
        if (Objects.equals(ResLocal.getText(), "")) {
            ResLocal.setText("S");
        }
        if (Objects.equals(SumVisit.getText(), "")) {
            SumVisit.setText("T");
        }
        if (Objects.equals(ResVisit.getText(), "")) {
            ResVisit.setText("G");
        }
        try {
            // Obtener los valores seleccionados
            int tiempoFinal = Integer.parseInt((String) Minutos.getValue());
            String sumaLocal = SumLocal.getText().toUpperCase(Locale.ROOT);
            String restaLocal = ResLocal.getText().toUpperCase(Locale.ROOT);
            String sumaVisitante = SumVisit.getText().toUpperCase(Locale.ROOT);
            String restaVisitante = ResVisit.getText().toUpperCase(Locale.ROOT);

            // Cargar el archivo FXML de la vista hello-view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de HelloController y establecer los valores
            HelloController helloController = loader.getController();
            helloController.establecerValores(tiempoFinal, sumaLocal, restaLocal, sumaVisitante, restaVisitante);

            // Cambiar la escena a hello-view.fxml
            Stage stage = (Stage) Minutos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Por favor, selecciona un valor válido para el tiempo.");
        }
    }

    @FXML
    void OnCancelarClic(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la vista de Hello
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Obtener el Stage actual desde el botón de cancelación
            Stage stage = (Stage) Minutos.getScene().getWindow();

            // Cambiar la escena a la vista de Hello
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] mins={"1", "10", "20", "25", "30"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Minutos.getItems().addAll(mins);
    }
}