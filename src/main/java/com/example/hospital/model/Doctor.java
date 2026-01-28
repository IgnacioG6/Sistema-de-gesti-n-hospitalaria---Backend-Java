package com.example.hospital.model;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.HorarioAtencion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctores")
@NoArgsConstructor
@Setter
@Getter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String licenciaMedica;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    private String email;
    private String telefono;

    private int añosExperiencia;

    @Enumerated(EnumType.STRING)
    private HorarioAtencion horarioAtencion;

    private boolean disponible;

}
