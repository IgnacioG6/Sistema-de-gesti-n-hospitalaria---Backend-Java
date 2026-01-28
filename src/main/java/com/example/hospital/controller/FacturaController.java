package com.example.hospital.controller;

import com.example.hospital.dto.request.CambiarEstadoFacturaRequest;
import com.example.hospital.dto.request.FacturaRequestDTO;
import com.example.hospital.dto.response.FacturaResponseDTO;
import com.example.hospital.model.enums.EstadoFactura;
import com.example.hospital.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public ResponseEntity<FacturaResponseDTO> CrearFactura(@RequestBody FacturaRequestDTO facturaRequestDTO) {
        FacturaResponseDTO factura = facturaService.crearFactura(facturaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }

    @GetMapping
    public ResponseEntity<List<FacturaResponseDTO>> ListarFacturas() {
        return  ResponseEntity.status(HttpStatus.OK).body(facturaService.listarFacturas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> buscarFactura(@PathVariable Long id){
        FacturaResponseDTO factura = facturaService.buscarFacturaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(factura);
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<List<FacturaResponseDTO>> buscarFacturasPorPacientes(@PathVariable Long id){
        List<FacturaResponseDTO> facturas = facturaService.buscarFacturasPorPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(facturas);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<FacturaResponseDTO>> buscarFacturasPorEstado(@PathVariable EstadoFactura estado){
        List<FacturaResponseDTO> facturas = facturaService.buscarFacturaPorEstado(estado);
        return ResponseEntity.status(HttpStatus.OK).body(facturas);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<FacturaResponseDTO> actualizarEstado(@PathVariable Long id, @RequestBody CambiarEstadoFacturaRequest request) {
        return ResponseEntity.ok(facturaService.actualizarEstadoFactura(id, request.estado()));
    }


}
