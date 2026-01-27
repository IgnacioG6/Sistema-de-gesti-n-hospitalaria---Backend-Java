package com.example.hospital.mapper;

import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.model.Factura;

public class FacturaMapper {

    public static FacturaResponseDTO toResponseDTO(Factura factura) {
        if (factura == null) return null;

        return new FacturaResponseDTO(
                factura.getId(),
                factura.getNroFactura(),
                factura.getPaciente().getId(),
                factura.getPaciente().getNombre(),
                factura.getCita().getId(),
                factura.getItemsFactura(),
                factura.getSubtotal(),
                factura.getDescuento(),
                factura.getTotal(),
                factura.getEstadoFactura().toString(),
                factura.getFechaEmision(),
                factura.getFechaVencimiento());
    }

}
