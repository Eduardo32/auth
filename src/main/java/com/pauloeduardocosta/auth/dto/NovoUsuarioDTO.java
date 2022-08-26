package com.pauloeduardocosta.auth.dto;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ToString
public class NovoUsuarioDTO {

    @NotBlank
    @Length(min = 5)
    public String login;

    @NotBlank
    @Length(min = 5)
    public String senha;

    @NotNull
    public List<Long> perfis;
}
