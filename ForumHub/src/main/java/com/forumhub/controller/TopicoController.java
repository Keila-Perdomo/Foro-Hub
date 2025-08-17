package com.forumhub.controller;

import com.forumhub.domain.Topico;
import com.forumhub.dto.TopicoRequest;
import com.forumhub.dto.TopicoResponse;
import com.forumhub.dto.TopicoUpdateRequest;
import com.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoResponse> crear(@RequestBody @Valid TopicoRequest request) {
        Topico t = service.crear(request);
        TopicoResponse resp = toResponse(t);
        return ResponseEntity.created(URI.create("/topicos/" + t.getId())).body(resp);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio
    ) {
        Page<Topico> result = (curso != null || anio != null)
                ? service.buscar(curso, anio, page, size, asc)
                : service.listar(page, size, asc);
        Page<TopicoResponse> mapped = result.map(this::toResponse);
        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> detalle(@PathVariable Long id) {
        Optional<Topico> opt = service.detalle(id);
        return opt.map(t -> ResponseEntity.ok(toResponse(t)))
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> actualizar(@PathVariable Long id,
                                                     @RequestBody @Valid TopicoUpdateRequest request) {
        Optional<Topico> opt = service.actualizar(id, request);
        return opt.map(t -> ResponseEntity.ok(toResponse(t)))
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean ok = service.eliminar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private TopicoResponse toResponse(Topico t) {
        return new TopicoResponse(
                t.getId(), t.getTitulo(), t.getMensaje(), t.getFechaCreacion(), t.getStatus(), t.getAutor(), t.getCurso()
        );
    }
}
