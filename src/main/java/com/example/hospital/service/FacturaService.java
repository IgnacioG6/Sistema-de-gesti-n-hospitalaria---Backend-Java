package com.example.hospital.service;

import com.example.hospital.dto.ItemsFacturaDTO;
import com.example.hospital.dto.request.ActualizarFacturaRequestDTO;
import com.example.hospital.dto.request.FacturaRequestDTO;

import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.exception.*;
import com.example.hospital.mapper.FacturaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.Factura;
import com.example.hospital.model.ItemsFactura;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.model.enums.EstadoFactura;
import com.example.hospital.repository.CitaRepository;
import com.example.hospital.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FacturaService {

    private final CitaRepository citaRepository;
    private final FacturaRepository facturaRepository;

    public FacturaService(CitaRepository citaRepository, FacturaRepository facturaRepository) {
        this.citaRepository = citaRepository;
        this.facturaRepository = facturaRepository;
    }

    public FacturaResponseDTO crearFactura(FacturaRequestDTO dto) {
        Cita cita = buscarCitaValidada(dto.citaId());
        validarDescuento(dto, cita);

        Factura factura = FacturaMapper.toEntity(dto, cita);

        facturaRepository.save(factura);
        factura.setNroFactura(String.format("FAC-%05d", factura.getId()));
        facturaRepository.save(factura);

        return FacturaMapper.toResponseDTO(factura);
    }

    private Cita buscarCitaValidada(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrada con ID: " + citaId));

        if (facturaRepository.existsByCitaId(citaId)) {
            throw new ValidacionException("La cita ya tiene una factura");
        }
        if (cita.getEstadoCita() != EstadoCita.COMPLETADO) {
            throw new ValidacionException("La cita debe estar completada");
        }
        return cita;
    }

    private void validarDescuento(FacturaRequestDTO dto, Cita cita) {
        BigDecimal subtotal = dto.items().stream()
                .map(item -> item.precioUnitario().multiply(BigDecimal.valueOf(item.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dto.descuento().compareTo(subtotal) > 0) {
            throw new ValidacionException("El descuento no puede ser mayor que el subtotal");
        }
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
        validarTransicionEstado(factura, nuevoEstado);

        factura.setEstadoFactura(nuevoEstado);
        facturaRepository.save(factura);
        return FacturaMapper.toResponseDTO(factura);
    }

    private void validarTransicionEstado(Factura factura, EstadoFactura nuevoEstado) {
        switch (factura.getEstadoFactura()) {
            case PENDIENTE -> {
                if (nuevoEstado != EstadoFactura.PAGADO && nuevoEstado != EstadoFactura.VENCIDO && nuevoEstado != EstadoFactura.CANCELADO)
                    throw new EstadoInvalidoException("Cambio de estado inválido desde PENDIENTE");
            }
            case VENCIDO -> {
                if (nuevoEstado != EstadoFactura.PAGADO && nuevoEstado != EstadoFactura.CANCELADO)
                    throw new EstadoInvalidoException("Cambio de estado inválido desde VENCIDO");
            }
            case PAGADO, CANCELADO ->
                    throw new EstadoInvalidoException("No se puede cambiar el estado de una factura " + factura.getEstadoFactura());
        }
    }

    public void crearAutomaticaPorCita(Cita cita) {
        if (facturaRepository.existsByCitaId(cita.getId())) {
            return ;
        }

        Factura factura = new Factura();
        factura.setPaciente(cita.getPaciente());
        factura.setCita(cita);
        factura.setDescuento(BigDecimal.ZERO);

        ItemsFactura item = new ItemsFactura();
        item.setFactura(factura);
        item.setDescripcion("Consulta médica - " + cita.getDoctor().getEspecialidad());
        item.setCantidad(1);
        item.setPrecioUnitario(new BigDecimal("500.00"));
        item.setTotal(new BigDecimal("500.00"));
        factura.getItemsFactura().add(item);

        facturaRepository.save(factura);
        factura.setNroFactura(String.format("FAC-%05d", factura.getId()));
        facturaRepository.save(factura);
        ;
    }

    public FacturaResponseDTO actualizarFactura(Long id, ActualizarFacturaRequestDTO dto) {
        Factura factura = buscarPorEntidad(id);
        validarDescuentoActualizacion(factura, dto);
        FacturaMapper.updateEntity(factura, dto);
        facturaRepository.save(factura);
        return FacturaMapper.toResponseDTO(factura);
    }

    private void validarDescuentoActualizacion(Factura factura, ActualizarFacturaRequestDTO dto) {
        BigDecimal subtotal = dto.items().stream()
                .map(item -> item.precioUnitario().multiply(BigDecimal.valueOf(item.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dto.descuento().compareTo(subtotal) > 0) {
            throw new ValidacionException("El descuento no puede ser mayor que el subtotal");
        }
    }

    }



