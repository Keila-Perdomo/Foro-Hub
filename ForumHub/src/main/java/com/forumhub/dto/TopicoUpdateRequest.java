package com.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public class TopicoUpdateRequest {
    @NotBlank
    private String titulo;
    @NotBlank
    private String mensaje;
    @NotBlank
    private String autor;
    @NotBlank
    private String curso;
    @NotBlank
    private String status;

    public TopicoUpdateRequest() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
