package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)

@Entity
@Table(name = "usuarios")

@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column
    private String contrasenia;

    @Column
    private String rol;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Nota> listaNotas;

    public Usuario(String nombreUsuario, String contrasenia, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    @ManyToMany(mappedBy = "listaProfesores")
    @ToString.Exclude
    private Set<Modulo> modulosQueImparte;
}
