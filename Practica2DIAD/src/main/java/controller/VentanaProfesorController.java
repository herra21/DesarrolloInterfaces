package controller;

import dao.NotaDAOImp;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.Modulo;
import model.Nota;
import model.Usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VentanaProfesorController {

    private Usuario profesor;

    @FXML
    private Label lblNombreProfesor;
    @FXML
    private ChoiceBox<String> chbModulos;
    @FXML
    private Button btnVolverLogin;
    @FXML
    private TableView<Nota> tvNotasAlumnos;
    @FXML
    private TableColumn<Nota, String> tbNombreAlumno;
    @FXML
    private TableColumn<Nota, Double> tbNota;

    @FXML
    private void initialize(){

        tvNotasAlumnos.setEditable(true);
        tbNota.setEditable(true);

        tbNombreAlumno.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAlumno().getNombreUsuario()));

        tbNota.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getNota()).asObject());

        tbNota.setCellFactory(
                TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter())
        );
    }

    @FXML
    private void onVolverALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paginaInicio.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ventana de Inicio");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btnVolverLogin.getScene().getWindow();
            loginStage.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEditarNota(TableColumn.CellEditEvent<Nota, Double> event){
        Nota notaEditada = event.getRowValue();
        Double nuevaNota = event.getNewValue();

        // Validación básica
        if (nuevaNota < 0 || nuevaNota > 10) {
            crearAlerta("Número Inválido", "La nota debe de estar entre 0 y 10.", Alert.AlertType.ERROR);
            tvNotasAlumnos.refresh();
            return;
        }

        notaEditada.setNota(nuevaNota);

        NotaDAOImp notaDAO = new NotaDAOImp();
        notaDAO.actualizarNota(notaEditada);
    }

    public void setProfesor(Usuario profesor){
        this.profesor = profesor;
        cargarModulos();
    }

    private void cargarModulos(){
        lblNombreProfesor.setText(profesor.getNombreUsuario());

        ObservableList<String> modulosList = FXCollections.observableArrayList();
        for (Modulo m : profesor.getModulosQueImparte()) {
            modulosList.add(m.getNombre_modulo());
        }

        chbModulos.setItems(modulosList);

        if (!modulosList.isEmpty()) {
            chbModulos.getSelectionModel().selectFirst();
            mostrarNotasModulo(chbModulos.getSelectionModel().getSelectedItem());
        }

        chbModulos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarNotasModulo(newVal);
            }
        });
    }

    private void mostrarNotasModulo(String nombreModuloSeleccionado){
        ObservableList<Nota> notasFiltradas = FXCollections.observableArrayList();

        for (Modulo m : profesor.getModulosQueImparte()) {
            if (m.getNombre_modulo().equals(nombreModuloSeleccionado)) {
                Set<Nota> listaNotas = m.getListaNotas();
                if (listaNotas != null) {
                    notasFiltradas.addAll(listaNotas);
                }
            }
        }

        tvNotasAlumnos.setItems(notasFiltradas);
    }

    private void crearAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
