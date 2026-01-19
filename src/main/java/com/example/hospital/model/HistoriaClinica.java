package com.example.hospital.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class HistoriaClinica {

    private static long contador = 1;

    @Setter(AccessLevel.NONE)
    private Long id;

    private Paciente paciente;
    private Doctor doctor;
    private Cita cita;
    private LocalDateTime fecha;
    private String diagnostico;
    private String sintomas;
    private String tratamientoPrescrito;
    private String observaciones;

    public HistoriaClinica() {
        this.id = contador++;
    }


}
