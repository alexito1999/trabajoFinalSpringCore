package com.miempresa.trabajoFinal.service;

import com.miempresa.trabajoFinal.models.Pregunta;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionMultiple;
import com.miempresa.trabajoFinal.models.PreguntaSeleccionUnica;
import com.miempresa.trabajoFinal.models.PreguntaVerdaderoFalso;
import com.miempresa.trabajoFinal.models.Tematica;
import com.miempresa.trabajoFinal.repository.TematicaRepository;
import com.miempresa.trabajoFinal.service.preguntas.PreguntaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@Transactional
class PreguntaServiceTest {

    @Autowired
    private PreguntaServiceImpl preguntaService;

    @Autowired
    private TematicaRepository tematicaRepository;

    private Tematica programacion;
    private Tematica basesDatos;

    @BeforeEach
    void setUp() {
        programacion = tematicaRepository.save(new Tematica("Programación"));
        basesDatos = tematicaRepository.save(new Tematica("Bases de Datos"));
    }

    @Test
    void contarPreguntas_sinDatos_retornaCero() {
        assertThat(preguntaService.contarPreguntas()).isZero();
    }

    @Test
    void contarTematicas_conDatos_retornaCantidad() {
        assertThat(preguntaService.contarTematicas()).isEqualTo(2);
    }

