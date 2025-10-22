package com.hospital.hospital_system.service.impl;

import com.hospital.hospital_system.dto.HistoriaClinicaDTO;
import com.hospital.hospital_system.entity.HistoriaClinica;
import com.hospital.hospital_system.entity.Paciente;
import com.hospital.hospital_system.repository.HistoriaClinicaRepository;
import com.hospital.hospital_system.repository.PacienteRepository;
import com.hospital.hospital_system.service.HistoriaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // ✅ Registrar nueva historia
    @Override
    public HistoriaClinica registrarHistoria(HistoriaClinicaDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));

        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(paciente);
        historia.setFechaApertura(dto.getFechaApertura());
        historia.setObservaciones(dto.getObservaciones());

        return historiaClinicaRepository.save(historia);
    }

    // ✅ Listar todas las historias clínicas
    @Override
    public List<HistoriaClinica> listarHistorias() {
        return historiaClinicaRepository.findAll();
    }

    // ✅ Buscar historia por su ID
    @Override
    public HistoriaClinica obtenerHistoriaPorId(Integer id) {
        return historiaClinicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada con ID: " + id));
    }

    // ✅ Buscar historia por paciente
    @Override
    public HistoriaClinica obtenerPorPacienteId(Integer idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + idPaciente));

        List<HistoriaClinica> historias = historiaClinicaRepository.findByPaciente(paciente);

        if (historias.isEmpty()) {
            throw new RuntimeException("No se encontró historia clínica para el paciente con ID: " + idPaciente);
        }

        return historias.get(0);
    }

    // ✅ Actualizar historia existente
    @Override
    public HistoriaClinica actualizarHistoria(Integer id, HistoriaClinicaDTO dto) {
        HistoriaClinica historia = obtenerHistoriaPorId(id);

        // 🔹 Verificamos si el paciente cambió y lo reasignamos
        if (dto.getPacienteId() != null &&
                !dto.getPacienteId().equals(historia.getPaciente().getIdPaciente())) {

            Paciente nuevoPaciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));
            historia.setPaciente(nuevoPaciente);
        }

        // 🔹 Actualizamos la fecha de apertura (si cambió)
        if (dto.getFechaApertura() != null) {
            historia.setFechaApertura(dto.getFechaApertura());
        }

        // 🔹 Actualizamos observaciones (permitiendo null o texto vacío)
        historia.setObservaciones(dto.getObservaciones() != null ? dto.getObservaciones().trim() : null);

        return historiaClinicaRepository.save(historia);
    }

    // ✅ Eliminar historia por ID
    @Override
    public void eliminarHistoria(Integer id) {
        if (!historiaClinicaRepository.existsById(id)) {
            throw new RuntimeException("No existe una historia clínica con el ID: " + id);
        }
        historiaClinicaRepository.deleteById(id);
    }
}
