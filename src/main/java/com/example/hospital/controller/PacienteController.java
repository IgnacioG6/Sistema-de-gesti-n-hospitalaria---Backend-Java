package com.example.hospital.controller;

import com.example.hospital.dto.request.PacienteRequestDTO;
import com.example.hospital.dto.response.PacienteResponseDTO;
import com.example.hospital.model.enums.Estado;
import com.example.hospital.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(@RequestBody PacienteRequestDTO paciente) {
        PacienteResponseDTO pacienteDto = pacienteService.crearPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteDto);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorDni(@PathVariable String dni) {
        return ResponseEntity.ok(pacienteService.buscarPorDni(dni));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO paciente
    ) {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, paciente));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<PacienteResponseDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.cambiarEstado(id, Estado.ACTIVO));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<PacienteResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.cambiarEstado(id, Estado.INACTIVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
