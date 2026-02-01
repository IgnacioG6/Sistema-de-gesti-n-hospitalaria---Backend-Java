package com.example.hospital.mapper;

import com.example.hospital.dto.ItemsFacturaDTO;
import com.example.hospital.dto.request.ActualizarFacturaRequestDTO;
import com.example.hospital.dto.request.FacturaRequestDTO;
import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.model.Cita;
import com.example.hospital.model.Factura;
import com.example.hospital.model.ItemsFactura;

import java.math.BigDecimal;
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

    public static Factura toEntity(FacturaRequestDTO dto, Cita cita) {
        Factura factura = new Factura();
        factura.setPaciente(cita.getPaciente());
        factura.setCita(cita);
        factura.setDescuento(dto.descuento());

        dto.items().forEach(itemDTO -> {
            ItemsFactura item = new ItemsFactura();
            item.setFactura(factura);
            item.setDescripcion(itemDTO.descripcion());
            item.setCantidad(itemDTO.cantidad());
            item.setPrecioUnitario(itemDTO.precioUnitario());
            item.setTotal(itemDTO.precioUnitario().multiply(BigDecimal.valueOf(itemDTO.cantidad())));
            factura.getItemsFactura().add(item);
        });

        return factura;
    }

    // En FacturaMapper
    public static void updateEntity(Factura factura, ActualizarFacturaRequestDTO dto) {
        factura.setDescuento(dto.descuento());

        factura.getItemsFactura().clear();

        dto.items().forEach(itemDTO -> {
            ItemsFactura item = new ItemsFactura();
            item.setFactura(factura);
            item.setDescripcion(itemDTO.descripcion());
            item.setCantidad(itemDTO.cantidad());
            item.setPrecioUnitario(itemDTO.precioUnitario());
            item.setTotal(itemDTO.precioUnitario().multiply(BigDecimal.valueOf(itemDTO.cantidad())));
            factura.getItemsFactura().add(item);
        });
    }

}
