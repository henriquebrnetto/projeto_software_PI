package br.insper.prova.repository;

import br.insper.prova.models.Artigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String> {
    Artigo findByTitulo(String titulo);
}
