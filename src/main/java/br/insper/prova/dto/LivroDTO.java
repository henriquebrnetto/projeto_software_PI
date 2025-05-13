package br.insper.prova.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LivroDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    // adicione aqui todos os campos que a API de cursos retorna
}