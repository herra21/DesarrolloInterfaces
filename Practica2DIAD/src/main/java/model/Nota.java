package model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ToString(exclude = {"alumno", "modulo"})
@EqualsAndHashCode(exclude = {"alumno", "modulo"})

@Entity
@Table(name = "notas")


public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Usuario alumno;

    @ManyToOne
    @JoinColumn(name = "id_modulo")
    private Modulo modulo;

    @Column
    private double nota;

    @Column
    private String descripcion;



}
