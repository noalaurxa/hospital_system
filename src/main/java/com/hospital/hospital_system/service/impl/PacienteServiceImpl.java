package com.hospital.hospital_system.service.impl;

import com.hospital.hospital_system.dto.PacienteDTO;
import com.hospital.hospital_system.entity.HistoriaClinica;
import com.hospital.hospital_system.entity.Paciente;
import com.hospital.hospital_system.repository.HistoriaClinicaRepository;
import com.hospital.hospital_system.repository.PacienteRepository;
import com.hospital.hospital_system.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    public Paciente registrarPaciente(PacienteDTO dto) {
        // Verificar si ya existe un paciente con el mismo DNI
        Optional<Paciente> existente = pacienteRepository.findByDni(dto.getDni());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + dto.getDni());
        }

        Paciente paciente = new Paciente();
        paciente.setDni(dto.getDni());
        paciente.setNombres(dto.getNombres());
        paciente.setApellidos(dto.getApellidos());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setSexo(dto.getSexo());
        paciente.setDireccion(dto.getDireccion());
        paciente.setTelefono(dto.getTelefono());
        paciente.setCorreo(dto.getCorreo());
        paciente.setEstado("activo");

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        // Crear historia clínica automáticamente
        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(pacienteGuardado);
        historia.setFechaApertura(LocalDate.now());
        historia.setObservaciones("Historia clínica inicial generada automáticamente");

        historiaClinicaRepository.save(historia);

        return pacienteGuardado;
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente obtenerPacientePorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
    }

    @Override
    public Paciente actualizarPaciente(Integer id, PacienteDTO dto) {
        Paciente paciente = obtenerPacientePorId(id);

        paciente.setDni(dto.getDni());
        paciente.setNombres(dto.getNombres());
        paciente.setApellidos(dto.getApellidos());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setSexo(dto.getSexo());
        paciente.setDireccion(dto.getDireccion());
        paciente.setTelefono(dto.getTelefono());
        paciente.setCorreo(dto.getCorreo());

        return pacienteRepository.save(paciente);
    }

    @Override
    public void desactivarPaciente(Integer id) {
        Paciente paciente = obtenerPacientePorId(id);
        paciente.setEstado("inactivo");
        pacienteRepository.save(paciente);
    }
    @Override
    public void activarPaciente(Integer id) {
        Paciente paciente = obtenerPacientePorId(id);
        paciente.setEstado("activo");
        pacienteRepository.save(paciente);
    }

}
