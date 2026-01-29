package com.example.hospital.repository;

import com.example.hospital.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByHistoriaClinicaId(Long id);
}
