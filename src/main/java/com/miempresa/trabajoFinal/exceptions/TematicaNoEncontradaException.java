package com.miempresa.trabajoFinal.exceptions;

public class TematicaNoEncontradaException extends RuntimeException {
    private final Long id;

    public TematicaNoEncontradaException(Long id) {
        super("Temática con id " + id + " no encontrada");
        this.id = id;
    }

    public Long getTematicaId() {
        return id;
    }
}
