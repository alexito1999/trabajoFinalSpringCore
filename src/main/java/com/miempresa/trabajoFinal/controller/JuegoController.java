package com.miempresa.trabajoFinal.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                        @RequestParam(defaultValue = "0") int limite,
                        Model model) {
        List<Pregunta> preguntas = preguntaServiceImpl.listarPorTematica(tematicaId);
        int totalDisponible = preguntas.size();

        if (limite > 0 && preguntas.size() > limite) {
            Collections.shuffle(preguntas);
            preguntas = preguntas.subList(0, limite);
        }

        var tematica = preguntaServiceImpl.listarTematicas().stream()
            .filter(t -> t.getId().equals(tematicaId))
            .findFirst().orElse(null);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            json.append("{");
            json.append("\"tipo\":\"").append(p.getTipo()).append("\",");
            json.append("\"enunciado\":\"").append(escapeJson(p.getEnunciado())).append("\"");

            if (p instanceof PreguntaVerdaderoFalso vf) {
                json.append(",\"correcto\":").append(vf.isCorrecto());
            } else if (p instanceof PreguntaSeleccionUnica su) {
                json.append(",\"opciones\":[");
                json.append(su.getOpciones().stream()
                    .map(o -> "\"" + escapeJson(o) + "\"")
                    .collect(Collectors.joining(",")));
                json.append("],\"opcionCorrecta\":").append(su.getOpcionCorrecta());
            } else if (p instanceof PreguntaSeleccionMultiple sm) {
                json.append(",\"opciones\":[");
                json.append(sm.getOpciones().stream()
                    .map(o -> "\"" + escapeJson(o) + "\"")
                    .collect(Collectors.joining(",")));
                json.append("],\"opcionesCorrectas\":[");
                json.append(sm.getOpcionesCorrectas().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
                json.append("]");
            }

            json.append("}");
            if (i < preguntas.size() - 1) json.append(",");
        }
        json.append("]");

        model.addAttribute("preguntasJson", json.toString());
        model.addAttribute("total", preguntas.size());
        model.addAttribute("totalDisponible", totalDisponible);
        model.addAttribute("tematica", tematica);
        model.addAttribute("activeJugar", true);
        return "juego";
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
