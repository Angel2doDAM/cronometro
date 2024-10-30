package org.example.cronometro;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Label Crono;

    @FXML
    private TextField MarcadorLocal;

    @FXML
    private TextField MarcadorVisitante;

    @FXML
    private Label keyResLoc;

    @FXML
    private Label keyResVis;

    @FXML
    private Label keySumLoc;

    @FXML
    private Label keySumVis;

    private String teclaPulsada;

    private String sumaLocal = "W";
    private String restaLocal = "S";
    private String sumaVisitante = "T";
    private String restaVisitante = "G";

    private boolean encendido = false;

    private int minutosActual = 0;
    private int segundosActual = 0;
    private int totalMinutos = 30;
    private int golesLocal = 0;
    private int golesVisitante = 0;

    private Timeline timeline;

    // Rutas de archivo
    private final Path archivoLocal = Paths.get("src/main/resources/files/marcador_local.txt");
    private final Path archivoVisitante = Paths.get("src/main/resources/files/marcador_visitante.txt");

    @FXML
    void OnKeyPressed(KeyEvent event) {
        teclaPulsada = event.getCode().toString();

        if (Objects.equals(teclaPulsada, sumaLocal)){
            golesLocal++;
            guardarMarcadorEnArchivo(archivoLocal, golesLocal);
        } else if (Objects.equals(teclaPulsada, restaLocal)) {
            golesLocal--;
            guardarMarcadorEnArchivo(archivoLocal, golesLocal);
        } else if (Objects.equals(teclaPulsada, sumaVisitante)) {
            golesVisitante++;
            guardarMarcadorEnArchivo(archivoVisitante, golesVisitante);
        } else if (Objects.equals(teclaPulsada, restaVisitante)) {
            golesVisitante--;
            guardarMarcadorEnArchivo(archivoVisitante, golesVisitante);
        }

        /*switch (teclaPulsada) {
            case "W":
                golesLocal++;
                guardarMarcadorEnArchivo(archivoLocal, golesLocal);
                break;
            case "S":
                golesLocal--;
                guardarMarcadorEnArchivo(archivoLocal, golesLocal);
                break;
            case "T":
                golesVisitante++;
                guardarMarcadorEnArchivo(archivoVisitante, golesVisitante);
                break;
            case "G":
                golesVisitante--;
                guardarMarcadorEnArchivo(archivoVisitante, golesVisitante);
                break;
            default:
                return;
        }*/

        MarcadorLocal.setText(String.valueOf(golesLocal));
        MarcadorVisitante.setText(String.valueOf(golesVisitante));
    }

    @FXML
    void OnStartClic(ActionEvent event) {
        if (!encendido) {  // Solo iniciar si no está encendido
            encendido = true;
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarCronometro()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    void OnResetClic(ActionEvent event) {
        // Reinicia el cronómetro y el marcador
        minutosActual = 0;
        segundosActual = 0;
        updateCronoLabel();
        detenerCronometro();
        golesLocal = 0;
        golesVisitante = 0;
        MarcadorLocal.setText(String.valueOf(golesLocal));
        MarcadorVisitante.setText(String.valueOf(golesVisitante));
        // Reinicia los archivos de marcador
        guardarMarcadorEnArchivo(archivoLocal, golesLocal);
        guardarMarcadorEnArchivo(archivoVisitante, golesVisitante);
    }

    @FXML
    void OnEditClic(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la vista intro.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("intro.fxml"));
            Parent root = loader.load();

            // Cambiar la escena a intro.fxml
            Stage stage = (Stage) Crono.getScene().getWindow(); // Obtener la ventana actual
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void OnStopClic(ActionEvent event) {
        detenerCronometro();
    }

    private void detenerCronometro() {
        if (timeline != null) {
            timeline.stop();
            encendido = false;
        }
    }

    private void actualizarCronometro() {
        if (encendido) {
            segundosActual++;
            if (segundosActual == 60) {
                segundosActual = 0;
                minutosActual++;
            }
            updateCronoLabel();
            if (minutosActual >= totalMinutos) {
                encendido = false;
                detenerCronometro(); // Detenemos el cronómetro si se llega al tiempo máximo
            }
        }
    }

    private void updateCronoLabel() {
        String minutosTexto = (minutosActual < 10) ? "0" + minutosActual : String.valueOf(minutosActual);
        String segundosTexto = (segundosActual < 10) ? "0" + segundosActual : String.valueOf(segundosActual);
        Crono.setText(minutosTexto + ":" + segundosTexto);
    }

    private void guardarMarcadorEnArchivo(Path archivo, int marcador) {
        try {
            Files.writeString(archivo, String.valueOf(marcador), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MarcadorLocal.setText(String.valueOf(golesLocal));
        MarcadorVisitante.setText(String.valueOf(golesVisitante));
        updateCronoLabel();

        // Crear carpeta y archivos si no existen
        try {
            Files.createDirectories(archivoLocal.getParent());
            guardarMarcadorEnArchivo(archivoLocal, golesLocal);
            guardarMarcadorEnArchivo(archivoVisitante, golesVisitante);
        } catch (IOException e) {
            System.out.println("Error al crear archivos de marcador");
            e.printStackTrace();
        }
    }

    @FXML
    public void requestFocus() {
        // Código para solicitar el foco
        Crono.getParent().requestFocus();
    }

    public void establecerValores(int tiempoFinal, String sumLoc, String resLoc, String sumVis, String resVis) {
        totalMinutos = tiempoFinal;
        sumaLocal = sumLoc;
        restaLocal = resLoc;
        sumaVisitante = sumVis;
        restaVisitante = resVis;
        keySumLoc.setText(sumLoc);
        keyResLoc.setText(resLoc);
        keySumVis.setText(sumVis);
        keyResVis.setText(resVis);
    }
}