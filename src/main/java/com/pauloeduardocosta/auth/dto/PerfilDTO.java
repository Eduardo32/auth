package com.pauloeduardocosta.auth.dto;

import com.pauloeduardocosta.auth.entity.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PerfilDTO {

    private Long id;
    private String nome;

    public PerfilDTO(Perfil perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
    }

    public static List<PerfilDTO> montarDTO(List<Perfil> perfis) {
        return perfis.stream().map(PerfilDTO::new).collect(Collectors.toList());
    }

    public static Page<PerfilDTO> montarDTO(Page<Perfil> perfis) {
        return perfis.map(PerfilDTO::new);
    }
}
