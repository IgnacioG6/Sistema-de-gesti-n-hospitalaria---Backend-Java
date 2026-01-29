package com.example.hospital.repository;

import com.example.hospital.model.Factura;
import com.example.hospital.model.enums.EstadoFactura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByPacienteId(Long id);
    List<Factura> findByEstadoFactura(EstadoFactura estado);
}
