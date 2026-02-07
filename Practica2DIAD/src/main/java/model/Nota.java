package model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)

@Entity
@Table(name = "notas")

@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    @ToString.Exclude
    private Usuario alumno;

    @ManyToOne
    @JoinColumn(name = "id_modulo")
    @ToString.Exclude
    private Modulo modulo;

    @Column
    private double nota;

    @Column
    private String descripcion;



}
