package com.pauloeduardocosta.auth.security.service.impl;

import com.pauloeduardocosta.auth.config.security.service.impl.TokenServise;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.config.security.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TokenServiseTest {

    @InjectMocks
    private TokenServise tokenServise;

    private Funcionalidade funcionalidade;
    private Perfil perfil;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenServise,"expiration", 60000L);
        ReflectionTestUtils.setField(tokenServise,"secret", "teste");
        funcionalidade = Funcionalidade.builder()
                .id(1L)
                .nome("ADMIN")
                .build();

        perfil = Perfil.builder()
                .id(1l)
                .funcionalidades(Arrays.asList(funcionalidade))
                .build();

        usuario = Usuario.builder()
                .id(1L)
                .login("usuario")
                .uuid(UUID.randomUUID().toString())
                .senha("123456")
                .perfis(Arrays.asList(perfil))
                .build();
    }

    @Test
    @DisplayName("Dado que estou gerando um token valido")
    void gerandoTokenComSucessoTeste() {
        Authentication authenticate =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        String token = tokenServise.gerarToken(authenticate);
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Dado que estou validando o token gerado")
    void validandoTokenComSucessoTeste() {
        Authentication authenticate =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        String token = tokenServise.gerarToken(authenticate);
        Boolean tokenValido = tokenServise.isTokenValido(token);
        Assertions.assertTrue(tokenValido);
    }

    @Test
    @DisplayName("Dado que tenho um token valido estou recuperando o ID do usuario")
    void recuperandoIdDoUsuarioComSucessoTeste() {
        Authentication authenticate =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        String token = tokenServise.gerarToken(authenticate);
        Long id = tokenServise.getIdUsuario(token);
        Assertions.assertEquals(usuario.getId(), id);
    }

    @Test
    @DisplayName("Dado que tenho um token valido estou recuperando o UUID do usuario")
    void recuperandoUUIDDoUsuarioComSucessoTeste() {
        Authentication authenticate =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        String token = tokenServise.gerarToken(authenticate);
        String uuid = tokenServise.getUUIDUsuario(token);
        Assertions.assertEquals(usuario.getUuid(), uuid);
    }
}