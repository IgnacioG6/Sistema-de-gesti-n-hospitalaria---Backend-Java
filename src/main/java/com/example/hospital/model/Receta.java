package com.example.hospital.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Receta {
    private Long id;
    private HistoriaClinica historiaClinica;
    private List<Prescripciones> prescripciones;
    private String instrucciones;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public Receta(Long id, HistoriaClinica historiaClinica, String instrucciones, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        this.id = id;
        this.historiaClinica = historiaClinica;
        this.prescripciones = new ArrayList<>();
        this.instrucciones = instrucciones;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }
}
