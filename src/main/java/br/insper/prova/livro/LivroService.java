package br.insper.prova.livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> getLivros() {
        return livroRepository.findAll();
    }

    public Livro saveLivro(Livro livro) {
        if (livro.getTitulo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (livro.getAutor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (livro.getAnoPublicacao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return livroRepository.save(livro);
    }

    public Livro findLivroByTitulo(String titulo) {
        Livro livro = livroRepository.findByTitulo(titulo);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return livro;
    }

    public void deleteLivro(String titulo) {
        Livro livro = findLivroByTitulo(titulo);
        livroRepository.delete(livro);
    }
}
