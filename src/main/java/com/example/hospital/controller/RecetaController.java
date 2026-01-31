package com.example.hospital.controller;

import com.example.hospital.dto.request.RecetaRequestDTO;
import com.example.hospital.dto.response.RecetaResponseDTO;
import com.example.hospital.model.Receta;
import com.example.hospital.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @PostMapping
    public ResponseEntity<RecetaResponseDTO> crearReceta(@Valid @RequestBody RecetaRequestDTO recetaRequest) {
        RecetaResponseDTO receta = recetaService.crearReceta(recetaRequest);
        return  ResponseEntity.status(HttpStatus.CREATED).body(receta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> buscarRecetaPorId(@PathVariable Long id) {
        RecetaResponseDTO receta = recetaService.buscarRecetaPorId(id);
        return  ResponseEntity.status(HttpStatus.OK).body(receta);
    }

    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listarRecetas() {
        return ResponseEntity.status(HttpStatus.OK).body(recetaService.listarRecetas());
    }


    @GetMapping("/pacientes/{id}")
    public ResponseEntity<List<RecetaResponseDTO>> buscarRecetaPorIdPaciente(@PathVariable Long id) {
        List<RecetaResponseDTO> recetas = recetaService.buscarRecetaPorPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(recetas);
    }

    @GetMapping("/historias-clinicas/{id}")
    public ResponseEntity<List<RecetaResponseDTO>> buscarRecetaPorHistoriaClinica(@PathVariable Long id) {
        List<RecetaResponseDTO> receta = recetaService.buscarRecetaPorHistoriaClinica(id);
        return ResponseEntity.status(HttpStatus.OK).body(receta);
    }






}
