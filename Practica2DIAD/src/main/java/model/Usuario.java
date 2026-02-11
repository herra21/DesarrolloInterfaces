package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ToString(exclude = {"listaNotas", "modulosQueImparte"})
@EqualsAndHashCode(exclude = {"listaNotas", "modulosQueImparte"})

@Entity
@Table(name = "usuarios")


public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column
    private String contrasenia;

    @Column
    private String rol;

    public Usuario(String nombreUsuario, String contrasenia, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private Set<Nota> listaNotas;

    @ManyToMany(mappedBy = "listaProfesores")
    private Set<Modulo> modulosQueImparte;
}
