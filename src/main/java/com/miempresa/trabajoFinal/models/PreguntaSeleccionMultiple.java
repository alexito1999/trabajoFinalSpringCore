package com.miempresa.trabajoFinal.models;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
@DiscriminatorValue("SM")
public class PreguntaSeleccionMultiple extends Pregunta {

    @ElementCollection
    @CollectionTable(name = "sm_opciones", joinColumns = @JoinColumn(name = "pregunta_id"))
    @Column(name = "opcion")
    private List<String> opciones;

    @ElementCollection
    @CollectionTable(name = "sm_opciones_correctas", joinColumns = @JoinColumn(name = "pregunta_id"))
    @Column(name = "indice")
    private List<Integer> opcionesCorrectas;

    public PreguntaSeleccionMultiple() {
    }

    public PreguntaSeleccionMultiple(String enunciado, List<String> opciones, List<Integer> opcionesCorrectas) {
        super(enunciado);
        this.opciones = opciones;
        this.opcionesCorrectas = opcionesCorrectas;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public List<Integer> getOpcionesCorrectas() {
        return opcionesCorrectas;
    }

    public void setOpcionesCorrectas(List<Integer> opcionesCorrectas) {
        this.opcionesCorrectas = opcionesCorrectas;
    }

    @Override
    public String mostrarTipo() {
        return "Selección múltiple";
    }
}
