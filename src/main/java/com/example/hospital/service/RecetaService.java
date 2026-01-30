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

    public RecetaResponseDTO crearReceta(RecetaRequestDTO request) {
        HistoriaClinica historia = historiaClinicaService.buscarEntidadPorId(request.historiaClinicaId());

        if (request.medicamentos().isEmpty()) {
            throw new ValidacionException("La lista de medicamentos no puede estar vacia");
        }

        if (request.validaHasta().isBefore(LocalDate.now())) {
            throw new ValidacionException("La fecha debe ser futura");
        }

        Receta receta = new Receta();
        receta.setHistoriaClinica(historia);
        receta.setInstruccionesGenerales(request.instruccionesGenerales());
        receta.setValidaHasta(request.validaHasta());

        for (RecetaItemDTO itemDTO : request.medicamentos()) {
            RecetaItem item = new RecetaItem();
            item.setReceta(receta);
            item.setMedicamento(itemDTO.medicamento());
            item.setDosis(itemDTO.dosis());
            item.setFrecuencia(itemDTO.frecuencia());
            item.setDuracionDias(itemDTO.duracionDias());
            item.setInstrucciones(itemDTO.instrucciones());

            receta.getMedicamentos().add(item);
        }

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
