package com.pauloeduardocosta.auth.dto;

import com.pauloeduardocosta.auth.entity.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PerfilCompletoDTO {

    private Long id;
    private String nome;
    private List<FuncionalidadeDTO> funcionalidades = new ArrayList<>();

    public PerfilCompletoDTO(Perfil perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
        this.funcionalidades = FuncionalidadeDTO.montarDTO(perfil.getFuncionalidades());
    }

    public static List<PerfilCompletoDTO> montarDTO(List<Perfil> perfis) {
        return perfis.stream().map(PerfilCompletoDTO::new).collect(Collectors.toList());
    }
}
