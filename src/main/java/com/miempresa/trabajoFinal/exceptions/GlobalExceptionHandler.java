package com.miempresa.trabajoFinal.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PreguntaNoEncontradaException.class)
    public String preguntaNoEncontrada(PreguntaNoEncontradaException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("detalle", "La pregunta que buscas no existe o ha sido eliminada.");
        return "error/404";
    }

    @ExceptionHandler(TematicaNoEncontradaException.class)
    public String tematicaNoEncontrada(TematicaNoEncontradaException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("detalle", "La temática seleccionada no existe.");
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String errorGeneral(Exception ex, Model model) {
        model.addAttribute("mensaje", "Ha ocurrido un error inesperado");
        model.addAttribute("detalle", ex.getMessage() != null ? ex.getMessage() : "Contacta con el administrador.");
        return "error/500";
    }
}
