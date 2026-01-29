package com.example.hospital.repository;

import com.example.hospital.model.Doctor;
import com.example.hospital.model.enums.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLicenciaMedica(String licencia);
    List<Doctor> findByEspecialidad(Especialidad especialidad);
}