package com.pauloeduardocosta.auth.dto;

import com.pauloeduardocosta.auth.entity.Funcionalidade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FuncionalidadeDTO {

    private Long id;
    private String nome;

    public FuncionalidadeDTO(Funcionalidade funcionalidade) {
        this.id = funcionalidade.getId();
        this.nome = funcionalidade.getNome();
    }

    public static List<FuncionalidadeDTO> montarDTO(List<Funcionalidade> funcionalidades) {
        return funcionalidades.stream().map(FuncionalidadeDTO::new).collect(Collectors.toList());
    }
}
