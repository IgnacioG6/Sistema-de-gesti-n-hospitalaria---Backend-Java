package com.example.hospital.model;

import com.example.hospital.model.enums.EstadoFactura;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nroFactura;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemsFactura> itemsFactura = new ArrayList<>();

    private BigDecimal descuento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estadoFactura;

    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    @Transient
    public BigDecimal getSubtotal() {
        return itemsFactura.stream()
                .map(ItemsFactura::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public BigDecimal getTotal() {
        BigDecimal subtotal = getSubtotal();
        BigDecimal descuentoActual = descuento != null ? descuento : BigDecimal.ZERO;
        return subtotal.subtract(descuentoActual);
    }

    public Factura() {
        this.estadoFactura = EstadoFactura.PENDIENTE;
        this.fechaEmision = LocalDate.now();
        this.fechaVencimiento = LocalDate.now().plusDays(30);
    }
}
