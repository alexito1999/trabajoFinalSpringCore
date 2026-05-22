package com.miempresa.trabajoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

@Controller
public class HomeController {

    @Autowired
    private PreguntaServiceImpl preguntaServiceImpl;

    @GetMapping({"/", "home"})
    public String getMethodName(Model model) {
        model.addAttribute("totalPreguntas", preguntaServiceImpl.contarPreguntas());
        model.addAttribute("totalTematicas", preguntaServiceImpl.contarTematicas());
        model.addAttribute("mediaCorrectas", preguntaServiceImpl.mediaCorrectas());
        model.addAttribute("tematicas", preguntaServiceImpl.listarTematicas());
        return "home";
    }


}
