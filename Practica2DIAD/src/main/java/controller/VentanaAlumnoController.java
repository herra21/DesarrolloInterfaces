package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Nota;
import model.Usuario;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor

public class VentanaAlumnoController {

    private Usuario alumno;

    @FXML
    private Label lblNombreAlumno;
    @FXML
    private ChoiceBox<String> chbModulos;
    @FXML
    private Button btnVolverLogin;
    @FXML
    private TableView<Nota> tvNotasModulos;
    @FXML
    private TableColumn<Nota, String> tbNombreModulo;
    @FXML
    private TableColumn<Nota, Double> tbNota;
    @FXML
    private TableColumn<Nota, String> tbDescripcion;

    //Se carga automaticamente cuando se carga la vista xml, sirve para poblar la tabla de alumnos.
    @FXML
    private void initialize() {
        tbNombreModulo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getModulo().getNombre_modulo()));

        tbNota.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getNota()).asObject());

        tbDescripcion.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDescripcion()));
    }


    // Método para volver al login
    @FXML
    private void onVolverALogin(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paginaInicio.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ventana de Inicio");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btnVolverLogin.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Setter manual para cargar los modulos cuando se entra en la ventana de alumnos.
    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
        cargarModulos();
    }

    private void cargarModulos() {
        lblNombreAlumno.setText(alumno.getNombreUsuario());

        Set<String> nombresModulos = new HashSet<>();

        // Llenamos la lista de modulos
        for (Nota n : alumno.getListaNotas()) {
            nombresModulos.add(n.getModulo().getNombre_modulo());
        }

        // Convertimos a ObservableList
        ObservableList<String> modulosList = FXCollections.observableArrayList();
        modulosList.add("Todos los módulos");
        modulosList.addAll(nombresModulos);

        // Poblamos el choiceBox con los módulos.
        chbModulos.setItems(modulosList);

        // Seleccionamos "Todos los módulos" por defecto
        chbModulos.getSelectionModel().selectFirst();

        // Llenamos la tabla inmediatamente dependiendo del módulo en el nos encontremos
        mostrarNotasModulo(chbModulos.getSelectionModel().getSelectedItem());

        // Listener para actualizar la tabla según el módulo seleccionado
        chbModulos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarNotasModulo(newVal);
            }
        });
    }

    private void mostrarNotasModulo(String nombreModuloSeleccionado) {
        ObservableList<Nota> notasModulo = FXCollections.observableArrayList();

        for (Nota n : alumno.getListaNotas()) {
            // Si se selecciona "Todos los módulos", añadimos todas las notas
            if (nombreModuloSeleccionado.equals("Todos los módulos") ||
                    n.getModulo().getNombre_modulo().equals(nombreModuloSeleccionado)) {
                notasModulo.add(n);
            }
        }

        tvNotasModulos.setItems(notasModulo);
    }


}
