package com.example.hospital.service;

import com.example.hospital.dto.request.HistoriaClinicaRequestDTO;
import com.example.hospital.dto.response.HistoriaClinicaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.HistoriaClinicaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoriaClinicaService {

    private final CitaService citaService;
    private final HistoriaClinicaRepository historiaRepository;

    public HistoriaClinicaService(CitaService citaService, HistoriaClinicaRepository historiaRepository) {
        this.citaService = citaService;
        this.historiaRepository = historiaRepository;
    }

    public HistoriaClinicaResponseDTO crearHistoriaClinica(HistoriaClinicaRequestDTO request){
        Cita cita = citaService.buscarEntidadPorId(request.idCita());

        if (cita.getEstadoCita() != EstadoCita.COMPLETADO) {
            throw new ValidacionException("Solo se pueden crear historias de citas completadas");
        }

        boolean yaTieneHistoria = historiaRepository.existsByCitaId(request.idCita());

        if (yaTieneHistoria) {
            throw new ValidacionException("La cita ya tiene una historia clínica");
        }

        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setDoctor(cita.getDoctor());
        historiaClinica.setPaciente(cita.getPaciente());
        historiaClinica.setCita(cita);
        historiaClinica.setFecha(LocalDateTime.now());
        historiaClinica.setDiagnostico(request.diagnostico());
        historiaClinica.setSintomas(request.sintomas());
        historiaClinica.setTratamientoPrescrito(request.tratamientoPrescrito());
        historiaClinica.setObservaciones(request.observaciones());

        historiaRepository.save(historiaClinica);

        return HistoriaClinicaMapper.toResponseDTO(historiaClinica);
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



}
