package com.example.hospital.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HistoriaClinica {
    private Long id;
    private Paciente paciente;
    private Doctor doctor;
    private Cita cita;
    private LocalDate fecha;
    private String diagnostico;
    private String sintomas;
    private String tratamientoPrescrito;
    private String observaciones;

}
