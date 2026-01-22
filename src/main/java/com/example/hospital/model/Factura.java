package com.example.hospital.model;

import com.example.hospital.dto.ItemsFacturaDTO;
import com.example.hospital.model.enums.EstadoFactura;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Factura {

    private static long contador = 1;

    @Setter(AccessLevel.NONE)
    private Long id;


    private String nroFactura;
    private Paciente paciente;
    private Cita cita;
    private List<ItemsFacturaDTO> itemsFactura;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private EstadoFactura estadoFactura;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public Factura() {
        this.estadoFactura = EstadoFactura.PENDIENTE;
        this.nroFactura = String.format("FAC-%05d", contador);
        this.id = contador++;
        this.fechaEmision = LocalDate.now();
        this.fechaVencimiento = LocalDate.now().plusDays(30);
    }

    public BigDecimal getSubtotal() {
        return itemsFactura.stream()
                .map(ItemsFacturaDTO::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        BigDecimal subtotal = getSubtotal();
        BigDecimal descuentoActual = descuento != null ? descuento : BigDecimal.ZERO;
        return subtotal.subtract(descuentoActual);
    }
}
