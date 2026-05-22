package com.miempresa.trabajoFinal.models;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
@DiscriminatorValue("SU")
public class PreguntaSeleccionUnica extends Pregunta {

    @ElementCollection
    @CollectionTable(name = "su_opciones", joinColumns = @JoinColumn(name = "pregunta_id"))
    @Column(name = "opcion")
    private List<String> opciones;

    private int opcionCorrecta;

    public PreguntaSeleccionUnica() {
    }

    public PreguntaSeleccionUnica(String enunciado, List<String> opciones, int opcionCorrecta) {
        super(enunciado);
        this.opciones = opciones;
        this.opcionCorrecta = opcionCorrecta;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public int getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(int opcionCorrecta) {
        this.opcionCorrecta = opcionCorrecta;
    }

    @Override
    public String mostrarTipo() {
        return "Selección única";
    }
}
