//Clase padre de JavaFx. Obliga a sobreescribir el method start
import javafx.application.Application;
//Se encargar de cargar los archivos FXML. Sirve la para leer el .fxml
import javafx.fxml.FXMLLoader;
//La clase Scene representa lo que se muestra dentro de la ventana principal. Contiene la interfaz gráfica
import javafx.scene.Scene;
//Stage es la ventana principal
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        //Busca el archivo login.fxml dentro de la carpeta indicada. Conecta la vista con el controlador.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/paginaInicio.fxml"));
        //Carga el contenido FXML. Crea el scene que es lo que va dentro de la ventana (Stage)
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Ventana de Inicio"); // Título de la ventana
        stage.setScene(scene); //Contenido que debe mostrar la ventana
        stage.sizeToScene(); //Ajusta la ventana al tamaño del AnchorPane
        //stage.setWidth(600);    // prefWidth del AnchorPane
        //stage.setHeight(366);   // prefHeight del AnchorPane
        stage.setResizable(false); //No se puede modificar el tamaño
        stage.show(); //Mostrar la ventana
    }
}
