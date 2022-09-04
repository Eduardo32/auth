package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.AtualizarUsuarioDTO;
import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.dto.UsuarioCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioDTO;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.entity.Usuario;
import com.pauloeduardocosta.auth.repository.IUsuarioRepository;
import com.pauloeduardocosta.auth.service.exception.AtualizacaoDeSenhaException;
import com.pauloeduardocosta.auth.service.exception.LoginJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private PerfilService perfilService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Dado que estou criando um novo usuario")
    void criarUsuarioTeste() {
        NovoUsuarioDTO novoUsuarioDTO = NovoUsuarioDTO.builder()
                .login("teste")
                .senha("123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findByLogin(novoUsuarioDTO.getLogin()))
                .thenReturn(Optional.empty());
        Mockito.when(perfilService.buscarPerfis(novoUsuarioDTO.getPerfis()))
                .thenReturn(mockPerfis());
        UsuarioCompletoDTO usuarioCompletoDTO = usuarioService.criarUsuario(novoUsuarioDTO);

        Assertions.assertNotNull(usuarioCompletoDTO);
        Assertions.assertNotNull(usuarioCompletoDTO.getUuid());
        Assertions.assertEquals(novoUsuarioDTO.getLogin(), usuarioCompletoDTO.getLogin());
    }

    @Test
    @DisplayName("Dado que estou criando um novo usuario com um login repetido")
    void criarUsuarioComLoginJaExistenteTeste() {
        NovoUsuarioDTO novoUsuarioDTO = NovoUsuarioDTO.builder()
                .login("teste")
                .senha("123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findByLogin(novoUsuarioDTO.getLogin()))
                .thenReturn(Optional.of(new Usuario()));
        Assertions.assertThrows(LoginJaExistenteException.class, () -> usuarioService.criarUsuario(novoUsuarioDTO));
    }

    @Test
    @DisplayName("Dado que estou criando um novo usuario com um perfil que não existe")
    void criarUsuarioComPerfilNaoExistenteTeste() {
        NovoUsuarioDTO novoUsuarioDTO = NovoUsuarioDTO.builder()
                .login("teste")
                .senha("123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findByLogin(novoUsuarioDTO.getLogin()))
                .thenReturn(Optional.empty());
        Mockito.when(perfilService.buscarPerfis(novoUsuarioDTO.getPerfis()))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> usuarioService.criarUsuario(novoUsuarioDTO));
    }

    @Test
    @DisplayName("Dado que estou buscando todos os usuarios")
    void buscarTodosTeste() {
        Long id = 1L;
        PageImpl pagina = new PageImpl<>(List.of(mockUsuario(id)));
        Sort sort = Sort.by("Login").ascending();
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(usuarioRepository.findAll(pageRequest)).thenReturn(pagina);
        Page<UsuarioDTO> usuarioDTOS = usuarioService.buscarTodos(pageRequest);
        Assertions.assertNotNull(usuarioDTOS);
    }

    @Test
    @DisplayName("Dado que estou buscando usuarios por login")
    void buscarPorLoginTeste() {
        Long id = 1L;
        String login = "Teste";
        Page<Usuario> pagina = new PageImpl<>(List.of(mockUsuario(id)));
        Sort sort = Sort.by("Login").ascending();
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(usuarioRepository.findByLoginContainingIgnoreCase(login, pageRequest))
                .thenReturn(pagina);
        Page<UsuarioDTO> usuarioDTOS = usuarioService.buscarPorLogin(login, pageRequest);
        Assertions.assertNotNull(usuarioDTOS);
    }

    @Test
    @DisplayName("Dado que estou buscando um usuario por ID")
    void buscarPorIdTeste() {
        Long id = 1L;
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(mockUsuario(id)));
        UsuarioCompletoDTO usuarioCompletoDTO = usuarioService.buscarPorId(id);
        Assertions.assertNotNull(usuarioCompletoDTO);
        Assertions.assertEquals(id, usuarioCompletoDTO.getId());
    }

    @Test
    @DisplayName("Dado que estou buscando um usuario por ID que não existe")
    void buscarPorIdInexistenteTeste() {
        Long id = 1L;
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class ,() -> usuarioService.buscarPorId(id));
    }

    @Test
    @DisplayName("Dado que estou atualizando um usuario")
    void atualizarUsuarioTeste() {
        Long id = 1L;
        AtualizarUsuarioDTO atualizarUsuarioDTO = AtualizarUsuarioDTO.builder()
                .login("novoLogin")
                .senhaAtual("123456")
                .novaSenha("nova123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(mockUsuario(id)));
        Mockito.when(usuarioRepository.findByLogin(atualizarUsuarioDTO.getLogin())).thenReturn(Optional.empty());
        Mockito.when(perfilService.buscarPerfis(atualizarUsuarioDTO.getPerfis())).thenReturn(mockPerfis());
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        UsuarioCompletoDTO usuarioCompletoDTO = usuarioService.atualizarUsuario(id, atualizarUsuarioDTO);
        Assertions.assertNotNull(usuarioCompletoDTO);
        Assertions.assertEquals(atualizarUsuarioDTO.getLogin(), usuarioCompletoDTO.getLogin());
    }

    @Test
    @DisplayName("Dado que estou atualizando um usuario com id inexistente")
    void atualizarUsuarioIdInexistenteTeste() {
        Long id = 1L;
        AtualizarUsuarioDTO atualizarUsuarioDTO = AtualizarUsuarioDTO.builder()
                .login("novoLogin")
                .senhaAtual("123456")
                .novaSenha("nova123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> usuarioService.atualizarUsuario(id, atualizarUsuarioDTO));
    }

    @Test
    @DisplayName("Dado que estou atualizando um usuario sem passa a senha atual")
    void atualizarUsuarioSemEnviarSenhaAtualTeste() {
        Long id = 1L;
        AtualizarUsuarioDTO atualizarUsuarioDTO = AtualizarUsuarioDTO.builder()
                .login("novoLogin")
                .novaSenha("nova123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(mockUsuario(id)));
        Mockito.when(usuarioRepository.findByLogin(atualizarUsuarioDTO.getLogin())).thenReturn(Optional.empty());
        Mockito.when(perfilService.buscarPerfis(atualizarUsuarioDTO.getPerfis())).thenReturn(mockPerfis());
        Assertions.assertThrows(AtualizacaoDeSenhaException.class, () -> usuarioService.atualizarUsuario(id, atualizarUsuarioDTO));
    }

    @Test
    @DisplayName("Dado que estou atualizando um usuario enviando uma senha que não bate")
    void atualizarUsuarioComSenhaQueNaoBateTeste() {
        Long id = 1L;
        AtualizarUsuarioDTO atualizarUsuarioDTO = AtualizarUsuarioDTO.builder()
                .login("novoLogin")
                .senhaAtual("123456")
                .novaSenha("nova123456")
                .perfis(List.of(1L))
                .build();
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(mockUsuario(id)));
        Mockito.when(usuarioRepository.findByLogin(atualizarUsuarioDTO.getLogin())).thenReturn(Optional.empty());
        Mockito.when(perfilService.buscarPerfis(atualizarUsuarioDTO.getPerfis())).thenReturn(mockPerfis());
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        Assertions.assertThrows(AtualizacaoDeSenhaException.class, () -> usuarioService.atualizarUsuario(id, atualizarUsuarioDTO));
    }

    @Test
    @DisplayName("Dado que estou excluindo um usuario")
    void excluirPerfilTeste() {
        Long id = 1L;
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(mockUsuario(id)));
        usuarioService.excluirPerfil(id);
        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(Mockito.any(Usuario.class));
    }

    @Test
    @DisplayName("Dado que estou excluindo um usuario com um id inexistente")
    void excluirPerfilIdInexistenteTeste() {
        Long id = 1L;
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> usuarioService.excluirPerfil(id));
    }

    private Usuario mockUsuario(Long id) {
        return Usuario.builder()
                .id(id)
                .login("usuario")
                .uuid(UUID.randomUUID().toString())
                .senha("123456")
                .perfis(mockPerfis())
                .build();
    }

    private List<Perfil> mockPerfis() {
        return List.of(Perfil.builder()
                .id(1L)
                .nome("Teste")
                .funcionalidades(List.of(Funcionalidade.builder()
                                        .id(1L)
                                        .nome("FUNCIOALIDADE_TESTE")
                                        .build()
                        )
                )
                .build()
        );
    }
}