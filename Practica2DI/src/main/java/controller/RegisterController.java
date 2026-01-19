package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

//Clase padre de JavaFx. Obliga a sobreescribir el método start
import javafx.application.Application;
//Se encargar de cargar los archivos FXML. Sirve la para leer el .fxml
import javafx.fxml.FXMLLoader;
//La clase Scene representa lo que se muestra dentro de la ventana principal. Contiene la interfaz gráfica
import javafx.scene.Scene;
//Stage es la ventana principal
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    public void initialize() {
        cmbCargo.getItems().addAll(
                "Alumn@",
                "Profesor/a"
        );
    }

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox cmbCargo;

    @FXML
    private void onEntrar() {

    }
    @FXML
    private void onSalir(){
        System.exit(0);
    }
    @FXML
    private void onLimpiar(){
        tfUsuario.clear();
        tfPass.clear();
        tfNombre.clear();
        cmbCargo.getSelectionModel().clearSelection();
        cmbCargo.setValue(null);
    }
}
