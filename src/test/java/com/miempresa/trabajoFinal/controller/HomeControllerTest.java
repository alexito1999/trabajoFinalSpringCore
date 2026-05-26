package com.miempresa.trabajoFinal.controller;

import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private PreguntaServiceImpl preguntaServiceImpl;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void home_returnsHomeViewWithStats() throws Exception {
        when(preguntaServiceImpl.contarPreguntas()).thenReturn(42L);
        when(preguntaServiceImpl.contarTematicas()).thenReturn(4L);
        when(preguntaServiceImpl.mediaCorrectas()).thenReturn(75.0);
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(model().attribute("totalPreguntas", 42L))
            .andExpect(model().attribute("totalTematicas", 4L))
            .andExpect(model().attribute("mediaCorrectas", 75.0))
            .andExpect(model().attributeExists("tematicas"))
            .andExpect(model().attributeExists("preguntasPorTematica"));
    }

    @Test
    void homePath_returnsHomeView() throws Exception {
        when(preguntaServiceImpl.contarPreguntas()).thenReturn(0L);
        when(preguntaServiceImpl.contarTematicas()).thenReturn(0L);
        when(preguntaServiceImpl.mediaCorrectas()).thenReturn(0.0);
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/home"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(model().attribute("totalPreguntas", 0L));
    }

    @Test
    void home_sinDatos_retornaCeros() throws Exception {
        when(preguntaServiceImpl.contarPreguntas()).thenReturn(0L);
        when(preguntaServiceImpl.contarTematicas()).thenReturn(0L);
        when(preguntaServiceImpl.mediaCorrectas()).thenReturn(0.0);
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of());

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("totalPreguntas", 0L))
            .andExpect(model().attribute("totalTematicas", 0L))
            .andExpect(model().attribute("mediaCorrectas", 0.0));
    }

    @Test
    void home_conTematicas_cuentaPreguntasPorTematica() throws Exception {
        Tematica prog = new Tematica("Programacion");
        prog.setId(1L);
        Tematica bbdd = new Tematica("Bases de Datos");
        bbdd.setId(2L);

        when(preguntaServiceImpl.contarPreguntas()).thenReturn(5L);
        when(preguntaServiceImpl.contarTematicas()).thenReturn(2L);
        when(preguntaServiceImpl.mediaCorrectas()).thenReturn(60.0);
        when(preguntaServiceImpl.listarTematicas()).thenReturn(List.of(prog, bbdd));

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("totalTematicas", 2L))
            .andExpect(model().attribute("totalPreguntas", 5L));
    }
}
