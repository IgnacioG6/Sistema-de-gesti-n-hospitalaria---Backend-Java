package com.example.hospital.repository;

import com.example.hospital.model.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {

    Optional<HistoriaClinica> findByPacienteId(Long id);
    Optional<HistoriaClinica>findByCitaId(Long id);

}
