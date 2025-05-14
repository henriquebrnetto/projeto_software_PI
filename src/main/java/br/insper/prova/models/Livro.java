package br.insper.prova.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    private String id;
    private String titulo;
    private String autor;
    private String genero;
    private Integer anoPublicacao;
    private String nomeLivro;
    private String emailLivro;

    @Override
    public boolean equals(Object obj) {
        return this.titulo.equals(((Livro) obj).getTitulo()) && this.autor.equals(((Livro) obj).getAutor());
    }
}
