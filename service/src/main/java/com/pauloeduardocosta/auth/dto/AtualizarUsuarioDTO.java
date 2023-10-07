package com.pauloeduardocosta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AtualizarUsuarioDTO {

    private String login;
    private String senhaAtual;
    private String novaSenha;
    private List<Long> perfis;
}
