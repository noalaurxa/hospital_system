package com.hospital.hospital_system.controller;

import com.hospital.hospital_system.dto.PacienteDTO;
import com.hospital.hospital_system.entity.Paciente;
import com.hospital.hospital_system.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Listar pacientes
    @GetMapping("/listar")
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.listarPacientes());
        return "pacientes/listar"; // templates/pacientes/listar.html
    }

    // Formulario para registrar paciente
    @GetMapping("/registrar")
    public String mostrarFormularioRegistrar(Model model) {
        if (!model.containsAttribute("paciente")) {
            model.addAttribute("paciente", new PacienteDTO());
        }
        return "pacientes/registrar";
    }

    // Registrar paciente
    @PostMapping("/registrar")
    public String registrarPaciente(@Valid @ModelAttribute("paciente") PacienteDTO dto,
                                    RedirectAttributes redirectAttributes) {
        try {
            pacienteService.registrarPaciente(dto);
            redirectAttributes.addFlashAttribute("exito", "Paciente registrado correctamente");
            return "redirect:/pacientes/listar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("paciente", dto);
            return "redirect:/pacientes/registrar";
        }
    }

    // Formulario para actualizar paciente
    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable Integer id, Model model,
                                              RedirectAttributes redirectAttributes) {
        try {
            if (!model.containsAttribute("paciente")) {
                Paciente paciente = pacienteService.obtenerPacientePorId(id);
                model.addAttribute("paciente", paciente);
            }
            return "pacientes/actualizar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/pacientes/listar";
        }
    }

    // Actualizar paciente
    @PostMapping("/actualizar/{id}")
    public String actualizarPaciente(@PathVariable Integer id,
                                     @Valid @ModelAttribute("paciente") PacienteDTO dto,
                                     RedirectAttributes redirectAttributes) {
        try {
            pacienteService.actualizarPaciente(id, dto);
            redirectAttributes.addFlashAttribute("exito", "Paciente actualizado correctamente");
            return "redirect:/pacientes/listar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("paciente", dto);
            return "redirect:/pacientes/actualizar/" + id;
        }
    }

    // Desactivar paciente
    @GetMapping("/desactivar/{id}")
    public String desactivarPaciente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            pacienteService.desactivarPaciente(id);
            redirectAttributes.addFlashAttribute("exito", "Paciente desactivado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes/listar";
    }
    @GetMapping("/activar/{id}")
    public String activarPaciente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            pacienteService.activarPaciente(id);
            redirectAttributes.addFlashAttribute("exito", "Paciente activado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes/listar";
    }

}
