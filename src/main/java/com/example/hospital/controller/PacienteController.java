package com.example.hospital.controller;

import com.example.hospital.dto.request.PacienteRequestDTO;
import com.example.hospital.dto.response.PacienteResponseDTO;
import com.example.hospital.model.Paciente;
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
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes(){
        List<PacienteResponseDTO> pacientes = pacienteService.obtenerTodos();

        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PacienteRequestDTO> buscarPacientePorId(@PathVariable Long id){
        PacienteResponseDTO paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente);
    }


}
