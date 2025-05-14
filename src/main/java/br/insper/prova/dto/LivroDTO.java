package br.insper.prova.dto;

public record LivroDTO(
        String id,
        String titulo,
        String autor,
        String genero,
        Integer anoPublicacao,
        String nomeLivro,
        String emailLivro) {

}
