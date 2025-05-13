package br.insper.prova.livros.service;

import br.insper.prova.models.Livro;
import br.insper.prova.repository.LivroRepository;
import br.insper.prova.service.LivroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTests {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Test
    void test_saveLivroSuccessfully() {
        Livro livro = new Livro();
        livro.setTitulo("O Senhor dos Anéis");
        livro.setAutor("J.R.R. Tolkien");
        livro.setAnoPublicacao(1954);

        Mockito.when(livroRepository.save(livro)).thenReturn(livro);
        Livro livroReturn = livroService.saveLivro(livro);

        Assertions.assertEquals("O Senhor dos Anéis", livroReturn.getTitulo());
        Assertions.assertEquals("J.R.R. Tolkien", livroReturn.getAutor());
        Assertions.assertEquals(1954, livroReturn.getAnoPublicacao());
    }

    @Test
    void test_saveLivroErrorTituloIsNull() {
        Livro livro = new Livro();
        livro.setAutor("J.R.R. Tolkien");
        livro.setAnoPublicacao(1954);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.saveLivro(livro));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void test_saveLivroErrorAutorIsNull() {
        Livro livro = new Livro();
        livro.setTitulo("O Senhor dos Anéis");
        livro.setAnoPublicacao(1954);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.saveLivro(livro));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void test_saveLivroErrorAnoPublicacaoIsNull() {
        Livro livro = new Livro();
        livro.setTitulo("O Senhor dos Anéis");
        livro.setAutor("J.R.R. Tolkien");

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.saveLivro(livro));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void test_deleteSuccessfull() {
        Livro livro = new Livro();
        livro.setTitulo("O Senhor dos Anéis");
        livro.setAutor("J.R.R. Tolkien");
        livro.setAnoPublicacao(1954);

        Mockito.when(livroRepository.findByTitulo("O Senhor dos Anéis")).thenReturn(livro);
        Mockito.doNothing().when(livroRepository).delete(livro);

        livroService.deleteLivro("O Senhor dos Anéis");
        Mockito.verify(livroRepository, Mockito.times(1)).delete(livro);
    }

    @Test
    void test_findLivroByTituloSuccessfully() {
        Livro livro = new Livro();
        livro.setTitulo("O Senhor dos Anéis");
        livro.setAutor("J.R.R. Tolkien");
        livro.setAnoPublicacao(1954);

        Mockito.when(livroRepository.findByTitulo("O Senhor dos Anéis")).thenReturn(livro);
        Livro livroResp = livroService.findLivroByTitulo("O Senhor dos Anéis");

        Assertions.assertEquals(livro.getTitulo(), livroResp.getTitulo());
        Assertions.assertEquals(livro.getAutor(), livroResp.getAutor());
        Assertions.assertEquals(livro.getAnoPublicacao(), livroResp.getAnoPublicacao());
    }

    @Test
    void test_findLivroByTituloInvalid() {
        Mockito.when(livroRepository.findByTitulo("Livro Inexistente")).thenReturn(null);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.findLivroByTitulo("Livro Inexistente"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}