package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.repository.IPerfilRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PerfilServiceTest {

    @Mock
    private IPerfilRepository perfilRepository;

    @InjectMocks
    private PerfilService perfilService;

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

    private List<Perfil> mockPerfis() {
        return List.of(
                Perfil.builder()
                        .id(1L)
                        .nome("Teste")
                        .funcionalidades(List.of(
                                Funcionalidade.builder()
                                        .id(1L)
                                        .nome("FUNCIOALIDADE_TESTE")
                                        .build()
                                )
                        )
                        .build()
        );
    }
}