package br.insper.prova.repository;

import br.insper.prova.models.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends MongoRepository<Livro, String> {
    Livro findByTitulo(String titulo);
}
