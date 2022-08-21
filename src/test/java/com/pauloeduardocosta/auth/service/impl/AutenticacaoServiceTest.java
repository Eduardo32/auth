package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.LoginDTO;
import com.pauloeduardocosta.auth.dto.TokenDTO;
import com.pauloeduardocosta.auth.entitie.Funcionalidade;
import com.pauloeduardocosta.auth.entitie.Perfil;
import com.pauloeduardocosta.auth.entitie.Usuario;
import com.pauloeduardocosta.auth.enums.ETipoToken;
import com.pauloeduardocosta.auth.security.service.impl.TokenServise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AutenticacaoServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServise tokenService;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    private Funcionalidade funcionalidade;
    private Perfil perfil;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
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
                .senha("123456")
                .perfis(Arrays.asList(perfil))
                .build();
    }

    @Test
    @DisplayName("Dado que estou me logando com um usuario válido")
    void autenticacaoComSucessoTeste() {
        LoginDTO loginDTO = new LoginDTO(usuario.getLogin(), usuario.getSenha());
        Authentication authenticate = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        String token = "token123456";
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticate);
        when(tokenService.gerarToken(authenticate)).thenReturn(token);
        TokenDTO tokenDTO = autenticacaoService.autenticar(loginDTO);

        assertNotNull(tokenDTO);
        assertEquals(token, tokenDTO.getToken());
        assertEquals(ETipoToken.BEARER.getDescricao(), tokenDTO.getTipo());
    }
}