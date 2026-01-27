package com.example.hospital.service;

import com.example.hospital.dto.ItemsFacturaDTO;
import com.example.hospital.dto.request.FacturaRequestDTO;

import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.exception.*;
import com.example.hospital.mapper.FacturaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.Factura;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.model.enums.EstadoFactura;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacturaService {

    private final List<Factura> facturas = new ArrayList<>();
    private final CitaService  citaService;

    public FacturaService( CitaService citaService) {
        this.citaService = citaService;
    }

    public FacturaResponseDTO crearFactura(FacturaRequestDTO facturaRequestDTO) {
        Cita cita = citaService.buscarEntidadPorId(facturaRequestDTO.citaId());

        if (cita.getEstadoCita() != EstadoCita.COMPLETADO) {
            throw new ValidacionException("La cita debe estar completada");
        }

        if (facturaRequestDTO.items().isEmpty()) {
            throw new ValidacionException("La factura debe tener al menos un item");
        }

        if (facturaRequestDTO.descuento().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacionException("El descuento no puede ser negativo");
        }

        BigDecimal subtotal = facturaRequestDTO.items().stream()
                .map(ItemsFacturaDTO::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (facturaRequestDTO.descuento().compareTo(subtotal) > 0) {
            throw new ValidacionException("El descuento no puede ser mayor que el subtotal");
        }

        Factura factura = new Factura();
        factura.setPaciente(cita.getPaciente());
        factura.setCita(cita);
        factura.setItemsFactura(facturaRequestDTO.items());
        factura.setDescuento(facturaRequestDTO.descuento());


        facturas.add(factura);

        return FacturaMapper.toResponseDTO(factura);
    }


    public List<FacturaResponseDTO> listarFacturas() {
        return facturas.stream().map(FacturaMapper::toResponseDTO).toList();
    }

    public FacturaResponseDTO buscarFacturaPorId(Long id) {
        Factura factura = buscarPorEntidad(id);
        return FacturaMapper.toResponseDTO(factura);
    }

    public Factura buscarPorEntidad(Long id){
        return facturas.stream()
                .filter(factura -> factura.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new EntidadNoEncontradaException("Factura no encontrada con id: " + id));
    }

    public List<FacturaResponseDTO> buscarFacturasPorPaciente(Long id){
        return facturas.stream()
                .filter(factura -> factura.getPaciente().getId().equals(id))
                .map(FacturaMapper::toResponseDTO)
                .toList();
    }

    public List<FacturaResponseDTO> buscarFacturaPorEstado(EstadoFactura estadoFactura){
        return facturas.stream()
                .filter(factura -> estadoFactura.equals(factura.getEstadoFactura()))
                .map(FacturaMapper::toResponseDTO)
                .toList();
    }


    public FacturaResponseDTO actualizarEstadoFactura(Long id, EstadoFactura nuevoEstado) {
        Factura factura = buscarPorEntidad(id);

        EstadoFactura estadoActual = factura.getEstadoFactura();

        switch (estadoActual) {
            case PENDIENTE:
                if (nuevoEstado != EstadoFactura.PAGADO &&
                        nuevoEstado != EstadoFactura.VENCIDO &&
                        nuevoEstado != EstadoFactura.CANCELADO) {
                    throw new EstadoInvalidoException("Desde PENDIENTE solo se puede pasar a PAGADO, VENCIDO o CANCELADO");
                }
                break;

            case VENCIDO:
                if (nuevoEstado != EstadoFactura.PAGADO &&
                        nuevoEstado != EstadoFactura.CANCELADO) {
                    throw new EstadoInvalidoException("Desde VENCIDO solo se puede pasar a PAGADO o CANCELADO");
                }
                break;

            case PAGADO:
            case CANCELADO:
                throw new EstadoInvalidoException("No se puede cambiar el estado de una factura " + estadoActual);
        }

        factura.setEstadoFactura(nuevoEstado);
        return FacturaMapper.toResponseDTO(factura);
    }

    }


