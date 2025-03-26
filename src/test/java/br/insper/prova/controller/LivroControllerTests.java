package br.insper.prova.controller;

import br.insper.prova.livro.Livro;
import br.insper.prova.livro.LivroController;
import br.insper.prova.livro.LivroService;
import br.insper.prova.usuario.Usuario;
import br.insper.prova.usuario.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LivroControllerTests {

    @InjectMocks
    private LivroController livroController;

    @Mock
    private LivroService livroService;

    @Mock
    private UsuarioService usuarioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(livroController).build();
    }

    @Test
    void test_GetLivros() throws Exception {
        List<Livro> livros = Arrays.asList(
                new Livro("Título1", "Autor1", "Gênero1",
                        2001, "Nome1", "email1@example.com"),
                new Livro("Título2", "Autor2", "Gênero2",
                        2002, "Nome2", "email2@example.com")
        );

        when(livroService.getLivros()).thenReturn(livros);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/livro"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(livros)));
    }

    @Test
    void test_PostLivroSucesso() throws Exception {
        Livro livro = new Livro("Título", "Autor", "Gênero",
                2021, "Nome", "teste@teste.com");
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setPapel("ADMIN");

        when(usuarioService.findUsuarioByEmail("teste@teste.com")).thenReturn(usuario);
        when(livroService.saveLivro(any(Livro.class))).thenReturn(livro);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("email", "teste@teste.com")
                        .content(new ObjectMapper().writeValueAsString(livro)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(livro)));
    }

    @Test
    void test_PostLivroUsuarioNaoAutorizado() throws Exception {
        Livro livro = new Livro("Título", "Autor", "Gênero",
                2021, "Nome", "teste@teste.com");
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setPapel("USER");

        when(usuarioService.findUsuarioByEmail("teste@teste.com")).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("email", "teste@teste.com")
                        .content(new ObjectMapper().writeValueAsString(livro)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void test_PostLivroUsuarioNaoEncontrado() throws Exception {
        Livro livro = new Livro("Título", "Autor", "Gênero",
                2021, "Nome", "teste@teste.com");

        when(usuarioService.findUsuarioByEmail("teste@teste.com")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("email", "teste@teste.com")
                        .content(new ObjectMapper().writeValueAsString(livro)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_DeleteLivroSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("user@teste.com");
        usuario.setPapel("ADMIN");

        when(usuarioService.findUsuarioByEmail("user@teste.com")).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/livro/teste@teste.com")
                        .header("email", "user@teste.com"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_DeleteLivroUsuarioNaoEncontrado() throws Exception {
        when(usuarioService.findUsuarioByEmail("user@teste.com")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/livro/teste@teste.com")
                        .header("email", "user@teste.com"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_DeleteLivroUsuarioNaoAutorizado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("admin@teste.com");
        usuario.setPapel("USER");

        when(usuarioService.findUsuarioByEmail("admin@teste.com")).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/livro/teste@teste.com")
                        .header("email", "admin@teste.com"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
