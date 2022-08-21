package com.pauloeduardocosta.auth.security.service.impl;

import com.pauloeduardocosta.auth.entitie.Usuario;
import com.pauloeduardocosta.auth.security.service.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServise implements ITokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServise.class);

    @Value("${forum.jwt.expiration}")
    private Long expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    @Override
    public String gerarToken(Authentication authenticate) {
        Usuario usuario = (Usuario) authenticate.getPrincipal();
        LOGGER.info("Gerando token para o usuario {}", usuario);
        Date hoje = new Date();
        Date exp = new Date(hoje.getTime() + expiration);
        StringBuilder subject = new StringBuilder()
                .append(usuario.getId() + ";")
                .append(usuario.getUuid());

        return Jwts.builder()
                .setIssuer("API")
                .setSubject(subject.toString())
                .setIssuedAt(hoje)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public Boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getIdUsuario(String token) {
        Claims body = getBody(token);
        return Long.parseLong(body.getSubject().split("|")[0]);
    }

    @Override
    public Long getUUIDUsuario(String token) {
        Claims body = getBody(token);
        return Long.parseLong(body.getSubject().split("|")[1]);
    }

    private Claims getBody(String token) {
        return Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
