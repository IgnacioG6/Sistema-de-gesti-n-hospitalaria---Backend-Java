package com.example.hospital.service;

import com.example.hospital.dto.request.RecetaRequestDTO;
import com.example.hospital.dto.response.RecetaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.RecetaMapper;
import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.Receta;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecetaService {

    List<Receta> recetas = new ArrayList<>();
    HistoriaClinicaService historiaClinicaService;

    public RecetaService(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    public RecetaResponseDTO crearReceta(RecetaRequestDTO request) {
        HistoriaClinica historia = historiaClinicaService.buscarEntidadPorId(request.historiaClinicaId());

        if (request.medicamentos().isEmpty()) {
            throw new ValidacionException("La lista de medicamentos no puede estar vacia");
        }

        if (request.validaHasta().isBefore(LocalDate.now())) {
            throw new ValidacionException("La fecha debe ser futura");
        }

        Receta receta = new Receta();
        receta.setMedicamentos(request.medicamentos());
        receta.setHistoriaClinica(historia);
        receta.setInstruccionesGenerales(request.instruccionesGenerales());
        receta.setValidaHasta(request.validaHasta());

        recetas.add(receta);

        return RecetaMapper.toResponseDTO(receta);

    }

    public List<RecetaResponseDTO> listarRecetas(){
        return recetas.stream().map(RecetaMapper::toResponseDTO).toList();
    }

    public RecetaResponseDTO buscarRecetaPorId(Long id){
        return recetas.stream()
                .filter(receta -> receta.getId().equals(id))
                .findFirst()
                .map(RecetaMapper::toResponseDTO)
                .orElseThrow(() -> new EntidadNoEncontradaException("Receta no encontrada con ID: " + id));
    }


    public List<RecetaResponseDTO> buscarRecetaPorPaciente(Long id){
        return recetas.stream()
                .filter(receta -> receta.getHistoriaClinica().getPaciente().getId().equals(id))
                .map(RecetaMapper::toResponseDTO)
                .toList();
    }

    public List<RecetaResponseDTO> buscarRecetaPorHistoriaClinica(Long id) {
        return recetas.stream()
                .filter(receta -> receta.getHistoriaClinica().getId().equals(id))
                .map(RecetaMapper::toResponseDTO)
                .toList();
    }



}
