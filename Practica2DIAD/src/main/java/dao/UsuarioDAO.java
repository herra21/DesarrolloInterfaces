package dao;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Usuario;

public interface UsuarioDAO {
    // Le tenemos que pasar Strings
    Usuario iniciarSesionAlumno(String nombreUsuario, String contrasenia, String cargo);
    Usuario iniciarSesionProfesor(String nombreUsuario, String contrasenia, String cargo);
}
