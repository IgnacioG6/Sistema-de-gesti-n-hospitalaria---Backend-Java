package com.example.hospital.model;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.HorarioAtencion;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
public class Doctor {
    private static long contador = 1;

    @Setter(AccessLevel.NONE)
    private Long id;

    private String nombre;
    private String licenciaMedica;
    private Especialidad especialidad;
    private String email;
    private String telefono;
    private int añosExperiencia;
    private HorarioAtencion horarioAtencion;
    private boolean disponible;

    public Doctor() {
        this.id = contador++;
    }
}
