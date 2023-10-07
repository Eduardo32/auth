package com.pauloeduardocosta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NovoUsuarioDTO {

    @NotBlank
    @Length(min = 5)
    private String login;

    @NotBlank
    @Length(min = 5)
    private String senha;

    @NotNull
    private List<Long> perfis;
}
