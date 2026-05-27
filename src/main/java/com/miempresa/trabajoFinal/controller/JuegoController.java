package com.miempresa.trabajoFinal.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionMultiple;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionUnica;
import com.miempresa.trabajoFinal.models.PreguntaVerdaderoFalso;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

@Controller
@RequestMapping("/jugar")
public class JuegoController {

    @Autowired
    private PreguntaServiceImpl preguntaServiceImpl;

    @GetMapping
    public String seleccionarTematica(Model model) {
        model.addAttribute("tematicas", preguntaServiceImpl.listarTematicas());
        model.addAttribute("activeJugar", true);
        return "jugar";
    }

    @GetMapping("/{tematicaId}")
    public String jugar(@PathVariable Long tematicaId,
                        @RequestParam(defaultValue = "10") int cantidad,
                        Model model) {
        List<Pregunta> preguntas = preguntaServiceImpl.listarPorTematica(tematicaId);
        int totalDisponible = preguntas.size();

        if (cantidad > 0 && preguntas.size() > cantidad) {
            Collections.shuffle(preguntas);
            preguntas = preguntas.subList(0, cantidad);
        }

        var tematica = preguntaServiceImpl.listarTematicas().stream()
            .filter(t -> t.getId().equals(tematicaId))
            .findFirst().orElse(null);

        // Build a list of simple DTOs for the view (instead of a JSON string)
        java.util.List<java.util.Map<String, Object>> preguntasDto = new java.util.ArrayList<>();
        for (Pregunta p : preguntas) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("tipo", p.getTipo());
            map.put("enunciado", p.getEnunciado());
            if (p instanceof PreguntaVerdaderoFalso vf) {
                map.put("correcto", vf.isCorrecto());
            } else if (p instanceof PreguntaSeleccionUnica su) {
                map.put("opciones", su.getOpciones());
                map.put("opcionCorrecta", su.getOpcionCorrecta());
            } else if (p instanceof PreguntaSeleccionMultiple sm) {
                map.put("opciones", sm.getOpciones());
                map.put("opcionesCorrectas", sm.getOpcionesCorrectas());
            }
            preguntasDto.add(map);
        }
        
        // Pass data to the view
        model.addAttribute("preguntas", preguntasDto);
        model.addAttribute("total", preguntasDto.size());
        model.addAttribute("totalDisponible", totalDisponible);
        model.addAttribute("tematica", tematica);
        model.addAttribute("activeJugar", true);
        return "juego";

    }
}
