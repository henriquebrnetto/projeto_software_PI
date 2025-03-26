package br.insper.prova.service;

import br.insper.prova.livro.CountLivroDTO;
import br.insper.prova.livro.Livro;
import br.insper.prova.livro.LivroRepository;
import br.insper.prova.livro.LivroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTests {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Test
    void test_findAllLivrosWhenLivrosIsEmpty() {

        // preparacao
        Mockito.when(livroRepository.findAll()).thenReturn(new ArrayList<>());

        // chamada
        List<Livro> livros = livroService.getLivros();

        //verificacaoes
        Assertions.assertEquals(0, livros.size());
    }

    @Test
    void test_saveLivroSuccessfully() {

        Livro livro = new Livro();
        livro.setEmail("a@a.com");
        livro.setNome("Teste");

        Mockito.when(livroRepository.save(livro)).thenReturn(livro);

        Livro livroReturn = livroService.saveLivro(livro);

        Assertions.assertEquals("a@a.com", livroReturn.getEmail());
        Assertions.assertEquals("Teste", livroReturn.getNome());

    }

    @Test
    void test_saveLivroErrorEmailIsNull() {

        Livro livro = new Livro();
        livro.setNome("a@a.com");

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.saveLivro(livro));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

    }

    @Test
    void test_saveLivroErrorNomeIsNull() {

        Livro livro = new Livro();
        livro.setEmail("Teste");

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.saveLivro(livro));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

    }

    @Test
    void test_deleteSuccessfully() {

        Livro livro = new Livro();
        livro.setNome("Teste");
        livro.setEmail("aa@aa.com");

        Mockito.when(livroRepository.findByTitulo("aa@aa.com")).thenReturn(livro);
        Mockito.doNothing().when(livroRepository).delete(livro);

        livroService.deleteLivro("aa@aa.com");

        Mockito.verify(livroRepository, Mockito.times(1))
                .delete(livro);

    }

    @Test
    void test_findLivroByEmailSuccessfully() {

        Livro livro = new Livro();
        livro.setNome("Teste");
        livro.setEmail("aa@aa.com");

        Mockito.when(livroRepository.findByTitulo("aa@aa.com")).thenReturn(livro);

        Livro livroResp = livroService.findLivroByEmail("aa@aa.com");

        Assertions.assertEquals(livro.getEmail(), livroResp.getEmail());
        Assertions.assertEquals(livro.getNome(), livroResp.getNome());

    }

    @Test
    void test_findLivroByEmailWhenEmailIsInvalid() {

        Mockito.when(livroRepository.findByTitulo("aa@aa.com")).thenReturn(null);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> livroService.findLivroByEmail("aa@aa.com"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void test_countLivroSuccessfully() {

        Mockito.when(livroRepository.count()).thenReturn(10l);

        CountLivroDTO countLivroDTO = livroService.countLivros();
        Assertions.assertEquals(10l, countLivroDTO.count());
    }


}
