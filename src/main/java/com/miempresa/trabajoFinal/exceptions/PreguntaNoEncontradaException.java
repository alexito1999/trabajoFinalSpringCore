package com.miempresa.trabajoFinal.exceptions;

public class PreguntaNoEncontradaException extends RuntimeException {
    private final Long id;

    public PreguntaNoEncontradaException(Long id) {
        super("Pregunta con id " + id + " no encontrada");
        this.id = id;
    }

    public Long getPreguntaId() {
        return id;
    }
}
