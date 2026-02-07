package controller;

import dao.UsuarioDAOImp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;


public class LoginController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContrasenia;
    @FXML
    private ComboBox<String> cmbCargo;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnSalir;

    @FXML
    private void onLimpiar(){
        tfUsuario.clear();
        tfContrasenia.clear();
    }
    @FXML
    public void initialize() {
        cmbCargo.getItems().addAll("Selecciona una opción...","Alumn@", "Profesor/a");
        cmbCargo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onSalir(){
        System.exit(0);
    }

    @FXML
    private void onEntrar(){
        UsuarioDAOImp usuarioDAOImp =  new UsuarioDAOImp();
        String nombreUsuario = tfUsuario.getText();
        String contrasenia = tfContrasenia.getText();
        String cargo = cmbCargo.getValue();

        if(nombreUsuario.isEmpty() || contrasenia.isEmpty() || cmbCargo.getValue().equals("Selecciona una opción...")){
            crearAlerta("Datos incorrectos", "Algún dato es incorrecto o está vacío.", Alert.AlertType.WARNING);
            onLimpiar();
            return;
        }
        //Entiende Strings
        Usuario usuario = null;

        //NO FUNCIONA, NO ENTIENDO PORQUÉ
        /*
        if (usuario!=null) {
            if (usuario.getRol().equals("Profesor/a")) {
                abrirVentanaProfesores();
            }else if (usuario.getRol().equals("Alumn@")) {
                abrirVentanaAlumnos();
            }
        } else {
            crearAlerta("Usuario desconocido", "No se encuentra usuario con esas credenciales", Alert.AlertType.ERROR);
        }
        */

        if (cargo.equals("Profesor/a")) {
            usuario = usuarioDAOImp.iniciarSesionProfesor(nombreUsuario, contrasenia, cargo);
            if (usuario != null) {
                abrirVentanaProfesores(usuario);
            }
        } else if (cargo.equals("Alumn@")) {
            usuario = usuarioDAOImp.iniciarSesionAlumno(nombreUsuario, contrasenia, cargo);
            if (usuario != null) {
                abrirVentanaAlumnos(usuario);
            }
        }

        if (usuario == null) {
            crearAlerta("Usuario desconocido", "No se encuentra usuario con esas credenciales", Alert.AlertType.ERROR);
        }
    }

    private void abrirVentanaProfesores(Usuario profesor) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventanaProfesores.fxml"));
            Parent root = loader.load();

            VentanaProfesorController ventanaProfesorController = loader.getController();
            ventanaProfesorController.setProfesor(profesor);

            Stage stage = new Stage();
            stage.setTitle("Ventana Profesor");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btnEntrar.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaAlumnos(Usuario alumno) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventanaAlumnos.fxml"));
            Parent root = loader.load();

            AlumnoController alumnoController = loader.getController();
            alumnoController.setAlumno(alumno);

            Stage stage = new Stage();
            stage.setTitle("Ventana Alumno");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btnEntrar.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
