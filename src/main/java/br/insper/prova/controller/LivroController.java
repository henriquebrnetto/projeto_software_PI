package br.insper.prova.controller;

import br.insper.prova.service.LivroService;
import br.insper.prova.models.Livro;
import br.insper.prova.models.Usuario;
import br.insper.prova.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Livro> getLivros() {
        return livroService.getLivros();
    }

    @PostMapping
    public Livro saveLivro(@RequestBody Livro livro, @RequestHeader(name = "email") String email) {
        Usuario usuario = usuarioService.findUsuarioByEmail(email);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!usuario.getPapel().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return livroService.saveLivro(livro);
    }

    @DeleteMapping("/{email}")
    public void deleteLivro(@PathVariable String email, @RequestHeader(name = "email") String userEmail) {
        Usuario usuario = usuarioService.findUsuarioByEmail(userEmail);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!usuario.getPapel().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        livroService.deleteLivro(email);
    }
}
