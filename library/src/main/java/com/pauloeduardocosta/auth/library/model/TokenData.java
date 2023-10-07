package com.pauloeduardocosta.auth.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TokenData {
    private Long id;
    private String uuid;
}
