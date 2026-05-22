package com.miempresa.trabajoFinal.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miempresa.trabajoFinal.dto.PreguntaForm;
import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionMultiple;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionUnica;
import com.miempresa.trabajoFinal.models.PreguntaVerdaderoFalso;
import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

@Controller
@RequestMapping("/pregunta")
public class PreguntaController {

    @Autowired
    private PreguntaServiceImpl preguntaServiceImpl;

    @GetMapping("/listar")
    public String listarPreguntas(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size,
                                  @RequestParam(required = false) Long tematicaId,
                                  @RequestParam(required = false) String tipo,
                                  Model model) {
        if (tipo != null && tipo.isEmpty()) tipo = null;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        model.addAttribute("pagina", preguntaServiceImpl.filtrarPreguntas(tematicaId, tipo, pageRequest));
        model.addAttribute("tematicas", preguntaServiceImpl.listarTematicas());
        model.addAttribute("tematicaId", tematicaId);
        model.addAttribute("tipoFiltro", tipo);
        model.addAttribute("activePreguntas", true);
        return "lista-pregunta";
    }

    @GetMapping("/nueva")
    public String nuevaPregunta(Model model) {
        model.addAttribute("form", new PreguntaForm());
        model.addAttribute("tematicas", preguntaServiceImpl.listarTematicas());
        model.addAttribute("activePreguntas", true);
        return "formulario-pregunta";
    }

    @GetMapping("/editar/{id}")
    public String editarPregunta(@PathVariable Long id, Model model) {
        Pregunta p = preguntaServiceImpl.obtenerPregunta(id);
        PreguntaForm form = new PreguntaForm();
        form.setId(p.getId());
        form.setEnunciado(p.getEnunciado());
        form.setTipo(p.getTipo());
        form.setTematicaId(p.getTematica() != null ? p.getTematica().getId() : null);

        if (p instanceof PreguntaVerdaderoFalso vf) {
            form.setCorrecto(vf.isCorrecto());
        } else if (p instanceof PreguntaSeleccionUnica su) {
            form.setOpciones(su.getOpciones());
            form.setOpcionCorrecta(su.getOpcionCorrecta());
        } else if (p instanceof PreguntaSeleccionMultiple sm) {
            form.setOpciones(sm.getOpciones());
            form.setOpcionesCorrectas(sm.getOpcionesCorrectas().stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(",")));
        }

        model.addAttribute("form", form);
        model.addAttribute("tematicas", preguntaServiceImpl.listarTematicas());
        model.addAttribute("activePreguntas", true);
        return "formulario-pregunta";
    }

    @PostMapping("/guardar")
    public String guardarPregunta(@Valid @ModelAttribute("form") PreguntaForm form,
                                  BindingResult result, Model model) {
        boolean tematicaValida = preguntaServiceImpl.listarTematicas().stream()
            .anyMatch(t -> t.getId().equals(form.getTematicaId()));
        if (!tematicaValida) {
            result.rejectValue("tematicaId", "tematica.invalida", "La temática seleccionada no existe");
        }

        if (result.hasErrors()) {
            model.addAttribute("tematicas", preguntaServiceImpl.listarTematicas());
            model.addAttribute("activePreguntas", true);
            return "formulario-pregunta";
        }

        Tematica tematica = preguntaServiceImpl.listarTematicas().stream()
            .filter(t -> t.getId().equals(form.getTematicaId()))
            .findFirst().orElse(null);

        Pregunta pregunta = switch (form.getTipo()) {
            case "VF" -> {
                PreguntaVerdaderoFalso vf = new PreguntaVerdaderoFalso(form.getEnunciado(), form.isCorrecto());
                vf.setTematica(tematica);
                if (form.getId() != null) vf.setId(form.getId());
                yield vf;
            }
            case "SU" -> {
                PreguntaSeleccionUnica su = new PreguntaSeleccionUnica(form.getEnunciado(), form.getOpciones(), form.getOpcionCorrecta());
                su.setTematica(tematica);
                if (form.getId() != null) su.setId(form.getId());
                yield su;
            }
            case "SM" -> {
                List<Integer> correctas = java.util.Arrays.stream(form.getOpcionesCorrectas().split(","))
                    .map(String::trim).map(Integer::parseInt).toList();
                PreguntaSeleccionMultiple sm = new PreguntaSeleccionMultiple(form.getEnunciado(), form.getOpciones(), correctas);
                sm.setTematica(tematica);
                if (form.getId() != null) sm.setId(form.getId());
                yield sm;
            }
            default -> null;
        };

        if (pregunta != null) {
            preguntaServiceImpl.guardarPregunta(pregunta);
        }
        return "redirect:/pregunta/listar";
    }

    @PostMapping("/borrar/{id}")
    public String eliminarPregunta(@PathVariable Long id) {
        preguntaServiceImpl.eliminarPregunta(id);
        return "redirect:/pregunta/listar";
    }

}
