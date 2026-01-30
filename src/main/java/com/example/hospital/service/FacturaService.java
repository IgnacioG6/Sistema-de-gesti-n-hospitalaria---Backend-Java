package com.example.hospital.service;

import com.example.hospital.dto.ItemsFacturaDTO;
import com.example.hospital.dto.RecetaItemDTO;
import com.example.hospital.dto.request.FacturaRequestDTO;

import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.exception.*;
import com.example.hospital.mapper.FacturaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.Factura;
import com.example.hospital.model.ItemsFactura;
import com.example.hospital.model.RecetaItem;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.model.enums.EstadoFactura;
import com.example.hospital.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FacturaService {

    private final CitaService  citaService;
    private final FacturaRepository facturaRepository;


    public FacturaService( CitaService citaService, FacturaRepository facturaRepository) {
        this.citaService = citaService;
        this.facturaRepository = facturaRepository;
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
        factura.setDescuento(facturaRequestDTO.descuento());

        for (ItemsFacturaDTO itemFactura : facturaRequestDTO.items()) {
            ItemsFactura item = new ItemsFactura();
            item.setFactura(factura);
            item.setDescripcion(itemFactura.descripcion());
            item.setCantidad(itemFactura.cantidad());
            item.setPrecioUnitario(itemFactura.precioUnitario());
            item.setTotal(itemFactura.total());

            factura.getItemsFactura().add(item);
        }


        facturaRepository.save(factura);

        return FacturaMapper.toResponseDTO(factura);
    }


    public List<FacturaResponseDTO> listarFacturas() {
        return facturaRepository.findAll().stream().map(FacturaMapper::toResponseDTO).toList();
    }

    public FacturaResponseDTO buscarFacturaPorId(Long id) {
        return FacturaMapper.toResponseDTO(buscarPorEntidad(id));
    }

    protected Factura buscarPorEntidad(Long id){
        return facturaRepository.findById(id)
                .orElseThrow(()-> new EntidadNoEncontradaException("Factura no encontrada con id: " + id));
    }

    public List<FacturaResponseDTO> buscarFacturasPorPaciente(Long id){
        return facturaRepository.findByPacienteId(id).stream()
                .map(FacturaMapper::toResponseDTO)
                .toList();
    }

    public List<FacturaResponseDTO> buscarFacturaPorEstado(EstadoFactura estadoFactura){
        return facturaRepository.findByEstadoFactura(estadoFactura).stream()
                .map(FacturaMapper::toResponseDTO)
                .toList();
    }


    public FacturaResponseDTO actualizarEstadoFactura(Long id, EstadoFactura nuevoEstado) {
            Factura factura = buscarPorEntidad(id);
            EstadoFactura estadoActual = factura.getEstadoFactura();

            switch (estadoActual) {
                case PENDIENTE -> {
                    if (nuevoEstado != EstadoFactura.PAGADO && nuevoEstado != EstadoFactura.VENCIDO && nuevoEstado != EstadoFactura.CANCELADO)
                        throw new EstadoInvalidoException("Cambio de estado inválido desde PENDIENTE");
                }
                case VENCIDO -> {
                    if (nuevoEstado != EstadoFactura.PAGADO && nuevoEstado != EstadoFactura.CANCELADO)
                        throw new EstadoInvalidoException("Cambio de estado inválido desde VENCIDO");
                }
                case PAGADO, CANCELADO ->
                        throw new EstadoInvalidoException("No se puede cambiar el estado de una factura " + estadoActual);
            }

            factura.setEstadoFactura(nuevoEstado);
            facturaRepository.save(factura);
            return FacturaMapper.toResponseDTO(factura);
        }
    }



