package com.hospital.hospital_system.service.impl;

import com.hospital.hospital_system.dto.AntecedenteMedicoDTO;
import com.hospital.hospital_system.entity.AntecedenteMedico;
import com.hospital.hospital_system.entity.HistoriaClinica;
import com.hospital.hospital_system.repository.AntecedenteMedicoRepository;
import com.hospital.hospital_system.repository.HistoriaClinicaRepository;
import com.hospital.hospital_system.service.AntecedenteMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntecedenteMedicoServiceImpl implements AntecedenteMedicoService {

    @Autowired
    private AntecedenteMedicoRepository antecedenteMedicoRepository;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    public AntecedenteMedico registrarAntecedente(AntecedenteMedicoDTO dto) {
        HistoriaClinica historia = historiaClinicaRepository.findById(dto.getHistoriaClinicaId())
                .orElseThrow(() -> new RuntimeException(
                        "Historia clínica no encontrada con ID: " + dto.getHistoriaClinicaId()));

        AntecedenteMedico antecedente = new AntecedenteMedico();
        antecedente.setHistoriaClinica(historia);
        antecedente.setTipo(dto.getTipo());
        antecedente.setDescripcion(dto.getDescripcion());

        return antecedenteMedicoRepository.save(antecedente);
    }

    @Override
    public List<AntecedenteMedico> listarAntecedentes() {
        return antecedenteMedicoRepository.findAll();
    }

    @Override
    public List<AntecedenteMedico> listarPorHistoria(Integer idHistoria) {
        HistoriaClinica historia = historiaClinicaRepository.findById(idHistoria)
                .orElseThrow(() -> new RuntimeException(
                        "Historia clínica no encontrada con ID: " + idHistoria));
        return antecedenteMedicoRepository.findByHistoriaClinica(historia);
    }

    @Override
    public AntecedenteMedico obtenerAntecedentePorId(Integer id) {
        return antecedenteMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Antecedente médico no encontrado con ID: " + id));
    }

    @Override
    public AntecedenteMedico actualizarAntecedente(Integer id, AntecedenteMedicoDTO dto) {
        AntecedenteMedico antecedente = obtenerAntecedentePorId(id);

        // Actualizamos la historia clínica si cambió
        if (!antecedente.getHistoriaClinica().getIdHistoria().equals(dto.getHistoriaClinicaId())) {
            HistoriaClinica historia = historiaClinicaRepository.findById(dto.getHistoriaClinicaId())
                    .orElseThrow(() -> new RuntimeException(
                            "Historia clínica no encontrada con ID: " + dto.getHistoriaClinicaId()));
            antecedente.setHistoriaClinica(historia);
        }

        antecedente.setTipo(dto.getTipo());
        antecedente.setDescripcion(dto.getDescripcion());

        return antecedenteMedicoRepository.save(antecedente);
    }

    @Override
    public void eliminarAntecedente(Integer id) {
        antecedenteMedicoRepository.deleteById(id);
    }
}
