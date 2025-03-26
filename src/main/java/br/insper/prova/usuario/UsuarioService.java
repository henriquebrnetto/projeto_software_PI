package br.insper.prova.usuario;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {

    private RestTemplate restTemplate;

    public Usuario findUsuarioByEmail(String email) {
        String url = "http://56.124.127.89:8080/api/usuario/" + email;
        return restTemplate.getForObject(url, Usuario.class);
    }
}