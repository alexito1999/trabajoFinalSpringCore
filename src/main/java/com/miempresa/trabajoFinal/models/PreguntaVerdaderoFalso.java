package com.miempresa.trabajoFinal.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VF")
public class PreguntaVerdaderoFalso extends Pregunta {

    private boolean correcto;

    public PreguntaVerdaderoFalso() {
    }

    public PreguntaVerdaderoFalso(String enunciado, boolean correcto) {
        super(enunciado);
        this.correcto = correcto;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    @Override
    public String mostrarTipo() {
        return "Verdadero / Falso";
    }
}
