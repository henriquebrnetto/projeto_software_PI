package br.insper.prova.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import br.insper.prova.repository.ArtigoRepository;
import br.insper.prova.models.Artigo;
import br.insper.prova.dto.ArtigoDTO;

@Service
@RequiredArgsConstructor
public class ArtigoService {
    private final ArtigoRepository repository;

    public Artigo create(ArtigoDTO dto) {
        Artigo artigo = new Artigo();
        artigo.setTitulo(dto.titulo());
        artigo.setDescricao(dto.descricao());
        artigo.setPrioridade(dto.prioridade());
        artigo.setEmailCriador(dto.emailCriador());
        return repository.save(artigo);
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artigo não encontrado");
        }
        repository.deleteById(id);
    }

    public List<Artigo> list() {
        return repository.findAll();
    }
}