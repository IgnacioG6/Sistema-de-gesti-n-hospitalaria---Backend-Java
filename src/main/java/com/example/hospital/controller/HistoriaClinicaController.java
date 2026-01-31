package com.example.hospital.controller;

import com.example.hospital.dto.request.HistoriaClinicaRequestDTO;
import com.example.hospital.dto.response.HistoriaClinicaResponseDTO;
import com.example.hospital.service.HistoriaClinicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historias-clinicas")
public class HistoriaClinicaController {

    private final HistoriaClinicaService historiaClinicaService;

    public HistoriaClinicaController(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    @PostMapping
    public ResponseEntity<HistoriaClinicaResponseDTO> CrearHistoriaClinica(@Valid @RequestBody HistoriaClinicaRequestDTO request) {
        HistoriaClinicaResponseDTO historiaClinica = historiaClinicaService.crearHistoriaClinica(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(historiaClinica);
    }

    @GetMapping
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> listarHistoriaClinicas() {
        return ResponseEntity.ok(historiaClinicaService.listarHistorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponseDTO> buscarPorId(@PathVariable Long id) {
        HistoriaClinicaResponseDTO historiaClinica = historiaClinicaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(historiaClinica);
    }


    @GetMapping("/pacientes/{id}")
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> buscarHistoriaPorPaciente(@PathVariable Long id) {
        List<HistoriaClinicaResponseDTO> historia = historiaClinicaService.buscarPorPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(historia);
    }

    @GetMapping("/doctores/{id}")
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> buscarHistoriaPorDoctor(@PathVariable Long id) {
        List<HistoriaClinicaResponseDTO> historia = historiaClinicaService.buscarPorDoctor(id);
        return ResponseEntity.status(HttpStatus.OK).body(historia);
    }


}
