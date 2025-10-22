package com.hospital.hospital_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "antecedente_medico")
public class AntecedenteMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAntecedente;

    @ManyToOne
    @JoinColumn(name = "idHistoria", nullable = false)
    private HistoriaClinica historiaClinica;

    @Column(length = 50)
    private String tipo; // alergias, enfermedades previas, etc.

    @Column(length = 255)
    private String descripcion;
}
