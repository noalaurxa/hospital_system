package com.hospital.hospital_system.service;

import com.hospital.hospital_system.dto.AntecedenteMedicoDTO;
import com.hospital.hospital_system.entity.AntecedenteMedico;

import java.util.List;

public interface AntecedenteMedicoService {

    // Registrar un nuevo antecedente médico
    AntecedenteMedico registrarAntecedente(AntecedenteMedicoDTO dto);

    // Listar todos los antecedentes médicos
    List<AntecedenteMedico> listarAntecedentes();

    // Listar antecedentes por ID de historia clínica
    List<AntecedenteMedico> listarPorHistoria(Integer idHistoria);

    // Obtener antecedente médico por su ID
    AntecedenteMedico obtenerAntecedentePorId(Integer id);

    // Actualizar un antecedente médico
    AntecedenteMedico actualizarAntecedente(Integer id, AntecedenteMedicoDTO dto);

    // Eliminar un antecedente médico por su ID
    void eliminarAntecedente(Integer id);
}
