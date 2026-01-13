package com.example.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemsFactura {
    private String descripcion;
    private BigDecimal precioUnitario;
    private int cantiadad;
    private BigDecimal total;
}
