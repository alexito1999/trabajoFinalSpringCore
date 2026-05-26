package com.miempresa.trabajoFinal.controller;

import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionUnica;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class JuegoControllerTest {

    @Mock
    private PreguntaServiceImpl preguntaServiceImpl;

    @InjectMocks
    private JuegoController juegoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(juegoController).build();
    }

    @Test
    void seleccionarTematica_returnsJugarViewWithTematicas() throws Exception {
        List<Tematica> tematicas = Arrays.asList(new Tematica(1L, "Java"), new Tematica(2L, "Spring"));
        when(preguntaServiceImpl.listarTematicas()).thenReturn(tematicas);

        mockMvc.perform(get("/jugar"))
                .andExpect(status().isOk())
                .andExpect(view().name("jugar"))
                .andExpect(model().attributeExists("tematicas"))
                .andExpect(model().attribute("tematicas", tematicas))
                .andExpect(model().attribute("activeJugar", true));
    }

    @Test
    void jugar_withTematicaId_returnsJuegoViewWithQuestions() throws Exception {
        Long tematicaId = 1L;
        Tematica tematica = new Tematica(tematicaId, "Java");
        List<Pregunta> preguntas = Collections.singletonList(
                new PreguntaSeleccionUnica(1L, "¿Qué es Java?", "Java", Arrays.asList("Java", "C++", "Python"), tematica)
        );
        
        when(preguntaServiceImpl.listarTematicas()).thenReturn(Collections.singletonList(tematica));
        when(preguntaServiceImpl.listarPorTematica(anyLong())).thenReturn(preguntas);

        mockMvc.perform(get("/jugar/{tematicaId}", tematicaId))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attributeExists("preguntas"))
                .andExpect(model().attributeExists("total"))
                .andExpect(model().attributeExists("totalDisponible"))
                .andExpect(model().attributeExists("tematica"))
                .andExpect(model().attribute("tematica", tematica))
                .andExpect(model().attribute("activeJugar", true));
    }

    @Test
    void jugar_withTematicaIdAndQuantity_returnsJuegoViewWithLimitedQuestions() throws Exception {
        Long tematicaId = 1L;
        Tematica tematica = new Tematica(tematicaId, "Java");
        List<Pregunta> preguntas = Arrays.asList(
                new PreguntaSeleccionUnica(1L, "¿Qué es Java?", "Java", Arrays.asList("Java", "C++", "Python"), tematica),
                new PreguntaSeleccionUnica(2L, "¿Qué es Spring?", "Spring", Arrays.asList("Spring", "Hibernate", "Maven"), tematica)
        );

        when(preguntaServiceImpl.listarTematicas()).thenReturn(Collections.singletonList(tematica));
        when(preguntaServiceImpl.listarPorTematica(anyLong())).thenReturn(preguntas);

        mockMvc.perform(get("/jugar/{tematicaId}", tematicaId).param("cantidad", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attributeExists("preguntas"))
                .andExpect(model().attribute("total", 1)); // Expecting only 1 question due to quantity param
    }
}