    @Test
    void guardarPregunta_vf_persisteCorrectamente() {
        PreguntaVerdaderoFalso p = new PreguntaVerdaderoFalso("Java es interpretado", true);
        p.setTematica(programacion);
        Pregunta guardada = preguntaService.guardarPregunta(p);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getEnunciado()).isEqualTo("Java es interpretado");
        assertThat(preguntaService.contarPreguntas()).isEqualTo(1);
    }

    @Test
    void guardarPregunta_su_persisteConOpciones() {
        PreguntaSeleccionUnica p = new PreguntaSeleccionUnica("¿Qué es Java?", List.of("Lenguaje", "BD", "OS"), 0);
        p.setTematica(programacion);
        Pregunta guardada = preguntaService.guardarPregunta(p);

        assertThat(guardada.getId()).isNotNull();
        assertThat(preguntaService.contarPreguntas()).isEqualTo(1);
    }

    @Test
    void guardarPregunta_sm_persisteConOpciones() {
        PreguntaSeleccionMultiple p = new PreguntaSeleccionMultiple(
                "¿Frameworks Java?", List.of("Spring", "Django", "Hibernate"), List.of(0, 2));
        p.setTematica(programacion);
        Pregunta guardada = preguntaService.guardarPregunta(p);

        assertThat(guardada.getId()).isNotNull();
        assertThat(preguntaService.contarPreguntas()).isEqualTo(1);
    }

    @Test
    void obtenerPregunta_existente_retornaPregunta() {
        PreguntaVerdaderoFalso p = new PreguntaVerdaderoFalso("Test", true);
        p.setTematica(programacion);
        Pregunta guardada = preguntaService.guardarPregunta(p);

        Pregunta encontrada = preguntaService.obtenerPregunta(guardada.getId());
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getEnunciado()).isEqualTo("Test");
    }

    @Test
    void obtenerPregunta_inexistente_retornaNull() {
        assertThat(preguntaService.obtenerPregunta(999L)).isNull();
    }

    @Test
    void eliminarPregunta_existente_laElimina() {
        PreguntaVerdaderoFalso p = new PreguntaVerdaderoFalso("Eliminar", true);
        p.setTematica(programacion);
        Pregunta guardada = preguntaService.guardarPregunta(p);

        preguntaService.eliminarPregunta(guardada.getId());
        assertThat(preguntaService.obtenerPregunta(guardada.getId())).isNull();
        assertThat(preguntaService.contarPreguntas()).isZero();
    }

    @Test
    void listarTematicas_retornaTodas() {
        assertThat(preguntaService.listarTematicas()).hasSize(2);
    }

    @Test
    void obtenerTematica_existente_retornaTematica() {
        Tematica encontrada = preguntaService.obtenerTematica(programacion.getId());
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getNombre()).isEqualTo("Programación");
    }

    @Test
    void obtenerTematica_inexistente_retornaNull() {
        assertThat(preguntaService.obtenerTematica(999L)).isNull();
    }

    @Test
    void mediaCorrectas_soloVfCalculaPorcentaje() {
        preguntaService.guardarPregunta(crearVF("Correcta", true));
        preguntaService.guardarPregunta(crearVF("Incorrecta", false));

        assertThat(preguntaService.mediaCorrectas()).isEqualTo(50.0);
    }

    @Test
    void mediaCorrectas_sinVf_retornaCero() {
        PreguntaSeleccionUnica su = new PreguntaSeleccionUnica("Pregunta SU", List.of("A", "B", "C"), 0);
        su.setTematica(programacion);
        preguntaService.guardarPregunta(su);

        assertThat(preguntaService.mediaCorrectas()).isZero();
    }

    @Test
    void mediaCorrectas_sinPreguntas_retornaCero() {
        assertThat(preguntaService.mediaCorrectas()).isEqualTo(0.0);
    }

    @Test
    void filtrarPreguntas_sinFiltros_retornaTodo() {
        preguntaService.guardarPregunta(crearVF("P1", true));
        preguntaService.guardarPregunta(crearVF("P2", false));

        Page<Pregunta> resultado = preguntaService.filtrarPreguntas(null, null, null, Pageable.ofSize(10));
        assertThat(resultado.getTotalElements()).isEqualTo(2);
    }

    @Test
    void filtrarPreguntas_porTexto_retornaCoincidencias() {
        preguntaService.guardarPregunta(crearVF("Java es genial", true));
        preguntaService.guardarPregunta(crearVF("Python es genial", true));

        Page<Pregunta> resultado = preguntaService.filtrarPreguntas(null, null, "Java", Pageable.ofSize(10));
        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getEnunciado()).contains("Java");
    }

    @Test
    void filtrarPreguntas_porTexto_ignoraMayusculas() {
        preguntaService.guardarPregunta(crearVF("java es genial", true));

        Page<Pregunta> resultado = preguntaService.filtrarPreguntas(null, null, "JAVA", Pageable.ofSize(10));
        assertThat(resultado.getTotalElements()).isEqualTo(1);
    }

    @Test
    void filtrarPreguntas_porTexto_retornaVacioSinCoincidencias() {
        preguntaService.guardarPregunta(crearVF("Java", true));

        Page<Pregunta> resultado = preguntaService.filtrarPreguntas(null, null, "PHP", Pageable.ofSize(10));
        assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    void filtrarPreguntas_porTipo_retornaFiltrados() {
        preguntaService.guardarPregunta(crearVF("VF 1", true));
        PreguntaSeleccionUnica su = new PreguntaSeleccionUnica("SU 1", List.of("A", "B"), 0);
        su.setTematica(programacion);
        preguntaService.guardarPregunta(su);

        Page<Pregunta> resultadoPorVF = preguntaService.filtrarPreguntas(null, "VF", null, Pageable.ofSize(10));
        assertThat(resultadoPorVF.getTotalElements()).isEqualTo(1);

        Page<Pregunta> resultadoPorSU = preguntaService.filtrarPreguntas(null, "SU", null, Pageable.ofSize(10));
        assertThat(resultadoPorSU.getTotalElements()).isEqualTo(1);
    }

    @Test
    void filtrarPreguntas_porTematica_retornaFiltrados() {
        PreguntaVerdaderoFalso p1 = crearVF("Preg Prog", true);
        p1.setTematica(programacion);
        preguntaService.guardarPregunta(p1);

        PreguntaVerdaderoFalso p2 = crearVF("Preg BD", true);
        p2.setTematica(basesDatos);
        preguntaService.guardarPregunta(p2);

        Page<Pregunta> resultado = preguntaService.filtrarPreguntas(programacion.getId(), null, null, Pageable.ofSize(10));
        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getEnunciado()).isEqualTo("Preg Prog");
    }

    @Test
    void filtrarPreguntas_textoEnBlanco_trataComoNull() {
        preguntaService.guardarPregunta(crearVF("Test", true));

        Page<Pregunta> resultado = preguntaService.filtrarPreguntas(null, null, "   ", Pageable.ofSize(10));
        assertThat(resultado.getTotalElements()).isEqualTo(1);
    }

    @Test
    void listarPorTematica_retornaCorrectas() {
        PreguntaVerdaderoFalso p1 = crearVF("P1", true);
        p1.setTematica(programacion);
        preguntaService.guardarPregunta(p1);
        PreguntaVerdaderoFalso p2 = crearVF("P2", false);
        p2.setTematica(programacion);
        preguntaService.guardarPregunta(p2);

        assertThat(preguntaService.listarPorTematica(programacion.getId())).hasSize(2);
        assertThat(preguntaService.listarPorTematica(basesDatos.getId())).isEmpty();
    }

    private PreguntaVerdaderoFalso crearVF(String enunciado, boolean correcto) {
        PreguntaVerdaderoFalso p = new PreguntaVerdaderoFalso(enunciado, correcto);
        p.setTematica(programacion);
        return p;
    }
}
