package com.miempresa.trabajoFinal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private PreguntaServiceImpl preguntaServiceImpl;

    @GetMapping({"/", "home"})
    public String getMethodName(Model model, HttpServletRequest request) {
        long totalPreguntas = preguntaServiceImpl.contarPreguntas();
        long totalTematicas = preguntaServiceImpl.contarTematicas();
        double mediaCorrectas = preguntaServiceImpl.mediaCorrectas();
        List<Tematica> tematicas = preguntaServiceImpl.listarTematicas();

        Map<Long, Integer> preguntasPorTematica = new HashMap<>();
        for (Tematica t : tematicas) {
            preguntasPorTematica.put(t.getId(), t.getPreguntas().size());
        }

        model.addAttribute("totalPreguntas", totalPreguntas);
        model.addAttribute("totalTematicas", totalTematicas);
        model.addAttribute("mediaCorrectas", mediaCorrectas);
        model.addAttribute("tematicas", tematicas);
        model.addAttribute("preguntasPorTematica", preguntasPorTematica);
        return "home";
    }
}
