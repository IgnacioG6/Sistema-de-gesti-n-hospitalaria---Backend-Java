package com.example.hospital.model;

import com.example.hospital.model.enums.EstadoFactura;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Factura {
    private int nroFactura;
    private Paciente paciente;
    private Cita cita;
    private List<ItemsFactura> itemsFactura;
    private BigDecimal subtotal;
    private BigDecimal descuenta;
    private BigDecimal total;
    private EstadoFactura estadoFactura;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public Factura(int nroFactura, Paciente paciente, Cita cita, BigDecimal subtotal, BigDecimal descuenta, BigDecimal total, EstadoFactura estadoFactura, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        this.nroFactura = nroFactura;
        this.paciente = paciente;
        this.cita = cita;
        this.itemsFactura = new ArrayList<>();
        this.subtotal = subtotal;
        this.descuenta = descuenta;
        this.total = total;
        this.estadoFactura = estadoFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }
}
