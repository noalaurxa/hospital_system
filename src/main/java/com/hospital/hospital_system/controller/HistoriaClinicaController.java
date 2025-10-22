package com.hospital.hospital_system.controller;

import com.hospital.hospital_system.dto.HistoriaClinicaDTO;
import com.hospital.hospital_system.entity.HistoriaClinica;
import com.hospital.hospital_system.service.HistoriaClinicaService;
import com.hospital.hospital_system.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // 👈 Importación CLAVE
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/historias")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/listar")
    public String listarHistorias(Model model) {
        List<HistoriaClinica> historias = historiaClinicaService.listarHistorias();
        model.addAttribute("historias", historias);
        return "historias/listar";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistrar(Model model) {
        if (!model.containsAttribute("historia")) {
            model.addAttribute("historia", new HistoriaClinicaDTO());
        }
        model.addAttribute("pacientes", pacienteService.listarPacientes());
        return "historias/registrar";
    }

    @PostMapping("/registrar")
    public String registrarHistoria(@Valid @ModelAttribute("historia") HistoriaClinicaDTO dto,
                                    BindingResult result, // Para manejar la validación en el POST
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.historia", result);
            redirectAttributes.addFlashAttribute("historia", dto);
            return "redirect:/historias/registrar";
        }

        try {
            historiaClinicaService.registrarHistoria(dto);
            redirectAttributes.addFlashAttribute("exito", "Historia clínica registrada correctamente");
            return "redirect:/historias/listar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("historia", dto);
            return "redirect:/historias/registrar";
        }
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable Integer id, Model model) {
        HistoriaClinica historia = historiaClinicaService.obtenerHistoriaPorId(id);

        HistoriaClinicaDTO dto = new HistoriaClinicaDTO();
        dto.setIdHistoria(historia.getIdHistoria());
        dto.setPacienteId(historia.getPaciente().getIdPaciente());
        dto.setFechaApertura(historia.getFechaApertura());
        dto.setObservaciones(historia.getObservaciones());

        model.addAttribute("historia", dto);
        model.addAttribute("pacientes", pacienteService.listarPacientes());

        return "historias/actualizar";
    }


    @PostMapping("/actualizar/{id}")
    public String actualizarHistoria(@PathVariable Integer id,
                                     @Valid @ModelAttribute("historia") HistoriaClinicaDTO dto,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {

        // 🧩 1. Validaciones de campos (DTO)
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.historia", result);
            redirectAttributes.addFlashAttribute("historia", dto);
            redirectAttributes.addFlashAttribute("error", "Error de validación: revisa los campos marcados.");
            // ✅ Corregido: redirige al formulario correcto
            return "redirect:/historias/actualizar/" + id;
        }

        try {
            // 🧠 2. Lógica de negocio (actualizar historia)
            historiaClinicaService.actualizarHistoria(id, dto);

            // ✅ 3. Mensaje de éxito
            redirectAttributes.addFlashAttribute("exito", "Historia clínica actualizada correctamente.");
            return "redirect:/historias/listar";

        } catch (RuntimeException e) {
            // ⚠️ 4. Error controlado desde el servicio
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("historia", dto);
            // ✅ Corregido
            return "redirect:/historias/actualizar/" + id;

        } catch (Exception e) {
            // ❌ 5. Error inesperado
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado al actualizar la historia clínica.");
            redirectAttributes.addFlashAttribute("historia", dto);
            // ✅ Corregido
            return "redirect:/historias/actualizar/" + id;
        }
    }





    @GetMapping("/eliminar/{id}")
    public String eliminarHistoria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            historiaClinicaService.eliminarHistoria(id);
            redirectAttributes.addFlashAttribute("exito", "Historia clínica eliminada correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/historias/listar";
    }

    @GetMapping("/por-paciente/{idPaciente}")
    public String verHistoriaPorPaciente(@PathVariable Integer idPaciente, Model model,
                                         RedirectAttributes redirectAttributes) {
        try {
            HistoriaClinica historia = historiaClinicaService.obtenerPorPacienteId(idPaciente);
            model.addAttribute("historia", historia);
            return "historias/ver";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/historias/listar";
        }
    }
}