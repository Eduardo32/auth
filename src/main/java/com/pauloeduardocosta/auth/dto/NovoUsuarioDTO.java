package com.pauloeduardocosta.auth.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NovoUsuarioDTO {

    public String login;
    public String senha;
    public List<Long> perfis;
}
