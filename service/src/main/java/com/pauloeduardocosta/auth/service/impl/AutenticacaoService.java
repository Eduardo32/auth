package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.LoginDTO;
import com.pauloeduardocosta.auth.dto.TokenDTO;
import com.pauloeduardocosta.auth.config.security.enums.ETipoToken;
import com.pauloeduardocosta.auth.config.security.service.ITokenService;
import com.pauloeduardocosta.auth.service.IAutenticacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements IAutenticacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ITokenService tokenService;

    @Override
    public TokenDTO autenticar(LoginDTO loginDTO) {
        LOGGER.info("Tentando autenticar usuario {}", loginDTO);
        UsernamePasswordAuthenticationToken dadosLogin = loginDTO.converter();
        Authentication authenticate = authenticationManager.authenticate(dadosLogin);
        String token = tokenService.gerarToken(authenticate);

        return new TokenDTO(token, ETipoToken.BEARER.getDescricao());
    }
}
