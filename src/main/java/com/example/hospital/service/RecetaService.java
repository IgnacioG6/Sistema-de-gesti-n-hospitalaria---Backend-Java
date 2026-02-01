package com.example.hospital.service;

import com.example.hospital.dto.RecetaItemDTO;
import com.example.hospital.dto.request.RecetaRequestDTO;
import com.example.hospital.dto.response.RecetaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.RecetaMapper;
import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.Receta;
import com.example.hospital.model.RecetaItem;
import com.example.hospital.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecetaService {

    private final HistoriaClinicaService historiaClinicaService;
    private final RecetaRepository recetaRepository;

    public RecetaService(HistoriaClinicaService historiaClinicaService, RecetaRepository recetaRepository) {
        this.historiaClinicaService = historiaClinicaService;
        this.recetaRepository = recetaRepository;
    }

    public RecetaResponseDTO crearReceta(RecetaRequestDTO dto) {
        HistoriaClinica historia = historiaClinicaService.buscarEntidadPorId(dto.historiaClinicaId());
        Receta receta = RecetaMapper.toEntity(dto, historia);
        recetaRepository.save(receta);
        return RecetaMapper.toResponseDTO(receta);
    }

    public List<RecetaResponseDTO> listarRecetas(){
        return recetaRepository.findAll().stream()
                .map(RecetaMapper::toResponseDTO)
                .toList();
    }

    public RecetaResponseDTO buscarRecetaPorId(Long id){
        return recetaRepository.findById(id)
                .map(RecetaMapper::toResponseDTO)
                .orElseThrow(() -> new EntidadNoEncontradaException("Receta no encontrada con ID: " + id));
    }


    public List<RecetaResponseDTO> buscarRecetaPorPaciente(Long id){
        return recetaRepository.findByHistoriaClinicaPacienteId(id).stream()
                .map(RecetaMapper::toResponseDTO)
                .toList();
    }

    public List<RecetaResponseDTO> buscarRecetaPorHistoriaClinica(Long id) {
        return recetaRepository.findByHistoriaClinicaId(id).stream()
                .map(RecetaMapper::toResponseDTO)
                .toList();
    }



}
