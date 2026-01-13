package com.example.hospital.service;

import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.enums.Estado;

import java.util.List;
import java.util.Optional;

public interface PacienteService {

    public List<Paciente> obtenerPacientes();

    public Optional BuscarPacientePorId(String dni);

    public Paciente crarPaciente(Paciente paciente);

    public void eliminarPaciente(int id);

    public Paciente actualizarPaciente(Paciente paciente);

    public Paciente actualizarEstado(Estado estado);

    public HistoriaClinica mostrarHistorial(HistoriaClinica historiaClinica);


}
