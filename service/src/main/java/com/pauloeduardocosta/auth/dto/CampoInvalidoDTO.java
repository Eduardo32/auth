package com.pauloeduardocosta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampoInvalidoDTO {

    private String campo;
    private String erro;
}
