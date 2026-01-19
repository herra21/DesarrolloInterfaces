package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPass;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnSalir;

    @FXML
    private void onLimpiar(){
        tfPass.clear();
        tfUsuario.clear();
    }
    @FXML
    private void onSalir(){
        System.exit(0);
    }
}
