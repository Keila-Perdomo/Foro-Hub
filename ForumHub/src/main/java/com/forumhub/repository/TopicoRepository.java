package com.forumhub.repository;

import com.forumhub.domain.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findAllByOrderByFechaCreacionAsc(Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE (:curso IS NULL OR LOWER(t.curso) LIKE LOWER(CONCAT('%', :curso, '%'))) " +
           "AND (:desde IS NULL OR t.fechaCreacion >= :desde) " +
           "AND (:hasta IS NULL OR t.fechaCreacion <= :hasta)")
    Page<Topico> buscar(String curso, LocalDateTime desde, LocalDateTime hasta, Pageable pageable);
}
