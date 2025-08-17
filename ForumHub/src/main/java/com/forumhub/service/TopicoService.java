package com.forumhub.service;

import com.forumhub.domain.Topico;
import com.forumhub.dto.TopicoRequest;
import com.forumhub.dto.TopicoUpdateRequest;
import com.forumhub.repository.TopicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository repository;

    public TopicoService(TopicoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Topico crear(TopicoRequest req) {
        if (repository.existsByTituloAndMensaje(req.getTitulo(), req.getMensaje())) {
            throw new IllegalArgumentException("Tópico duplicado: ya existe un tópico con el mismo título y mensaje.");
        }
        Topico t = new Topico(req.getTitulo(), req.getMensaje(), req.getAutor(), req.getCurso());
        return repository.save(t);
    }

    public Page<Topico> listar(int page, int size, boolean asc) {
        Pageable pageable = PageRequest.of(page, size, asc ? org.springframework.data.domain.Sort.by("fechaCreacion").ascending()
                                                           : org.springframework.data.domain.Sort.by("fechaCreacion").descending());
        return repository.findAll(pageable);
    }

    public Page<Topico> buscar(String curso, Integer anio, int page, int size, boolean asc) {
        Pageable pageable = PageRequest.of(page, size, asc ? org.springframework.data.domain.Sort.by("fechaCreacion").ascending()
                                                           : org.springframework.data.domain.Sort.by("fechaCreacion").descending());
        LocalDateTime desde = null;
        LocalDateTime hasta = null;
        if (anio != null) {
            desde = LocalDate.of(anio, 1, 1).atStartOfDay();
            hasta = LocalDate.of(anio, 12, 31).atTime(LocalTime.MAX);
        }
        return repository.buscar(curso, desde, hasta, pageable);
    }

    public Optional<Topico> detalle(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Optional<Topico> actualizar(Long id, TopicoUpdateRequest req) {
        return repository.findById(id).map(t -> {
            t.setTitulo(req.getTitulo());
            t.setMensaje(req.getMensaje());
            t.setAutor(req.getAutor());
            t.setCurso(req.getCurso());
            t.setStatus(req.getStatus());
            return t;
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
