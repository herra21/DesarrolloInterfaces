package database;

public class consultasSQL {
    public static String comprobarUsuarioAlumno = "FROM Usuario u " +
            "LEFT JOIN FETCH u.listaNotas ln " +
            "LEFT JOIN FETCH ln.modulo " +
            "WHERE u.nombreUsuario = :usuarioArgs " +
            "AND u.contrasenia = :contraseniaArgs " +
            "AND u.rol = :cargoArgs";

    public static String comprobarUsuarioProfesor = "FROM Usuario u " +
            "LEFT JOIN FETCH u.listaNotas ln " +
            "LEFT JOIN FETCH ln.modulo " +
            "LEFT JOIN FETCH u.modulosQueImparte ms " +
            "LEFT JOIN FETCH ms.listaNotas mn " +
            "WHERE u.nombreUsuario = :usuarioArgs " +
            "AND u.contrasenia = :contraseniaArgs " +
            "AND u.rol = :cargoArgs";
}
