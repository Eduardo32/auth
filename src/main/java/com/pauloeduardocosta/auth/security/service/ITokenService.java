package com.pauloeduardocosta.auth.security.service;

import org.springframework.security.core.Authentication;

public interface ITokenService {

    String gerarToken(Authentication authenticate);

    Boolean isTokenValido(String token);

    Long getIdUsuario(String token);

    Long getUUIDUsuario(String token);
}
