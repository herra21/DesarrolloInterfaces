    package model;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.Set;

    @ToString(exclude = {"listaNotas", "listaProfesores"})
    @EqualsAndHashCode(exclude = {"listaNotas", "listaProfesores"})
    @AllArgsConstructor
    @NoArgsConstructor
    @Data

    @Entity
    @Table(name = "modulos")

    public class Modulo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column
        private String nombre_modulo;

        @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
        private Set<Nota> listaNotas;

        @ManyToMany
        @JoinTable(
                name = "profesor_modulo",
                joinColumns = @JoinColumn(name = "id_modulo"),
                inverseJoinColumns = @JoinColumn(name = "id_profesor")
        )
        private Set<Usuario> listaProfesores;

    }
