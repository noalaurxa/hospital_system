package com.hospital.hospital_system.repository;

import com.hospital.hospital_system.entity.HistoriaClinica;
import com.hospital.hospital_system.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Integer> {
    List<HistoriaClinica> findByPaciente(Paciente paciente);
}