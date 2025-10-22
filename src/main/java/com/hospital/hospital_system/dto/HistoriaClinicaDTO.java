package com.hospital.hospital_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HistoriaClinicaDTO {



    private Integer idHistoria;

    @NotNull(message = "Debe seleccionar un paciente")
    private Integer pacienteId;

    @NotNull(message = "La fecha de apertura es obligatoria")
    private LocalDate fechaApertura;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;

    // Lombok @Data se encarga de getters y setters
}