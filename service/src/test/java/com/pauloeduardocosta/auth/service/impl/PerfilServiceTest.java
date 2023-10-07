package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.AtualizarPerfilDTO;
import com.pauloeduardocosta.auth.dto.NovoPerfilDTO;
import com.pauloeduardocosta.auth.dto.PerfilCompletoDTO;
import com.pauloeduardocosta.auth.dto.PerfilDTO;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.repository.IPerfilRepository;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.auth.service.exception.PerfilJaExistenteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PerfilServiceTest {

    @Mock
    private IPerfilRepository perfilRepository;

    @Mock
    private FuncionalidadeService funcionalidadeService;

    @InjectMocks
    private PerfilService perfilService;

    private List<Perfil> perfis;
    private Funcionalidade funcionalidade;

    @BeforeEach
    void setUp() {
        funcionalidade = Funcionalidade.builder()
                .id(1L)
                .nome("FUNCIONALIDADE_TESTE")
                .build();
        perfis = new ArrayList<>();
        perfis.addAll(Arrays.asList(
                Perfil.builder()
                        .id(1L)
                        .nome("PERFIL_TESTE_A")
                        .funcionalidades(List.of(funcionalidade))
                        .build(),
                Perfil.builder()
                        .id(2L)
                        .nome("PERFIL_TESTE_B")
                        .funcionalidades(new ArrayList<>())
                        .build(),
                Perfil.builder()
                        .id(3L)
                        .nome("PERFIL_TESTE_C")
                        .funcionalidades(new ArrayList<>())
                        .build()
        ));
    }

    @Test
    @DisplayName("Dado que estou buscando perfis pelo id")
    void buscarPerfisTeste() {
        List<Long> pirfilIds = List.of(1L);

        Mockito.when(perfilRepository.findAllById(pirfilIds))
                .thenReturn(mockPerfis());
        List<Perfil> perfils = perfilService.buscarPerfis(pirfilIds);

        Assertions.assertNotNull(perfils);
        Assertions.assertEquals(pirfilIds.size(), perfils.size());
    }

    @Test
    @DisplayName("Dado que estou buscando todas os perfis")
    void buscarTodosTeste() {
        Sort sort = Sort.by("nome").ascending();
        PageRequest pagina = PageRequest.of(0, 10, sort);
        Page paginaResposta = new PageImpl(perfis);
        Mockito.when(perfilRepository.findAll(pagina)).thenReturn(paginaResposta);
        Page<PerfilDTO> perfilDTOS = perfilService.buscarTodos(pagina);
        Assertions.assertNotNull(perfilDTOS);
        Assertions.assertEquals(3, perfilDTOS.getTotalElements());
    }

    @Test
    @DisplayName("Dado que estou salvando um novo Perfil")
    void criarPerfilTeste() {
        NovoPerfilDTO novoPerfil = NovoPerfilDTO.builder()
                .nome("PERFIL_TESTE")
                .funcionalidades(new ArrayList<>())
                .build();
        Mockito.when(perfilRepository.findByNome(novoPerfil.getNome())).thenReturn(Optional.empty());
        Mockito.when(funcionalidadeService.buscarFuncionalidades(novoPerfil.getFuncionalidades()))
                .thenReturn(new ArrayList<>());
        PerfilCompletoDTO perfilCompletoDTO = perfilService.criarPerfil(novoPerfil);
        Mockito.verify(perfilRepository, Mockito.times(1)).save(Mockito.any(Perfil.class));
        Assertions.assertNotNull(perfilCompletoDTO);
        Assertions.assertEquals(novoPerfil.getNome(), perfilCompletoDTO.getNome());
    }

    @Test
    @DisplayName("Dado que estou salvando um novo Perfil com uma funcionalidade inexistente")
    void criarPerfilFuncionalidadeInexistenteTeste() {
        NovoPerfilDTO novoPerfil = NovoPerfilDTO.builder()
                .nome("PERFIL_TESTE")
                .funcionalidades(List.of(99L))
                .build();
        Mockito.when(perfilRepository.findByNome(novoPerfil.getNome())).thenReturn(Optional.empty());
        Mockito.when(funcionalidadeService.buscarFuncionalidades(novoPerfil.getFuncionalidades()))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> perfilService.criarPerfil(novoPerfil));
    }

    @Test
    @DisplayName("Dado que estou tentando salvar um perfil com um nome que já existe")
    void criarPerfilJaExistenteTeste() {
        NovoPerfilDTO novoPerfil = NovoPerfilDTO.builder()
                .nome("PERFIL_TESTE")
                .build();
        Mockito.when(perfilRepository.findByNome(novoPerfil.getNome()))
                .thenReturn(mockPerfil(novoPerfil.getNome()));
        Assertions.assertThrows(PerfilJaExistenteException.class,
                () -> perfilService.criarPerfil(novoPerfil));
        Mockito.verify(perfilRepository, Mockito.times(0)).save(Mockito.any(Perfil.class));
    }

    @Test
    @DisplayName("Dado que estou pesquisando um perfil por nome")
    void buscarPorNomeTeste() {
        String nome = "TESTE_B";
        Sort sort = Sort.by("nome").ascending();
        PageRequest pagina = PageRequest.of(0, 10, sort);
        Optional<Perfil> funcionalidade = perfis.stream()
                .filter(f -> f.getNome().contains(nome)).findFirst();
        Page paginaResposta = new PageImpl(List.of(funcionalidade.get()));
        Mockito.when(perfilRepository.findByNomeContainingIgnoreCase(nome, pagina))
                .thenReturn(paginaResposta);
        Page<PerfilDTO> funcionalidadeDTOS = perfilService.buscarPorNome(nome, pagina);
        Assertions.assertNotNull(funcionalidadeDTOS);
        Assertions.assertEquals(1, funcionalidadeDTOS.getTotalElements());
    }

    @Test
    @DisplayName("Dado que estou pesquisando um perfil por ID")
    void buscarPorIdTeste() {
        Long id = 2L;
        Optional<Perfil> perfil = perfis.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        Mockito.when(perfilRepository.findById(id)).thenReturn(perfil);
        PerfilCompletoDTO perfilCompletoDTO = perfilService.buscarPorId(id);
        Assertions.assertNotNull(perfilCompletoDTO);
        Assertions.assertEquals(id, perfilCompletoDTO.getId());
        Assertions.assertEquals(perfil.get().getNome(), perfilCompletoDTO.getNome());
    }

    @Test
    @DisplayName("Dado que estou pesquisando um perfil por ID que não existe")
    void buscarPorIdInexistenteTeste() {
        Long id = 2L;
        Mockito.when(perfilRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> perfilService.buscarPorId(id));
    }

    @Test
    @DisplayName("Dado que estou tentando atualizar um perfil")
    void atualizarPerfilTeste() {
        Long id = 2L;
        List<Long> funcionalidadeId = List.of(1L);
        AtualizarPerfilDTO novoPerfil = AtualizarPerfilDTO.builder()
                .nome("PERFIL_TESTE_ATUALIZADO")
                .funcionalidades(funcionalidadeId)
                .build();
        Optional<Perfil> perfil = perfis.stream()
                .filter(f -> f.getId().equals(id)).findFirst();
        Mockito.when(perfilRepository.findById(id)).thenReturn(perfil);
        Mockito.when(funcionalidadeService.buscarFuncionalidades(novoPerfil.getFuncionalidades()))
                .thenReturn(List.of(funcionalidade));
        PerfilCompletoDTO perfilCompletoDTO = perfilService.atualizarPerfil(id, novoPerfil);
        Assertions.assertNotNull(perfilCompletoDTO);
        Assertions.assertEquals(novoPerfil.getNome(), perfilCompletoDTO.getNome());
    }

    @Test
    @DisplayName("Dado que estou tentando atualizar um perfil com um id inexistente")
    void atualizarPerfilIdInexistenteTeste() {
        Long id = 2L;
        AtualizarPerfilDTO novoPerfil = AtualizarPerfilDTO.builder()
                .nome("PERFIL_TESTE_ATUALIZADO")
                .build();
        Mockito.when(perfilRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> perfilService.atualizarPerfil(id, novoPerfil));
    }

    @Test
    @DisplayName("Dado que estou tentando excluir um perfil")
    void excluirPerfilTeste() {
        Long id = 2L;
        Optional<Perfil> funcionalidade = perfis.stream()
                .filter(f -> f.getId().equals(id)).findFirst();
        Mockito.when(perfilRepository.findById(id)).thenReturn(funcionalidade);
        perfilService.excluirPerfil(id);
        Mockito.verify(perfilRepository, Mockito.times(1))
                .delete(Mockito.any(Perfil.class));
    }

    @Test
    @DisplayName("Dado que estou tentando excluir um perfil com um id inexistente")
    void excluirPerfilIdInexistenteTeste() {
        Long id = 2L;
        Mockito.when(perfilRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> perfilService.excluirPerfil(id));
    }

    private Optional<Perfil> mockPerfil(String nome) {
        return Optional.of(Perfil.builder()
                .id(1L)
                .nome(nome)
                .build());
    }

    private List<Perfil> mockPerfis() {
        return List.of(
                Perfil.builder()
                        .id(1L)
                        .nome("Teste")
                        .build()
        );
    }
}