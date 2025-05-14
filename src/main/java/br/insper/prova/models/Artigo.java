package br.insper.prova.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "artigos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Artigo {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String prioridade;      // "BAIXA", "MÉDIA" ou "ALTA"
    private String emailCriador;    // recebido via payload
}