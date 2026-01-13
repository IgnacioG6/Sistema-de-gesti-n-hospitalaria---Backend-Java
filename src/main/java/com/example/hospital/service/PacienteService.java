package com.example.hospital.service;

import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.enums.Estado;

import java.util.List;
import java.util.Optional;

public interface PacienteService {

    List<Paciente> obtenerPacientes();

    Optional BuscarPacientePorId(String dni);

    Paciente crarPaciente(Paciente paciente);

    void eliminarPaciente(int id);

    Paciente actualizarPaciente(Paciente paciente);

    Paciente actualizarEstado(Estado estado);

    HistoriaClinica mostrarHistorial(HistoriaClinica historiaClinica);


}
