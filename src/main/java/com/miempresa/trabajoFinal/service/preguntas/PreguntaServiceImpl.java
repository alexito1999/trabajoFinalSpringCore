package com.miempresa.trabajoFinal.service.preguntas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miempresa.trabajoFinal.exceptions.PreguntaNoEncontradaException;
import com.miempresa.trabajoFinal.exceptions.TematicaNoEncontradaException;
import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.repository.PreguntaRepository;
import com.miempresa.trabajoFinal.repository.TematicaRepository;

@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private TematicaRepository tematicaRepository;

    @Override
    public Page<Pregunta> listarPreguntas(Pageable pageable) {
        return preguntaRepository.findAll(pageable);
    }

    @Override
    public Page<Pregunta> filtrarPreguntas(Long tematicaId, String tipo, String texto, Pageable pageable) {
        if (texto != null && texto.isBlank()) texto = null;
        if (tematicaId == null && tipo == null && texto == null) {
            return preguntaRepository.findAll(pageable);
        }
        return preguntaRepository.filtrar(tematicaId, tipo, texto, pageable);
    }

    @Override
    public Pregunta obtenerPregunta(Long id) {
        return preguntaRepository.findById(id)
            .orElseThrow(() -> new PreguntaNoEncontradaException(id));
    }

    @Override
    public Pregunta guardarPregunta(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    @Override
    public void eliminarPregunta(Long id) {
        preguntaRepository.deleteById(id);
    }

    @Override
    public long contarPreguntas() {
        return preguntaRepository.count();
    }

    @Override
    public long contarTematicas() {
        return tematicaRepository.count();
    }

    @Override
    public double mediaCorrectas() {
        long total = preguntaRepository.count();
        if (total == 0) return 0.0;
        long correctas = preguntaRepository.countVFCorrectas();
        return (double) correctas / total * 100;
    }

    @Override
    public List<Tematica> listarTematicas() {
        return tematicaRepository.findAll();
    }

    @Override
    public Tematica obtenerTematica(Long id) {
        return tematicaRepository.findById(id)
            .orElseThrow(() -> new TematicaNoEncontradaException(id));
    }

    @Override
    public List<Pregunta> listarPorTematica(Long tematicaId) {
        return preguntaRepository.findByTematicaId(tematicaId);
    }

}
