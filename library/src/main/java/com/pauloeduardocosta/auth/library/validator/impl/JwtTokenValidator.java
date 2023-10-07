package com.pauloeduardocosta.auth.library.validator.impl;

import com.pauloeduardocosta.auth.library.model.TokenData;
import com.pauloeduardocosta.auth.library.validator.ITokenValidator;
import io.jsonwebtoken.*;

import java.security.InvalidParameterException;

public class JwtTokenValidator implements ITokenValidator {

    public static final String KEY_PROPERTY = "AUTH_SECRET";
    //private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenServise.class);
    private final JwtParser jwtParser;

    public JwtTokenValidator() {
        this(System.getenv(KEY_PROPERTY));
    }

    public JwtTokenValidator(final String secret) {
        if(secret == null || secret.trim().isEmpty()) {
            throw new InvalidParameterException("Key must not be empty (set " + KEY_PROPERTY + " environment variable)");
        }
        this.jwtParser = Jwts.parser().setSigningKey(secret);
    }

    @Override
    public Boolean validarToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public TokenData extrairDados(String token) {
        Claims body = getBody(token);
        Long id = Long.parseLong(body.getSubject().split(" ")[0]);
        String uuid = body.getSubject().split(" ")[1];
        return new TokenData(id, uuid);
    }

    private Claims getBody(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
