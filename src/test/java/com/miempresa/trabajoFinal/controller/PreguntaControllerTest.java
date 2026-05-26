package com.miempresa.trabajoFinal.controller;

import com.miempresa.trabajoFinal.dto.PreguntaForm;
import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PreguntaControllerTest {

    @Mock
    private PreguntaServiceImpl preguntaServiceImpl;

    @InjectMocks
    private PreguntaController preguntaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(preguntaController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void listar_sinFiltros_retornaVistaConPagina() throws Exception {
        Page<Pregunta> pagina = new PageImpl<>(List.of());
        when(preguntaServiceImpl.filtrarPreguntas(isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(pagina);
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/pregunta/listar"))
            .andExpect(status().isOk())
            .andExpect(view().name("lista-pregunta"))
            .andExpect(model().attributeExists("pagina"))
            .andExpect(model().attributeExists("tematicas"))
            .andExpect(model().attribute("activePreguntas", true));
    }

    @Test
    void listar_conFiltros_pasaParametros() throws Exception {
        when(preguntaServiceImpl.filtrarPreguntas(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of()));
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/pregunta/listar")
                .param("tematicaId", "1")
                .param("tipo", "VF")
                .param("texto", "Java")
                .param("page", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("lista-pregunta"))
            .andExpect(model().attribute("tematicaId", 1L))
            .andExpect(model().attribute("tipoFiltro", "VF"))
            .andExpect(model().attribute("textoBusqueda", "Java"))
            .andExpect(model().attribute("activePreguntas", true));
    }

    @Test
    void listar_conFiltrosVacios_trataComoNull() throws Exception {
        when(preguntaServiceImpl.filtrarPreguntas(isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/pregunta/listar")
                .param("tipo", "")
                .param("texto", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("lista-pregunta"))
            .andExpect(model().attributeDoesNotExist("tipoFiltro"))
            .andExpect(model().attributeDoesNotExist("textoBusqueda"));
    }

    @Test
    void nueva_retornaFormulario() throws Exception {
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/pregunta/nueva"))
            .andExpect(status().isOk())
            .andExpect(view().name("formulario-pregunta"))
            .andExpect(model().attributeExists("form"))
            .andExpect(model().attribute("activePreguntas", true));
    }

    @Test
    void guardar_conErrores_retornaFormulario() throws Exception {
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(post("/pregunta/guardar")
                .param("enunciado", "")
                .param("tipo", "VF")
                .param("tematicaId", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("formulario-pregunta"))
            .andExpect(model().attributeExists("form"))
            .andExpect(model().attribute("activePreguntas", true));
    }
}
