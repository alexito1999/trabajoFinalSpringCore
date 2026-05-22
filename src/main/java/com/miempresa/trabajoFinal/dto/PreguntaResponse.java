package com.miempresa.trabajoFinal.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con los datos de una pregunta")
public class PreguntaResponse {

    private Long id;

    private String enunciado;

    private String tipo;

    private String tipoNombre;

    private String tematica;

    private Long tematicaId;

    private Boolean correcto;

    private List<String> opciones;

    private Integer opcionCorrecta;

    private List<Integer> opcionesCorrectas;
}
