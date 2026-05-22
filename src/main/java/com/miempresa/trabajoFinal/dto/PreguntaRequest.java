package com.miempresa.trabajoFinal.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaRequest {

    @NotBlank(message = "El enunciado es obligatorio")
    @Size(max = 500)
    private String enunciado;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "La temática es obligatoria")
    private Long tematicaId;

    private boolean correcto;

    private List<String> opciones;

    private int opcionCorrecta;

    private List<Integer> opcionesCorrectas;
}
