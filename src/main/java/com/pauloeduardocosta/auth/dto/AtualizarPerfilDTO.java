package com.pauloeduardocosta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AtualizarPerfilDTO {

    private String nome;
    private List<Long> funcionalidades = new ArrayList<>();
}
