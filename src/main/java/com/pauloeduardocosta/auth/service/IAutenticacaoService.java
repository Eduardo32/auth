package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.dto.LoginDTO;
import com.pauloeduardocosta.auth.dto.TokenDTO;

public interface IAutenticacaoService {

    /**
     * Autenticar o usuario
     *
     * @param loginDTO Dados de autenticação
     * @return Token da API
     */
    TokenDTO autenticar(LoginDTO loginDTO);
}
