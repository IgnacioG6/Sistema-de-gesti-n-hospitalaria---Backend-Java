package com.example.hospital.repository;

import com.example.hospital.model.Cita;
import com.example.hospital.model.enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByDoctorId(Long doctorId);
    List<Cita> findByEstadoCita(EstadoCita estado);
}
