    package model;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.List;
    import java.util.Set;

    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Data

    @Entity
    @Table(name = "modulos")

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)

    public class Modulo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private int id;

        @Column
        private String nombre_modulo;

        @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
        @ToString.Exclude
        private Set<Nota> listaNotas;

        @ManyToMany
        @JoinTable(
                name = "profesor_modulo",
                joinColumns = @JoinColumn(name = "id_modulo"),
                inverseJoinColumns = @JoinColumn(name = "id_profesor")
        )
        @ToString.Exclude
        private Set<Usuario> listaProfesores;

    }
