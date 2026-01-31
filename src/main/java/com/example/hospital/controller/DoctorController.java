package com.example.hospital.controller;

import com.example.hospital.dto.request.DoctorRequestDTO;
import com.example.hospital.dto.response.DoctorResponseDTO;
import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> listarDoctores() {
        return ResponseEntity.ok(doctorService.obtenerTodos());
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<DoctorResponseDTO>> listarDoctoresPorEspecialidad(@PathVariable Especialidad especialidad) {
        List<DoctorResponseDTO> listaDoctores = doctorService.buscarPorEspecialidad(especialidad);
        return ResponseEntity.ok(listaDoctores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> buscarDoctor(@PathVariable Long id){
        DoctorResponseDTO doctor = doctorService.buscarDoctorPorId(id);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> registrarDoctor(@Valid @RequestBody DoctorRequestDTO doctorRequestDTO){
        DoctorResponseDTO doctor = doctorService.registarDoctor(doctorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> actualizarDoctor(@PathVariable Long id,@Valid @RequestBody DoctorRequestDTO doctorRequestDTO){
        DoctorResponseDTO doctor =  doctorService.actualizarDoctor(id, doctorRequestDTO);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<DoctorResponseDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.cambiarDisponibilidad(id, true));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<DoctorResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.cambiarDisponibilidad(id, false));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDoctor(@PathVariable Long id){
        doctorService.eliminarDoctor(id);
        return ResponseEntity.noContent().build();
    }


}
