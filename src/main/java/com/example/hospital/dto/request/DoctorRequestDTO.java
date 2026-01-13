package com.example.hospital.dto.request;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.HorarioAtencion;

public record DoctorRequestDTO(
       String nombre,
       String licenciaMedica,
       Especialidad especialidad,
       String email,
       String telefono,
       int añosExperiencia,
       HorarioAtencion horarioAtencion
) {}
