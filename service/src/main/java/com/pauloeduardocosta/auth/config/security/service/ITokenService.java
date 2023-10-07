package com.pauloeduardocosta.auth.config.security.service;

import org.springframework.security.core.Authentication;

public interface ITokenService {

    String gerarToken(Authentication authenticate);

    Boolean isTokenValido(String token);

    Long getIdUsuario(String token);

    String getUUIDUsuario(String token);
}
