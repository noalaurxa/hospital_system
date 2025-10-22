package com.hospital.hospital_system.controller;

import com.hospital.hospital_system.dto.AntecedenteMedicoDTO;
import com.hospital.hospital_system.entity.AntecedenteMedico;
import com.hospital.hospital_system.service.AntecedenteMedicoService;
import com.hospital.hospital_system.service.HistoriaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/antecedentes")
public class AntecedenteMedicoController {

    @Autowired
    private AntecedenteMedicoService antecedenteMedicoService;

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    // ✅ Listar antecedentes
    @GetMapping("/listar")
    public String listarAntecedentes(Model model) {
        List<AntecedenteMedico> antecedentes = antecedenteMedicoService.listarAntecedentes();
        model.addAttribute("antecedentes", antecedentes);
        return "antecedentes/listar";
    }

    // ✅ Formulario para registrar antecedente
    @GetMapping("/registrar")
    public String mostrarFormularioRegistrar(Model model) {
        if (!model.containsAttribute("antecedente")) {
            model.addAttribute("antecedente", new AntecedenteMedicoDTO());
        }
        model.addAttribute("historias", historiaClinicaService.listarHistorias());
        return "antecedentes/registrar";
    }

    // ✅ Registrar antecedente
    @PostMapping("/registrar")
    public String registrarAntecedente(@Valid @ModelAttribute("antecedente") AntecedenteMedicoDTO dto,
                                       RedirectAttributes redirectAttributes) {
        try {
            antecedenteMedicoService.registrarAntecedente(dto);
            redirectAttributes.addFlashAttribute("exito", "Antecedente médico registrado correctamente");
            return "redirect:/antecedentes/listar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("antecedente", dto);
            return "redirect:/antecedentes/registrar";
        }
    }

    // ✅ Formulario para actualizar antecedente
    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable Integer id, Model model,
                                              RedirectAttributes redirectAttributes) {
        try {
            if (!model.containsAttribute("antecedente")) {
                AntecedenteMedico antecedente = antecedenteMedicoService.obtenerAntecedentePorId(id);
                AntecedenteMedicoDTO dto = new AntecedenteMedicoDTO();

                // 🩺 Asignamos todos los datos al DTO
                dto.setId(antecedente.getIdAntecedente());
                dto.setHistoriaClinicaId(antecedente.getHistoriaClinica().getIdHistoria());
                dto.setTipo(antecedente.getTipo());
                dto.setDescripcion(antecedente.getDescripcion());

                model.addAttribute("antecedente", dto);
            }

            model.addAttribute("historias", historiaClinicaService.listarHistorias());
            return "antecedentes/actualizar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/antecedentes/listar";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarAntecedente(@PathVariable Integer id,
                                        @Valid @ModelAttribute("antecedente") AntecedenteMedicoDTO dto,
                                        RedirectAttributes redirectAttributes) {
        try {
            // ⚠️ Asegúrate de usar dto.getId si quieres mantener consistencia
            antecedenteMedicoService.actualizarAntecedente(dto.getId(), dto);
            redirectAttributes.addFlashAttribute("exito", "Antecedente médico actualizado correctamente");
            return "redirect:/antecedentes/listar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("antecedente", dto);
            return "redirect:/antecedentes/actualizar/" + dto.getId();
        }
    }


    // ✅ Eliminar antecedente
    @GetMapping("/eliminar/{id}")
    public String eliminarAntecedente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            antecedenteMedicoService.eliminarAntecedente(id);
            redirectAttributes.addFlashAttribute("exito", "Antecedente médico eliminado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/antecedentes/listar";
    }
}
