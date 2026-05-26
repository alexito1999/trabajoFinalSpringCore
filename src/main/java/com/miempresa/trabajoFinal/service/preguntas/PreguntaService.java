package com.miempresa.trabajoFinal.service.preguntas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.Tematica;

public interface PreguntaService  {
    
    Page<Pregunta> listarPreguntas(Pageable pageable);
    Page<Pregunta> filtrarPreguntas(Long tematicaId, String tipo, String texto, Pageable pageable);
    Pregunta obtenerPregunta(Long id);
    Pregunta guardarPregunta(Pregunta pregunta);
    void eliminarPregunta(Long id);
    long contarPreguntas();
    long contarTematicas();
    double mediaCorrectas();
    List<Tematica> listarTematicas();
    Tematica obtenerTematica(Long id);
    List<Pregunta> listarPorTematica(Long tematicaId);
}
