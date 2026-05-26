package com.miempresa.trabajoFinal.controller;

import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.PreguntaVerdaderoFalso;
import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PreguntaApiControllerTest {

    @Mock
    private PreguntaServiceImpl preguntaServiceImpl;

    @InjectMocks
    private PreguntaApiController preguntaApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(preguntaApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    private Pregunta crearPreguntaVF(Long id, String enunciado, boolean correcto, String tematicaNombre) {
        Tematica t = new Tematica(tematicaNombre);
        t.setId(id != null ? id : 1L);
        PreguntaVerdaderoFalso p = new PreguntaVerdaderoFalso(enunciado, correcto);
        p.setId(id);
        p.setTematica(t);
        return p;
    }

    @Test
    void listar_sinFiltros_retornaPagina() throws Exception {
        Pregunta p = crearPreguntaVF(1L, "Java es interpretado", true, "Programación");
        Page<Pregunta> pagina = new PageImpl<>(List.of(p), PageRequest.of(0, 10), 1);
        when(preguntaServiceImpl.filtrarPreguntas(isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(pagina);

        mockMvc.perform(get("/api/preguntas")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].enunciado").value("Java es interpretado"))
            .andExpect(jsonPath("$.content[0].tipoNombre").value("Verdadero / Falso"))
            .andExpect(jsonPath("$.content[0].correcto").value(true))
            .andExpect(jsonPath("$.content[0].tematica").value("Programación"));
    }

    @Test
    void listar_conFiltroTematica_retornaFiltrados() throws Exception {
        Pregunta p = crearPreguntaVF(2L, "SQL es un lenguaje", true, "Bases de Datos");
        Page<Pregunta> pagina = new PageImpl<>(List.of(p), PageRequest.of(0, 10), 1);
        when(preguntaServiceImpl.filtrarPreguntas(eq(1L), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(pagina);

        mockMvc.perform(get("/api/preguntas?tematicaId=1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].tematica").value("Bases de Datos"));
    }

    @Test
    void listar_conFiltroTexto_retornaCoincidencias() throws Exception {
        Pregunta p = crearPreguntaVF(3L, "Java es un lenguaje", true, "Programación");
        Page<Pregunta> pagina = new PageImpl<>(List.of(p), PageRequest.of(0, 10), 1);
        when(preguntaServiceImpl.filtrarPreguntas(isNull(), isNull(), eq("Java"), any(Pageable.class)))
                .thenReturn(pagina);

        mockMvc.perform(get("/api/preguntas?texto=Java")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].enunciado").value("Java es un lenguaje"));
    }

    @Test
    void obtener_porIdExistente_retornaPregunta() throws Exception {
        Pregunta p = crearPreguntaVF(1L, "Java es interpretado", true, "Programación");
        when(preguntaServiceImpl.obtenerPregunta(1L)).thenReturn(p);

        mockMvc.perform(get("/api/preguntas/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.enunciado").value("Java es interpretado"))
            .andExpect(jsonPath("$.tipoNombre").value("Verdadero / Falso"));
    }

    @Test
    void obtener_porIdInexistente_retornaNotFound() throws Exception {
        when(preguntaServiceImpl.obtenerPregunta(999L)).thenReturn(null);

        mockMvc.perform(get("/api/preguntas/999")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
