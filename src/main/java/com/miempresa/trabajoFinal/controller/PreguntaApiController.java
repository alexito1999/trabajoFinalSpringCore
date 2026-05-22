package com.miempresa.trabajoFinal.controller;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miempresa.trabajoFinal.dto.PreguntaRequest;
import com.miempresa.trabajoFinal.dto.PreguntaResponse;
import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionMultiple;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionUnica;
import com.miempresa.trabajoFinal.models.PreguntaVerdaderoFalso;
import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/preguntas")
@Tag(name = "Preguntas", description = "API REST para la gestión de preguntas")
public class PreguntaApiController {

    @Autowired
    private PreguntaServiceImpl preguntaServiceImpl;

    @GetMapping
    @Operation(summary = "Listar preguntas", description = "Devuelve una lista paginada de preguntas, con filtros opcionales por temática y tipo")
    public ResponseEntity<Page<PreguntaResponse>> listar(
            @RequestParam(required = false) @Parameter(description = "Filtrar por ID de temática") Long tematicaId,
            @RequestParam(required = false) @Parameter(description = "Filtrar por tipo (VF, SU, SM)") String tipo,
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if (tipo != null && tipo.isEmpty()) tipo = null;
        Page<Pregunta> pagina = preguntaServiceImpl.filtrarPreguntas(tematicaId, tipo, pageable);
        Page<PreguntaResponse> respuesta = pagina.map(this::toResponse);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pregunta por ID", description = "Devuelve los detalles de una pregunta específica")
    public ResponseEntity<PreguntaResponse> obtener(@PathVariable Long id) {
        Pregunta pregunta = preguntaServiceImpl.obtenerPregunta(id);
        if (pregunta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(pregunta));
    }

    @PostMapping
    @Operation(summary = "Crear pregunta", description = "Crea una nueva pregunta")
    public ResponseEntity<PreguntaResponse> crear(@Valid @RequestBody PreguntaRequest request) {
        Tematica tematica = preguntaServiceImpl.obtenerTematica(request.getTematicaId());
        if (tematica == null) {
            return ResponseEntity.badRequest().build();
        }

        Pregunta pregunta = buildFromRequest(request, tematica, null);
        if (pregunta == null) {
            return ResponseEntity.badRequest().build();
        }

        Pregunta guardada = preguntaServiceImpl.guardarPregunta(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(guardada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pregunta", description = "Actualiza una pregunta existente")
    public ResponseEntity<PreguntaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PreguntaRequest request) {
        Pregunta existente = preguntaServiceImpl.obtenerPregunta(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        Tematica tematica = preguntaServiceImpl.obtenerTematica(request.getTematicaId());
        if (tematica == null) {
            return ResponseEntity.badRequest().build();
        }

        Pregunta pregunta = buildFromRequest(request, tematica, id);
        if (pregunta == null) {
            return ResponseEntity.badRequest().build();
        }

        Pregunta guardada = preguntaServiceImpl.guardarPregunta(pregunta);
        return ResponseEntity.ok(toResponse(guardada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pregunta", description = "Elimina una pregunta por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Pregunta pregunta = preguntaServiceImpl.obtenerPregunta(id);
        if (pregunta == null) {
            return ResponseEntity.notFound().build();
        }
        preguntaServiceImpl.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }

    private PreguntaResponse toResponse(Pregunta pregunta) {
        PreguntaResponse.PreguntaResponseBuilder builder = PreguntaResponse.builder()
                .id(pregunta.getId())
                .enunciado(pregunta.getEnunciado())
                .tipo(pregunta.getTipo())
                .tipoNombre(pregunta.mostrarTipo());

        if (pregunta.getTematica() != null) {
            builder.tematica(pregunta.getTematica().getNombre());
            builder.tematicaId(pregunta.getTematica().getId());
        }

        if (pregunta instanceof PreguntaVerdaderoFalso vf) {
            builder.correcto(vf.isCorrecto());
        } else if (pregunta instanceof PreguntaSeleccionUnica su) {
            builder.opciones(su.getOpciones());
            builder.opcionCorrecta(su.getOpcionCorrecta());
        } else if (pregunta instanceof PreguntaSeleccionMultiple sm) {
            builder.opciones(sm.getOpciones());
            builder.opcionesCorrectas(sm.getOpcionesCorrectas());
        }

        return builder.build();
    }

    private Pregunta buildFromRequest(PreguntaRequest request, Tematica tematica, Long id) {
        Pregunta pregunta = switch (request.getTipo()) {
            case "VF" -> {
                PreguntaVerdaderoFalso vf = new PreguntaVerdaderoFalso(request.getEnunciado(), request.isCorrecto());
                vf.setTematica(tematica);
                if (id != null) vf.setId(id);
                yield vf;
            }
            case "SU" -> {
                if (request.getOpciones() == null || request.getOpciones().isEmpty()) {
                    yield null;
                }
                PreguntaSeleccionUnica su = new PreguntaSeleccionUnica(request.getEnunciado(), request.getOpciones(), request.getOpcionCorrecta());
                su.setTematica(tematica);
                if (id != null) su.setId(id);
                yield su;
            }
            case "SM" -> {
                if (request.getOpciones() == null || request.getOpciones().isEmpty() || request.getOpcionesCorrectas() == null || request.getOpcionesCorrectas().isEmpty()) {
                    yield null;
                }
                PreguntaSeleccionMultiple sm = new PreguntaSeleccionMultiple(request.getEnunciado(), request.getOpciones(), request.getOpcionesCorrectas());
                sm.setTematica(tematica);
                if (id != null) sm.setId(id);
                yield sm;
            }
            default -> null;
        };
        return pregunta;
    }
}
