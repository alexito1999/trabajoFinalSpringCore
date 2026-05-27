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
import static org.hamcrest.Matchers.*;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ExtendWith(MockitoExtension.class)
class JuegoControllerTest {

    @Mock
    private PreguntaServiceImpl preguntaServiceImpl;

    @InjectMocks
    private JuegoController juegoController;

    private MockMvc mockMvc;

    private Tematica crearTematica(Long id, String nombre) {
        Tematica t = new Tematica(nombre);
        t.setId(id);
        return t;
    }

    private PreguntaSeleccionUnica crearPreguntaSU(Long id, String enunciado, List<String> opciones, int opcionCorrecta, Tematica tematica) {
        PreguntaSeleccionUnica p = new PreguntaSeleccionUnica(enunciado, opciones, opcionCorrecta);
        p.setId(id);
        p.setTematica(tematica);
        return p;
    }

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(juegoController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void seleccionarTematica_returnsJugarViewWithTematicas() throws Exception {
        Tematica java = crearTematica(1L, "Java");
        Tematica spring = crearTematica(2L, "Spring");
        List<Tematica> tematicas = Arrays.asList(java, spring);
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
        Tematica tematica = crearTematica(tematicaId, "Java");
        PreguntaSeleccionUnica pregunta = crearPreguntaSU(
                1L, "Que es Java?", Arrays.asList("Java", "C++", "Python"), 0, tematica
        );
        List<Pregunta> preguntas = Collections.singletonList(pregunta);

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
                .andExpect(model().attribute("activeJugar", true))
                .andExpect(model().attribute("preguntas", hasSize(1)));
    }

    @Test
    void jugar_withTematicaIdAndQuantity_returnsJuegoViewWithLimitedQuestions() throws Exception {
        Long tematicaId = 1L;
        Tematica tematica = crearTematica(tematicaId, "Java");
        PreguntaSeleccionUnica p1 = crearPreguntaSU(
                1L, "Que es Java?", Arrays.asList("Java", "C++", "Python"), 0, tematica
        );
        PreguntaSeleccionUnica p2 = crearPreguntaSU(
                2L, "Que es Spring?", Arrays.asList("Spring", "Hibernate", "Maven"), 0, tematica
        );
        List<Pregunta> preguntas = Arrays.asList(p1, p2);

        when(preguntaServiceImpl.listarTematicas()).thenReturn(Collections.singletonList(tematica));
        when(preguntaServiceImpl.listarPorTematica(anyLong())).thenReturn(preguntas);

        mockMvc.perform(get("/jugar/{tematicaId}", tematicaId).param("cantidad", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attributeExists("preguntas"))
                .andExpect(model().attribute("total", 1))
                .andExpect(model().attribute("preguntas", hasSize(1)));
    }

    @Test
    void jugar_withTematicaId_returnsAllQuestionsIfCantidadIsZeroOrNegative() throws Exception {
        Long tematicaId = 1L;
        Tematica tematica = crearTematica(tematicaId, "Java");
        PreguntaSeleccionUnica p1 = crearPreguntaSU(
                1L, "Que es Java?", Arrays.asList("Java", "C++", "Python"), 0, tematica
        );
        PreguntaSeleccionUnica p2 = crearPreguntaSU(
                2L, "Que es Spring?", Arrays.asList("Spring", "Hibernate", "Maven"), 0, tematica
        );
        List<Pregunta> preguntas = Arrays.asList(p1, p2);

        when(preguntaServiceImpl.listarTematicas()).thenReturn(Collections.singletonList(tematica));
        when(preguntaServiceImpl.listarPorTematica(anyLong())).thenReturn(preguntas);

        mockMvc.perform(get("/jugar/{tematicaId}", tematicaId).param("cantidad", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attribute("total", 2))
                .andExpect(model().attribute("preguntas", hasSize(2)));

        mockMvc.perform(get("/jugar/{tematicaId}", tematicaId).param("cantidad", "-5"))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attribute("total", 2))
                .andExpect(model().attribute("preguntas", hasSize(2)));
    }

    @Test
    void jugar_withTematicaId_returnsAllQuestionsIfCantidadIsGreaterThanAvailable() throws Exception {
        Long tematicaId = 1L;
        Tematica tematica = crearTematica(tematicaId, "Java");
        PreguntaSeleccionUnica pregunta = crearPreguntaSU(
                1L, "Que es Java?", Arrays.asList("Java", "C++", "Python"), 0, tematica
        );
        List<Pregunta> preguntas = Collections.singletonList(pregunta);

        when(preguntaServiceImpl.listarTematicas()).thenReturn(Collections.singletonList(tematica));
        when(preguntaServiceImpl.listarPorTematica(anyLong())).thenReturn(preguntas);

        mockMvc.perform(get("/jugar/{tematicaId}", tematicaId).param("cantidad", "100"))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attribute("total", 1))
                .andExpect(model().attribute("preguntas", hasSize(1)));
    }

    @Test
    void jugar_withNonExistentTematicaId_returnsJuegoViewWithNoQuestions() throws Exception {
        Long nonExistentTematicaId = 99L;

        when(preguntaServiceImpl.listarTematicas()).thenReturn(Collections.emptyList());
        when(preguntaServiceImpl.listarPorTematica(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/jugar/{tematicaId}", nonExistentTematicaId))
                .andExpect(status().isOk())
                .andExpect(view().name("juego"))
                .andExpect(model().attribute("preguntas", hasSize(0)))
                .andExpect(model().attribute("total", 0));
    }
}
