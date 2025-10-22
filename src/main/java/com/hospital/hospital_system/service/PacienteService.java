package com.hospital.hospital_system.service;

import com.hospital.hospital_system.dto.PacienteDTO;
import com.hospital.hospital_system.entity.Paciente;

import java.util.List;

public interface PacienteService {

    Paciente registrarPaciente(PacienteDTO dto);

    List<Paciente> listarPacientes();

    Paciente obtenerPacientePorId(Integer id);

    Paciente actualizarPaciente(Integer id, PacienteDTO dto);

    void desactivarPaciente(Integer id);
    void activarPaciente(Integer id);
}
