package br.insper.prova.livro;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Livro {

    @Id
    private String id;
    private String titulo;
    private String autor;
    private String genero;
    private Integer anoPublicacao;
    private String nomeLivro;
    private String emailLivro;

    public Livro(String titulo, String autor, String genero, Integer anoPublicacao,
                 String nomeLivro, String emailLivro) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.nomeLivro = nomeLivro;
        this.emailLivro = emailLivro;
    }


    public Livro() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getEmailLivro() {
        return emailLivro;
    }

    public void setEmailLivro(String emailLivro) {
        this.emailLivro = emailLivro;
    }

    @Override
    public boolean equals(Object obj) {
        return this.titulo.equals(((Livro) obj).getTitulo()) && this.autor.equals(((Livro) obj).getAutor());
    }
}
