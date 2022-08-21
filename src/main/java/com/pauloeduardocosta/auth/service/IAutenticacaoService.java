package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.dto.LoginDTO;
import com.pauloeduardocosta.auth.dto.TokenDTO;

public interface IAutenticacaoService {

    TokenDTO autenticar(LoginDTO loginDTO);
}
