package br.insper.prova.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import br.insper.prova.dto.ArtigoDTO;
import br.insper.prova.models.Artigo;
import br.insper.prova.service.ArtigoService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/artigos")
@RequiredArgsConstructor
public class ArtigoController {
    private final ArtigoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artigo create(@AuthenticationPrincipal Jwt jwt, @RequestBody ArtigoDTO dto) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        service.delete(id);
    }

    @GetMapping
    public List<Artigo> list() {
        return service.list();
    }
}