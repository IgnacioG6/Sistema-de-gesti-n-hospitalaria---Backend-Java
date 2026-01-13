package com.example.hospital.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prescripciones {
    private String medicamento;
    private double dosis;
    private double frecuencia;
    private int duracionDias;
    private String instruccionesEspecificas;
}
