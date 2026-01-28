package com.example.hospital.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "receta_items")
@Setter
@Getter
@NoArgsConstructor
public class RecetaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    private Receta receta;

    private String medicamento;
    private String dosis;
    private String frecuencia;
    private int duracionDias;

    @Column(length = 500)
    private String instrucciones;
}