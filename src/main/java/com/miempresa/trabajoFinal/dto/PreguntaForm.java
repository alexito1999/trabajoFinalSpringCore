package com.miempresa.trabajoFinal.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PreguntaForm {

    private Long id;

    @NotBlank(message = "El enunciado es obligatorio")
    @Size(max = 500, message = "El enunciado no puede tener más de 500 caracteres")
    private String enunciado;

    @NotNull(message = "Selecciona un tipo de pregunta")
    private String tipo;

    private boolean correcto;

    private List<String> opciones;

    private int opcionCorrecta;

    private String opcionesCorrectas;

    @NotNull(message = "Debes seleccionar una temática")
    private Long tematicaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public boolean isCorrecto() { return correcto; }
    public void setCorrecto(boolean correcto) { this.correcto = correcto; }
    public List<String> getOpciones() { return opciones; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }
    public int getOpcionCorrecta() { return opcionCorrecta; }
    public void setOpcionCorrecta(int opcionCorrecta) { this.opcionCorrecta = opcionCorrecta; }
    public String getOpcionesCorrectas() { return opcionesCorrectas; }
    public void setOpcionesCorrectas(String opcionesCorrectas) { this.opcionesCorrectas = opcionesCorrectas; }
    public Long getTematicaId() { return tematicaId; }
    public void setTematicaId(Long tematicaId) { this.tematicaId = tematicaId; }
}
