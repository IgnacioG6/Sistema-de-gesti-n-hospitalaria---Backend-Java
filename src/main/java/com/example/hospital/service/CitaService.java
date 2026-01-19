package com.example.hospital.service;

import com.example.hospital.dto.request.CitaRequestDTO;
import com.example.hospital.dto.response.CitaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.EstadoInvalidoException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.CitaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.Doctor;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.enums.Estado;
import com.example.hospital.model.enums.EstadoCita;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {

    private final PacienteService pacienteService;
    private final DoctorService doctorService;
    private List<Cita> citas = new ArrayList<>();

    public CitaService(PacienteService pacienteService, DoctorService doctorService) {
        this.pacienteService = pacienteService;
        this.doctorService = doctorService;
    }

    public List<CitaResponseDTO> obtenerTodos() {
        return citas.stream()
                .map(CitaMapper::toResponseDTO)
                .toList();
    }


    public List<CitaResponseDTO> buscarPorPaciente(long idPaciente) {
        return citas.stream()
                .filter(cita -> cita.getPaciente().getId().equals(idPaciente))
                .map(CitaMapper::toResponseDTO)
                .toList();
    }

    public List<CitaResponseDTO> buscarPorDoctor(long idDoctor) {
        return citas.stream()
                .filter(cita -> cita.getDoctor().getId().equals(idDoctor))
                .map(CitaMapper::toResponseDTO)
                .toList();
    }

    public List<CitaResponseDTO> buscarPorEstado(EstadoCita estado) {
        return citas.stream()
                .filter(cita -> cita.getEstadoCita().equals(estado))
                .map(CitaMapper::toResponseDTO)
                .toList();
    }

    public CitaResponseDTO cambiarEstado(EstadoCita estado, Long id) {
        Cita cita = buscarEntidadPorId(id);

        switch (cita.getEstadoCita()) {
            case PROGRAMADO:
                if (estado != EstadoCita.EN_PROCESO && estado != EstadoCita.CANCELADO) {
                    throw new EstadoInvalidoException("Desde PROGRAMADO solo se puede pasar a EN_PROCESO o CANCELADO");
                }
                break;

            case EN_PROCESO:
                if (estado != EstadoCita.COMPLETADO && estado != EstadoCita.CANCELADO) {
                    throw new EstadoInvalidoException("Desde EN_PROCESO solo se puede pasar a COMPLETADO o CANCELADO");
                }
                break;

            case COMPLETADO:
            case CANCELADO:
                throw new EstadoInvalidoException("No se puede cambiar el estado de una cita " + cita.getEstadoCita());
        }

        cita.setEstadoCita(estado);

        return CitaMapper.toResponseDTO(cita);

    }


    public void cancelarCita(Long id) {
        Cita cita = buscarEntidadPorId(id);

        if (cita.getEstadoCita() != EstadoCita.PROGRAMADO) {
            throw new EstadoInvalidoException("Solo se pueden cancelar citas en estado PROGRAMADO");
        }

        citas.remove(cita);
    }



    public CitaResponseDTO crearCita(CitaRequestDTO request) {
        Paciente paciente = pacienteService.buscarEntidadPorId(request.idPaciente());
        Doctor doctor = doctorService.buscarEntidadPorId(request.idDoctor());

        validarDisponibilidadDoctor(request.idDoctor(), request.fechaHora(), request.duracion(), null);
        validarLimiteCitasPaciente(paciente.getId());


        if (paciente.getEstado() != Estado.ACTIVO) {
            throw new ValidacionException("El paciente debe estar activo");
        }

        if (!doctor.isDisponible()) {
            throw new ValidacionException("El doctor no está disponible");
        }

        if (request.fechaHora().isBefore(LocalDateTime.now())) {
            throw new ValidacionException("La fecha debe ser futura");
        }

        if (request.fechaHora().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new ValidacionException("No se pueden agendar citas los domingos");
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setFechaHora(request.fechaHora());
        cita.setEstadoCita(EstadoCita.PROGRAMADO);
        cita.setMotivo(request.motivo());
        cita.setNotas(request.notas());
        cita.setDuracion(request.duracion());

        citas.add(cita);

        return CitaMapper.toResponseDTO(cita);
    }


    private void validarLimiteCitasPaciente(Long pacienteId) {
        long citasProgramadas = citas.stream()
                .filter(c -> c.getPaciente().getId().equals(pacienteId))
                .filter(c -> c.getEstadoCita() == EstadoCita.PROGRAMADO)
                .count();

        if (citasProgramadas >= 3) {
            throw new ValidacionException("El paciente ya tiene 3 citas programadas");
        }
    }



    public Cita buscarEntidadPorId(Long id) {
        return citas.stream()
                .filter(cita -> cita.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrada con ID: " + id));
    }

    public CitaResponseDTO buscarPorId(Long id) {
        Cita cita = buscarEntidadPorId(id);
        return CitaMapper.toResponseDTO(cita);
    }


    public CitaResponseDTO actualizarCita(Long id, CitaRequestDTO request) {
        Cita cita = buscarEntidadPorId(id);

        if (cita.getEstadoCita() != EstadoCita.PROGRAMADO) {
            throw new EstadoInvalidoException("Solo se pueden actualizar citas en estado PROGRAMADO");
        }

        if (!cita.getFechaHora().equals(request.fechaHora())) {

            if (request.fechaHora().isBefore(LocalDateTime.now())) {
                throw new ValidacionException("La fecha debe ser futura");
            }

            if (request.fechaHora().getDayOfWeek() == DayOfWeek.SUNDAY) {
                throw new ValidacionException("No se pueden agendar citas los domingos");
            }

            validarDisponibilidadDoctor(
                    cita.getDoctor().getId(),
                    request.fechaHora(),
                    request.duracion(),
                    id
            );
        }

        cita.setFechaHora(request.fechaHora());
        cita.setMotivo(request.motivo());
        cita.setNotas(request.notas());
        cita.setDuracion(request.duracion());

        return CitaMapper.toResponseDTO(cita);
    }


    private List<Cita> obtenerCitasActivasDeDoctor(Long doctorId) {
        return citas.stream()
                .filter(cita -> cita.getDoctor().getId().equals(doctorId))
                .filter(cita -> cita.getEstadoCita() == EstadoCita.PROGRAMADO ||
                        cita.getEstadoCita() == EstadoCita.EN_PROCESO)
                .toList();
    }

    private void validarDisponibilidadDoctor(Long doctorId, LocalDateTime fechaHora, int duracion, Long citaIdActual) {
        List<Cita> citasDoctor = obtenerCitasActivasDeDoctor(doctorId);

        LocalDateTime inicioNuevaCita = fechaHora;
        LocalDateTime finNuevaCita = fechaHora.plusMinutes(duracion);

        for (Cita cita : citasDoctor) {
            if (cita.getId().equals(citaIdActual)) {
                continue;
            }

            LocalDateTime inicioCitaExistente = cita.getFechaHora();
            LocalDateTime finCitaExistente = inicioCitaExistente.plusMinutes(cita.getDuracion());

            boolean haySuperposicion =
                    (inicioNuevaCita.isBefore(finCitaExistente)) &&
                            (finNuevaCita.isAfter(inicioCitaExistente));

            if (haySuperposicion) {
                throw new ValidacionException("El doctor ya tiene una cita en ese horario");
            }
        }
    }


}
