package com.pauloeduardocosta.auth.library;

import com.pauloeduardocosta.auth.library.model.TokenData;
import com.pauloeduardocosta.auth.library.validator.ITokenValidator;
import com.pauloeduardocosta.auth.library.validator.impl.JwtTokenValidator;

public class Teste {
    public static void main(String[] args) {
        ITokenValidator tokenService = new JwtTokenValidator();
        TokenData tokenData = tokenService.extrairDados("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgLSBBdXRoIiwic3ViIjoiMSAxYzc5MTZjYS0yYWZhLTRmYmQtYTE1ZC05NGEzZmUzYWMwNTAiLCJpYXQiOjE2OTY2NDA0OTQsImV4cCI6MTY5NjcyNjg5NCwiTWV1OnRlc3RlIjoidGVzdGUxMjMifQ.-ZJdUUYWxGWL9jpmykfzjzAH3d5zPE2_Qw8MI7AQA_k");
        System.out.println("TESTE");
        System.out.println(tokenData);
    }
}
