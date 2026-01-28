package com.example.hospital.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historias_clinicas")
@NoArgsConstructor
@Setter
@Getter
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    private LocalDateTime fecha;
    private String diagnostico;
    private String sintomas;
    private String tratamientoPrescrito;
    private String observaciones;




}
