package br.insper.prova.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import br.insper.prova.dto.ArtigoDTO;
import br.insper.prova.models.Artigo;
import br.insper.prova.service.ArtigoService;

@RestController
@RequestMapping("/api/artigos")
@RequiredArgsConstructor
public class ArtigoController {
    private final ArtigoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artigo create(@RequestBody ArtigoDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping
    public List<Artigo> list() {
        return service.list();
    }
}