package com.example.hospital.model;

import com.example.hospital.dto.RecetaItemDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Receta {

    @Setter(AccessLevel.NONE)
    private Long id;

    private HistoriaClinica historiaClinica;
    private List<RecetaItemDTO> medicamentos = new ArrayList<>();
    private String instruccionesGenerales;
    private LocalDate fechaEmision;
    private LocalDate validaHasta;

    private static long contador = 1;

    public Receta() {
        this.id = contador++;
        this.fechaEmision = LocalDate.now();
    }
}