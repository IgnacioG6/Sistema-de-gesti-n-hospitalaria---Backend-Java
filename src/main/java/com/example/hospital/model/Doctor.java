package com.example.hospital.model;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.HorarioAtencion;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class Doctor {
    private Long id;
    private String nombre;
    private String licenciaMedica;
    private Especialidad especialidad;
    private String email;
    private String telefono;
    private int añosExperiencia;
    private HorarioAtencion horarioAtencion;
    private boolean disponible;
}
