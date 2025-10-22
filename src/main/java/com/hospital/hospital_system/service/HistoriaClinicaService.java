package com.hospital.hospital_system.service;

import com.hospital.hospital_system.dto.HistoriaClinicaDTO;
import com.hospital.hospital_system.entity.HistoriaClinica;
import java.util.List;

public interface HistoriaClinicaService {

    HistoriaClinica registrarHistoria(HistoriaClinicaDTO dto);

    List<HistoriaClinica> listarHistorias();

    HistoriaClinica obtenerHistoriaPorId(Integer id);

    HistoriaClinica obtenerPorPacienteId(Integer idPaciente);

    HistoriaClinica actualizarHistoria(Integer id, HistoriaClinicaDTO dto);

    void eliminarHistoria(Integer id);
}
