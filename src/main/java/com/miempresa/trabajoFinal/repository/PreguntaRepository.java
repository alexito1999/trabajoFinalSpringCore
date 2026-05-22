package com.miempresa.trabajoFinal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.miempresa.trabajoFinal.models.Pregunta;

import java.util.List;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long>{
    List<Pregunta> findByTematicaId(Long tematicaId);
    Page<Pregunta> findByTematicaId(Long tematicaId, Pageable pageable);

    @Query("SELECT p FROM Pregunta p WHERE " +
           "(:tematicaId IS NULL OR p.tematica.id = :tematicaId) AND " +
           "(:tipo IS NULL OR p.tipo = :tipo)")
    Page<Pregunta> filtrar(@Param("tematicaId") Long tematicaId,
                           @Param("tipo") String tipo,
                           Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM pregunta WHERE tipo = 'VF' AND correcto = true", nativeQuery = true)
    long countVFCorrectas();
}
