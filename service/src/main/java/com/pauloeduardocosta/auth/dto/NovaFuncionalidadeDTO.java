package com.pauloeduardocosta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NovaFuncionalidadeDTO {

    @NotBlank
    @Length(min = 5)
    private String nome;
}
