package com.example.hospital.controller;

import com.example.hospital.dto.request.CambiarEstadoCitaRequest;
import com.example.hospital.dto.request.CitaRequestDTO;
import com.example.hospital.dto.response.CitaResponseDTO;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cita")
public class CitaController {

    private CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }


    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> getCitas() {
        return ResponseEntity.ok(citaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> BuscarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.buscarPorId(id));
    }


    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<CitaResponseDTO>> BuscarCitasPorPaciente(@PathVariable Long id){
        return ResponseEntity.ok(citaService.buscarPorPaciente(id));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<CitaResponseDTO>> BuscarCitasPorDoctor(@PathVariable Long id){
        return ResponseEntity.ok(citaService.buscarPorDoctor(id));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaResponseDTO>> buscarCitaPorEstado(@PathVariable EstadoCita estado) {
        return ResponseEntity.ok(citaService.buscarPorEstado(estado));
    }


    @PostMapping
    public ResponseEntity<CitaResponseDTO> registrarCita(@RequestBody CitaRequestDTO citaRequest) {
        CitaResponseDTO cita = citaService.crearCita(citaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cita);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizarCita(@PathVariable Long id, @RequestBody CitaRequestDTO citaRequest) {
        CitaResponseDTO cita = citaService.actualizarCita(id, citaRequest);
        return ResponseEntity.ok(cita);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CitaResponseDTO> cambiarEstadoCita(@PathVariable Long id,@RequestBody CambiarEstadoCitaRequest request) {
        return ResponseEntity.ok(citaService.cambiarEstado(request.estado(), id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return ResponseEntity.noContent().build();
    }







}
