package br.insper.prova.dto;

public record ArtigoDTO(
        String titulo,
        String descricao,
        String prioridade,
        String emailCriador
) {}