package br.insper.prova.service;

import br.insper.prova.models.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTests {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void test_findUsuarioByEmailSuccessfully() {
        String email = "teste@teste.com";
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail(email);
        usuarioMock.setNome("Teste");

        String url = "http://56.124.127.89:8080/api/usuario/" + email;
        Mockito.when(restTemplate.getForObject(url, Usuario.class)).thenReturn(usuarioMock);

        Usuario usuarioResp = usuarioService.findUsuarioByEmail(email);

        Assertions.assertNotNull(usuarioResp);
        Assertions.assertEquals(email, usuarioResp.getEmail());
        Assertions.assertEquals("Teste", usuarioResp.getNome());
    }

    @Test
    void test_findUsuarioByEmailNotFound() {
        String email = "naoexiste@teste.com";
        String url = "http://56.124.127.89:8080/api/usuario/" + email;

        Mockito.when(restTemplate.getForObject(url, Usuario.class)).thenReturn(null);

        Usuario usuarioResp = usuarioService.findUsuarioByEmail(email);

        Assertions.assertNull(usuarioResp);
    }
}
