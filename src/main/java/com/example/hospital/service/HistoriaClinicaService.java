package com.example.hospital.service;

import com.example.hospital.dto.request.ActualizarHistoriaClinicaRequestDTO;
import com.example.hospital.dto.request.HistoriaClinicaRequestDTO;
import com.example.hospital.dto.response.HistoriaClinicaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.HistoriaClinicaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.repository.CitaRepository;
import com.example.hospital.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriaClinicaService {

    private final CitaRepository citaRepository;
    private final HistoriaClinicaRepository historiaRepository;

    public HistoriaClinicaService(CitaRepository citaRepository, HistoriaClinicaRepository historiaRepository) {
        this.citaRepository = citaRepository;
        this.historiaRepository = historiaRepository;
    }

    public HistoriaClinicaResponseDTO crearHistoriaClinica(HistoriaClinicaRequestDTO dto) {
        Cita cita = buscarCitaValidada(dto.idCita());
        HistoriaClinica historia = HistoriaClinicaMapper.toEntity(cita, dto);
        historiaRepository.save(historia);
        return HistoriaClinicaMapper.toResponseDTO(historia);
    }


    private Cita buscarCitaValidada(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrada con ID: " + citaId));

        if (cita.getEstadoCita() != EstadoCita.COMPLETADO) {
            throw new ValidacionException("Solo se pueden crear historias de citas completadas");
        }
        if (historiaRepository.existsByCitaId(citaId)) {
            throw new ValidacionException("La cita ya tiene una historia clínica");
        }
        return cita;
    }


    public List<HistoriaClinicaResponseDTO> listarHistorias(){
        return historiaRepository.findAll().stream()
                .map(HistoriaClinicaMapper::toResponseDTO)
                .toList();
    }

    public HistoriaClinicaResponseDTO buscarPorId(Long id) {
        HistoriaClinica historia = buscarEntidadPorId(id);
        return  HistoriaClinicaMapper.toResponseDTO(historia);
    }


    public HistoriaClinica buscarEntidadPorId(Long id) {
        return historiaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Historia clinica no encontrada con ID: " + id));
    }


    public List<HistoriaClinicaResponseDTO> buscarPorPaciente(Long id){
        return historiaRepository.findByPacienteId(id).stream()
                .map(HistoriaClinicaMapper::toResponseDTO)
                .toList();
    }

    public List<HistoriaClinicaResponseDTO> buscarPorDoctor(Long citaId) {
        return historiaRepository.findByDoctorId(citaId).stream()
                .map(HistoriaClinicaMapper::toResponseDTO)
                .toList();
    }


    public void crearHistoriaAutomaticaPorCita(Cita cita) {
        if (historiaRepository.existsByCitaId(cita.getId())) {
            return ;
        }

        HistoriaClinicaRequestDTO dto = new HistoriaClinicaRequestDTO(
                cita.getId(),
                "Pendiente de completar",
                "Pendiente de completar",
                "Pendiente de completar",
                "Historia clínica generada automáticamente"
        );

        HistoriaClinica historia = HistoriaClinicaMapper.toEntity(cita, dto);
        historiaRepository.save(historia);
    }

    // En HistoriaClinicaService
    public HistoriaClinicaResponseDTO actualizarHistoriaClinica(Long id, ActualizarHistoriaClinicaRequestDTO dto) {
        HistoriaClinica historia = buscarEntidadPorId(id);
        HistoriaClinicaMapper.updateEntity(historia, dto);
        historiaRepository.save(historia);
        return HistoriaClinicaMapper.toResponseDTO(historia);
    }


}
