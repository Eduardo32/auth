package com.pauloeduardocosta.auth.library.validator.impl;

import com.pauloeduardocosta.auth.library.model.TokenData;
import com.pauloeduardocosta.auth.library.validator.ITokenValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenValidatorTest {

    private final String secret = "teste_secret";
    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgLSBBdXRoIiwic3ViIjoiMSAxYzc5MTZjYS0yYWZhLTRmYmQtYTE1ZC05NGEzZmUzYWMwNTAiLCJpYXQiOjE2OTY2NDA4MDIsIk1ldTp0ZXN0ZSI6InRlc3RlMTIzIn0.Rv-SdzNaMIpxcEE-fAiT5EicC1ucWOQSWgJB3SC79gQ";

    private ITokenValidator tokenValidator;

    @BeforeEach
    void setUp() {
        tokenValidator = new JwtTokenValidator(secret);
    }

    @Test
    @DisplayName("")
    void validarToken() {
        Boolean valido = tokenValidator.validarToken(token);
        assertTrue(valido);
    }

    @Test
    void extrairDados() {
        TokenData tokenData = tokenValidator.extrairDados(token);
        assertEquals(1L, tokenData.getId());
        assertEquals("1c7916ca-2afa-4fbd-a15d-94a3fe3ac050", tokenData.getUuid());
    }
}