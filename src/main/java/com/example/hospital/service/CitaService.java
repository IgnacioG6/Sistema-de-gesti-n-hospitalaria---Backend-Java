package com.example.hospital.service;

import com.example.hospital.dto.request.CitaRequestDTO;
import com.example.hospital.dto.response.CitaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.EstadoInvalidoException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.CitaMapper;
import com.example.hospital.model.*;
import com.example.hospital.model.enums.Estado;
import com.example.hospital.model.enums.EstadoCita;
import com.example.hospital.repository.CitaRepository;
import com.example.hospital.repository.FacturaRepository;
import com.example.hospital.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final PacienteService pacienteService;
    private final DoctorService doctorService;
    private final CitaRepository  citaRepository;
    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final FacturaRepository facturaRepository;

    public CitaService(PacienteService pacienteService, DoctorService doctorService,
                       CitaRepository citaRepository, HistoriaClinicaRepository historiaClinicaRepository,
                       FacturaRepository facturaRepository) {

        this.pacienteService = pacienteService;
        this.doctorService = doctorService;
        this.citaRepository = citaRepository;
        this.historiaClinicaRepository = historiaClinicaRepository;
        this.facturaRepository = facturaRepository;
    }

    public List<CitaResponseDTO> obtenerTodos() {
        return citaRepository.findAll().stream()
                .map(CitaMapper::toResponseDTO)
                .toList();
    }


    public List<CitaResponseDTO> buscarPorPaciente(long idPaciente) {
        return citaRepository.findByPacienteId(idPaciente).stream()
                .map(CitaMapper::toResponseDTO)
                .toList();
    }

    public List<CitaResponseDTO> buscarPorDoctor(long idDoctor) {
        return citaRepository.findByDoctorId(idDoctor).stream()
                .map(CitaMapper::toResponseDTO)
                .toList();

    }

    public List<CitaResponseDTO> buscarPorEstado(EstadoCita estado) {
        return citaRepository.findByEstadoCita(estado).stream()
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
        citaRepository.save(cita);

        if (estado == EstadoCita.COMPLETADO) {
            try {
                crearHistoriaClinicaAutomatica(cita);
                crearFacturaAutomatica(cita);
            } catch (Exception e) {
                System.err.println("Error creando historia/factura automática: " + e.getMessage());
            }
        }

        return CitaMapper.toResponseDTO(cita);

    }


    public void cancelarCita(Long id) {
        Cita cita = buscarEntidadPorId(id);

        if (cita.getEstadoCita() != EstadoCita.PROGRAMADO) {
            throw new EstadoInvalidoException("Solo se pueden cancelar citas en estado PROGRAMADO");
        }

        citaRepository.delete(cita);
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

        citaRepository.save(cita);

        return CitaMapper.toResponseDTO(cita);
    }


    private void validarLimiteCitasPaciente(Long pacienteId) {
        long citasProgramadas = citaRepository.findByPacienteId(pacienteId).stream()
                .filter(c -> c.getEstadoCita() == EstadoCita.PROGRAMADO)
                .count();

        if (citasProgramadas >= 3) {
            throw new ValidacionException("El paciente ya tiene 3 citas programadas");
        }
    }



    public Cita buscarEntidadPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrado con id: " + id));
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

        citaRepository.save(cita);
        return CitaMapper.toResponseDTO(cita);
    }


    private List<Cita> obtenerCitasActivasDeDoctor(Long doctorId) {
        return citaRepository.findByDoctorId(doctorId).stream()
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

    private void crearHistoriaClinicaAutomatica(Cita cita) {
        if (historiaClinicaRepository.existsByCitaId(cita.getId())) {
            return;
        }

        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(cita.getPaciente());
        historia.setDoctor(cita.getDoctor());
        historia.setCita(cita);
        historia.setFecha(LocalDateTime.now());
        historia.setDiagnostico("Pendiente de completar");
        historia.setSintomas("Pendiente de completar");
        historia.setTratamientoPrescrito("Pendiente de completar");
        historia.setObservaciones("Historia clínica generada automáticamente");

        historiaClinicaRepository.save(historia);
    }

    private void crearFacturaAutomatica(Cita cita) {
        if (facturaRepository.existsByCitaId(cita.getId())) {
            return;
        }

        Factura factura = new Factura();
        factura.setPaciente(cita.getPaciente());
        factura.setCita(cita);
        factura.setDescuento(BigDecimal.ZERO);

        ItemsFactura item = new ItemsFactura();
        item.setFactura(factura);
        item.setDescripcion("Consulta médica - " + cita.getDoctor().getEspecialidad());
        item.setCantidad(1);
        item.setPrecioUnitario(new BigDecimal("500.00"));
        item.setTotal(new BigDecimal("500.00"));

        factura.getItemsFactura().add(item);

        facturaRepository.save(factura);

        factura.setNroFactura(String.format("FAC-%05d", factura.getId()));
        facturaRepository.save(factura);
    }


}
