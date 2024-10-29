package org.example.cronometro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Label Crono;

    @FXML
    private TextField MarcadorLocal;

    @FXML
    private TextField MarcadorVisitante;

    String teclaPulsada;

    int GolesLocal=0;
    int GolesVisitante=0;

    @FXML
    void OnKeyPressed(KeyEvent event) {
        teclaPulsada = event.getCharacter();
        System.out.printf(teclaPulsada);
        switch (teclaPulsada) {
            case "w":
                GolesLocal++;
                break;
            case "s":
                GolesLocal--;
                break;
            case "t":
                GolesVisitante++;
                break;
            case "g":
                GolesVisitante--;
                break;
            default:
        }
        MarcadorLocal.setText(String.valueOf(GolesLocal));
        MarcadorVisitante.setText(String.valueOf(GolesVisitante));
    }

    @FXML
    void OnResetClic(ActionEvent event) {

    }

    @FXML
    void OnStartClic(ActionEvent event) {

    }

    @FXML
    void OnStopClic(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MarcadorLocal.setText(String.valueOf(GolesLocal));
        MarcadorVisitante.setText(String.valueOf(GolesVisitante));
    }
}