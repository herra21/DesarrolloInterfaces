package dao;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import static database.consultasSQL.comprobarUsuarioAlumno;
import static database.consultasSQL.comprobarUsuarioProfesor;

public class UsuarioDAOImp implements UsuarioDAO{

    private Session session;
    private Transaction transaction;

    private Usuario usuario;

    @Override
    public Usuario iniciarSesionAlumno(String nombreUsuario, String contrasenia, String cargo) {

        session = new Configuration().configure().buildSessionFactory().openSession();

        Query<Usuario> query = session.createQuery(comprobarUsuarioAlumno, Usuario.class);
        query.setParameter("usuarioArgs", nombreUsuario);
        query.setParameter("contraseniaArgs", contrasenia);
        query.setParameter("cargoArgs", cargo);

        usuario = query.uniqueResult();

        session.close();

        // Devolvemos alumno para pasarle al controlador.
        return usuario;
    }

    @Override
    public Usuario iniciarSesionProfesor(String nombreUsuario, String contrasenia, String cargo) {

        session = new Configuration().configure().buildSessionFactory().openSession();

        Query<Usuario> query = session.createQuery(comprobarUsuarioProfesor, Usuario.class);
        query.setParameter("usuarioArgs", nombreUsuario);
        query.setParameter("contraseniaArgs", contrasenia);
        query.setParameter("cargoArgs", cargo);

        usuario = query.uniqueResult();

        session.close();

        // Devolvemos profesor para pasarle al controlador.
        return usuario;

    }
}
