package com.hospital.hospital_system.repository;

import com.hospital.hospital_system.entity.AntecedenteMedico;
import com.hospital.hospital_system.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntecedenteMedicoRepository extends JpaRepository<AntecedenteMedico, Integer> {

    // Obtener todos los antecedentes médicos de una historia clínica específica
    List<AntecedenteMedico> findByHistoriaClinica(HistoriaClinica historiaClinica);
}
