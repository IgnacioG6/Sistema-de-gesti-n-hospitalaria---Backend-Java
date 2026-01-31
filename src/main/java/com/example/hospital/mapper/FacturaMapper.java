package com.example.hospital.mapper;

import com.example.hospital.dto.ItemsFacturaDTO;
import com.example.hospital.dto.RecetaItemDTO;
import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.model.Factura;

import java.util.List;

public class FacturaMapper {

    public static FacturaResponseDTO toResponseDTO(Factura factura) {
        if (factura == null) return null;

        List<ItemsFacturaDTO> itemsDTO = factura.getItemsFactura().stream()
                .map(item -> new ItemsFacturaDTO(
                        item.getDescripcion(),
                        item.getCantidad(),
                        item.getPrecioUnitario()
                ))
                .toList();

        return new FacturaResponseDTO(
                factura.getId(),
                factura.getNroFactura(),
                factura.getPaciente().getId(),
                factura.getPaciente().getNombre(),
                factura.getCita().getId(),
                itemsDTO,
                factura.getSubtotal(),
                factura.getDescuento(),
                factura.getTotal(),
                factura.getEstadoFactura().toString(),
                factura.getFechaEmision(),
                factura.getFechaVencimiento());
    }

}
