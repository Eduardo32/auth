package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.FuncionalidadeDTO;
import com.pauloeduardocosta.auth.dto.NovaFuncionalidadeDTO;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.repository.IFuncionalidadeRepository;
import com.pauloeduardocosta.auth.service.exception.FuncionalidadeJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
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
class FuncionalidadeServiceTest {

    @Mock
    private IFuncionalidadeRepository funcionalidadeRepository;

    @InjectMocks
    private FuncionalidadeService funcionalidadeService;

    private List<Funcionalidade> funcionalidades;

    @BeforeEach
    void setUp() {
        funcionalidades = new ArrayList<>();
        funcionalidades.addAll(Arrays.asList(
                Funcionalidade.builder()
                        .id(1L)
                        .nome("FUNCIONALIDADE_TESTE_A")
                        .build(),
                Funcionalidade.builder()
                        .id(2L)
                        .nome("FUNCIONALIDADE_TESTE_B")
                        .build(),
                Funcionalidade.builder()
                        .id(3L)
                        .nome("FUNCIONALIDADE_TESTE_C")
                        .build()
                ));
    }

    @Test
    @DisplayName("Dado que estou salvando uma nova funcionalidade")
    void criarFuncionalidadeTeste() {
        NovaFuncionalidadeDTO novaFuncionalidadeDTO = NovaFuncionalidadeDTO.builder()
                .nome("FUNCIONALIDADE_TESTE")
                .build();
        Mockito.when(funcionalidadeRepository.findByNome(novaFuncionalidadeDTO.getNome())).thenReturn(Optional.empty());
        FuncionalidadeDTO funcionalidadeDTO = funcionalidadeService.criarFuncionalidade(novaFuncionalidadeDTO);
        Mockito.verify(funcionalidadeRepository, Mockito.times(1)).save(Mockito.any(Funcionalidade.class));
        Assertions.assertNotNull(funcionalidadeDTO);
        Assertions.assertEquals(novaFuncionalidadeDTO.getNome(), funcionalidadeDTO.getNome());
    }

    @Test
    @DisplayName("Dado que estou tentando salvar uma funcionalidade com um nome que já existe")
    void criarFuncionalidadeJaExistenteTeste() {
        NovaFuncionalidadeDTO novaFuncionalidadeDTO = NovaFuncionalidadeDTO.builder()
                .nome("FUNCIONALIDADE_TESTE")
                .build();
        Mockito.when(funcionalidadeRepository.findByNome(novaFuncionalidadeDTO.getNome()))
                .thenReturn(mockFuncionalidade(novaFuncionalidadeDTO.getNome()));
        Assertions.assertThrows(FuncionalidadeJaExistenteException.class,
                () -> funcionalidadeService.criarFuncionalidade(novaFuncionalidadeDTO));
        Mockito.verify(funcionalidadeRepository, Mockito.times(0)).save(Mockito.any(Funcionalidade.class));
    }

    @Test
    @DisplayName("Dado que estou buscando todas as funcionalidades")
    void buscarTodosTeste() {
        Sort sort = Sort.by("nome").ascending();
        PageRequest pagina = PageRequest.of(0, 10, sort);
        Page paginaResposta = new PageImpl(funcionalidades);
        Mockito.when(funcionalidadeRepository.findAll(pagina)).thenReturn(paginaResposta);
        Page<FuncionalidadeDTO> funcionalidadeDTOS = funcionalidadeService.buscarTodos(pagina);
        Assertions.assertNotNull(funcionalidadeDTOS);
        Assertions.assertEquals(3, funcionalidadeDTOS.getTotalElements());
    }

    @Test
    @DisplayName("Dado que estou pesquisando uma funcionalidade por nome")
    void buscarPorNomeTeste() {
        String nome = "TESTE_B";
        Sort sort = Sort.by("nome").ascending();
        PageRequest pagina = PageRequest.of(0, 10, sort);
        Optional<Funcionalidade> funcionalidade = funcionalidades.stream()
                .filter(f -> f.getNome().contains(nome)).findFirst();
        Page paginaResposta = new PageImpl(List.of(funcionalidade.get()));
        Mockito.when(funcionalidadeRepository.findByNomeContainingIgnoreCase(nome, pagina))
                .thenReturn(paginaResposta);
        Page<FuncionalidadeDTO> funcionalidadeDTOS = funcionalidadeService.buscarPorNome(nome, pagina);
        Assertions.assertNotNull(funcionalidadeDTOS);
        Assertions.assertEquals(1, funcionalidadeDTOS.getTotalElements());
    }

