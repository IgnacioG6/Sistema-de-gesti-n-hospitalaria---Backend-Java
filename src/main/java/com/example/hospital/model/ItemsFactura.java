package com.example.hospital.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "factura_items")
@Getter
@Setter
@NoArgsConstructor
public class ItemsFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;

    private String descripcion;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal total;
}
