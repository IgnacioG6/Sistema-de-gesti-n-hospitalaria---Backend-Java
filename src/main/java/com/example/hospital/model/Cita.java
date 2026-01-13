package com.example.hospital.model;

import com.example.hospital.model.enums.EstadoCita;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cita {
    private Long id;
    private Paciente paciente;
    private Doctor doctor;
    private LocalDateTime fechaHora;
    private String motivo;
    private EstadoCita estadoCita;
    private int duracion;
    private String notas;
}