    @Test
    @DisplayName("Dado que estou pesquisando uma funcionalidade por ID")
    void buscarPorIdTeste() {
        Long id = 2L;
        Optional<Funcionalidade> funcionalidade = funcionalidades.stream()
                .filter(f -> f.getId().equals(id)).findFirst();
        Mockito.when(funcionalidadeRepository.findById(id)).thenReturn(funcionalidade);
        FuncionalidadeDTO funcionalidadeDTOS = funcionalidadeService.buscarPorId(id);
        Assertions.assertNotNull(funcionalidadeDTOS);
        Assertions.assertEquals(id, funcionalidadeDTOS.getId());
        Assertions.assertEquals(funcionalidade.get().getNome(), funcionalidadeDTOS.getNome());
    }

    @Test
    @DisplayName("Dado que estou pesquisando uma funcionalidade por ID que não existe")
    void buscarPorIdInexistenteTeste() {
        Long id = 2L;
        Mockito.when(funcionalidadeRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> funcionalidadeService.buscarPorId(id));
    }

    @Test
    @DisplayName("Dado que estou tentando atualizar uma funcionalidade")
    void atualizarFuncionalidadeTeste() {
        Long id = 2L;
        NovaFuncionalidadeDTO novaFuncionalidadeDTO = NovaFuncionalidadeDTO.builder()
                .nome("FUNCIONALIDADE_TESTE_ATUALIZADO")
                .build();
        Optional<Funcionalidade> funcionalidade = funcionalidades.stream()
                .filter(f -> f.getId().equals(id)).findFirst();
        Mockito.when(funcionalidadeRepository.findById(id)).thenReturn(funcionalidade);
        FuncionalidadeDTO funcionalidadeDTO = funcionalidadeService.atualizarFuncionalidade(id, novaFuncionalidadeDTO);
        Assertions.assertNotNull(funcionalidadeDTO);
        Assertions.assertEquals(novaFuncionalidadeDTO.getNome(), funcionalidadeDTO.getNome());
    }

    @Test
    @DisplayName("Dado que estou tentando atualizar uma funcionalidade com um id inexistente")
    void atualizarFuncionalidadeIdInexistenteTeste() {
        Long id = 2L;
        NovaFuncionalidadeDTO novaFuncionalidadeDTO = NovaFuncionalidadeDTO.builder()
                .nome("FUNCIONALIDADE_TESTE_ATUALIZADO")
                .build();
        Mockito.when(funcionalidadeRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> funcionalidadeService.atualizarFuncionalidade(id, novaFuncionalidadeDTO));
    }

    @Test
    @DisplayName("Dado que estou tentando excluir uma funcionalidade")
    void excluirFuncionalidadeTeste() {
        Long id = 2L;
        Optional<Funcionalidade> funcionalidade = funcionalidades.stream()
                .filter(f -> f.getId().equals(id)).findFirst();
        Mockito.when(funcionalidadeRepository.findById(id)).thenReturn(funcionalidade);
        funcionalidadeService.excluirFuncionalidade(id);
        Mockito.verify(funcionalidadeRepository, Mockito.times(1))
                .delete(Mockito.any(Funcionalidade.class));
    }

    @Test
    @DisplayName("Dado que estou tentando excluir uma funcionalidade com um id inexistente")
    void excluirFuncionalidadeIdInexistenteTeste() {
        Long id = 2L;
        Mockito.when(funcionalidadeRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjetoNaoEncotradoException.class, () -> funcionalidadeService.excluirFuncionalidade(id));
    }

    @Test
    @DisplayName("Dado que estou buscando funcionalidades por uma lista de ids ")
    void buscarFuncionalidadesTest() {
        List<Long> ids = List.of(1L, 2L, 3L);
        Mockito.when(funcionalidadeRepository.findAllById(ids))
                .thenReturn(funcionalidades);
        List<Funcionalidade> funcionalidadesEncontradas = funcionalidadeService.buscarFuncionalidades(ids);
        Assertions.assertNotNull(funcionalidadesEncontradas);
        Assertions.assertEquals(funcionalidades.size(), funcionalidadesEncontradas.size());
    }

    private Optional<Funcionalidade> mockFuncionalidade(String nome) {
        return Optional.of(Funcionalidade.builder()
                        .id(1L)
                        .nome(nome)
                        .build());
    }
}