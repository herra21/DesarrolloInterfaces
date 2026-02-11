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

    //Se carga automaticamente cuando se carga la vista xml, sirve para poblar la tabla de profesores.
    @FXML
    private void initialize(){

        //Marcamos las columnas como editables para poder editar los datos
        tvNotasAlumnos.setEditable(true);
        tbNota.setEditable(true);

        tbNombreAlumno.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAlumno().getNombreUsuario()));

        tbNota.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getNota()).asObject());

        //Parsear de double a string
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
    private void onEditarNota(TableColumn.CellEditEvent<Nota, Double> evento){
        Nota notaEditada = evento.getRowValue();
        Double nuevaNota = evento.getNewValue();

        // Validación básica
        if (nuevaNota < 0 || nuevaNota > 10) {
            crearAlerta("Número Inválido", "La nota debe de estar entre 0 y 10.", Alert.AlertType.ERROR);
            tvNotasAlumnos.refresh();
            return;
        }

        notaEditada.setNota(nuevaNota);

        NotaDAOImp notaDAO = new NotaDAOImp();
        //Actualizar en BD
        notaDAO.actualizarNota(notaEditada);
    }

    //Setter manual para cargar los modulos cuando se entra en la ventana de profesores.
    public void setProfesor(Usuario profesor){
        this.profesor = profesor;
        cargarModulos();
    }

    private void cargarModulos(){
        lblNombreProfesor.setText(profesor.getNombreUsuario());

        ObservableList<String> modulosList = FXCollections.observableArrayList();

        // Llenamos la lista de modulos
        for (Modulo m : profesor.getModulosQueImparte()) {
            modulosList.add(m.getNombre_modulo());
        }

        // Poblamos el choiceBox con los módulos.
        chbModulos.setItems(modulosList);

        // Seleccionamos el primer módulo por defecto
        if (!modulosList.isEmpty()) {
            chbModulos.getSelectionModel().selectFirst();
            // Llenamos la tabla inmediatamente dependiendo del módulo en el nos encontremos
            mostrarNotasModulo(chbModulos.getSelectionModel().getSelectedItem());
        }

        // Listener para actualizar la tabla según el módulo seleccionado
        chbModulos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarNotasModulo(newVal);
            }
        });
    }

    private void mostrarNotasModulo(String nombreModuloSeleccionado){
        ObservableList<Nota> notasAlumno = FXCollections.observableArrayList();

        for (Modulo m : profesor.getModulosQueImparte()) {
            if (m.getNombre_modulo().equals(nombreModuloSeleccionado)) {
                Set<Nota> listaNotas = m.getListaNotas();
                if (listaNotas != null) {
                    notasAlumno.addAll(listaNotas);
                }
            }
        }

        tvNotasAlumnos.setItems(notasAlumno);
    }

    private void crearAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
