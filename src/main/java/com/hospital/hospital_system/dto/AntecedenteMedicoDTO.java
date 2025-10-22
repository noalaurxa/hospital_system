package com.hospital.hospital_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AntecedenteMedicoDTO {

    private Integer id; // ⚠️ para actualizar correctamente

    @NotNull(message = "Debe seleccionar una historia clínica")
    private Integer historiaClinicaId;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    private String tipo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;
}
