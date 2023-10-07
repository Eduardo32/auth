package com.pauloeduardocosta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NovoPerfilDTO {

    @NotBlank
    @Length(min = 5)
    private String nome;

    @NotNull
    private List<Long> funcionalidades = new ArrayList<>();
}
