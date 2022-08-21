package com.pauloeduardocosta.auth.security.service.impl;

import com.pauloeduardocosta.auth.entitie.Usuario;
import com.pauloeduardocosta.auth.repository.IUsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserDetailsServiceImplTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("Dado que estou carregando um usuario pelo login")
    void carregandoUsuarioProLoginComSucessoTeste() {
        String login = "usuarioTeste";
        when(usuarioRepository.findByLogin(login)).thenReturn(mockUsuario(login));
        UserDetails usuario = userDetailsService.loadUserByUsername(login);

        Assertions.assertNotNull(usuario);
    }

    @Test
    @DisplayName("Dado que estou tentando carregando um usuario que não existe")
    void carregandoUsuarioInexistenteTeste() {
        String login = "usuarioTeste";
        when(usuarioRepository.findByLogin(login)).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(login));
    }

    private Optional<Usuario> mockUsuario(String login) {
        return Optional.of(Usuario.builder()
                .id(1L)
                .uuid(UUID.randomUUID().toString())
                .login(login).build());
    }

}