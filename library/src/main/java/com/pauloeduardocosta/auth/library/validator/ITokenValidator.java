package com.pauloeduardocosta.auth.library.validator;

import com.pauloeduardocosta.auth.library.model.TokenData;

public interface ITokenValidator {
    Boolean validarToken(String token);
    TokenData extrairDados(String token);
}
